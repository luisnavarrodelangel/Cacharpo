Oye {
	classvar <version;
	classvar <w;
	classvar <s;
	classvar <allowBroadcast;
	classvar <printO;
	classvar <printIO;
	classvar <toThinkingModule;
	classvar <printI;
	classvar <b;

	*init {
		version = "Jun 2017";
		RGGTRN.turboCargar;
		~now = Main.elapsedTime;
		~openDoc = Document.open("/home/lui/cacharpo.scd");
		~currentDocument = Document.current("/home/lui/cacharpo.scd");
		~state = "deciding";
		//~musicalMode = "waitingForLuisToSound";
		~musicalMode = "waitingForLuisToType";
		~queueOfTypingOp = [];
		~op = nil;
		~contador = 0;
		~lThreeInputs = Dictionary.new;
		~lThreePitchInputs = Dictionary.new;
		~pitchSet = [];//probably is better to initialize it;
		~newPitchSet = [];
		~lOneOutputs  = Dictionary.new;
		~lOneOutputs = ~lOneOutputs.putAll(Dictionary[\kick -> 0, \guira -> 0, \cowbell -> 0, \bajo -> 0, \pad -> 0, \mel -> 0, \loudness -> 0,
			\cMajorScale -> 0]);
		~lThreeInputs = (makeKick: 0, makeGuira: 0, makeTimbalSolo: 0, makeCowBell: 0, makeBajo: 0, makePad: 0,
			makeMelody: 0, loudness: 0, useCmajorScale: 0, useDmajorScale: 0, useEmajorScale:0, useFmajorScale:0, useGmajorScale:0, useAmajorScale: 0, useBmajorScale:0);
		~firstPattern = nil;
		~secondPattern = nil;
		~potentialChangesOnBass = nil;
		~potentialChangesOnPad = nil;
		~potentialChangesOnMelody = nil;
		~firstPatternChosen = nil;
		~secondPatternChosen = nil;
		~instrumentsAlreadyUsedInPad = Array.new;
		~instrumentsAlreadyUsedInPad = ~instrumentsAlreadyUsedInPad.addFirst('padE');
		~durationsAlreadyUsedInPad = Array.new;
		~durationsAlreadyUsedInPad = ~durationsAlreadyUsedInPad.addFirst(0.5);
		~panAlreadyUsedInPad = Array.new;
		~panAlreadyUsedInPad = ~panAlreadyUsedInPad.addFirst(0);
		~instrumentsAlreadyUsedInMel = Array.new;
		~instrumentsAlreadyUsedInMel = ~instrumentsAlreadyUsedInMel.addFirst('melE');
		~durationsAlreadyUsedInMel = Array.new;
		~durationsAlreadyUsedInMel = ~durationsAlreadyUsedInMel.addFirst(0.5);
		~patternsAlreadyUsedInMel = Array.new;
		~patternsAlreadyUsedInMel = ~patternsAlreadyUsedInMel.addFirst(Pseq);
		~relAlreadyUsedInMel = Array.new;
		~relAlreadyUsedInMel = ~relAlreadyUsedInMel.addFirst(0.25);
		~panAlreadyUsedInMel = Array.new;
		~panAlreadyUsedInMel = ~panAlreadyUsedInMel.addFirst(0);
		~durAlreadyUsedInCowbell = Array.new;
		~durAlreadyUsedInCowbell = ~durAlreadyUsedInCowbell.addFirst(1);
		~panAlreadyUsedInCowbell = Array.new;
		~panAlreadyUsedInCowbell = ~panAlreadyUsedInCowbell.addFirst(0);
		~panAlreadyUsedInKick = Array.new;
		~panAlreadyUsedInKick = ~panAlreadyUsedInKick.addFirst(0);
		~panAlreadyUsedInGuira = Array.new;
		~panAlreadyUsedInGuira = ~panAlreadyUsedInGuira.addFirst(0);
		~oldMelPitches = [];
		~presentYourself = false;
		~cMajorScale = [60, 62, 64, 65, 67, 69, 71];
		~dMajorScale = [60, 62, 64, 65, 67, 69, 71]+2;
		~eMajorScale = [60, 62, 64, 65, 67, 69, 71]+4;
		~fMajorScale = [60, 62, 64, 65, 67, 69, 71]+5;
		~gMajorScale = [60, 62, 64, 65, 67, 69, 71]+7;
		~aMajorScale = [60, 62, 64, 65, 67, 69, 71]+9;
		~bMajorScale = [60, 62, 64, 65, 67, 69, 71]+11;
		~currentMelDb = -12;
		~currentBajoDb = -15;
		~currentPadDb = -7;
		~hasKick = false;
		~hasGuira = false;
		~hasCowbell = false;
		~numOfBassChords = 0;
		~numOfPadChords = 0;
		~luisIsSounding = false;
		~loudnessOfinstrumentsSounding = 0;
		//Oye.l2;
		("Oye v.1.0_" ++ version ++ ": initalized").postln;
	}

	*go {arg slidersL3 = false,  slidersL1 = false, netReady = false, port = ~port, printNetInput = ~printNetInput;
		~netReady = netReady; ~slidersL1 = slidersL1; ~port = port; ~printNetInput = false;
		Oye.init;
		Oye.receiveL1OutputsFromNet(~slidersL1); //optimize this
		Oye.analyseBus(~port);
		if (slidersL3, {Oye.sliders;});
		Tdef (\stateMachine, {inf.do {
			~musicalMode.postln;
			Oye.l2;
			Oye.whichState;
			0.1.wait;

		}}).play(TempoClock(tempo: 150/60));
	}

	*typingDetection {
		~luis = Document.current;
		~luis.keyDownAction = { |doc char modifier ascii keycode|
			if (char == $/, {~presentYourself = true});
		}
	}

	*decide {
		// if nothing has been typed yet, and we haven't typed anything, continue to wait
		if(~musicalMode == "waitingForLuisToType", {
			Oye.typingDetection;
			if( ~presentYourself == false, {
				^["wait", Main.elapsedTime + 5.0];
			},{~musicalMode = "presentYourself";});
		});
		if(~musicalMode == "presentYourself", {
			~musicalMode = "waitingForLuisToSound";
			^["presentYourself", "     //////////////cacharpo v.1.0//////////////     " ++ "\n" ];
		});

		// if nothing has been heard yet, and we haven't heard anything, continue to wait
		if(~musicalMode == "waitingForLuisToSound",{
			if(~lThreeInputs[\loudness]<0.05,{
				^["wait", Main.elapsedTime + 5.0];
			},{
				~luisIsSounding = true;
				//select available scales from l3 Inputs..
				~listOfPossiblePitches = Array.new;
				if (~lThreeInputs[\useCmajorScale] == 1, {~listOfPossiblePitches = ~cMajorScale;});
				if (~lThreeInputs[\useDmajorScale] == 1, {~listOfPossiblePitches = ~dMajorScale;});
				if (~lThreeInputs[\useEmajorScale] == 1, {~listOfPossiblePitches = ~eMajorScale;});
				if (~lThreeInputs[\useFmajorScale] == 1, {~listOfPossiblePitches = ~fMajorScale;});
				if (~lThreeInputs[\useGmajorScale] == 1, {~listOfPossiblePitches = ~gMajorScale;});
				if (~lThreeInputs[\useAmajorScale] == 1, {~listOfPossiblePitches = ~aMajorScale;});
				if (~lThreeInputs[\useBmajorScale] == 1, {~listOfPossiblePitches = ~bMajorScale;});
				if ((~lThreeInputs[\useCmajorScale] == 0) && (~lThreeInputs[\useDmajorScale] == 0) && (~lThreeInputs[\useEmajorScale] == 0) &&
					(~lThreeInputs[\useFmajorScale] == 0) && (~lThreeInputs[\useGmajorScale] == 0) && (~lThreeInputs[\useAmajorScale] == 0) &&
					(~lThreeInputs[\useBmajorScale] == 0), {~listOfPossiblePitches = [~cMajorScale, ~dMajorScale, ~cMajorScale, ~eMajorScale, ~fMajorScale, ~gMajorScale,
						~aMajorScale, ~bMajorScale].choose;});
				//pick an initial pitch for that role
				~listOfPossiblePitches.removeAt(6);
				~root = ~listOfPossiblePitches.choose;

				////////////////////////chord possibilities for major scales///////////////////////////////////////
				if ((~root == ~listOfPossiblePitches[0]) || (~root == ~listOfPossiblePitches[3]) || (~root == ~listOfPossiblePitches[4]),
					{~majorChord = [~root, ~root + 4, ~root + 7]; ~chord = ~majorChord});
				if ((~root == ~listOfPossiblePitches[1]) || (~root == ~listOfPossiblePitches[2]) || (~root == ~listOfPossiblePitches[5]),
					{~minorChord = [~root, ~root + 3, ~root + 7]; ~chord = ~minorChord});
				//if ((~root == ~listOfPossiblePitches[6]), {~dimChord = [~root, ~root + 3, ~root + 7]; ~chord = ~dimChord});

				~pitchSet = [~chord[0], ~chord[1], ~chord[2]];

				~musicalMode = "addFirstPattern";});
		});
		//////////////////////////////////////////add first and second patterns//////////////////////////////////////////

		if(~musicalMode == "addFirstPattern", {
			//select available roles from l3 Inputs..
			~listOfPossibilitiesForFirstPattern = Array.new;
			~lThreeAvailInputs = ~lThreeInputs.keysValuesDo{arg key,value; if (value >= 0.8,
				{~listOfPossibilitiesForFirstPattern = ~listOfPossibilitiesForFirstPattern.add(key)}
			)};
			// pick a role for that role...based on coins
			~listOfPossibilitiesForFirstPattern.remove(\loudness); ~listOfPossibilitiesForFirstPattern.remove(\useCmajorScale);
			~listOfPossibilitiesForFirstPattern.remove(\useDmajorScale); ~listOfPossibilitiesForFirstPattern.remove(\useEmajorScale);
			~listOfPossibilitiesForFirstPattern.remove(\useFmajorScale); ~listOfPossibilitiesForFirstPattern.remove(\useGmajorScale);
			~listOfPossibilitiesForFirstPattern.remove(\useAmajorScale); ~listOfPossibilitiesForFirstPattern.remove(\useBmajorScale);
			~listOfPossibilitiesForFirstPattern = ~listOfPossibilitiesForFirstPattern.choose;
			~firstPatternChosen = ~listOfPossibilitiesForFirstPattern.asString;


			//pick a pattern

			if (~firstPatternChosen == "makeMelody", {
				~firstPattern = "Melody";
				~melPitches = ~pitchSet.choose;
				~musicalMode = "waitingToHearTheChanges";
				^["makeMelody", "Mel.toca(inst: 'melE',"  ++ "\n"
					++ "nota: " ++  "Pseq(#[" ++ ~melPitches.asString ++ "], inf)," ++ "\n"
					++ "dur: 0.5," ++ "\n"
					++ "atk: 0.025," ++ "\n"
					++ "rel: 0.25," ++ "\n"
					++ "arpegiar: 0," ++ "\n"
					++ "pan: 0," ++ "\n"
					++ "db: -20" ++ "\n"
					++ ");"  ++ "\n"];
			});

			if (~firstPatternChosen == "makePad", {
				~firstPattern = "Pad";
				~padPitches = [~pitchSet[0],~pitchSet[1],~pitchSet[2]];
				~musicalMode = "waitingToHearTheChanges";
				^["makePad", "Keyboard.toca(inst: 'padE',"  ++ "\n"
					++ "nota: "  ++ "Pseq(#[" ++ "r, " ++ ~padPitches.asString ++ "]-12, inf)," ++ "\n"
					++ "dur: 0.5," ++ "\n"
					++ "atk: 0.05," ++ "\n"
					++ "rel: 0.25," ++ "\n"
					++ "pan: 0," ++ "\n"
					++ "db: -20" ++ "\n"
					++ ");" ++ "\n"];
			});


			if (~firstPatternChosen == "makeBajo", {
				~firstPattern = "Bajo";
				~bassPitches = [~pitchSet[0], ~pitchSet[1], ~pitchSet[2]];
				~numOfBassChords = ~numOfBassChords + 1;
				~musicalMode = "waitingToHearTheChanges";
				^["makeBajo", "Bajo.toca(" ++ "\n" ++ "nota: " ++ "Pseq(" ++ ~bassPitches.asString ++ "-12" ++", inf)," ++ "\n"
					++ "pan: 0," ++ "\n"
					++ "db: -19" ++ "\n"
					++ ");" ++ "\n"];
			});


			if   (~firstPatternChosen == "makeCowBell", {
				~firstPattern = "Ritmo";
				~musicalMode = "waitingToHearTheChanges";
				~hasCowbell = true;
				^["makeCowbell", "Ritmo.toca("++"\n"
					++"campanaDb: -15," ++ "\n"
					++"campanaDur: 1,"++ "\n"
					++"campanaPan: 0,"++ "\n"
					++ "bomboDb: -15,"++ "\n"
					++ "bomboPan: 0,"++ "\n"
					++"guiraDb: -40,"++ "\n"
					++"guiraDur: 0.25,"++ "\n"
					++ "guiraPan: 0,"++ "\n"
					++");"++ "\n"];
			});

			if   (~firstPatternChosen == "makeKick", {
				~firstPattern = "Ritmo";
				~musicalMode = "waitingToHearTheChanges";
				~hasKick = true;
				^["makeKick", "Ritmo.toca("++"\n"
					++"bomboDb: -15," ++"\n"
					++"bomboPan: 0," ++"\n"
					++"campanaDb: -200, " ++"\n"
					++"campanaDur: 1," ++"\n"
					++"campanaPan: 0," ++"\n"
					++"guiraDb: -200," ++"\n"
					++"guiraDur: 0.25," ++"\n"
					++ "guiraPan: 0"++ "\n"
					");"++ "\n"];
			});


			if   (~firstPatternChosen == "makeGuira", {
				~firstPattern = "Ritmo";
				~musicalMode = "waitingToHearTheChanges";
				~hasGuira = true;
				^["makeGuira", "Ritmo.toca("++"\n"
					++"guiraDb: -60," ++"\n"
					++"guiraDur: 0.25,"++"\n"
					++ "guiraPan: 0,"++"\n"
					++"bomboDb: -40," ++"\n"
					++"bomboPan: 0," ++"\n"
					++"campanaDb: -200,"++"\n"
					++"campanaDur: 1," ++"\n"
					++"campanaPan: 0,"++"\n"
					++"\n"++");"];
			});
		});

		if(~musicalMode == "addSecondPattern", {
			//select available roles for 2nd role from l3 Inputs..
			~listOfPossibilitiesForSecondPattern = Array.new;
			~lThreeAvailInputs = ~lThreeInputs.keysValuesDo{arg key,value; if (value >= 0.8,
				{~listOfPossibilitiesForSecondPattern = ~listOfPossibilitiesForSecondPattern.add(key)}
			)};


			// pick a second role for that role...based on coins

			~listOfPossibilitiesForSecondPattern.remove(\loudness); ~listOfPossibilitiesForSecondPattern.remove(\useCmajorScale);
			~listOfPossibilitiesForSecondPattern.remove(\useDmajorScale); ~listOfPossibilitiesForSecondPattern.remove(\useEmajorScale);
			~listOfPossibilitiesForSecondPattern.remove(\useFmajorScale); ~listOfPossibilitiesForSecondPattern.remove(\useGmajorScale);
			~listOfPossibilitiesForSecondPattern.remove(\useAmajorScale); ~listOfPossibilitiesForSecondPattern.remove(\useBmajorScale);
			~listOfPossibilitiesForSecondPattern.remove(~firstPatternChosen.asSymbol);
			if (~listOfPossibilitiesForSecondPattern.notEmpty, {
				~listOfPossibilitiesForSecondPattern = ~listOfPossibilitiesForSecondPattern.choose;
				~secondPatternChosen = ~listOfPossibilitiesForSecondPattern.asString;

				//pick a second pattern
				if (~secondPatternChosen == "makeMelody", {
					~secondPattern = "Melody";
					~melPitches = ~pitchSet.choose;
					~musicalMode = "waitingToHearTheChanges";
					^["makeMelody", "Mel.toca(inst: 'melE',"  ++ "\n"
						++ "nota: " ++  "Pseq(#[" ++ ~melPitches.asString ++ "], inf)," ++ "\n"
						++ "dur: 0.5," ++ "\n"
						++ "atk: 0.025," ++ "\n"
						++ "rel: 0.25," ++ "\n"
						++ "arpegiar: 0," ++ "\n"
						++ "pan: 0," ++ "\n"
						++ "db: -20" ++ "\n"
						++ ");"  ++ "\n"];
				});


				if (~secondPatternChosen == "makePad", {
					~secondPattern = "Pad";
					~padPitches =[~pitchSet[0], ~pitchSet[1], ~pitchSet[2]];
					~musicalMode = "waitingToHearTheChanges";
					^["makePad", "Keyboard.toca(inst: 'padE',"  ++ "\n"
						++ "nota: "  ++ "Pseq(#[" ++ "r, " ++ ~padPitches.asString ++ "]-12, inf)," ++ "\n"
						++ "dur: 0.5," ++ "\n"
						++ "atk: 0.05," ++ "\n"
						++ "rel: 0.25," ++ "\n"
						++ "pan: 0," ++ "\n"
						++ "db: -20" ++ "\n"
						++ ");" ++ "\n"];
				});


				if (~secondPatternChosen == "makeBajo", {
					~secondPattern = "Bajo";
					~bassPitches = [~pitchSet[0], ~pitchSet[1], ~pitchSet[2]];
					~numOfBassChords = ~numOfBassChords + 1;
					~musicalMode = "waitingToHearTheChanges";
					^["makeBajo", "Bajo.toca(" ++ "\n"
						++ "nota: " ++ "Pseq(" ++ ~bassPitches.asString ++ "-12" ++", inf)," ++ "\n"
						++ "pan: 0," ++ "\n"
						++ "db: -19" ++ "\n"
						++ ");"++ "\n"];
				});

				if   (~secondPatternChosen == "makeCowBell", {
					~secondPattern = "Ritmo";
					~musicalMode = "waitingToHearTheChanges";
					~hasCowbell = true;
					^["makeCowbell", "Ritmo.toca("++"\n"
						++"campanaDb: -15," ++ "\n"
						++"campanaDur: 1,"++ "\n"
						++"campanaPan: 0,"++ "\n"
						++ "bomboDb: -15,"++ "\n"
						++ "bomboPan: 0,"++ "\n"
						++"guiraDb: -40,"++ "\n"
						++"guiraDur: 0.25,"++ "\n"
						++ "guiraPan: 0,"++ "\n"
						++");"++ "\n"];
				});

				if   (~secondPatternChosen == "makeKick", {
					~secondPattern = "Ritmo";
					~musicalMode = "waitingToHearTheChanges";
					~hasKick = true;
					^["makeKick", "Ritmo.toca("++"\n"
						++"bomboDb: -15," ++"\n"
						++"bomboPan: 0," ++"\n"
						++"campanaDb: -200, " ++"\n"
						++"campanaDur: 1," ++"\n"
						++"campanaPan: 0," ++"\n"
						++"guiraDb: -200," ++"\n"
						++"guiraDur: 0.25," ++"\n"
						++ "guiraPan: 0"++ "\n"
						++");"++ "\n"];
				});


				if   (~secondPatternChosen == "makeGuira", {
					~secondPattern = "Ritmo";
					~musicalMode = "waitingToHearTheChanges";
					~hasGuira = true;
					^["makeGuira", "Ritmo.toca("++"\n"
						++"guiraDb: -60," ++"\n"
						++"guiraDur: 0.25,"++"\n"
						++ "guiraPan: 0,"++"\n"
						++"bomboDb: -40," ++"\n"
						++"bomboPan: 0," ++"\n"
						++"campanaDb: -200,"++"\n"
						++"campanaDur: 1," ++"\n"
						++"campanaPan: 0,"++"\n"
						++"\n"++");"];
				});
			});
		});

		//////////////////////////////potential changes on melody//////////////////////

		if(~musicalMode == "changeMelody", {
			~potentialChangesOnMelody = ["addMorePitchesToMelody", "tryDifferentInstrumentOnMelody", "tryDifferentDurationOnMelody", "changePseqToPrandOnMelody","changeReleaseOnMel", "tryDifferentPanOnMel"/*, "endingMel"*/].choose;
			["~potentialChangesOnMel: " ++ ~potentialChangesOnMelody].postln;


			if(~potentialChangesOnMelody == "addMorePitchesToMelody", {

				//update available scales from l3 Inputs..
				~listOfPossiblePitches = Array.new;
				if (~lThreeInputs[\useCmajorScale] == 1, {~listOfPossiblePitches = ~cMajorScale;});
				if (~lThreeInputs[\useDmajorScale] == 1, {~listOfPossiblePitches = ~dMajorScale;});
				if (~lThreeInputs[\useEmajorScale] == 1, {~listOfPossiblePitches = ~eMajorScale;});
				if (~lThreeInputs[\useFmajorScale] == 1, {~listOfPossiblePitches = ~fMajorScale;});
				if (~lThreeInputs[\useGmajorScale] == 1, {~listOfPossiblePitches = ~gMajorScale;});
				if (~lThreeInputs[\useAmajorScale] == 1, {~listOfPossiblePitches = ~aMajorScale;});
				if (~lThreeInputs[\useBmajorScale] == 1, {~listOfPossiblePitches = ~bMajorScale;});
				if ((~lThreeInputs[\useCmajorScale] == 0) && (~lThreeInputs[\useDmajorScale] == 0) && (~lThreeInputs[\useEmajorScale] == 0) &&
					(~lThreeInputs[\useFmajorScale] == 0) && (~lThreeInputs[\useGmajorScale] == 0) && (~lThreeInputs[\useAmajorScale] == 0) &&
					(~lThreeInputs[\useBmajorScale] == 0), {~listOfPossiblePitches = [~cMajorScale, ~dMajorScale, ~cMajorScale, ~eMajorScale, ~fMajorScale, ~gMajorScale,
						~aMajorScale, ~bMajorScale].choose;});

				//update pitch for that role
				~root = ~listOfPossiblePitches.choose;

				////////////////////////chord possibilities for major scales///////////////////////////////////////
				if ((~root == ~listOfPossiblePitches[0]) || (~root == ~listOfPossiblePitches[3]) || (~root == ~listOfPossiblePitches[4]),
					{~majorChord = [~root, ~root + 4, ~root + 7]; ~chord = ~majorChord});
				if ((~root == ~listOfPossiblePitches[1]) || (~root == ~listOfPossiblePitches[2]) || (~root == ~listOfPossiblePitches[5]),
					{~minorChord = [~root, ~root + 3, ~root + 7]; ~chord = ~minorChord});
				//if ((~root == ~listOfPossiblePitches[6]), {~dimChord = [~root, ~root + 3, ~root + 7]; ~chord = ~dimChord});

				~pitchSet = [~chord[0], ~chord[1], ~chord[2]];


				~oldMelPitches =  ~oldMelPitches.addFirst(~melPitches);
				~melPitches = ~pitchSet.choose;
				~newMelPitches = ~melPitches ;
				~musicalMode = "waitingToHearTheChanges";
				~potentialChangesOnMelody = nil;
				^["addMorePitchesToMelody", ~newMelPitches.asString, "Mel", ");",~oldMelPitches[0].asString, "nota: "];
			});


			if(~potentialChangesOnMelody == "tryDifferentInstrumentOnMelody", {
				~instrumentsAvailForMel = ['melE', 'mel2E', 'mel3E', 'pluckE', 'pluckTriE']; 		//which instruments are available for lead?
				~instrumentsAvailForMel.remove(~instrumentsAlreadyUsedInMel[0]);
				~newInstrumentForMel = ~instrumentsAvailForMel.choose; //choose from that list the availables one
				//save list of instruments as oldPadInstruments
				~instrumentsAlreadyUsedInMel = ~instrumentsAlreadyUsedInMel.addFirst(~newInstrumentForMel);
				~musicalMode = "waitingToHearTheChanges";
				~potentialChangesOnMelody = nil;
				^["tryDifferentInstrumentOnMelody", "'" ++ ~newInstrumentForMel.asString ++ "'", "Mel", ");", "'" ++ ~instrumentsAlreadyUsedInMel[1].asString ++ "'", "inst: "];
			});


			if(~potentialChangesOnMelody == "tryDifferentDurationOnMelody", {
				~durationsAvailForMel = [0.25, 0.5]; 		//which durations are commonly used in mel?
				~durationsAvailForMel.remove(~durationsAlreadyUsedInMel[0]);
				~newDurationsForMel = ~durationsAvailForMel.choose; //choose from that list the available ones
				//save list of instruments as oldPadInstruments
				~durationsAlreadyUsedInMel = ~durationsAlreadyUsedInMel.addFirst(~newDurationsForMel);
				~musicalMode = "waitingToHearTheChanges";
				~potentialChangesOnMelody = nil;
				^["tryDifferentDurationOnMelody", ~newDurationsForMel.asString, "Mel", ");", ~durationsAlreadyUsedInMel[1].asString, "dur: "];
			});

			if(~potentialChangesOnMelody == "changePseqToPrandOnMelody", {
				~patternAvailForMel = [Pseq, Prand]; 		//which durations are commonly used in Pad?
				~patternAvailForMel.remove(~patternsAlreadyUsedInMel[0]);
				~newPatternForMel = ~patternAvailForMel.choose; //choose from that list the available ones
				//save list of instruments as oldPadInstruments
				~patternsAlreadyUsedInMel = ~patternsAlreadyUsedInMel.addFirst(~newPatternForMel);
				~musicalMode = "waitingToHearTheChanges";
				~potentialChangesOnMelody = nil;
				^["changePseqToPrandOnMelody", ~newPatternForMel.asString, "Mel", ");", ~patternsAlreadyUsedInMel[1].asString, "nota: "];
			});

			if(~potentialChangesOnMelody == "changeReleaseOnMel", {
				~relAvailForMel = [0.25, 0.5, 1, 2, 4]; //which durations are available for Mel?
				~relAvailForMel.remove(~relAlreadyUsedInMel[0]);
				~newRelForMel = ~relAvailForMel.choose; //choose from that list the available ones
				//save list of old selected releases
				~relAlreadyUsedInMel = ~relAlreadyUsedInMel.addFirst(~newRelForMel);
				~musicalMode = "waitingToHearTheChanges";
				~potentialChangesOnMelody = nil;
				^["changeReleaseOnMel", ~newRelForMel.asString, "Mel", ");", ~relAlreadyUsedInMel[1].asString, "rel"];
			});

			if(~potentialChangesOnMelody == "tryDifferentPanOnMel", {
				~panAvailForMel = [-1, 0, 1]; //which durations are available for Mel?
				~panAvailForMel.remove(~panAlreadyUsedInMel[0]);
				~newPanForMel = ~panAvailForMel.choose; //choose from that list the available ones
				//save list of old selected releases
				~panAlreadyUsedInMel = ~panAlreadyUsedInMel.addFirst(~newPanForMel);
				~musicalMode = "waitingToHearTheChanges";
				~potentialChangesOnMelody = nil;
				^["tryDifferentPanOnMel", ~newPanForMel.asString, "Mel", ");", ~panAlreadyUsedInMel[1].asString, "pan"];
			});

			if(~potentialChangesOnMelody == "endingMel", {
				if((~lThreeInputs[\loudness] <= 0.25) && (~luisIsSounding == true) && (~firstPattern != nil) && (~secondPattern != nil), {
					~pastMelDb = ~currentMelDb;
					~currentMelDb = ~currentMelDb - 1;
					~newMelDb = ~currentMelDb;
					~musicalMode = "waitingToHearTheChanges";
					^["endingMel", ~pastMelDb.asString, "Mel", ");", ~newMelDb.asString, "db:"];
				}, {~musicalMode = "waitingToHearTheChanges";});
			}, {~musicalMode = "waitingToHearTheChanges";
			});
		});


		//////////////////////////////potential changes for Bass//////////////////////

		if (~musicalMode == "changeBajo", {
			~potentialChangesOnBass = ["addMorePitchesToBass", "holdBass", "endingBass"].choose;
			["~potentialChangesOnBajo: " ++ ~potentialChangesOnBass].postln;

			if(~potentialChangesOnBass == "addMorePitchesToBass", {
				if (Oye.sensor[2] < 1, {
					//update available scales from l3 Inputs..
					~listOfPossiblePitches = Array.new;
					if (~lThreeInputs[\useCmajorScale] == 1, {~listOfPossiblePitches = ~cMajorScale;});
					if (~lThreeInputs[\useDmajorScale] == 1, {~listOfPossiblePitches = ~dMajorScale;});
					if (~lThreeInputs[\useEmajorScale] == 1, {~listOfPossiblePitches = ~eMajorScale;});
					if (~lThreeInputs[\useFmajorScale] == 1, {~listOfPossiblePitches = ~fMajorScale;});
					if (~lThreeInputs[\useGmajorScale] == 1, {~listOfPossiblePitches = ~gMajorScale;});
					if (~lThreeInputs[\useAmajorScale] == 1, {~listOfPossiblePitches = ~aMajorScale;});
					if (~lThreeInputs[\useBmajorScale] == 1, {~listOfPossiblePitches = ~bMajorScale;});
					if ((~lThreeInputs[\useCmajorScale] == 0) && (~lThreeInputs[\useDmajorScale] == 0) && (~lThreeInputs[\useEmajorScale] == 0) &&
						(~lThreeInputs[\useFmajorScale] == 0) && (~lThreeInputs[\useGmajorScale] == 0) && (~lThreeInputs[\useAmajorScale] == 0) &&
						(~lThreeInputs[\useBmajorScale] == 0), {~listOfPossiblePitches = [~cMajorScale, ~dMajorScale, ~cMajorScale, ~eMajorScale, ~fMajorScale, ~gMajorScale,
							~aMajorScale, ~bMajorScale].choose;});

					//update pitch for that role
					~root = ~listOfPossiblePitches.choose;

					////////////////////////chord possibilities for major scales///////////////////////////////////////
					if ((~root == ~listOfPossiblePitches[0]) || (~root == ~listOfPossiblePitches[3]) || (~root == ~listOfPossiblePitches[4]),
						{~majorChord = [~root, ~root + 4, ~root + 7]; ~chord = ~majorChord});
					if ((~root == ~listOfPossiblePitches[1]) || (~root == ~listOfPossiblePitches[2]) || (~root == ~listOfPossiblePitches[5]),
						{~minorChord = [~root, ~root + 3, ~root + 7]; ~chord = ~minorChord});
					//if ((~root == ~listOfPossiblePitches[6]), {~dimChord = [~root, ~root + 3, ~root + 7]; ~chord = ~dimChord});

					~newPitchSet = [~chord[0], ~chord[1], ~chord[2]];

					if (~newPitchSet.notEmpty, {
						if (~bassPitches != ~newPitchSet, {
							~oldBassPitches = [~bassPitches[0], ~bassPitches[1], ~bassPitches[2]];
							~bassPitches = ~newPitchSet;
							~newBassPitches = ~bassPitches ;

							if (~newBassPitches.notEmpty, {
								~moreBassPitches = [~newBassPitches[0], ~newBassPitches[1], ~newBassPitches[2]];
								~newBassPitches = ~newBassPitches.drop(3);
								~musicalMode = "waitingToHearTheChanges";
								~numOfBassChords = ~numOfBassChords + 1;
								~potentialChangesOnBass = nil;
								^["addMorePitchesToBass", ~moreBassPitches.asString, "Bajo", ");",~oldBassPitches.asString, "nota: " ];
							});
						}, { ~musicalMode = "waitingToHearTheChanges";
							~potentialChangesOnBass = nil;

						});
					}, { ~musicalMode = "waitingToHearTheChanges";
						~potentialChangesOnBass = nil;
					});
				}, { ~musicalMode = "waitingToHearTheChanges";
					~potentialChangesOnBass = nil;
				});
			});


			if(~potentialChangesOnBass == "holdBass", {
				if (~soloTimbal.notNil, {
					~musicalMode = "waitingToHearTheChanges";
					^["holdBass", "toca", "Bajo", ");","detener"];
				}, { ~musicalMode = "waitingToHearTheChanges";
					~potentialChangesOnBass = nil;
				});
			}, { ~musicalMode = "waitingToHearTheChanges";
				~potentialChangesOnBass = nil;
			});


			if(~potentialChangesOnBass == "endingBass", {
				if((~lThreeInputs[\loudness] <= 0.25) && (~luisIsSounding == true) && (~firstPattern != nil) && (~secondPattern != nil), {
					~pastBajoDb = ~currentBajoDb;
					~currentBajoDb = ~currentBajoDb - 1;
					~newBajoDb = ~pastBajoDb;
					~musicalMode = "waitingToHearTheChanges";
					^["endingBass", ~pastBajoDb.asString, "Bajo", ");", ~newBajoDb.asString, "db:"];
				}, {~musicalMode = "waitingToHearTheChanges";});
			}, {~musicalMode = "waitingToHearTheChanges";
			});
		});

		//////////////////////////////potential changes on Pad//////////////////////
		if (~musicalMode == "changePad", {
			~potentialChangesOnPad = ["tryDifferentInstrumentOnPad", "addMoreChordsToPad",/* "tryDifferentDurationOnPad",*/ "tryDifferentPanOnPad", "endingPad"].choose;
			["~potentialChangesOnPad: " ++ ~potentialChangesOnPad].postln;


			if(~potentialChangesOnPad == "addMoreChordsToPad", {
				if (Oye.sensor[3] < 1 , {

					//update available scales from l3 Inputs..
					~listOfPossiblePitches = Array.new;
					if (~lThreeInputs[\useCmajorScale] == 1, {~listOfPossiblePitches = ~cMajorScale;});
					if (~lThreeInputs[\useDmajorScale] == 1, {~listOfPossiblePitches = ~dMajorScale;});
					if (~lThreeInputs[\useEmajorScale] == 1, {~listOfPossiblePitches = ~eMajorScale;});
					if (~lThreeInputs[\useFmajorScale] == 1, {~listOfPossiblePitches = ~fMajorScale;});
					if (~lThreeInputs[\useGmajorScale] == 1, {~listOfPossiblePitches = ~gMajorScale;});
					if (~lThreeInputs[\useAmajorScale] == 1, {~listOfPossiblePitches = ~aMajorScale;});
					if (~lThreeInputs[\useBmajorScale] == 1, {~listOfPossiblePitches = ~bMajorScale;});
					if ((~lThreeInputs[\useCmajorScale] == 0) && (~lThreeInputs[\useDmajorScale] == 0) && (~lThreeInputs[\useEmajorScale] == 0) &&
						(~lThreeInputs[\useFmajorScale] == 0) && (~lThreeInputs[\useGmajorScale] == 0) && (~lThreeInputs[\useAmajorScale] == 0) &&
						(~lThreeInputs[\useBmajorScale] == 0), {~listOfPossiblePitches = [~cMajorScale, ~dMajorScale, ~cMajorScale, ~eMajorScale, ~fMajorScale, ~gMajorScale,
							~aMajorScale, ~bMajorScale].choose;});

					//update pitch for that role
					~root = ~listOfPossiblePitches.choose;

					////////////////////////chord possibilities for major scales///////////////////////////////////////
					if ((~root == ~listOfPossiblePitches[0]) || (~root == ~listOfPossiblePitches[3]) || (~root == ~listOfPossiblePitches[4]),
						{~majorChord = [~root, ~root + 4, ~root + 7]; ~chord = ~majorChord});
					if ((~root == ~listOfPossiblePitches[1]) || (~root == ~listOfPossiblePitches[2]) || (~root == ~listOfPossiblePitches[5]),
						{~minorChord = [~root, ~root + 3, ~root + 7]; ~chord = ~minorChord});
					//if ((~root == ~listOfPossiblePitches[6]), {~dimChord = [~root, ~root + 3, ~root + 7]; ~chord = ~dimChord});

					~newPitchSet = [~chord[0], ~chord[1], ~chord[2]];


					if (~newPitchSet.notEmpty, {
						if (~padPitches != ~newPitchSet, {
							~oldPadChordPitches = [~padPitches[0], ~padPitches[1], ~padPitches[2]];
							~padPitches = ~newPitchSet;
							~newPadPitches = ~padPitches;
							if (~newPadPitches.notEmpty, {
								~moreChordsPitches = [~newPadPitches[0],~newPadPitches[1], ~newPadPitches[2]];
								~newPadPitches = ~newPadPitches.drop(3);
								~musicalMode = "waitingToHearTheChanges";
								~numOfPadChords = ~numOfPadChords + 1;
								~potentialChangesOnPad = nil;
								^["addMoreChordsToPad", ~moreChordsPitches.asString, "Keyboard", ");", ~oldPadChordPitches.asString, "nota: "];
							});
						}, { ~musicalMode = "waitingToHearTheChanges";
							~potentialChangesOnPad = nil;
						});
					}, { ~musicalMode = "waitingToHearTheChanges";
						~potentialChangesOnPad = nil;
					});
				}, {~oldPadChordPitches
					^["deleteChordsFromPad", ~moreChordsPitches.asString, "Keyboard", ");", ~oldPadChordPitches.asString, "nota: "];});
			});

			if(~potentialChangesOnPad == "tryDifferentInstrumentOnPad", {
				~instrumentsAvailForPad = ['padE', 'pad2E', ]; 		//which instruments are for Pad?
				~instrumentsAvailForPad.remove(~instrumentsAlreadyUsedInPad[0]);
				~newInstrumentForPad = ~instrumentsAvailForPad.choose; //choose from that list the availables one
				//save list of instruments as oldPadInstruments
				~instrumentsAlreadyUsedInPad = ~instrumentsAlreadyUsedInPad.addFirst(~newInstrumentForPad);
				~musicalMode = "waitingToHearTheChanges";
				~potentialChangesOnPad = nil;
				^["tryDifferentInstrumentOnPad", "'" ++ ~newInstrumentForPad.asString ++ "'", "Keyboard", ");", "'" ++ ~instrumentsAlreadyUsedInPad[1].asString ++ "'", "inst: "];
			});

			if(~potentialChangesOnPad == "tryDifferentDurationOnPad", {
				~durationsAvailForPad = [0.5, 1]; 		//which durations are commonly used in Pad?
				~durationsAvailForPad.remove(~durationsAlreadyUsedInPad[0]);
				~newDurationsForPad = ~durationsAvailForPad.choose; //choose from that list the available ones
				//save list of instruments as oldPadInstruments
				~durationsAlreadyUsedInPad = ~durationsAlreadyUsedInPad.addFirst(~newDurationsForPad);
				~musicalMode = "waitingToHearTheChanges";
				~potentialChangesOnPad = nil;
				^["tryDifferentDurationOnPad", ~newDurationsForPad.asString, "Keyboard", ");", ~durationsAlreadyUsedInPad[1].asString, "dur: "];
			});

			if(~potentialChangesOnPad == "tryDifferentPanOnPad", {
				~panAvailForPad = [-1, 0, 1]; 		//which panning should we use in Pad?
				~panAvailForPad.remove(~panAlreadyUsedInPad[0]);
				~newPanForPad = ~panAvailForPad.choose; //choose from that list the available ones
				//save list of instruments as oldPadInstruments
				~panAlreadyUsedInPad = ~panAlreadyUsedInPad.addFirst(~newPanForPad);
				~musicalMode = "waitingToHearTheChanges";
				~potentialChangesOnPad = nil;
				^["tryDifferentPanOnPad", ~newPanForPad.asString, "Keyboard", ");", ~panAlreadyUsedInPad[1].asString, "pan: "];
			});

			if(~potentialChangesOnPad == "endingPad", {
				if((~lThreeInputs[\loudness] <= 0.25) && (~luisIsSounding == true) && (~firstPattern != nil) && (~secondPattern != nil), {
					~pastPadDb = ~currentPadDb;
					~currentPadDb = ~currentPadDb - 1;
					~newPadDb = ~pastPadDb;
					~musicalMode = "waitingToHearTheChanges";
					^["endingPad", ~pastPadDb.asString, "Keyboard", ");", ~newPadDb.asString, "db:"];
				}, {~musicalMode = "waitingToHearTheChanges";});
			});
		});

		//////////////////////////////potential changes for Ritmo///////////////////////////////

		if (~musicalMode == "changeRitmo", {
			~potentialChangesOnRitmo = ["addCowbell", "addKick", "addGuira", "tryDifferentPanOnGuira", "tryDifferentPanOnKick", "tryDifferentPanOnCowbell", /*"tryDifferentDurOnKick", "tryDifferentDurOnGuira",*/ "tryDifferentDurOnCowbell" /*, "makeKickFreqArray"*/].choose;
			["~potentialChangesOnRitmo: " ++ ~potentialChangesOnRitmo].postln;

			if(~potentialChangesOnRitmo == "addKick", {
				if (~hasKick == false, {
					~dbAlreadyUsedInKick = -40;
					~kick = true;
					^["addKick", "0.10", "Ritmo", ");", ~dbAlreadyUsedInKick.asString, "bomboDb: "];
				});
			});

			if(~potentialChangesOnRitmo == "addCowbell", {
				if (~hasKick == false, {
					~dbAlreadyUsedInCowbell = -60;
					~cowbell = true;
					^["addCowbell", "0.10", "Ritmo", ");", ~dbAlreadyUsedInCowbell.asString, "campanaDb: "];
				});
			});

			if(~potentialChangesOnRitmo == "addGuira", {
				if (~hasGuira == false, {
					~dbAlreadyUsedInGuira = -60;
					~guira = true;
					^["addGuira", "0.10", "Ritmo", ");", ~dbAlreadyUsedInGuira.asString, "guiraDb: "];
				});
			});

			if(~potentialChangesOnRitmo == "tryDifferentPanOnKick", {
				if (~kick == true, {
					~panAvailForKick = [-1, 0, 1]; 		//which panning should we use in Pad?
					~panAvailForKick.remove(~panAlreadyUsedInKick[0]);
					~newPanForKick = ~panAvailForKick.choose; //choose from that list the available ones
					//save list of instruments as oldPadInstruments
					~panAlreadyUsedInKick = ~panAlreadyUsedInKick.addFirst(~newPanForKick);
					~musicalMode = "waitingToHearTheChanges";
					~potentialChangesOnRitmo = nil;
					^["tryDifferentPanOnKick", ~newPanForKick.asString, "Ritmo", ");", ~panAlreadyUsedInKick[1].asString, "bomboPan: "];
				}, {~musicalMode == "changeRitmo"});
			});

			/*
			if(~potentialChangesOnRitmo == "tryDifferentDurOnKick", {
			if (~kick == true, {
			~durAvailForKick = [0.5, 1]; 		//which panning should we use in Pad?
			~durAvailForKick.remove(~durAlreadyUsedInKick[0]);
			~newDurForKick = ~durAvailForKick.choose; //choose from that list the available ones
			//save list of instruments as oldPadInstruments
			~durAlreadyUsedInKick = ~durAlreadyUsedInKick.addFirst(~newDurForKick);
			~musicalMode = "waitingToHearTheChanges";
			~potentialChangesOnRitmo = nil;
			^["tryDifferentDurOnKick", ~newDurForKick.asString, "Ritmo", ");", ~durAlreadyUsedInKick[1].asString, "bomboPan: "];
			}, {~musicalMode == "changeRitmo"});
			});*/

			if(~potentialChangesOnRitmo == "tryDifferentPanOnGuira", {
				if (~hasGuira == true, {
					~panAvailForGuira = [-1, 0, 1]; 		//which panning should we use in Pad?
					~panAvailForGuira.remove(~panAlreadyUsedInGuira[0]);
					~newPanForGuira = ~panAvailForGuira.choose; //choose from that list the available ones
					//save list of instruments as oldPadInstruments
					~panAlreadyUsedInGuira = ~panAlreadyUsedInGuira.addFirst(~newPanForGuira);
					~musicalMode = "waitingToHearTheChanges";
					~potentialChangesOnRitmo = nil;
					^["tryDifferentPanOnGuira", ~newPanForGuira.asString, "Ritmo", ");", ~panAlreadyUsedInGuira[1].asString, "guiraPan: "];
				}, {~musicalMode == "changeRitmo"});
			});

			/*		if(~potentialChangesOnRitmo == "tryDifferentDurOnGuira", {
			if (~guira == true, {
			~durAvailForGuira = [0.5, 1]; 		//which panning should we use in Pad?
			~durAvailForGuira.remove(~panAlreadyUsedInGuira[0]);
			~newDurForGuira = ~durAvailForGuira.choose; //choose from that list the available ones
			//save list of instruments as oldPadInstruments
			~durAlreadyUsedInGuira = ~durAlreadyUsedInGuira.addFirst(~newDurForGuira);
			~musicalMode = "waitingToHearTheChanges";
			~potentialChangesOnRitmo = nil;
			^["tryDifferentDurOnGuira", ~newDurForGuira.asString, "Ritmo", ");", ~durAlreadyUsedInGuira[1].asString, "guiraPan: "];
			}, {~musicalMode == "changeRitmo"});
			});*/


			if(~potentialChangesOnRitmo == "tryDifferentPanOnCowbell", {
				if (~hasCowbell == true, {
					~panAvailForCowbell = [-1, 0, 1]; 		//which panning should we use in Pad?
					~panAvailForCowbell.remove(~panAlreadyUsedInCowbell[0]);
					~newPanForCowbell= ~panAvailForCowbell.choose; //choose from that list the available ones
					//save list of instruments as oldPadInstruments
					~panAlreadyUsedInCowbell = ~panAlreadyUsedInCowbell.addFirst(~newPanForCowbell);
					~musicalMode = "waitingToHearTheChanges";
					~potentialChangesOnRitmo = nil;
					^["tryDifferentPanOnCowbell", ~newPanForCowbell.asString, "Ritmo", ");", ~panAlreadyUsedInCowbell[1].asString, "guiraPan: "];
				}, {~musicalMode == "changeRitmo"});
			});


			if(~potentialChangesOnRitmo == "tryDifferentPanOnCowbell", {
				if (~hasCowbell == true, {
					~durAvailForCowbell = [0.5, 1]; 		//which panning should we use in Pad?
					~durAvailForCowbell.remove(~durAlreadyUsedInCowbell[0]);
					~newDurForCowbell= ~durAvailForCowbell.choose; //choose from that list the available ones
					//save list of instruments as oldPadInstruments
					~durAlreadyUsedInCowbell = ~durAlreadyUsedInCowbell.addFirst(~newDurForCowbell);
					~musicalMode = "waitingToHearTheChanges";
					~potentialChangesOnRitmo = nil;
					^["tryDifferentPanOnCowbell", ~newDurForCowbell.asString, "Ritmo", ");", ~durAlreadyUsedInCowbell[1].asString, "guiraPan: "];
				}, {~musicalMode == "changeRitmo"});
			});
		});


		/////////////////////////////waiting after each decision//////////////////////
		if (~musicalMode == "waitingToHearTheChanges", {
			if (~secondPattern.isNil, {
				~musicalMode = ["addSecondPattern", "change" ++ ~firstPattern].choose;
			}, {~musicalMode = ["change" ++ ~firstPattern, "change" ++ ~secondPattern].choose;
			});

			^["wait", Main.elapsedTime + 10.rand];
		});
	}
	*sensor {
		~maxDensity = 0.75;
		~energy = 0;
		//	~density = ~lOneOutputs[\kick] + ~lOneOutputs[\guira] + ~lOneOutputs[\pad] + ~lOneOutputs[\cowbell] + ~lOneOutputs[\mel] + ~lOneOutputs[\bajo];
		//		~density = ~density/6;
		~loudnessL3 = ~loudnessL3;
		~density = 0;
		~numOfBassChords = ~numOfBassChords;
		~numOfPadChords = ~numOfPadChords;


		^[~loudnessL3, ~energy, ~numOfBassChords, ~numOfPadChords ];
	}

	*translateDecision { // take a representation of decision, maybe do stuff, always returns state to transition to (or old state if no change)
		if(~decision[0] == "wait", {
			~waitEnd = ~decision[1];//~action[1];
			^"waiting";
		});
		///inserting initial stuff
		if (~decision[0] == "presentYourself", {
			Oye.addInsertOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});
		if (~decision[0] == "makeBajo", {
			Oye.addInsertOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if (~decision[0] == "makePad", {
			Oye.addInsertOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if (~decision[0] == "makeCowbell", {
			Oye.addInsertOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if (~decision[0] == "makeKick", {
			Oye.addInsertOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});


		if (~decision[0] == "makeGuira", {
			Oye.addInsertOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if (~decision[0] == "makeMelody", {
			Oye.addInsertOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});
		////////////////////////////////changes on melody//////////////////////
		//inserting new chords on melody
		if(~decision[0] == "addMorePitchesToMelody", {
			"addMorePitchesToMelody".postln;
			Oye.addUpdateThingsToFindOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4], ~decision[5]);
			Oye.addLocateEndingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addLocateVarOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addLocateThingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addInsertCommaAfterSetOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addInsertNewStuffInPreviousStatementOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addAdjustFormatOfTextOpToueue(~decision[1], ~decision[2]);
			Oye.addEvaluateOpToQueue;

			^"typing";
		});

		//try different instrument on Mel

		if(~decision[0] == "tryDifferentInstrumentOnMelody", {
			"tryDifferentInstrumentOnMelody".postln;
			Oye.addUpdateThingsToFindOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4], ~decision[5]);
			Oye.addLocateEndingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addLocateVarOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addLocateThingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addDeleteOpToQueue(~decision[4]);
			Oye.addReplaceTextOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		//try different dur on Mel

		if(~decision[0] == "tryDifferentDurationOnMelody", {
			"tryDifferentDurationOnMelody".postln;
			Oye.addUpdateThingsToFindOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4], ~decision[5]);
			Oye.addLocateEndingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addLocateVarOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addLocateThingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addDeleteOpToQueue(~decision[4]);
			Oye.addReplaceTextOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;

			^"typing";
		});


		//change Pseq to Prand on Mel

		if(~decision[0] == "changePseqToPrandOnMelody", {
			"changePseqToPrandOnMelody".postln;
			Oye.addUpdateThingsToFindOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4], ~decision[5]);
			Oye.addLocateEndingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addLocateVarOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addLocateThingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addDeleteOpToQueue(~decision[4]);
			Oye.addReplaceTextOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;

			^"typing";
		});

		if(~decision[0] == "changeReleaseOnMel", {
			"changeReleaseOnMel".postln;
			Oye.addUpdateThingsToFindOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4], ~decision[5]);
			Oye.addLocateEndingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addLocateVarOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addLocateThingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addDeleteOpToQueue(~decision[4]);
			Oye.addReplaceTextOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if(~decision[0] == "tryDifferentPanOnMel", {
			"tryDifferentPanOnMel".postln;
			Oye.addUpdateThingsToFindOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4], ~decision[5]);
			Oye.addLocateEndingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addLocateVarOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addLocateThingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addDeleteOpToQueue(~decision[4]);
			Oye.addReplaceTextOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		/////////////ending mel //////////////

		if(~decision[0] == "endingMel", {
			"endingMel".postln;
			Oye.addUpdateThingsToFindOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4], ~decision[5]);
			Oye.addLocateEndingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addLocateVarOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addLocateThingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addDeleteOpToQueue(~decision[4]);
			Oye.addReplaceTextOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		///////////////////////////////////changes on Bass///////////////////////////////////////////
		//inserting stuff on already written stuff
		if(~decision[0] == "addMorePitchesToBass", {
			"addMorePitchesToBass".postln;
			Oye.addUpdateThingsToFindOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4], ~decision[5]);
			Oye.addLocateEndingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addLocateVarOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addLocateThingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addInsertCommaAfterSetOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addInsertNewStuffInPreviousStatementOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addAdjustFormatOfTextOpToueue(~decision[1], ~decision[2]);
			Oye.addEvaluateOpToQueue;

			^"typing";
		});

		//stoping and playing Bass when timbal Solo is on (at some section of the solo)

		if(~decision[0] == "holdBass", {
			"holdBass".postln;
			Oye.addUpdateThingsToFindOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4], ~decision[5]);
			Oye.addLocateEndingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addLocateVarOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addLocateThingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addInsertCommaAfterSetOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addInsertNewStuffInPreviousStatementOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addAdjustFormatOfTextOpToueue(~decision[1], ~decision[2]);
			Oye.addEvaluateOpToQueue;

			^"typing";
		});

		////replacing stuff

		if(~decision[0] == "replacingStuffOnBass", {
			// queue up a bunch of typing operations (insert, replace, delete, move cursor, evaluation, etc)
			Oye.addDeleteOpToQueue(~decision[2]);
			Oye.addReplaceTextOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if(~decision[0] == "endingBajo", {
			"endingBajo".postln;
			Oye.addUpdateThingsToFindOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4], ~decision[5]);
			Oye.addLocateEndingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addLocateVarOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addLocateThingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addDeleteOpToQueue(~decision[4]);
			Oye.addReplaceTextOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});
		///////////////////////////////////changes on Pad///////////////////////////////////////////

		//inserting more chords
		if(~decision[0] == "addMoreChordsToPad", {
			Oye.addUpdateThingsToFindOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4], ~decision[5]);
			Oye.addLocateEndingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addLocateVarOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addLocateThingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addInsertCommaAfterSetOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addInsertNewStuffInPreviousStatementOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addAdjustFormatOfTextOpToueue(~decision[1], ~decision[2]);
			Oye.addEvaluateOpToQueue;

			^"typing";
		});

		//try different instrument on Pad
		if(~decision[0] == "tryDifferentInstrumentOnPad", {
			"tryDifferentInstrumentOnPad".postln;
			Oye.addUpdateThingsToFindOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4], ~decision[5]);
			Oye.addLocateEndingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addLocateVarOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addLocateThingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addDeleteOpToQueue(~decision[4]);
			Oye.addReplaceTextOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";

		});

		//try different durations on Pad
		if(~decision[0] == "tryDifferentDurationOnPad", {
			"tryDifferentDurationOnPad".postln;
			Oye.addUpdateThingsToFindOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4], ~decision[5]);
			Oye.addLocateEndingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addLocateVarOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addLocateThingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addDeleteOpToQueue(~decision[4]);
			Oye.addReplaceTextOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";

		});

		//try different durations on Pad
		if(~decision[0] == "tryDifferentPanOnPad", {
			"tryDifferentPanOnPad".postln;
			Oye.addUpdateThingsToFindOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4], ~decision[5]);
			Oye.addLocateEndingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addLocateVarOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addLocateThingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addDeleteOpToQueue(~decision[4]);
			Oye.addReplaceTextOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";

		});

		if(~decision[0] == "endingPad", {
			"endingPad".postln;
			Oye.addUpdateThingsToFindOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4], ~decision[5]);
			Oye.addLocateEndingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addLocateVarOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4]);
			Oye.addLocateThingOpToQueue(~decision[1], ~decision[2], ~decision[3], ~decision[4] );
			Oye.addDeleteOpToQueue(~decision[4]);
			Oye.addReplaceTextOpToQueue(~decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});



	}

	*addInsertOpToQueue {arg decision;
		~queueOfTypingOp = ~queueOfTypingOp.addFirst(["insert", decision, decision]);
	}

	*addInsertNewStuffInPreviousStatementOpToQueue {arg whatToAdd, inWhichInstrument,  whereIsLastParenthesisOfThatInstrument, afterWhatThing;
		~queueOfTypingOp = ~queueOfTypingOp.addFirst(["insertNewStuffInPreviousStatement", whatToAdd, inWhichInstrument,  whereIsLastParenthesisOfThatInstrument, afterWhatThing]);
	}

	*addInsertCommaAfterSetOpToQueue {arg whatToAdd, inWhichInstrument,  whereIsLastParenthesisOfThatInstrument, afterWhatThing;
		~queueOfTypingOp = ~queueOfTypingOp.addFirst(["insertCommaAfterSet", whatToAdd, inWhichInstrument, whereIsLastParenthesisOfThatInstrument,afterWhatThing]);
	}

	*addLocateVarOpToQueue {arg whatToAdd, inWhichInstrument,  whereIsLastParenthesisOfThatInstrument, afterWhatThing;
		~queueOfTypingOp = ~queueOfTypingOp.addFirst(["locateVar", whatToAdd, inWhichInstrument, whereIsLastParenthesisOfThatInstrument,afterWhatThing]);
	}

	*addLocateThingOpToQueue {arg whatToAdd, inWhichInstrument,  whereIsLastParenthesisOfThatInstrument, afterWhatThing;
		~queueOfTypingOp = ~queueOfTypingOp.addFirst(["locateThing", whatToAdd, inWhichInstrument, whereIsLastParenthesisOfThatInstrument,afterWhatThing]);
	}

	*addLocateEndingOpToQueue {arg whatToAdd, inWhichInstrument,  whereIsLastParenthesisOfThatInstrument, afterWhatThing;
		~queueOfTypingOp = ~queueOfTypingOp.addFirst(["locateEnding", whatToAdd, inWhichInstrument, whereIsLastParenthesisOfThatInstrument,afterWhatThing]);
	}

	*addUpdateThingsToFindOpToQueue {arg whatToAdd, inWhichInstrument,  whereIsLastParenthesisOfThatInstrument, afterWhatThing, afterWhatVar;
		~queueOfTypingOp = ~queueOfTypingOp.addFirst(["updateThingsToFind", whatToAdd, inWhichInstrument, whereIsLastParenthesisOfThatInstrument,afterWhatThing, afterWhatVar]);
	}

	*addAdjustFormatOfTextOpToueue {arg stuff;
		~queueOfTypingOp = ~queueOfTypingOp.addFirst(["adjustFormatOfText", stuff, stuff ]);
	}

	*addEvaluateOpToQueue {
		~queueOfTypingOp = ~queueOfTypingOp.addFirst(["evaluate"]);
	}

	*addReplaceTextOpToQueue {arg decision;
		~queueOfTypingOp = ~queueOfTypingOp.addFirst(["replace", decision]);
	}


	*addDeleteOpToQueue {arg decision;
		~queueOfTypingOp = ~queueOfTypingOp.addFirst(["delete", decision]);
	}


	*addDisplayCursorAtEndOfTextOpToQueue {
		~queueOfTypingOp = ~queueOfTypingOp.addFirst(["cursorAtEndOfText", Oye.displayCursorAtEndOfTextOp]);
	}

	*insertOp {
		if (~op[1].notEmpty, {
			~currentDocument.text = ~currentDocument.text ++ ~op[1].first;
			Oye.displayCursorAtEndOfTextOp;
			~op[1] =  ~op[1].drop(1);
		})
	}
	*replaceOp {
		if (~op[1].notEmpty, {
			~currentDocument.insertText(~op[1].first.asString, ~findThingToErase + ~contador);
			Oye.displayCursorWhenReplacingOp;
			~op[1] =  ~op[1].drop(1);
		})
	}
	*updateThingsToFindOp {
		if (~op[1].notEmpty, {
			~inWhichInstrument = ~currentDocument.text.find(~op[2]);
			~whereIsLastParenthesisOfThatInstrument = ~currentDocument.text.findAll(~op[3]);
			~whereIsVarToFind = ~currentDocument.text.findAll(~op[5]);

			if ((~op[2] == "Bajo") || (~op[2] == "Mel") || (~op[2] == "Ritmo"), {
				if ((~op[4].first == $[ && ~op[4].last == $]), {
					~op[4] =  ~op[4].drop(1); ~op[4] =  ~op[4].drop(-1);
				});
			});
			~whereIsThingToFind =  ~currentDocument.text.findAll(~op[4]);
		});

		~op[1] = [];
	}

	*locateEndingOp {
		if (~op[1].notEmpty, {
			if (~whereIsLastParenthesisOfThatInstrument.size > 1, {
				if ((~whereIsLastParenthesisOfThatInstrument[0] > ~inWhichInstrument) &&
					(~whereIsLastParenthesisOfThatInstrument[0] < ~whereIsLastParenthesisOfThatInstrument[1]),
					{~whereIsLastParenthesisOfThatInstrument = ~whereIsLastParenthesisOfThatInstrument[0];
						~op[1] = [];
					},{~whereIsLastParenthesisOfThatInstrument = ~whereIsLastParenthesisOfThatInstrument.drop(1);
				});
			}, {~whereIsLastParenthesisOfThatInstrument = ~whereIsLastParenthesisOfThatInstrument[0];
				~op[1] = [];
			});
		});
	}

	*locateVarOp {

		if (~op[1].notEmpty, {
			if (~whereIsVarToFind.size > 1, {
				if ((~whereIsVarToFind[0] >  ~inWhichInstrument) && (~whereIsVarToFind[0] < ~whereIsLastParenthesisOfThatInstrument), {
					~whereIsVarToFind = ~whereIsVarToFind[0];
					~op[1] = [];
				}, {~whereIsVarToFind = ~whereIsVarToFind.drop(1);
				});
			}, {~whereIsVarToFind = ~whereIsVarToFind[0];
				~op[1] = [];
			});
		});
	}

	*locateThingOp {
		if (~op[1].notEmpty, {
			if(~whereIsThingToFind.size > 1, {
				if ((~whereIsThingToFind[0] > ~whereIsVarToFind) && (~whereIsThingToFind[0] <
					~whereIsLastParenthesisOfThatInstrument), {
					~whereIsThingToFind = ~whereIsThingToFind[0];
					~op[1] = [];
				}, {~whereIsThingToFind = ~whereIsThingToFind.drop(1);
				});
			}, {~whereIsThingToFind = ~whereIsThingToFind[0];
				~op[1] = [];
			});
		});
	}

	*insertCommaAfterSetOp {
		if (~op[1].notEmpty, {
			if(~op[2] == "Pad", {
				~currentDocument = ~currentDocument.insertText(", r,", (~whereIsThingToFind + (~op[4].size)));
			});
			if(~op[2] == "Bajo", {
				~currentDocument = ~currentDocument.insertText(",", (~whereIsThingToFind + (~op[4].size-2)));
			});
			if(~op[2] == "Mel", {
				~currentDocument = ~currentDocument.insertText(",", (~whereIsThingToFind + (~op[4].size)));
			});
		});
		~op[1] = [];


	}

	*insertNewStuffInPreviousStatementOp {

		if (~op[1].notEmpty, {
			if ((~op[2] == "Bajo") || (~op[2] == "Melodia") || (~op[2] == "Ritmo"), {
				if (~op[1].first.asString == "[", {~op[1] =  ~op[1].drop(1);});
				if (~op[1].last.asString == "]", {~op[1] =  ~op[1].drop(-1);});
			});

			if(~op[2] == "Pad", {
				~currentDocument.insertText (
					~op[1].first.asString, (~whereIsThingToFind + (~op[4].size + 4)) + (~contador));
				Oye.displayCursorWhenInsertingNewStuffOp;
				~op[1] =  ~op[1].drop(1);
			});

			if(~op[2] == "Bajo", {
				~currentDocument.insertText (
					~op[1].first.asString, (~whereIsThingToFind + (~op[4].size-1)) + (~contador));
				Oye.displayCursorWhenInsertingNewStuffOp;

				~op[1] =  ~op[1].drop(1);
			});

			if(~op[2] == "Mel", {
				~currentDocument.insertText (
					~op[1].first.asString, (~whereIsThingToFind + (~op[4].size+1)) + (~contador));
				Oye.displayCursorWhenInsertingNewStuffOp;

				~op[1] =  ~op[1].drop(1);
			});
		});
	}

	*displayCursorAtEndOfTextOp {
		~currentDocument = ~currentDocument.insertText("|", (~currentDocument.text.size));
		0.15.wait;
		~currentDocument.text = ~currentDocument.text.replace("|", "");
		0.15.wait;
	}

	*displayCursorWhenReplacingOp {
		~currentDocument =
		~currentDocument.insertText("|", (~findThingToErase + ~contador+1)); //replace thing to erase with cursor
		0.15.wait;
		~currentDocument.text = ~currentDocument.text.replace("|", ""); //move cursor forward
		~contador = ~contador + 1;
		0.15.wait;
	}



	*displayCursorWhenDeletingOp {
		~currentDocument = ~currentDocument.insertText("|", (~findThingToErase + ~op[1].size)); //replace thing to erase with cursor
		0.15.wait;
		~currentDocument.text = ~currentDocument.text.replace("|", ""); //move cursor backwards and replace the thing to erase
		0.15.wait;
	}

	*displayCursorWhenInsertingNewStuffOp {
		if (~op[2] == "Pad", {
			~currentDocument = ~currentDocument.insertText("|", (~whereIsThingToFind + (~op[4].size + 4)) + (~contador));
			0.15.wait;
			~currentDocument.text = ~currentDocument.text.replace("|", ""); //move cursor backwards and replace the thing to erase
			~contador = ~contador + 1;
			0.15.wait;
		}, {
			~currentDocument = ~currentDocument.insertText("|", (~whereIsThingToFind + (~op[4].size -1)) + (~contador));
			0.15.wait;
			~currentDocument.text = ~currentDocument.text.replace("|", ""); //move cursor backwards and replace the thing to erase
			~contador = ~contador + 1;
			0.15.wait;
		});

	}

	*evaluateOp {
		~op = nil;
		~whereIsThingToFind = nil;
		~inWhichInstrument = nil;
		~whereIsLastParenthesisOfThatInstrument = nil;
		~locateDoubleSpacesBeforeSqBracket = nil;
		~currentDocument.text = ~currentDocument.text ++ "\n";
		//~op[1].interpret;
		~contador = 0;
		~currentDocument.currentLine;
		~currentDocument.text.interpret;

	}

	*deleteOp {
		if (~op[1].notEmpty, {
			~text = ~currentDocument.text;
			~findThingToErase = ~whereIsThingToFind; //~currentDocument.text.findBackwards(~op[1]); //find index
			Oye.displayCursorWhenDeletingOp;
			~text.removeAt((~findThingToErase + (~op[1].size-1)));
			~currentDocument.text = ~text;
			~op[1] = ~op[1].drop(-1);
		})
	}

	*adjustFormatOfTextOp  {
		if (~op[1].notEmpty,{
			~text = ~currentDocument.text;
			~spacesBeforeSqBracket = "  ]";
			~locateDoubleSpacesBeforeSqBracket = ~currentDocument.text.findAll(~spacesBeforeSqBracket);
			~op[1] = [];});

		if (~locateDoubleSpacesBeforeSqBracket.notNil, {
			if (~op[2].notEmpty,  {
				~text.removeAt(~locateDoubleSpacesBeforeSqBracket[0] + 1);
				~currentDocument.text = ~text;
				~op[2] = [];
			})
		});
	}

	*doTyping {
		// op op
		if(~op[0] == "insert", {Oye.insertOp;});
		if(~op[0] == "insertNewStuffInPreviousStatement", {Oye.insertNewStuffInPreviousStatementOp;});
		if(~op[0] == "replace", {Oye.replaceOp;});
		if(~op[0] == "delete", {Oye.deleteOp;});
		if(~op[0] == "cursorAtEndOfText", {Oye.displayCursorAtEndOfTextOp;});
		if(~op[0] == "displayCursorWhenDeleting", {Oye.displayCursorWhenDeletingOp;});
		if(~op[0] == "displayCursorWhenReplacing", {Oye.displayCursorWhenReplacingOp;});
		if(~op[0] == "insertCommaAfterSet", {Oye.insertCommaAfterSetOp;});
		if(~op[0] == "locateVar", {Oye.locateVarOp;});
		if(~op[0] == "locateThing", {Oye.locateThingOp;});
		if(~op[0] == "locateEnding", {Oye.locateEndingOp;});
		if(~op[0] == "updateThingsToFind", {Oye.updateThingsToFindOp;});
		if(~op[0] == "adjustFormatOfText", {Oye.adjustFormatOfTextOp;});
		if(~op[0] == "displayCursorWhenInsertingNewStuffOp", {Oye.displayCursorWhenInsertingNewStuffOp;});
		if(~op[0] == "evaluate", {Oye.evaluateOp;}); // this is probably actually invisible, but no problem...//~op[1].interpret;
	}

	*whichState {
		~currentTime = Main.elapsedTime;
		if (~state == "waiting", {
			"im waiting".postln;
			Oye.displayCursorAtEndOfTextOp;
			if (~currentTime > ~waitEnd, {~state = "deciding"});
		});

		if (~state == "deciding", {
			~decision = Oye.decide;
			"im deciding".postln;
			~state = Oye.translateDecision(~decision);
		});

		if (~state == "typing", {
			"im typing".postln;
			if (~queueOfTypingOp.isEmpty, {~state = "deciding"},{
				//~op = ~queueOfTypingOp.pop;//pop returns the earliest item in the queue
				~op = if (~op.isNil, {~queueOfTypingOp.pop;}, {
					if (~op.notNil, {
						if ((~queueOfTypingOp.size >= 1 && ~op[1].isEmpty),
							{~queueOfTypingOp.pop;}, {~op = ~op});
					})
				});
				Oye.doTyping(~op);
			});
		});
	}

	*execute {arg x;
		~updatedCurrentDoc = ~currentDocument.text;
		thisProcess.interpreter.interpret(~updatedCurrentDoc);
	}

	*sliders {
		w = Window.new("Oye: L3 Inputs testing Sliders", Rect(170,-300,170,420)).front;
		//	w = Window.new("Oye: L3 Inputs testing Sliders", Rect(800,-300,700,300)).front;
		w.alpha = 1;
		w.alwaysOnTop = true;

		~makeMelodyString=StaticText(w,Rect(20,20,150,20));
		~makeMelodyString.string= "makeMelody";
		~mMW = NumberBox(w, Rect(20, 40, 150, 20));
		~mMS = Slider(w, Rect(20, 60, 150, 20));

		~makeBajoString=StaticText(w,Rect(20,80,150,20));
		~makeBajoString.string= "makeBajo";
		~mBW = NumberBox(w, Rect(20,100,150,20));
		~mBS = Slider(w, Rect(20, 120, 150, 20));

		~makePadString=StaticText(w,Rect(20,140,150,20));
		~makePadString.string= "makeKeyboard/synthesizer";
		~mPW = NumberBox(w, Rect(20,160,150,20));
		~mPS = Slider(w, Rect(20, 180, 150, 20));
		~makeCowBellString=StaticText(w,Rect(20,200,150,20));
		~makeCowBellString.string= "makeCowBell";
		~mCBW = NumberBox(w, Rect(20, 220, 150, 20));
		~mCBS = Slider(w, Rect(20, 240, 150, 20));


		~makeKickString=StaticText(w,Rect(20,260,150,20));
		~makeKickString.string= "makeKick";
		~mKW = NumberBox(w, Rect(20, 280,150,20));
		~mKS = Slider(w, Rect(20, 300, 150, 20));

		~makeGuiraString=StaticText(w,Rect(20,320,150,20));
		~makeGuiraString.string= "makeGuira";
		~mGW = NumberBox(w, Rect(20, 340, 150, 20));
		~mGS = Slider(w, Rect(20, 360, 150, 20));

		~loudnessL3String=StaticText(w,Rect(20,380,150,20));
		~loudnessL3String.string= "loudnessL3";
		~loudnessL3W = NumberBox(w, Rect(20, 400, 150, 20));
		~loudnessL3S = Slider(w, Rect(20, 420, 150, 20));

		/*
		~makeTimbalSoloString=StaticText(w,Rect(200,140,150,20));
		~makeTimbalSoloString.string= "makeTimbalSolo";
		~mTSW = NumberBox(w, Rect(200, 160, 150, 20));
		~mTSS = Slider(w, Rect(200, 180, 150, 20));
		/*.action_({
		~mTSW.value_(~mTSS.value);
		//~lThreeInputs[\makeTimbalSolo] = ~mTSS.value;
		});*/
		//~mTSS.action.value;

		~useCmajorScaleString=StaticText(w,Rect(380,20,150,20));
		~useCmajorScaleString.string= "use_CmajorScale";
		~useCmajorScaleW = NumberBox(w, Rect(380, 40, 150, 20));
		~useCmajorScaleS = Slider(w, Rect(380, 60, 150, 20));

		~useDmajorScaleString=StaticText(w,Rect(380,80,150,20));
		~useDmajorScaleString.string= "use_DmajorScale";
		~useDmajorScaleW = NumberBox(w, Rect(380, 100, 150, 20));
		~useDmajorScaleS = Slider(w, Rect(380, 120, 150, 20));

		~useEmajorScaleString=StaticText(w,Rect(380,140,150,20));
		~useEmajorScaleString.string= "use_EmajorScale";
		~useEmajorScaleW = NumberBox(w, Rect(380, 160, 150, 20));
		~useEmajorScaleS = Slider(w, Rect(380, 180, 150, 20));

		~useFmajorScaleString=StaticText(w,Rect(380,200,150,20));
		~useFmajorScaleString.string= "use_FmajorScale";
		~useFmajorScaleW = NumberBox(w, Rect(380, 220, 150, 20));
		~useFmajorScaleS = Slider(w, Rect(380, 240, 150, 20));

		~useGmajorScaleString=StaticText(w,Rect(550,20,150,20));
		~useGmajorScaleString.string= "use_GmajorScale";
		~useGmajorScaleW = NumberBox(w, Rect(550, 40, 150, 20));
		~useGmajorScaleS = Slider(w, Rect(550, 60, 150, 20));

		~useAmajorScaleString=StaticText(w,Rect(550,80,150,20));
		~useAmajorScaleString.string= "use_AmajorScale";
		~useAmajorScaleW = NumberBox(w, Rect(550, 100, 150, 20));
		~useAmajorScaleS = Slider(w, Rect(550, 120, 150, 20));

		~useBmajorScaleString=StaticText(w,Rect(550,140,150,20));
		~useBmajorScaleString.string= "use_B";
		~useBmajorScaleW = NumberBox(w, Rect(550, 160, 150, 20));
		~useBmajorScaleS = Slider(w, Rect(550, 180, 150, 20));
		*/

		/*	{~loudnessL3W.value = ~loudnessL3; ~loudnessL3S.value = ~loudnessL3}.defer;
		{~useCmajorScaleW.value = ~useCmajorScale; ~useCmajorScaleS.value = ~useCmajorScale}.defer;
		{~useDmajorScaleW.value = ~useDmajorScale; ~useDmajorScaleS.value = ~useDmajorScale}.defer;
		{~useEmajorScaleW.value = ~useEmajorScale; ~useEmajorScaleS.value = ~useEmajorScale}.defer;
		{~useFmajorScaleW.value = ~useFmajorScale; ~useFmajorScaleS.value = ~useFmajorScale}.defer;
		{~useGmajorScaleW.value = ~useGmajorScale; ~useGmajorScaleS.value = ~useGmajorScale}.defer;
		{~useAmajorScaleW.value = ~useAmajorScale; ~useAmajorScaleS.value = ~useAmajorScale}.defer;
		{~useBmajorScaleW.value = ~useBmajorScale; ~useBmajorScaleS.value = ~useBmajorScale}.defer;*/

	}


	*slidersManual {
		w = Window.new("Oye: L3 Inputs testing Sliders", Rect(800,-300,700,300)).front;
		w.alpha = 1;
		w.alwaysOnTop = true;
		~makeKick=StaticText(w,Rect(20,20,150,20));
		~makeKick.string= "makeKick";
		~mKW = NumberBox(w, Rect(20,40,150,20));
		~mKS = Slider(w, Rect(20, 60, 150, 20))
		.action_({
			~mKW.value_(~mKS.value);
			~lThreeInputs[\makeKick] = ~mKS.value;
		});
		~mKS.action.value;

		~makeGuira=StaticText(w,Rect(200,20,150,20));
		~makeGuira.string= "makeGuira";
		~mGW = NumberBox(w, Rect(200, 40, 150, 20));
		~mGS = Slider(w, Rect(200, 60, 150, 20))
		.action_({
			~mGW.value_(~mGS.value);
			~lThreeInputs[\makeGuira] = ~mGS.value;
		});
		~mGS.action.value;


		~makeTimbalSoloString=StaticText(w,Rect(20,80,150,20));
		~makeTimbalSoloString.string= "makeTimbalSolo";
		~mTSW = NumberBox(w, Rect(20,100,150,20));
		~mTSS = Slider(w, Rect(20, 120, 150, 20))
		.action_({
			~mTSW.value_(~mTSS.value);
			//~lThreeInputs[\makeTimbalSolo] = ~mTSS.value;
		});
		//~mTSS.action.value;

		~makeCowBellString=StaticText(w,Rect(200,80,150,20));
		~makeCowBellString.string= "makeCowBell";
		~mCBW = NumberBox(w, Rect(200, 100, 150, 20));
		~mCBS = Slider(w, Rect(200, 120, 150, 20))
		.action_({
			~mCBW.value_(~mCBS.value);
			~lThreeInputs[\makeCowBell] = ~mCBS.value;
		});
		~mCBS.action.value;

		~makeBajoString=StaticText(w,Rect(20,140,150,20));
		~makeBajoString.string= "makeBass";
		~mBW = NumberBox(w, Rect(20,160,150,20));
		~mBS = Slider(w, Rect(20, 180, 150, 20))
		.action_({
			~mBW.value_(~mBS.value);
			~lThreeInputs[\makeBajo] = ~mBS.value;
		});
		~mBS.action.value;

		~makePadString=StaticText(w,Rect(200,140,150,20));
		~makePadString.string= "makePad";
		~mPW = NumberBox(w, Rect(200, 160, 150, 20));
		~mPS = Slider(w, Rect(200, 180, 150, 20))
		.action_({
			~mPW.value_(~mPS.value);
			~lThreeInputs[\makePad] = ~mPS.value;
		});
		~mPS.action.value;

		~makeMelodyString=StaticText(w,Rect(20,200,150,20));
		~makeMelodyString.string= "makeMelody";
		~mMW = NumberBox(w, Rect(20, 220, 150, 20));
		~mMS = Slider(w, Rect(20, 240, 150, 20))
		.action_({
			~mMW.value_(~mMS.value);
			~lThreeInputs[\makeMelody] = ~mMS.value;
		});
		~mMS.action.value;

		~loudnessString=StaticText(w,Rect(200,200,150,20));
		~loudnessString.string= "loudness";
		~loudnessW = NumberBox(w, Rect(200, 220, 150, 20));
		~loudnessS = Slider(w, Rect(200, 240, 150, 20))
		.action_({
			~loudnessW.value_(~loudnessS.value);
			~lThreeInputs[\loudness] = ~loudnessS.value;
		});
		~loudnessS.action.value;

		~useCmajorScaletring=StaticText(w,Rect(380,20,150,20));
		~useCmajorScaletring.string= "use_C";
		~useCmajorScaleW = NumberBox(w, Rect(380, 40, 150, 20));
		~useCmajorScale = Slider(w, Rect(380, 60, 150, 20))
		.action_({
			~useCmajorScaleW.value_(~useCmajorScale.value);
			~lThreeInputs[\useC] = ~useCmajorScale.value;
		});
		~useCmajorScale.action.value;

		~useDmajorScaleString=StaticText(w,Rect(380,80,150,20));
		~useDmajorScaleString.string= "use_D";
		~useDmajorScaleW = NumberBox(w, Rect(380, 100, 150, 20));
		~useDmajorScaleS = Slider(w, Rect(380, 120, 150, 20))
		.action_({
			~useDmajorScaleW.value_(~useDmajorScaleS.value);
			~lThreeInputs[\useD] = ~useDmajorScaleS.value;
		});
		~useDmajorScaleS.action.value;

		~useEmajorScaleString=StaticText(w,Rect(380,140,150,20));
		~useEmajorScaleString.string= "use_E";
		~useEmajorScaleW = NumberBox(w, Rect(380, 160, 150, 20));
		~useEmajorScaleS = Slider(w, Rect(380, 180, 150, 20))
		.action_({
			~useEmajorScaleW.value_(~useEmajorScaleS.value);
			~lThreeInputs[\useE] = ~useEmajorScaleS.value;
		});
		~useDmajorScaleS.action.value;

		~useFmajorScaleString=StaticText(w,Rect(380,200,150,20));
		~useFmajorScaleString.string= "use_F";
		~useFmajorScaleW = NumberBox(w, Rect(380, 220, 150, 20));
		~useFmajorScaleS = Slider(w, Rect(380, 240, 150, 20))
		.action_({
			~useFmajorScaleW.value_(~useFmajorScaleS.value);
			~lThreeInputs[\useF] = ~useFmajorScaleS.value;
		});
		~useFmajorScaleS.action.value;


		~useGmajorScaleString=StaticText(w,Rect(550,20,150,20));
		~useGmajorScaleString.string= "use_G";
		~useGmajorScaleW = NumberBox(w, Rect(550, 40, 150, 20));
		~useGmajorScaleS = Slider(w, Rect(550, 60, 150, 20))
		.action_({
			~useGmajorScaleW.value_(~useGmajorScaleS.value);
			~lThreeInputs[\useG] = ~useGmajorScaleS.value;
		});
		~useGmajorScaleS.action.value;

		~useAmajorScaleString=StaticText(w,Rect(550,80,150,20));
		~useAmajorScaleString.string= "use_A";
		~useAmajorScaleW = NumberBox(w, Rect(550, 100, 150, 20));
		~useAmajorScaleS = Slider(w, Rect(550, 120, 150, 20))
		.action_({
			~useAmajorScaleW.value_(~useAmajorScaleS.value);
			~lThreeInputs[\useA] = ~useAmajorScaleS.value;
		});
		~useAmajorScaleS.action.value;


		~useBmajorScaleString=StaticText(w,Rect(550,140,150,20));
		~useBmajorScaleString.string= "use_B";
		~useBmajorScaleW = NumberBox(w, Rect(550, 160, 150, 20));
		~useBmajorScaleS = Slider(w, Rect(550, 180, 150, 20))
		.action_({
			~useBmajorScaleW.value_(~useBmajorScaleS.value);
			~lThreeInputs[\useB] = ~useBmajorScaleS.value;
		});
		~useBmajorScaleS.action.value;
	}

	*receiveL1OutputsFromNet {arg slidersL1 = ~slidersL1, netReady = ~netReady, print = ~printNetInput;
		if (netReady == true, {
			OSCdef (\lOneOutputs, {arg msg;
				if (print == true, {msg.postln});
				~lOneOutputs  = Dictionary.new;
				~lOneOutputs[\mel] = msg[1].clip(0,1);
			    ~lOneOutputs[\pad] = msg[2].clip(0,1);
    			 ~lOneOutputs[\bajo] = msg[3].clip(0,1);
				~lOneOutputs[\guira] = msg[4].clip(0,1);
				~lOneOutputs[\cowbell] = msg[5].clip(0,1);
				~lOneOutputs[\kick] = msg[6].clip(0,1);

				~lOneOutputs[\c] = msg[7].clip(0,1);
				~lOneOutputs[\cs] = msg[8].clip(0,1);
				~lOneOutputs[\d] = msg[9].clip(0,1);
				~lOneOutputs[\ds] = msg[10].clip(0,1);
				~lOneOutputs[\e] = msg[11].clip(0,1);
				~lOneOutputs[\f] = msg[12].clip(0,1);
				~lOneOutputs[\fs] = msg[13].clip(0,1);
				~lOneOutputs[\g] = msg[14].clip(0,1);
				~lOneOutputs[\gs] = msg[15].clip(0,1);
				~lOneOutputs[\a] = msg[16].clip(0,1);
				~lOneOutputs[\as] = msg[17].clip(0,1);
				~lOneOutputs[\b] = msg[18].clip(0,1);
                ~lOneOutputs[\energy] = msg[19].clip(0,1);



				///////////////mapping L1 input to sliders//////////
			   {~melW.value = msg[1].clip(0,1); ~melS.value = msg[1].clip(0,1)}.defer;
			   {~padW.value = msg[2].clip(0,1); ~padS.value = msg[2].clip(0,1)}.defer;
			   {~bassW.value = msg[3].clip(0,1); ~bassS.value = msg[3].clip(0,1)}.defer;
		       {~guiraW.value = msg[4].clip(0,1); ~guiraS.value = msg[4].clip(0,1)}.defer;
    			{~cowbellW.value = msg[5].clip(0,1); ~cowbellS.value = msg[5].clip(0,1)}.defer;
			  {~kickW.value = msg[6].clip(0,1); ~kickS.value = msg[6].clip(0,1)}.defer;

				{~cW.value = msg[7].clip(0,1); ~cS.value = msg[7].clip(0,1)}.defer;
				{~csW.value = msg[8].clip(0,1); ~csS.value = msg[8].clip(0,1)}.defer;
				{~dW.value = msg[9].clip(0,1); ~dS.value = msg[9].clip(0,1)}.defer;
				{~dsW.value = msg[10].clip(0,1); ~dsS.value = msg[10].clip(0,1)}.defer;
				{~eW.value = msg[11].clip(0,1); ~eS.value = msg[11].clip(0,1)}.defer;
				{~fW.value = msg[12].clip(0,1); ~fS.value = msg[12].clip(0,1)}.defer;
				{~fsW.value = msg[13].clip(0,1); ~fsS.value = msg[13].clip(0,1)}.defer;
				{~gW.value = msg[14].clip(0,1); ~gS.value = msg[14].clip(0,1)}.defer;
				{~gsW.value = msg[15].clip(0,1); ~gsS.value = msg[15].clip(0,1)}.defer;
				{~aW.value = msg[16].clip(0,1); ~aS.value = msg[16].clip(0,1)}.defer;
				{~asW.value = msg[17].clip(0,1); ~asS.value = msg[17].clip(0,1)}.defer;
				{~bW.value = msg[18].clip(0,1); ~bS.value = msg[18].clip(0,1)}.defer;

					{~energyW.value = msg[19].clip(0,1); ~energyS.value = msg[19].clip(0,1)}.defer;

				//{~timbalW.value = msg[3].clip(0,1); ~timbalS.value = msg[3].clip(0,1)}.defer;
				//{~hihatsW.value = msg[2].clip(0,1); ~hihatsS.value = msg[2].clip(0,1)}.defer;
				//{~snareW.value = msg[3].clip(0,1); ~snareS.value = msg[2].clip(0,1)}.defer;



			}, "/n");
		},  {~lOneOutputs  = Dictionary.new;
			~lOneOutputs = ~lOneOutputs.putAll(Dictionary[\kick -> 0, \guira -> 0, \cowbell -> 0, \bajo -> 0, \pad -> 0, \mel -> 0, \loudness -> 0,
				\cMajorScale -> 0]);
		});

		//////////////Sliders////////////////

		if (slidersL1, {
			w = Window.new("Oye: Layer 1", Rect(-800, -300, 600, 420)).front;
			//w = Window.new("Oye: Layer 1", Rect(-800, -300, 400, 400)).front;
			//			w.alpha = 0.8;
			w.alwaysOnTop = true;
			~melString=StaticText(w,Rect(20,20,150,20));
			~melString.string= "melody";
			~melW = NumberBox(w, Rect(20,40,150,20));
			~melS = Slider(w, Rect(20, 60, 150, 20));

			~padString=StaticText(w,Rect(20,140,150,20));
			~padString.string= "keyboard/synthesizer";
			~padW = NumberBox(w, Rect(20,160,150,20));
			~padS = Slider(w, Rect(20, 180, 150, 20));

			~bassString=StaticText(w,Rect(20,80,150,20));
			~bassString.string= "bass";
			~bassW = NumberBox(w, Rect(20,100,150,20));
			~bassS = Slider(w, Rect(20, 120, 150, 20));


			~cowbellString=StaticText(w,Rect(20,200,150,20));
			~cowbellString.string= "cowbell";
			~cowbellW = NumberBox(w, Rect(20, 220, 150, 20));
			~cowbellS = Slider(w, Rect(20, 240, 150, 20));

			~kickString=StaticText(w,Rect(20,260,150,20));
			~kickString.string= "kick";
			~kickW = NumberBox(w, Rect(20, 280, 150, 20));
			~kickS = Slider(w, Rect(20, 300, 150, 20));

			~guiraString=StaticText(w,Rect(20,320,150,20));
			~guiraString.string= "guira";
			~guiraW = NumberBox(w, Rect(20, 340, 150, 20));
			~guiraS = Slider(w, Rect(20, 360, 150, 20));


			/*~cString=StaticText(w,Rect(20,380,150,20));
			~cString.string= "loudness";
			~cW = NumberBox(w, Rect(20, 400, 150, 20));
			~cS = Slider(w, Rect(20, 420, 150, 20));*/

			~cString=StaticText(w,Rect(200,20,150,20));
			~cString.string= "c";
			~cW = NumberBox(w, Rect(200, 40, 150, 20));
			~cS = Slider(w, Rect(200, 60, 150, 20));


			~cSharpString=StaticText(w,Rect(200,80,150,20));
			~cSharpString.string= "c#";
			~csW = NumberBox(w, Rect(200, 100, 150, 20));
			~csS = Slider(w, Rect(200, 120, 150, 20));


			~dString=StaticText(w,Rect(200, 140,150,20));
			~dString.string= "d";
			~dW = NumberBox(w, Rect(200, 160, 150, 20));
			~dS = Slider(w, Rect(200, 180, 150, 20));


			~dSharpString=StaticText(w,Rect(200,200,150,20));
			~dSharpString.string= "d#";
			~dsW = NumberBox(w, Rect(200, 220, 150, 20));
			~dsS = Slider(w, Rect(200, 240, 150, 20));


			~eString=StaticText(w,Rect(200,260,150,20));
			~eString.string= "e";
			~eW = NumberBox(w, Rect(200, 280, 150, 20));
			~eS = Slider(w, Rect(200, 300, 150, 20));


			~fString=StaticText(w,Rect(200,320,150,20));
			~fString.string= "f";
			~fW = NumberBox(w, Rect(200, 340, 150, 20));
			~fS = Slider(w, Rect(200, 360, 150, 20));


			~fSharpString=StaticText(w,Rect(400,20,150,20));
			~fSharpString.string= "f#";
			~fsW = NumberBox(w, Rect(400, 40, 150, 20));
			~fsS = Slider(w, Rect(400, 60, 150, 20));


			~gString=StaticText(w,Rect(400,80,150,20));
			~gString.string= "g";
			~gW = NumberBox(w, Rect(400, 100, 150, 20));
			~gS = Slider(w, Rect(400, 120, 150, 20));


			~gSharpString=StaticText(w,Rect(400,140,150,20));
			~gSharpString.string= "g#";
			~gsW = NumberBox(w, Rect(400, 160, 150, 20));
			~gsS = Slider(w, Rect(400, 180, 150, 20));


			~aString=StaticText(w,Rect(400,200,150,20));
			~aString.string= "a";
			~aW = NumberBox(w, Rect(400, 220, 150, 20));
			~aS = Slider(w, Rect(400, 240, 150, 20));

			~aSharpString=StaticText(w,Rect(400,260,150,20));
			~aSharpString.string= "a#";
			~asW = NumberBox(w, Rect(400, 280, 150, 20));
			~asS = Slider(w, Rect(400, 300, 150, 20));

			~bString=StaticText(w,Rect(400,320,150,20));
			~bString.string= "b";
			~bW = NumberBox(w, Rect(400, 340, 150, 20));
			~bS = Slider(w, Rect(400, 360, 150, 20));

			~energyString=StaticText(w,Rect(400,380,150,20));
			~energyString.string= "energy";
			~energyW = NumberBox(w, Rect(400, 400, 150, 20));
			~energyS = Slider(w, Rect(400, 420, 150, 20));


			/*	~timbalString=StaticText(w,Rect(200,20,150,20));
			~timbalString.string= "timbal";
			~timbalW = NumberBox(w, Rect(200, 40, 150, 20));
			~timbalS = Slider(w, Rect(200, 60, 150, 20));

			~hihatsString=StaticText(w,Rect(200,80,150,20));
			~hihatsString.string= "hihats";
			~hihatsW = NumberBox(w, Rect(200, 100, 150, 20));
			~hihatsS = Slider(w, Rect(200, 120, 150, 20));


			~snareString=StaticText(w,Rect(200,200,150,20));
			~snareString.string= "snare";
			~snareW = NumberBox(w, Rect(200, 220, 150, 20));
			~snareS = Slider(w, Rect(200, 240, 150, 20));




			~cMajorScaleString=StaticText(w,Rect(200,260,150,20));
			~cMajorScaleString.string= "cMajorScale";
			~cMajorScaleW = NumberBox(w, Rect(200, 280, 150, 20));
			~cMajorScaleS = Slider(w, Rect(200, 300, 150, 20));*/

		});
	}

	*l2 {//connecting l1 to l3
		//~l3 = 1 - ~l1;
		~makeKick = 1 - ~lOneOutputs[\kick];
		~makeGuira = 1 - ~lOneOutputs[\guira];
		~makeCowBell = 1 - ~lOneOutputs[\cowbell];
		~makeBajo = 1 - ~lOneOutputs[\bajo];
		~makeMel = 1 - ~lOneOutputs[\mel];
		~makePad = 1 - ~lOneOutputs[\pad];
		/*~instrumentsSounding = Array.new;
		~loudnessOfinstrumentsSounding = Array.new;
		~instrumentsSounding = ~lOneOutputs.keysValuesDo{arg key,value; if (value >= 0.5, {~loudnessOfinstrumentsSounding = ~loudnessOfinstrumentsSounding.add(value)}, {~loudnessOfinstrumentsSounding = ~loudnessOfinstrumentsSounding})};
		~loudnessOfinstrumentsSounding = ~loudnessOfinstrumentsSounding.mean;
		~loudnessL3 = ~loudnessOfinstrumentsSounding;
		if (~lOneOutputs[\kick] >= 0.5, {~loudnessL3 = 1}, ~loudnessL3 = 0) ;*/
		~loudnessL3 = 	~lOneOutputs[\loudness];

		~useCmajorScale = 1; //- ~lOneOutputs[\cMajorScale]; //if C major scale is being played, then (1) play it as well
		~useDmajorScale = 0;//- ~lOneOutputs[\cMajorScale];
		~useEmajorScale = 0; //- ~lOneOutputs[\cMajorScale];
		~useFmajorScale = 0; //- ~lOneOutputs[\cMajorScale];
		~useGmajorScale = 0; //- ~lOneOutputs[\cMajorScale];
		~useAmajorScale = 0; //- ~lOneOutputs[\cMajorScale];
		~useBmajorScale = 0; //- ~lOneOutputs[\cMajorScale];

		/*	~useCmajorScale = 1 - ~lOneOutputs[\dMajorScale];
		~useDmajorScale = 0 - ~lOneOutputs[\dMajorScale]; //if C major scale is being played, then (1) play it as well
		~useEmajorScale = 1 - ~lOneOutputs[\dMajorScale];
		~useFmajorScale = 1 - ~lOneOutputs[\dMajorScale];
		~useGmajorScale = 1 - ~lOneOutputs[\dMajorScale];
		~useAmajorScale = 1 - ~lOneOutputs[\dMajorScale]; */

		~lThreeInputs = (makeKick: ~makeKick, makeGuira: ~makeGuira, makeTimbalSolo: 0, makeCowBell: ~makeCowBell, makeBajo: ~makeBajo, makePad: ~makePad,
			makeMelody: ~makeMel, loudness: ~loudnessL3, useCmajorScale: ~useCmajorScale, useDmajorScale: ~useDmajorScale, useEmajorScale:~useEmajorScale,
			useFmajorScale: ~useFmajorScale, useGmajorScale: ~useGmajorScale, useAmajorScale: ~useAmajorScale, useBmajorScale:~useAmajorScale);

		{~mKW.value = ~makeKick; ~mKS.value = ~makeKick}.defer;
		{~mGW.value = ~makeGuira; ~mGS.value = ~makeGuira}.defer;
		{~mCBW.value = ~makeCowBell; ~mCBS.value = ~makeCowBell}.defer;
		{~mBW.value = ~makeBajo; ~mBS.value = ~makeBajo}.defer;
		{~mPW.value = ~makePad; ~mPS.value = ~makePad}.defer;
		{~mMW.value = ~makeMel; ~mMS.value = ~makeMel}.defer;
		{~loudnessL3W.value = ~loudnessL3; ~loudnessL3S.value = ~loudnessL3}.defer;
	}

	*stop {
		~closeDoc = ~currentDocument.close("/home/lui/cacharpo.scd");
		~killWindows = w.close;
	}

	/////////////////////////////////normalizing functions/////////////////////////////////////////

	*normalize {
		arg x,y, threshold = (-40);
		var z = y.clip(threshold.dbamp, 300.dbamp);//normliztion to 0.dbmps?
		^ (x/z);
		//(y < (threshold.dbamp)).asInteger *
	}

	*outOfRange {
		arg x, y, threshold= (-40), outOfRange = (-1);
		var a = x * (y > (threshold.dbamp)); //  x or 0
		var b = (y <= (threshold.dbamp)) * outOfRange;
		^a+b;
	}

	/////////////////////////////////training function/////////////////////////////////////////

	*analyseRecording {
		arg pathToAudio = "~/path", dKickOutput = Env.new([-1,-1], [30]), dHiHatsOutput = Env.new([-1,-1], [30]),
		dSnareOutput = Env.new([-1,-1], [30]), dGuiraOutput = Env.new([-1,-1], [30]), dTimbalOutput = Env.new([-1,-1], [30]),
		dCowbellOutput = Env.new([-1,-1], [30]), dPadOutput = Env.new([-1,-1], [30]), dBassOutput = Env.new([-1,-1], [30]),
		dMelOutput = Env.new([-1,-1], [30]), dLoudnessOutput = Env.new([-1,-1], [30]),
		dDensityOutput = Env.new([-1,-1], [30]), loopSample = 0, port = 57700, printIandO = false, printInputs = false, printOutputs = false, doneActionKick = 0, doneActionHihats = 0, doneActionSnare = 0, doneActionGuira = 0, doneActionTimbal = 0, doneActionCowbell = 0, doneActionPad = 0, doneActionBass = 0, doneActionMel = 0, doneActionLoudness = 0, doneActionC = 0, doneActionCsharp = 0, doneActionD = 0, doneActionDsharp = 0, doneActionE = 0, doneActionF = 0, doneActionFsharp = 0, doneActionG = 0,
		doneActionGsharp = 0, doneActionA = 0, doneActionAsharp = 0, doneActionB = 0, doneActionEnergy = 0,
		doneActionDensity = 0, 	dCOutput = Env.new([-1,-1], [30]), dCsharpOutput = Env.new([-1,-1], [30]), dDOutput = Env.new([-1,-1], [30]), dDsharpOutput = Env.new([-1,-1], [30]), dEOutput = Env.new([-1,-1], [30]), dFOutput = Env.new([-1,-1], [30]), dFsharpOutput = Env.new([-1,-1], [30]), dGOutput = Env.new([-1,-1], [30]), dGsharpOutput = Env.new([-1,-1], [30]), dAOutput = Env.new([-1,-1], [30]), dAsharpOutput = Env.new([-1,-1], [30]), dBOutput = Env.new([-1,-1], [30]), dEnergyOutput = Env.new([-1,-1], [30])  ;

		~doneActionKick = doneActionKick; ~doneActionHihats = doneActionHihats;~doneActionSnare =  doneActionSnare;
		~doneActionGuira = doneActionGuira; ~doneActionTimbal = doneActionTimbal;  ~doneActionCowbell = doneActionCowbell;
		~doneActionPad = doneActionPad;   ~doneActionBass = doneActionBass; ~doneActionMel = doneActionMel;
		~doneActionLoudness = doneActionLoudness; ~doneActionDensity = doneActionDensity; 	~doneActionC = doneActionC;
		~doneActionCsharp = doneActionCsharp; ~doneActionD = doneActionD; ~doneActionDsharp = doneActionDsharp; ~doneActionE = doneActionE; ~doneActionF = doneActionF; 	~doneActionFsharp = doneActionFsharp; ~doneActionG = doneActionG; ~doneActionGsharp = doneActionGsharp; ~doneActionA = doneActionA; ~doneActionAsharp = doneActionAsharp; ~doneActionB = doneActionB; ~doneActionEnergy = doneActionEnergy;


		toThinkingModule = NetAddr.new("localhost", port);
		allowBroadcast = true;
		printO = printOutputs;
		printI = printInputs;
		printIO = printIandO;
		b = Bus.control(s, 12);
		//b.scope;

		~bufferAlloc0 = Buffer.alloc(s, 1024,2);//for sampling rates 44100 and 48000
		~bufferAlloc1 = Buffer.alloc(s, 1024,2);
		~bufferAlloc2 = Buffer.alloc(s, 1024,2);
		~bufferAlloc3 = Buffer.alloc(s, 1024,2);
		~bufferAlloc4 = Buffer.alloc(s, 1024,2);
		~bufferAlloc5 = Buffer.alloc(s, 1024,2);
		~bufferAlloc6 = Buffer.alloc(s, 1024,2);

		~performance = Buffer.read(s, pathToAudio);
		~dKickOutput = dKickOutput;
		~dHiHatsOutput = dHiHatsOutput;
		~dSnareOutput = dSnareOutput;
		~dGuiraOutput = dGuiraOutput;
		~dTimbalOutput = dTimbalOutput;
		~dCowbellOutput = dCowbellOutput;
		~dPadOutput = dPadOutput;
		~dBassOutput = dBassOutput;
		~dMelOutput = dMelOutput;
		~loopSample = loopSample;
		~dLoudnessOutput = dLoudnessOutput;
		~dDensityOutput = dDensityOutput;
		~dCOutput = dCOutput;
		~dCsharpOutput = dCsharpOutput;
		~dDOutput = dDOutput;
		~dDsharpOutput = dDsharpOutput;
		~dEOutput = dEOutput;
		~dFOutput = dFOutput;
		~dFsharpOutput = dFsharpOutput;
		~dGOutput = dGOutput;
		~dGsharpOutput = dGsharpOutput;
		~dAOutput = dAOutput;
		~dAsharpOutput = dAsharpOutput;
		~dBOutput = dBOutput;
		~dEnergyOutput = dEnergyOutput;


		SynthDef (\training, {
			var in0 = PlayBuf.ar(2, ~performance, BufRateScale.kr(~performance),1, 0, ~loopSample, doneAction:2); //read buffer two channels(stereo)
			var in = Mix.ar(in0); //mixing to one channel (mono)
			var fft0 = FFT(~bufferAlloc0, in);
			var fft1 = PV_Copy(fft0, ~bufferAlloc1);
			var fft2 = PV_Copy(fft0, ~bufferAlloc2);
			var fft3 = PV_Copy(fft0, ~bufferAlloc3);
			var fft4 = PV_Copy(fft0, ~bufferAlloc4);
			var fft5 = PV_Copy(fft0, ~bufferAlloc5);
			var fft6 = PV_Copy(fft0, ~bufferAlloc6);

			//////////////////////feature extraction Ugens//////////////////
			var loudness = Loudness.kr(fft0); //loudness = amplitude = intensity
			var pitch = Pitch.kr(in, initFreq:in, minFreq:16); //pitch detection in Hertz
			// mfcc = MFCC.kr(fft1, 13); //timbre detection only for monophonic instruments/pieces
			var amplitude = Amplitude.kr(in);
			var centroid = Oye.outOfRange(SpecCentroid.kr(fft1), amplitude); //amount of brightness = amount of treble freqs
			var filteringVeryLowFreqs = Oye.outOfRange(Amplitude.kr(LPF.ar(in, 60)), amplitude); //filtering freqs as Rhythm 20hz and below
			var filteringLowFreqs = Oye.outOfRange(Amplitude.kr (BPF.ar(in, 120, 1.3)), amplitude); //filtering low freqs between 20 and 100hz
			var filteringMidFreqs = Oye.outOfRange(Amplitude.kr(BPF.ar(in, 240, 1.3)), amplitude); //filtering mid freq between 100 and 500hz
			var filteringHighFreqs = Oye.outOfRange(Amplitude.kr(HPF.ar(in,480)), amplitude); //filtering high freq above 501hz
			var cFilter =  Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,[24,36,48,60,72,84,96].midicps,0.008))), amplitude);//Filtering pitch class of C
			var csFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+1).midicps,0.008))), amplitude); //... C#
			var dFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+2).midicps,0.008))), amplitude); //... D
			var dsFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+3).midicps,0.008))), amplitude); //... D#
			var eFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+4).midicps,0.008))), amplitude); //... E
			var fFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+5).midicps,0.008))), amplitude); //... F
			var fsFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+6).midicps,0.008))), amplitude); //... F#
			var gFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+7).midicps,0.008))), amplitude); //... G
			var gsFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+8).midicps,0.008))), amplitude); //... G#
			var aFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+9).midicps,0.008))), amplitude); //... A
			var asFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+10).midicps,0.008))), amplitude); //... A#
			var bFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+11).midicps,0.008))), amplitude); //... B
			var onsets = Onsets.kr(fft2,0.3,\magsum,1, 0.0001,1); //detecting intitial impulse of a sound (transient)
			var zeroCrossing = Oye.outOfRange(ZeroCrossing.kr(A2K.kr(in)), amplitude); //detecting variation or changes in the signal
			var specFlatness = Oye.outOfRange(SpecFlatness.kr(fft3), amplitude);// how "noise-like is a sound" (wikipedia), how tonal or noisy is, how close is a sound to being a noise, 0 = pure sinusoid to 1 = white noise
			var specPcile = Oye.outOfRange(SpecPcile.kr(fft4, 0.95), amplitude); //amount of skweness (assimetry) of a wave// cumulative distribution of the freq spectrum// in which band (high, mid or low) is the energy located
			var keyTrack = KeyTrack.kr(fft5);

			////////////////////desired outputs/////////////////

			var desiredKick = EnvGen.kr(~dKickOutput, doneAction: ~doneActionKick);
			var desiredHihats = EnvGen.kr(~dHiHatsOutput, doneAction: ~doneActionHihats);
			var desiredSnare = EnvGen.kr(~dSnareOutput, doneAction: ~doneActionSnare);
			var desiredGuira = EnvGen.kr(~dGuiraOutput, doneAction: ~doneActionGuira);
			var desiredTimbal = EnvGen.kr(~dTimbalOutput, doneAction: ~doneActionTimbal);
			var desiredCowbell = EnvGen.kr(~dCowbellOutput, doneAction: ~doneActionCowbell);
			var desiredPad = EnvGen.kr(~dPadOutput, doneAction: ~doneActionPad);
			var desiredBass = EnvGen.kr(~dBassOutput, doneAction: ~doneActionBass);
			var desiredMel = EnvGen.kr(~dMelOutput, doneAction: ~doneActionMel);
			var desiredLoudness = EnvGen.kr(~dLoudnessOutput, doneAction: ~doneActionLoudness);
			var desiredDensity = EnvGen.kr(~dDensityOutput, doneAction: ~doneActionDensity);
			var desiredC = EnvGen.kr(~dCOutput, doneAction: ~doneActionC);
			var desiredCsharp = EnvGen.kr(~dCsharpOutput, doneAction: ~doneActionCsharp);
			var desiredD = EnvGen.kr(~dDOutput, doneAction: ~doneActionD);
			var desiredDsharp = EnvGen.kr(~dDsharpOutput, doneAction: ~doneActionDsharp);
			var desiredE = EnvGen.kr(~dEOutput, doneAction: ~doneActionE);
			var desiredF = EnvGen.kr(~dFOutput, doneAction: ~doneActionF);
			var desiredFsharp = EnvGen.kr(~dFsharpOutput, doneAction: ~doneActionFsharp);
			var desiredG = EnvGen.kr(~dGOutput, doneAction: ~doneActionG);
			var desiredGsharp = EnvGen.kr(~dGsharpOutput, doneAction: ~doneActionGsharp);
			var desiredA = EnvGen.kr(~dAOutput, doneAction: ~doneActionA);
			var desiredAsharp = EnvGen.kr(~dAsharpOutput, doneAction: ~doneActionAsharp);
			var desiredB = EnvGen.kr(~dBOutput, doneAction: ~doneActionB);
			var desiredEnergy = EnvGen.kr(~dEnergyOutput, doneAction: ~doneActionEnergy);


			var quarternotetick, eighthnotetick, sixteenthnotetick, tempo, features;
			#quarternotetick, eighthnotetick, sixteenthnotetick, tempo =	BeatTrack.kr(fft6);


			///////////////////////sending sound features to a list////////////////////////////
			features = [/*3*/loudness, /*4*/pitch[0], /*5*/centroid, /*6*/filteringVeryLowFreqs, /*7*/ filteringLowFreqs,
				/*8*/filteringMidFreqs, /*9*/filteringHighFreqs, /*10*/ cFilter, /*11*/csFilter, /*12*/ dFilter, /*13*/ dsFilter,
				/*14*/eFilter, /*15*/fFilter, /*16*/ fsFilter, /*17*/ gFilter, /*18*/ gsFilter, /*19*/aFilter, /*20*/asFilter, /*21*/ bFilter,
				/*22*/ onsets, /*23*/ zeroCrossing, /*24*/ specFlatness, /*25*/ specPcile, /*26*/ keyTrack, /*27*/ quarternotetick,
				/*28*/ eighthnotetick, /*29*/ sixteenthnotetick, /*30*/ tempo, /*31*/ desiredKick, /*32*/desiredHihats, /*33*/ desiredSnare,
				/*34*/ desiredGuira, /*35*/ desiredTimbal, /*36*/ desiredCowbell, /*37*/ desiredPad, /*38*/ desiredBass, /*39*/desiredMel,
				/*40*/ desiredLoudness, /*41*/ desiredC, /*42*/ desiredCsharp, /*43*/desiredD, /*44*/ desiredDsharp, /*45*/ desiredE, /*46*/ desiredF, /*47*/ desiredFsharp, /*48*/ desiredG, /*49*/ desiredGsharp, /*50*/ desiredA, /*51*/ desiredAsharp, /*52*/ desiredB, /*53*/ desiredEnergy];
			SendReply.kr(Impulse.kr(20), "/training", features);
			Out.kr(b.index, [cFilter, csFilter, dFilter, dsFilter, eFilter, fFilter, fsFilter, gFilter, gsFilter, aFilter, asFilter, bFilter]);
			Out.ar(0, in0);
		}).play;

		//receiving features and adding them to a list
		OSCdef (\feat, {arg msg, time;
			var d = Dictionary.new;
			d[\time] = time;
			///*1*/d[\amplitude] = msg[3].linlin(0,0.5,  -1,1);// 60 Db-0Db
			/*1*/d[\loudness] = msg[3].linlin(0,64, -1,1);//0 - 64 sones
			/*2*/d[\pitch] = msg[4].linlin(16, 2900, -1,1);//set to a max of 4000 hz
			/*3*/d[\centroid] = msg[5].linlin(0, 15000, -1,1); //confirm later 0 - 20000 hz?
			/*4*/d[\filteringVeryLowFreqs] = msg[6].linlin(0,0.1, -1,1); //normalized 0.01 - 1
			/*5*/d[\filteringLowFreqs] = msg[7].linlin(0, 0.1, -1,1);
			/*6*/d[\filteringMidFreqs] = msg[8].linlin(0, 0.1, -1,1);
			/*7*/d[\filteringHighFreqs] = msg[9].linlin(0, 0.1, -1,1);
			/*8*/d[\cFilter] = msg[10].linlin(0.01, 1, -1,1);
			/*9*/d[\csFilter] = msg[11].linlin(0.01, 1, -1,1);
			/*10*/d[\dFilter] = msg[12].linlin(0.01, 1, -1,1);
			/*11*/d[\dsFilter] = msg[13].linlin(0.01, 1, -1,1);
			/*12*/d[\eFilter] = msg[14].linlin(0.01, 1, -1,1);
			/*13*/d[\fFilter] = msg[15].linlin(0.01, 1, -1,1);
			/*14*/d[\fsFilter] = msg[16].linlin(0.01, 1, -1,1);
			/*15*/d[\gFilter] = msg[17].linlin(0.01, 1, -1,1);
			/*16*/d[\gsFilter] = msg[18].linlin(0.01, 1, -1,1);
			/*17*/d[\aFilter] = msg[19].linlin(0.01, 1, -1,1);
			/*18*/d[\asFilter] = msg[20].linlin(0.01, 1, -1,1);
			/*19*/d[\bFilter] = msg[21].linlin(0.01, 1, -1,1);
			/*20*/d[\onsets] = msg[22].linlin(0,1, -1,1);//0 -> on, 1-> off
			/*21*/d[\zeroCrossing] = msg[23].linlin(0,160, -1,1); //confirm later 0 - 9000 hz?
			/*22*/d[\specFlatness] = msg[24];//.linlin(0,1, -1,1); //0 -> pure sine, 1-> white noise
			/*23*/d[\specPcile] = msg[25].linlin(21,20000, -1,1); //confirm later 0 - 20000 hz?
			////////////////////////////////////////////////////////////////////////////
			/*24*/d[\keyTrack] = msg[26].linlin(0,23, -1,1);
			/*25*/d[\quarternotetick] = msg[27].linlin(0,1, -1,1);
			/*26*/d[\eighthnotetick] = msg[28].linlin(0,1, -1,1);
			/*27*/d[\sixteenthnotetick] = msg[29].linlin(0,1, -1,1);
			/*28*/d[\tempo] = msg[30].linlin(0,2, -1,1);
			/////////////desired outputs//////////
			/*29*/d[\desiredKick] = msg[31];
			/*30*/d[\desiredHihats] = msg[32];
			/*31*/d[\desiredSnare] = msg[33];
			/*32*/d[\desiredGuira] = msg[34];
			/*33*/d[\desiredTimbal] = msg[35];
			/*34*/d[\desiredCowbell] = msg[36];
			/*35*/d[\desiredPad] = msg[37];
			/*36*/d[\desiredBass] = msg[38];
			/*37*/d[\desiredMel] = msg[39];
			/*38*/d[\desiredLoudness] = msg[40];
			/*39*/d[\desiredC] = msg[41];
			/*40*/d[\desiredCsharp] = msg[42];
			/*41*/d[\desiredD] = msg[43];
			/*42*/d[\desiredDsharp] = msg[44];
			/*43*/d[\desiredE] = msg[45];
			/*44*/d[\desiredF] = msg[46];
			/*45*/d[\desiredFsharp] = msg[47];
			/*46*/d[\desiredG] = msg[48];
			/*47*/d[\desiredGsharp] = msg[49];
			/*48*/d[\desiredA] = msg[50];
			/*49*/d[\desiredAsharp] = msg[51];
			/*50*/d[\desiredB] = msg[52];
			/*51*/d[\energy] = msg [53];
			////////////////printOutputs/////////////////
			if (printOutputs, {["kick " ++ d[\desiredKick].asFloat, "guira " ++ d[\desiredGuira].asFloat,"cowbell " ++ d[\desiredCowbell].asFloat, "keyboard "
				++ d[\desiredPad].asFloat, "bass " ++ d[\desiredBass].asFloat, 	"Mel " ++ d[\desiredMel].asFloat
			/*, "C: " ++ d[\desiredC].asFloat,  "C#: " ++ d[\desiredCsharp].asFloat,  "D: " ++ d[\desiredD].asFloat,"D#: " ++ d[\desiredDsharp].asFloat,  "E: " ++ d[\desiredE].asFloat,  "F: " ++ d[\desiredF].asFloat,  "Fsharp: " ++ d[\desiredFsharp].asFloat,  "G: " ++ d[\desiredG].asFloat,  "Gsharp: " ++ d[\desiredGsharp].asFloat,  "A: " ++ d[\desiredA].asFloat,  "Asharp: " ++ d[\desiredAsharp].asFloat,  "B: " ++ d[\desiredB].asFloat*/].postln});

			////////////////printInputs/////////////////
			if (printInputs, {"inputs" ++ [/*"loudness " ++ d[\loudness].asFloat,  "pitch: " ++ d[\pitch].asFloat, */"centroid:  " ++
				d[\centroid].asFloat, /*"onsets: "++ d[\onsets].asFloat,*/ "zeroCross: " ++ d[\zeroCrossing].asFloat, "SpecFlat: "++ d[\specFlatness].asFloat,
				"specPercentile: " ++ d[\specPcile].asFloat,  "VLowFreqs:  " ++d[\filteringVeryLowFreqs].asFloat, "lowFreqs: " ++
				d[\filteringLowFreqs].asFloat, "midFreqs: " ++ d[\filteringMidFreqs].asFloat, "highFreqs: " ++ d[\filteringHighFreqs].asFloat,
				/*	"C: " ++ d[\cFilter].asFloat, "C#: " ++ d[\csFilter].asFloat, "D: " ++ d[\dFilter].asFloat, "D#: " ++ d[\dsFilter].asFloat,
				"E: " ++ d[\eFilter].asFloat, "F: " ++ d[\fFilter].asFloat, "F#: " ++ d[\fsFilter].asFloat, "G: " ++ d[\gFilter].asFloat,
				"G#: " ++ d[\gsFilter].asFloat, "A: " ++ d[\aFilter].asFloat, "A#: " ++ d[\asFilter].asFloat, "B: " ++ d[\bFilter].asFloat,*/].postln});

			////////////////printIandO/////////////////
			if (printIandO, {["inputs" ++ [/* "pitch: " ++ d[\pitch].asFloat,"loudness: " ++ d[\loudness].asFloat, */"centroid: " ++
				d[\centroid].asFloat, "SpecFlat: "++ d[\specFlatness].asFloat,
				"specPercentile: " ++	d[\specPcile].asFloat, "zeroCross: " ++ d[\zeroCrossing].asFloat,  "onsets: "++ d[\onsets].asFloat, "VLowFreqs " ++d[\filteringVeryLowFreqs].asFloat, "lowFreqs " ++
				d[\filteringLowFreqs].asFloat, "midFreqs:  " ++ d[\filteringMidFreqs].asFloat,"highFreqs: " ++ d[\filteringHighFreqs].asFloat,/*
					"C: " ++ d[\cFilter].asFloat, "C#: " ++ d[\csFilter].asFloat, "D: " ++ d[\dFilter].asFloat, "D#: " ++ d[\dsFilter].asFloat,
				"E: " ++ d[\eFilter].asFloat, "F: " ++ d[\fFilter].asFloat, "F#: " ++ d[\fsFilter].asFloat, "G: " ++ d[\gFilter].asFloat,
				"G#: " ++ d[\gsFilter].asFloat, "A: " ++ d[\aFilter].asFloat, "A#: " ++ d[\asFilter].asFloat, "B: " ++ d[\bFilter].asFloat*/],
			   "outputs" ++ [/*"kick " ++ d[\desiredKick].asFloat,"guira " ++ d[\desiredGuira].asFloat,"cowbell " ++ d[\desiredCowbell].asFloat,*/ "teclado: "
				++ d[\desiredPad].asFloat/*,"bass: " ++ d[\desiredBass].asFloat, 	"Mel " ++ d[\desiredMel].asFloat
			, "C: " ++ d[\desiredC].asFloat,  "C#: " ++ d[\desiredCsharp].asFloat,  "D: " ++ d[\desiredD].asFloat,"D#: " ++ d[\desiredDsharp].asFloat,  "E: " ++ d[\desiredE].asFloat,  "F: " ++ d[\desiredF].asFloat,  "Fsharp: " ++ d[\desiredFsharp].asFloat,  "G: " ++ d[\desiredG].asFloat,  "Gsharp: " ++ d[\desiredGsharp].asFloat,  "A: " ++ d[\desiredA].asFloat,  "Asharp: " ++ d[\desiredAsharp].asFloat,  "B: " ++ d[\desiredB].asFloat, "energy: " ++ d[\energy].asFloat] */]].postln;});

			if (allowBroadcast, {
				toThinkingModule.sendMsg("/train", /*d[\loudness].asFloat, d[\pitch].asFloat,*/
				   d[\centroid].asFloat,  d[\specFlatness].asFloat,  d[\specPcile].asFloat, d[\zeroCrossing].asFloat, 	d[\onsets].asFloat, d[\filteringVeryLowFreqs].asFloat,
					d[\filteringLowFreqs].asFloat, d[\filteringMidFreqs].asFloat, d[\filteringHighFreqs].asFloat,/*
					 d[\cFilter].asFloat, d[\csFilter].asFloat, d[\dFilter].asFloat, d[\dsFilter].asFloat,
					d[\eFilter].asFloat, d[\fFilter].asFloat, d[\fsFilter].asFloat, d[\gFilter].asFloat, d[\gsFilter].asFloat,
					d[\aFilter].asFloat, d[\asFilter].asFloat, d[\bFilter].asFloat,  d[\desiredKick].asFloat,
					 d[\desiredGuira].asFloat, d[\desiredCowbell].asFloat,*/ d[\desiredPad].asFloat/*, d[\desiredBass].asFloat, d[\desiredMel].asFloat,	 d[\desiredC].asFloat,   d[\desiredCsharp].asFloat,  d[\desiredD].asFloat, d[\desiredDsharp].asFloat, d[\desiredE].asFloat,  d[\desiredF].asFloat,
			d[\desiredFsharp].asFloat, d[\desiredG].asFloat,  d[\desiredGsharp].asFloat, d[\desiredA].asFloat,  d[\desiredAsharp].asFloat,  d[\desiredB].asFloat, d[\energy].asFloat*/)});
		}, "/training");
	}

	*analyseBus {
		arg port = 57800, printInputs = false, bus = 4;

		~bus = bus;
		toThinkingModule = NetAddr.new("localhost", port);
		allowBroadcast = true;
		printI = printInputs;
		b = Bus.control(s, 12);
		//b.scope;
		~roles = Bus.control(s, 9);
		//~roles.scope;
		~bufferAlloc0 = Buffer.alloc(s, 1024,2);//for sampling rates 44100 and 48000
		~bufferAlloc1 = Buffer.alloc(s, 1024,2);
		~bufferAlloc2 = Buffer.alloc(s, 1024,2);
		~bufferAlloc3 = Buffer.alloc(s, 1024,2);
		~bufferAlloc4 = Buffer.alloc(s, 1024,2);
		~bufferAlloc5 = Buffer.alloc(s, 1024,2);
		~bufferAlloc6 = Buffer.alloc(s, 1024,2);

		SynthDef (\performance, {
			var in0 = In.ar(bus:~bus, numChannels:2);
			var in = Mix.ar(in0); //mixing to one channel (mono)
			var fft0 = FFT(~bufferAlloc0, in);
			var fft1 = PV_Copy(fft0, ~bufferAlloc1);
			var fft2 = PV_Copy(fft0, ~bufferAlloc2);
			var fft3 = PV_Copy(fft0, ~bufferAlloc3);
			var fft4 = PV_Copy(fft0, ~bufferAlloc4);
			var fft5 = PV_Copy(fft0, ~bufferAlloc5);
			var fft6 = PV_Copy(fft0, ~bufferAlloc6);

			//////////////////////feature extraction Ugens//////////////////
				var loudness = Loudness.kr(fft0); //loudness = amplitude = intensity
			var pitch = Pitch.kr(in, initFreq:in, minFreq:16); //pitch detection in Hertz
			// mfcc = MFCC.kr(fft1, 13); //timbre detection only for monophonic instruments/pieces
			var amplitude = Amplitude.kr(in);
			var centroid = Oye.outOfRange(SpecCentroid.kr(fft1), amplitude); //amount of brightness = amount of treble freqs
			var filteringVeryLowFreqs = Oye.outOfRange(Amplitude.kr(LPF.ar(in, 60)), amplitude); //filtering freqs as Rhythm 20hz and below
			var filteringLowFreqs = Oye.outOfRange(Amplitude.kr (BPF.ar(in, 120, 1.3)), amplitude); //filtering low freqs between 20 and 100hz
			var filteringMidFreqs = Oye.outOfRange(Amplitude.kr(BPF.ar(in, 240, 1.3)), amplitude); //filtering mid freq between 100 and 500hz
			var filteringHighFreqs = Oye.outOfRange(Amplitude.kr(HPF.ar(in,480)), amplitude); //filtering high freq above 501hz
			var cFilter =  Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,[24,36,48,60,72,84,96].midicps,0.008))), amplitude);//Filtering pitch class of C
			var csFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+1).midicps,0.008))), amplitude); //... C#
			var dFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+2).midicps,0.008))), amplitude); //... D
			var dsFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+3).midicps,0.008))), amplitude); //... D#
			var eFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+4).midicps,0.008))), amplitude); //... E
			var fFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+5).midicps,0.008))), amplitude); //... F
			var fsFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+6).midicps,0.008))), amplitude); //... F#
			var gFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+7).midicps,0.008))), amplitude); //... G
			var gsFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+8).midicps,0.008))), amplitude); //... G#
			var aFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+9).midicps,0.008))), amplitude); //... A
			var asFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+10).midicps,0.008))), amplitude); //... A#
			var bFilter = Oye.normalize(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+11).midicps,0.008))), amplitude); //... B
			var onsets = Onsets.kr(fft2,0.3,\magsum,1, 0.0001,1); //detecting intitial impulse of a sound (transient)
			var zeroCrossing = Oye.outOfRange(ZeroCrossing.kr(A2K.kr(in)), amplitude); //detecting variation or changes in the signal
			var specFlatness = Oye.outOfRange(SpecFlatness.kr(fft3), amplitude);// how "noise-like is a sound" (wikipedia), how tonal or noisy is, how close is a sound to being a noise, 0 = pure sinusoid to 1 = white noise
			var specPcile = Oye.outOfRange(SpecPcile.kr(fft4, 0.95), amplitude); //amount of skweness (assimetry) of a wave// cumulative distribution of the freq spectrum// in which band (high, mid or low) is the energy located
			var keyTrack = KeyTrack.kr(fft5);

		///////////////////////sending sound features to a list////////////////////////////
			var features = [/*3*/loudness, /*4*/pitch[0], /*5*/centroid, /*6*/filteringVeryLowFreqs, /*7*/ filteringLowFreqs,
				/*8*/filteringMidFreqs, /*9*/filteringHighFreqs, /*10*/ cFilter, /*11*/csFilter, /*12*/ dFilter, /*13*/ dsFilter,
				/*14*/eFilter, /*15*/fFilter, /*16*/ fsFilter, /*17*/ gFilter, /*18*/ gsFilter, /*19*/aFilter, /*20*/asFilter, /*21*/ bFilter,
				/*22*/ onsets, /*23*/ zeroCrossing, /*24*/ specFlatness, /*25*/ specPcile];
			SendReply.kr(Impulse.kr(20), "/training", features);
			Out.kr(b.index, [cFilter, csFilter, dFilter, dsFilter, eFilter, fFilter, fsFilter, gFilter, gsFilter, aFilter, asFilter, bFilter]);
			Out.kr(~roles.index, [centroid,  specFlatness, specPcile, zeroCrossing, onsets, filteringVeryLowFreqs, filteringLowFreqs, filteringMidFreqs, filteringHighFreqs]);
			Out.ar(0, in0);
		}).play;

		//receiving features and adding them to a list.linlin(0, 15000, -1,1)
		OSCdef (\feat, {arg msg, time;
			var d = Dictionary.new;
			d[\time] = time;
			///*1*/d[\amplitude] = msg[3].linlin(0,0.5,  -1,1);// 60 Db-0Db
			/*1*/d[\loudness] = msg[3].linlin(0,64, -1,1);//0 - 64 sones
			/*2*/d[\pitch] = msg[4].linlin(16, 2900, -1,1);//set to a max of 4000 hz
			/*3*/d[\centroid] = msg[5].linlin(0, 15000, -1,1); //confirm later 0 - 20000 hz?
			/*4*/d[\filteringVeryLowFreqs] = msg[6].linlin(0,0.1, -1,1); //normalized 0.01 - 1
			/*5*/d[\filteringLowFreqs] = msg[7].linlin(0, 0.1, -1,1);
			/*6*/d[\filteringMidFreqs] = msg[8].linlin(0, 0.1, -1,1);
			/*7*/d[\filteringHighFreqs] = msg[9].linlin(0, 0.1, -1,1);
			/*8*/d[\cFilter] = msg[10].linlin(0.01, 1, -1,1);
			/*9*/d[\csFilter] = msg[11].linlin(0.01, 1, -1,1);
			/*10*/d[\dFilter] = msg[12].linlin(0.01, 1, -1,1);
			/*11*/d[\dsFilter] = msg[13].linlin(0.01, 1, -1,1);
			/*12*/d[\eFilter] = msg[14].linlin(0.01, 1, -1,1);
			/*13*/d[\fFilter] = msg[15].linlin(0.01, 1, -1,1);
			/*14*/d[\fsFilter] = msg[16].linlin(0.01, 1, -1,1);
			/*15*/d[\gFilter] = msg[17].linlin(0.01, 1, -1,1);
			/*16*/d[\gsFilter] = msg[18].linlin(0.01, 1, -1,1);
			/*17*/d[\aFilter] = msg[19].linlin(0.01, 1, -1,1);
			/*18*/d[\asFilter] = msg[20].linlin(0.01, 1, -1,1);
			/*19*/d[\bFilter] = msg[21].linlin(0.01, 1, -1,1);
			/*20*/d[\onsets] = msg[22].linlin(0,1, -1,1);//0 -> on, 1-> off
			/*21*/d[\zeroCrossing] = msg[23].linlin(0,160, -1,1); //confirm later 0 - 9000 hz?
			/*22*/d[\specFlatness] = msg[24].linlin(0,1, -1,1); //0 -> pure sine, 1-> white noise
			/*23*/d[\specPcile] = msg[25].linlin(21,20000, -1,1); //confirm later 0 - 20000 hz?

			////////////////printInputs/////////////////

				if (printInputs, {"inputs" ++ [/*"loudness " ++ d[\loudness].asFloat, "pitch: " ++ d[\pitch].asFloat, */"centroid " ++
				d[\centroid].asFloat, "specFlat: " ++ d[\specFlatness].asFloat,
				"specPecile " ++ d[\specPcile].asFloat , "zeroCross " ++ d[\zeroCrossing].asFloat, "onsets " ++ d[\onsets].asFloat, "VLowFreqs " ++d[\filteringVeryLowFreqs], "lowFreqs " ++
				d[\filteringLowFreqs].asFloat, "midFreqs " ++ d[\filteringMidFreqs].asFloat, "highFreqs " ++ d[\filteringHighFreqs].asFloat,
				"C: " ++ d[\cFilter].asFloat, "C#: " ++ d[\csFilter].asFloat, "D: " ++ d[\dFilter].asFloat, "D#: " ++ d[\dsFilter].asFloat,
				"E: " ++ d[\eFilter].asFloat, "F: " ++ d[\fFilter].asFloat, "F#: " ++ d[\fsFilter].asFloat, "G: " ++ d[\gFilter].asFloat,
				"G#: " ++ d[\gsFilter].asFloat, "A: " ++ d[\aFilter].asFloat, "A#: " ++ d[\asFilter].asFloat, "B: " ++ d[\bFilter].asFloat
			].postln});


			if (allowBroadcast, {
					toThinkingModule.sendMsg("/train",
				   d[\centroid].asFloat,  d[\specFlatness].asFloat,  d[\specPcile].asFloat, d[\zeroCrossing].asFloat, 	d[\onsets].asFloat, d[\filteringVeryLowFreqs].asFloat,
					d[\filteringLowFreqs].asFloat, d[\filteringMidFreqs].asFloat, d[\filteringHighFreqs].asFloat,
					 d[\cFilter].asFloat, d[\csFilter].asFloat, d[\dFilter].asFloat, d[\dsFilter].asFloat,
					d[\eFilter].asFloat, d[\fFilter].asFloat, d[\fsFilter].asFloat, d[\gFilter].asFloat, d[\gsFilter].asFloat,
					d[\aFilter].asFloat, d[\asFilter].asFloat, d[\bFilter].asFloat, d[\loudness].asFloat, d[\onsets].asFloat, d[\filteringHighFreqs].asFloat)});
		}, "/training");
	}
}

//~queueOfTypingOp = ~queueOfTypingOp.addFirst(["insert","O"]);
/* Oye.go
~queueOfTypingOp = ~queueOfTypingOp.addFirst(["insert","O"]);
q = q.addFirst(["insert","y"]);
q = q.addFirst(["insert","e"]);
q = q.addFirst(["insert","."]);
q = q.addFirst(["insert","g"]);
q = q.addFirst(["insert","o"]);
q = q.addFirst(["evaluate","Oye.go"]);*/


