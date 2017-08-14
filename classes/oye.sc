Oye {
	classvar <version; classvar <s; classvar <w;	classvar <toThinkingModule; 	classvar <printI; classvar <b;    classvar <openDoc; classvar <currentDocument; 	classvar <state; 	classvar <musicalMode; classvar <queueOfTypingOp; 	classvar <op; 	classvar <contador; classvar <lThreeInputs; classvar <lThreePitchInputs; classvar <pitchSet; classvar <newPitchSet; classvar <lOneOutputs; classvar <firstPattern; 	classvar <secondPattern; classvar <potentialChangesOnBass; classvar <potentialChangesOnPad ; classvar <potentialChangesOnMelody; classvar <firstPatternChosen; classvar <secondPatternChosen; classvar <instrumentsAlreadyUsedInPad; classvar <durationsAlreadyUsedInPad; classvar <panAlreadyUsedInPad; classvar <instrumentsAlreadyUsedInMel; classvar <durationsAlreadyUsedInMel; classvar <patternsAlreadyUsedInMel; classvar <relAlreadyUsedInMel; classvar <panAlreadyUsedInMel; classvar <durAlreadyUsedInCowbell; classvar <panAlreadyUsedInCowbell; classvar <panAlreadyUsedInKick; classvar <panAlreadyUsedInGuira; classvar <oldMelPitches; classvar <presentYourself; classvar <cMajorScale; classvar <dMajorScale; classvar <eMajorScale; classvar <fMajorScale; classvar <gMajorScale; classvar <aMajorScale; classvar <bMajorScale; classvar <currentMelDb; classvar <currentMelDb; classvar <currentMelDb; classvar <	hasKick; classvar <hasGuira; classvar <hasCowbell; classvar <numOfBassChords; classvar <numOfPadChords; classvar <	luisIsSounding; classvar <loudnessOfinstrumentsSounding; classvar <isNetReady;  classvar <showSlidersL1; classvar <netPort; classvar <showNetInput; classvar <luisDoc; classvar <listOfPossiblePitches; classvar <root; classvar <majorChord; classvar <chord; classvar <minorChord; classvar <listOfPossibilitiesForFirstPattern; classvar <lThreeAvailInputs; classvar <melPitches;  classvar <padPitches; classvar <bassPitches; classvar <listOfPossibilitiesForSecondPattern; classvar <newMelPitches; classvar <instrumentsAvailForMel; classvar <newInstrumentForMel; classvar <durationsAvailForMel; classvar <newDurationsForMel; classvar <patternAvailForMel; classvar <newPatternForMel; classvar <relAvailForMel; classvar <newRelForMel; classvar <panAvailForMel; classvar <newPanForMel; classvar <pastMelDb; classvar <newMelDb; classvar <oldBassPitches; classvar <newBassPitches; classvar <moreBassPitches; classvar <pastBajoDb; classvar <newBajoDb; classvar <oldPadChordPitches; classvar <newPadPitches; classvar <moreChordsPitches; classvar <instrumentsAvailForPad; classvar <newInstrumentForPad; classvar <durationsAvailForPad; classvar <newDurationsForPad; classvar <panAvailForPad; classvar <newPanForPad; classvar <pastPadDb; classvar <newPadDb; classvar <potentialChangesOnRitmo; classvar <dbAlreadyUsedInKick; classvar <kick; classvar <dbAlreadyUsedInCowbell; 	classvar <cowbell; classvar <dbAlreadyUsedInGuira; classvar <guira; classvar <panAvailForKick; classvar <newPanForKick; classvar <panAvailForGuira; classvar <newPanForGuira; classvar <panAvailForCowbell; classvar <newPanForCowbell; classvar <durAvailForCowbell; classvar <newDurForCowbell; classvar <decision; 	classvar <waitEnd; classvar <findThingToErase; classvar <inWhichInstrument; classvar <whereIsLastParenthesisOfThatInstrument; classvar <whereIsVarToFind; classvar <whereIsThingToFind; classvar <locateDoubleSpacesBeforeSqBracket; classvar <text; classvar <spacesBeforeSqBracket; classvar <currentTime; classvar <mMW; classvar <mMS;  classvar <mBW; classvar <mBS; classvar <mPW; classvar <mPS;  classvar <mCBW; classvar <mCBS; classvar <mKW; classvar <mKS; classvar <mGW; classvar mGS;  classvar <loudnessL3W; classvar <loudnessL3S; classvar <melW; classvar <padW; classvar <bassW; classvar <guiraW; classvar <cowbellW; classvar <kickW; classvar <melS; classvar <padS; classvar <bassS; classvar <guiraS; classvar <cowbellS; classvar <kickS; classvar <cW; classvar <csW; classvar <dW; classvar <dsW; classvar <eW; classvar <fW; classvar <fsW; classvar <gW; classvar <gsW; classvar <aW; classvar <asW; classvar <bW; classvar <energyW; classvar <cS; classvar <csS; classvar <dS; classvar <dsS; classvar <eS; classvar <fS; classvar <fsS; classvar <gS; classvar <gsS; classvar <aS; classvar <asS;  classvar <bS; classvar <energyS;  classvar <makeKick; classvar <makeGuira; classvar <makeCowBell; classvar <makeBajo; classvar <makeMel; classvar <makePad;  classvar <loudnessL1; classvar <loudnessL3; classvar <d; classvar <useC; classvar <useCsharp;  classvar <useD; classvar <useDsharp; classvar <useE; classvar <useF; classvar <useFsharp; classvar <useG; classvar <useGsharp; classvar <useA;  classvar  <useAsharp; classvar <useB; classvar <energyL3; classvar <usecW; classvar <usecS;   classvar <usecsW; classvar <usecsS; classvar<usedW; classvar <usedS; classvar < usedsW; 	classvar <usedsS; classvar <useeW; classvar <useeS; classvar <usefW; classvar <usefS; classvar <usefsW;  classvar <usefsS; classvar <usegW; classvar <usegS; classvar <usegsW; classvar<usegsS; classvar <useaW; classvar <useaS; classvar <useasW; classvar <useasS; classvar <usebS; classvar <usebW; classvar <energyL3W; classvar <energyL3S;   classvar makeMelodyString; classvar makeBajoString; classvar <mapSlidersL3; classvar <pitchCase;

	*init {
		version = "Jun 2017";
		RGGTRN.turboCargar;
		openDoc = Document.open("/home/lui/cacharpo.scd");
		currentDocument = Document.current("/home/lui/cacharpo.scd");
		state = "deciding";
		musicalMode = "waitingForLuisToType";
		queueOfTypingOp = [];
		op = nil;
		contador = 0;
		lThreeInputs = Dictionary.new;
		lThreePitchInputs = Dictionary.new;
		pitchSet = [];
		newPitchSet = [];
		loudnessL1 = 0;
		lOneOutputs  = Dictionary.new;
		lOneOutputs = lOneOutputs.putAll(Dictionary[\kick -> 0, \guira -> 0, \cowbell -> 0, \bajo -> 0, \pad -> 0, \mel -> 0, \c -> 0, \cs -> 0, \d -> 0, \ds -> 0, \e -> 0, \f -> 0,\fs -> 0,\g -> 0, \gs -> 0, \a -> 0, \as -> 0, \b -> 0]);
		lThreeInputs = (makeKick: 0, makeGuira: 0, makeCowBell: 0, makeBajo: 0, makePad: 0, makeMelody: 0, loudness: 0, useC: 0, useCsharp: 0, useD: 0,  useDsharp: 0,  useE: 0, useF:0, useFsharp: 0, useG:0, useGsharp: 0,  useA:0, useAsharp: 0,  useB:0, energy:0);
		firstPattern = nil;
		secondPattern = nil;
		potentialChangesOnBass = nil;
		potentialChangesOnPad = nil;
		potentialChangesOnMelody = nil;
		firstPatternChosen = nil;
		secondPatternChosen = nil;
		instrumentsAlreadyUsedInPad =Array.new.addFirst('padE');
		durationsAlreadyUsedInPad = Array.new.addFirst(0.5);
		panAlreadyUsedInPad = Array.new.addFirst(0);
		instrumentsAlreadyUsedInMel = Array.new.addFirst('melE');
		durationsAlreadyUsedInMel = Array.new.addFirst(0.5);
		patternsAlreadyUsedInMel = Array.new.addFirst(Pseq);
		relAlreadyUsedInMel = Array.new.addFirst(0.25);
		panAlreadyUsedInMel = Array.new.addFirst(0);
		durAlreadyUsedInCowbell = Array.new.addFirst(1);
		panAlreadyUsedInCowbell = Array.new.addFirst(0);
		panAlreadyUsedInKick = Array.new.addFirst(0);
		panAlreadyUsedInGuira = Array.new.addFirst(0);
		oldMelPitches = [];
		presentYourself = false;
		currentMelDb = -12;
		currentMelDb = -15;
		currentMelDb = -7;
		hasKick = false;
		hasGuira = false;
		hasCowbell = false;
		numOfBassChords = 0;
		numOfPadChords = 0;
		luisIsSounding = false;
		loudnessOfinstrumentsSounding = 0;
		("Oye v.1.0_" ++ version ++ ": initalized").postln;
	}

	*go {arg port = 57800, slidersL3 = false,  slidersL1 = false, netReady = false,  printNetInput = false;
		mapSlidersL3 = slidersL3;
		Oye.init;
		Oye.receiveL1OutputsFromNet(slidersL1, netReady, printNetInput);
		Oye.analyseSound(port:port);
		if (slidersL3, {Oye.slidersL3;});
		Tdef (\stateMachine, {inf.do {
			musicalMode.postln;
			Oye.l2;
			Oye.whichState;
			0.1.wait;
		}}).play(TempoClock(tempo: 150/60));
	}

	*typingDetection {
		luisDoc = Document.current;
		luisDoc.keyDownAction = { |doc char modifier ascii keycode|
			if (char == $/, {presentYourself = true});
		}
	}

	*decide {
		// if nothing has been typed yet, and we haven't typed anything, continue to wait
		if(musicalMode == "waitingForLuisToType", {
			Oye.typingDetection;
			if( presentYourself == false, {
				^["wait", Main.elapsedTime + 5.0];
			},{musicalMode = "presentYourself";});
		});
		if(musicalMode == "presentYourself", {
			musicalMode = "waitingForLuisToSound";
			^["presentYourself", "     //////////////cacharpo v.1.0//////////////     " ++ "\n" ];
		});

		// if nothing has been heard yet, and we haven't heard anything, continue to wait
		if(musicalMode == "waitingForLuisToSound",{
			if(lThreeInputs[\loudness]<0.05,{
				^["wait", Main.elapsedTime + 5.0];
			},{
				luisIsSounding = true;
				//select available pitches from l3 Inputs..
				listOfPossiblePitches = Array.new;
				pitchCase =  case
				{lThreeInputs[\useC] >= 0.5} {listOfPossiblePitches.addFirst(60)}
				{lThreeInputs[\useD] >= 0.5} {listOfPossiblePitches.addFirst(62)}
				{lThreeInputs[\useE] >= 0.5} {listOfPossiblePitches.addFirst(64)}
				{lThreeInputs[\useF] >= 0.5} {listOfPossiblePitches.addFirst(65)}
				{lThreeInputs[\useG] >= 0.5} {listOfPossiblePitches.addFirst(67)}
				{lThreeInputs[\useA] >= 0.5} {listOfPossiblePitches.addFirst(69)}
				{lThreeInputs[\useB] >= 0.5} {listOfPossiblePitches.addFirst(71)};
				root = listOfPossiblePitches.choose;
				if ((root == 60) || (root == 65) || (root == 67), {majorChord = [root, root + 4, root + 7]; chord = majorChord});
				if ((root == 62) || (root == 64) || (root == 69), {minorChord = [root, root + 3, root + 7]; chord = minorChord});
				pitchSet = [chord[0], chord[1], chord[2]];
				musicalMode = "addFirstPattern";});
		});
		//////////////////////////////////////////add first and second patterns//////////////////////////////////////////

		if(musicalMode == "addFirstPattern", {
			//select available roles from l3 Inputs..
			/*	listOfPossibilitiesForFirstPattern = Array.new;
			lThreeAvailInputs = lThreeInputs.keysValuesDo{arg key,value; if (value >= 0.8,
			{listOfPossibilitiesForFirstPattern = listOfPossibilitiesForFirstPattern.add(key)}
			)};
			// pick a role for that role...based on coins
			listOfPossibilitiesForFirstPattern.remove(\loudness); listOfPossibilitiesForFirstPattern.remove(\useC); listOfPossibilitiesForFirstPattern.remove(\useCsharp);
			listOfPossibilitiesForFirstPattern.remove(\useD); listOfPossibilitiesForFirstPattern.remove(\useDsharp); listOfPossibilitiesForFirstPattern.remove(\useE);
			listOfPossibilitiesForFirstPattern.remove(\useF); listOfPossibilitiesForFirstPattern.remove(\useFsharp); listOfPossibilitiesForFirstPattern.remove(\useG);
			listOfPossibilitiesForFirstPattern.remove(\useGsharp); listOfPossibilitiesForFirstPattern.remove(\useA); listOfPossibilitiesForFirstPattern.remove(\useAsharp);
			listOfPossibilitiesForFirstPattern.remove(\useB); listOfPossibilitiesForFirstPattern.remove(\energy);
			listOfPossibilitiesForFirstPattern = listOfPossibilitiesForFirstPattern.choose; 	if (lThreeInputs	[\makeKick] >=0.8, {listOfPossibilitiesForSecondPattern.remove(\makeGuiro); listOfPossibilitiesForSecondPattern.remove(\makeCowbell)});*/
			//firstPatternChosen = listOfPossibilitiesForFirstPattern.asString;
			firstPatternChosen = "makeCowBell";
			//pick a pattern
			if (firstPatternChosen == "makeMelody", {
				firstPattern = "Melody";
				melPitches = pitchSet.choose;
				musicalMode = "waitingToHearTheChanges";
				^["makeMelody", "Mel.toca(inst: 'melE',"  ++ "\n"
					++ "nota: " ++  "Pseq(#[" ++ melPitches.asString ++ "], inf)," ++ "\n"
					++ "dur: 0.5," ++ "\n"
					++ "atk: 0.025," ++ "\n"
					++ "rel: 0.25," ++ "\n"
					++ "arpegiar: 0," ++ "\n"
					++ "pan: 0," ++ "\n"
					++ "db: -20" ++ "\n"
					++ ");"  ++ "\n"];
			});

			if (firstPatternChosen == "makePad", {
				firstPattern = "Pad";
				padPitches = [pitchSet[0],pitchSet[1],pitchSet[2]];
				musicalMode = "waitingToHearTheChanges";
				^["makePad", "Teclado.toca(inst: 'padE',"  ++ "\n"
					++ "nota: "  ++ "Pseq(#[" ++ "r, " ++ padPitches.asString ++ "]-12, inf)," ++ "\n"
					++ "dur: 0.5," ++ "\n"
					++ "atk: 0.05," ++ "\n"
					++ "rel: 0.25," ++ "\n"
					++ "pan: 0," ++ "\n"
					++ "db: -20" ++ "\n"
					++ ");" ++ "\n"];
			});

			if (firstPatternChosen == "makeBajo", {
				firstPattern = "Bajo";
				bassPitches = [pitchSet[0], pitchSet[1], pitchSet[2]];
				numOfBassChords = numOfBassChords + 1;
				musicalMode = "waitingToHearTheChanges";
				^["makeBajo", "Bajo.toca(" ++ "\n" ++ "nota: " ++ "Pseq(" ++ bassPitches.asString ++ "-12" ++", inf)," ++ "\n"
					++ "pan: 0," ++ "\n"
					++ "db: -22" ++ "\n"
					++ ");" ++ "\n"];
			});

			if   (firstPatternChosen == "makeCowBell", {
				firstPattern = "Ritmo";
				musicalMode = "waitingToHearTheChanges";
				hasCowbell = true;
				^["makeCowbell", "Ritmo.toca("++"\n"
					++"campanaDb: -20," ++ "\n"
					++"campanaDur: 1,"++ "\n"
					++"campanaPan: 0,"++ "\n"
					++");"++ "\n"];
			});

			if   (firstPatternChosen == "makeKick", {
				firstPattern = "Ritmo";
				musicalMode = "waitingToHearTheChanges";
				hasKick = true;
				^["makeKick", "Ritmo.toca("++"\n"
					++"bomboDb: -20," ++"\n"
					++"bomboPan: 0," ++"\n"
					++ ");"++ "\n"];
			});

			if   (firstPatternChosen == "makeGuira", {
				firstPattern = "Ritmo";
				musicalMode = "waitingToHearTheChanges";
				hasGuira = true;
				^["makeGuira", "Ritmo.toca("++"\n"
					++"guiraDb: -60," ++"\n"
					++"guiraDur: 0.25,"++"\n"
					++ "guiraPan: 0,"++"\n"
					++");" ++ "\n"];
			});
		});

		if(musicalMode == "addSecondPattern", {
			//select available roles for 2nd role from l3 Inputs..
			listOfPossibilitiesForSecondPattern = Array.new;
			lThreeAvailInputs = lThreeInputs.keysValuesDo{arg key,value; if (value >= 0.8,
				{listOfPossibilitiesForSecondPattern = listOfPossibilitiesForSecondPattern.add(key)}
			)};

			// pick a second role for that role
			listOfPossibilitiesForSecondPattern.remove(\loudness); listOfPossibilitiesForSecondPattern.remove(\useCmajorScale);listOfPossibilitiesForSecondPattern.remove(\useCsharp); listOfPossibilitiesForSecondPattern.remove(\useD); listOfPossibilitiesForSecondPattern.remove(\useDsharp); listOfPossibilitiesForSecondPattern.remove(\useE); listOfPossibilitiesForSecondPattern.remove(\useF); listOfPossibilitiesForSecondPattern.remove(\useFsharp); listOfPossibilitiesForSecondPattern.remove(\useG);   listOfPossibilitiesForSecondPattern.remove(\useGsharp); listOfPossibilitiesForSecondPattern.remove(\useA); listOfPossibilitiesForSecondPattern.remove(\useAsharp); listOfPossibilitiesForSecondPattern.remove(\useB); listOfPossibilitiesForSecondPattern.remove(\energy); 	listOfPossibilitiesForSecondPattern.remove(firstPatternChosen.asSymbol);
if (firstPattern == "Ritmo", { listOfPossibilitiesForSecondPattern.remove(\makeKick);  listOfPossibilitiesForSecondPattern.remove(\makeGuira);   listOfPossibilitiesForSecondPattern.remove(\makeCowBell)});
			if (listOfPossibilitiesForSecondPattern.notEmpty, {
				listOfPossibilitiesForSecondPattern = listOfPossibilitiesForSecondPattern.choose;
				secondPatternChosen = listOfPossibilitiesForSecondPattern.asString;


				//pick a second pattern
				if (secondPatternChosen == "makeMelody", {
					secondPattern = "Melody";
					melPitches = pitchSet.choose;
					musicalMode = "waitingToHearTheChanges";
					^["makeMelody", "Mel.toca(inst: 'melE',"  ++ "\n"
						++ "nota: " ++  "Pseq(#[" ++ melPitches.asString ++ "], inf)," ++ "\n"
						++ "dur: 0.5," ++ "\n"
						++ "atk: 0.025," ++ "\n"
						++ "rel: 0.25," ++ "\n"
						++ "arpegiar: 0," ++ "\n"
						++ "pan: 0," ++ "\n"
						++ "db: -20" ++ "\n"
						++ ");"  ++ "\n"];
				});

				if (secondPatternChosen == "makePad", {
					secondPattern = "Pad";
					padPitches =[pitchSet[0], pitchSet[1], pitchSet[2]];
					musicalMode = "waitingToHearTheChanges";
					^["makePad", "Teclado.toca(inst: 'padE',"  ++ "\n"
						++ "nota: "  ++ "Pseq(#[" ++ "r, " ++ padPitches.asString ++ "]-12, inf)," ++ "\n"
						++ "dur: 0.5," ++ "\n"
						++ "atk: 0.05," ++ "\n"
						++ "rel: 0.25," ++ "\n"
						++ "pan: 0," ++ "\n"
						++ "db: -20" ++ "\n"
						++ ");" ++ "\n"];
				});

				if (secondPatternChosen == "makeBajo", {
					secondPattern = "Bajo";
					bassPitches = [pitchSet[0], pitchSet[1], pitchSet[2]];
					numOfBassChords = numOfBassChords + 1;
					musicalMode = "waitingToHearTheChanges";
					^["makeBajo", "Bajo.toca(" ++ "\n"
						++ "nota: " ++ "Pseq(" ++ bassPitches.asString ++ "-12" ++", inf)," ++ "\n"
						++ "pan: 0," ++ "\n"
						++ "db: -22" ++ "\n"
						++ ");"++ "\n"];
				});

				if   (secondPatternChosen == "makeCowBell", {
					secondPattern = "Ritmo";
					musicalMode = "waitingToHearTheChanges";
					hasCowbell = true;
					^["makeCowbell", "Ritmo.toca("++"\n"
						++"campanaDb: -15," ++ "\n"
						++"campanaDur: 1,"++ "\n"
						++"campanaPan: 0,"++ "\n"
						++");"++ "\n"];
				});

				if   (secondPatternChosen == "makeKick", {
					secondPattern = "Ritmo";
					musicalMode = "waitingToHearTheChanges";
					hasKick = true;
					^["makeKick", "Ritmo.toca("++"\n"
						++"bomboDb: -15," ++"\n"
						++"bomboPan: 0," ++"\n"
						++");"++ "\n"];
				});

				if   (secondPatternChosen == "makeGuira", {
					secondPattern = "Ritmo";
					musicalMode = "waitingToHearTheChanges";
					hasGuira = true;
					^["makeGuira", "Ritmo.toca("++"\n"
						++"guiraDb: -60," ++"\n"
						++"guiraDur: 0.25,"++"\n"
						++ "guiraPan: 0,"++"\n"
						++");" ++ "\n"];
				});
			});
		});

		//////////////////////////////potential changes on melody//////////////////////
		if(musicalMode == "changeMelody", {
			potentialChangesOnMelody = ["addMorePitchesToMelody", "tryDifferentInstrumentOnMelody", "tryDifferentDurationOnMelody", "changePseqToPrandOnMelody","changeReleaseOnMel", "tryDifferentPanOnMel"/*, "endingMel"*/].choose;
			["~potentialChangesOnMel: " ++ potentialChangesOnMelody].postln;

			if(potentialChangesOnMelody == "addMorePitchesToMelody", {
				//update available scales from l3 Inputs..
				listOfPossiblePitches = Array.new;
				pitchCase =  case
				{lThreeInputs[\useC] >= 0.5} {listOfPossiblePitches.addFirst(60)}
				{lThreeInputs[\useD] >= 0.5} {listOfPossiblePitches.addFirst(62)}
				{lThreeInputs[\useE] >= 0.5} {listOfPossiblePitches.addFirst(64)}
				{lThreeInputs[\useF] >= 0.5} {listOfPossiblePitches.addFirst(65)}
				{lThreeInputs[\useG] >= 0.5} {listOfPossiblePitches.addFirst(67)}
				{lThreeInputs[\useA] >= 0.5} {listOfPossiblePitches.addFirst(69)}
				{lThreeInputs[\useB] >= 0.5} {listOfPossiblePitches.addFirst(71)};
				root = listOfPossiblePitches.choose;
				if ((root == 60) || (root == 65) || (root == 67), {majorChord = [root, root + 4, root + 7]; chord = majorChord});
				if ((root == 62) || (root == 64) || (root == 69), {minorChord = [root, root + 3, root + 7]; chord = minorChord});
				pitchSet = [chord[0], chord[1], chord[2]];

				oldMelPitches =  oldMelPitches.addFirst(melPitches);
				melPitches = pitchSet.choose;
				newMelPitches = melPitches ;
				musicalMode = "waitingToHearTheChanges";
				potentialChangesOnMelody = nil;
				^["addMorePitchesToMelody", newMelPitches.asString, "Mel", ");",oldMelPitches[0].asString, "nota: "];
			});

			if(potentialChangesOnMelody == "tryDifferentInstrumentOnMelody", {
				instrumentsAvailForMel = ['melE', 'mel2E', 'mel3E', 'pluckE', 'pluckTriE'];
				instrumentsAvailForMel.remove(instrumentsAlreadyUsedInMel[0]);
				newInstrumentForMel = instrumentsAvailForMel.choose;
				instrumentsAlreadyUsedInMel = instrumentsAlreadyUsedInMel.addFirst(newInstrumentForMel);
				musicalMode = "waitingToHearTheChanges";
				potentialChangesOnMelody = nil;
				^["tryDifferentInstrumentOnMelody", "'" ++ newInstrumentForMel.asString ++ "'", "Mel", ");", "'" ++ instrumentsAlreadyUsedInMel[1].asString ++ "'", "inst: "];
			});

			if(potentialChangesOnMelody == "tryDifferentDurationOnMelody", {
				durationsAvailForMel = [0.25, 0.5]; 		// durations commonly used in mel
				durationsAvailForMel.remove(durationsAlreadyUsedInMel[0]);
				newDurationsForMel = durationsAvailForMel.choose; //choose from that list the available ones
				//save list of instruments as oldPadInstruments
				durationsAlreadyUsedInMel = durationsAlreadyUsedInMel.addFirst(newDurationsForMel);
				musicalMode = "waitingToHearTheChanges";
				potentialChangesOnMelody = nil;
				^["tryDifferentDurationOnMelody", newDurationsForMel.asString, "Mel", ");", durationsAlreadyUsedInMel[1].asString, "dur: "];
			});

			if(potentialChangesOnMelody == "changePseqToPrandOnMelody", {
				patternAvailForMel = [Pseq, Prand]; 		//durations are commonly used in Pad
				patternAvailForMel.remove(patternsAlreadyUsedInMel[0]);
				newPatternForMel = patternAvailForMel.choose; //choose from that list the available ones
				//save list of instruments as oldPadInstruments
				patternsAlreadyUsedInMel = patternsAlreadyUsedInMel.addFirst(newPatternForMel);
				musicalMode = "waitingToHearTheChanges";
				potentialChangesOnMelody = nil;
				^["changePseqToPrandOnMelody", newPatternForMel.asString, "Mel", ");", patternsAlreadyUsedInMel[1].asString, "nota: "];
			});

			if(potentialChangesOnMelody == "changeReleaseOnMel", {
				relAvailForMel = [0.25, 0.5, 1, 2, 4]; // durations available for Mel
				relAvailForMel.remove(relAlreadyUsedInMel[0]);
				newRelForMel = relAvailForMel.choose; //choose from that list the available ones
				//save list of old selected releases
				relAlreadyUsedInMel = relAlreadyUsedInMel.addFirst(newRelForMel);
				musicalMode = "waitingToHearTheChanges";
				potentialChangesOnMelody = nil;
				^["changeReleaseOnMel", newRelForMel.asString, "Mel", ");", relAlreadyUsedInMel[1].asString, "rel"];
			});

			if(potentialChangesOnMelody == "tryDifferentPanOnMel", {
				panAvailForMel = [-1, 0, 1]; // durations  available for Mel
				panAvailForMel.remove(panAlreadyUsedInMel[0]);
				newPanForMel = panAvailForMel.choose; //choose from that list the available ones
				//save list of old selected releases
				panAlreadyUsedInMel = panAlreadyUsedInMel.addFirst(newPanForMel);
				musicalMode = "waitingToHearTheChanges";
				potentialChangesOnMelody = nil;
				^["tryDifferentPanOnMel", newPanForMel.asString, "Mel", ");", panAlreadyUsedInMel[1].asString, "pan"];
			});

			if(potentialChangesOnMelody == "endingMel", {
				if((lThreeInputs[\loudness] <= 0.25) && (luisIsSounding == true) && (firstPattern != nil) && (secondPattern != nil), {
					pastMelDb = currentMelDb;
					currentMelDb = currentMelDb - 1;
					newMelDb = currentMelDb;
					musicalMode = "waitingToHearTheChanges";
					^["endingMel", pastMelDb.asString, "Mel", ");", newMelDb.asString, "db:"];
				}, {musicalMode = "waitingToHearTheChanges";});
			}, {musicalMode = "waitingToHearTheChanges";
			});
		});

		//////////////////////////////potential changes for Bass//////////////////////
		if (musicalMode == "changeBajo", {
			potentialChangesOnBass = ["addMorePitchesToBass", "holdBass", "endingBass"].choose;
			["~potentialChangesOnBajo: " ++ potentialChangesOnBass].postln;

			if(potentialChangesOnBass == "addMorePitchesToBass", {
				if (Oye.sensor[2] < 1, {
					//update available scales from l3 Inputs..
					listOfPossiblePitches = Array.new;
					listOfPossiblePitches = Array.new;
					pitchCase =  case
					{lThreeInputs[\useC] >= 0.5} {listOfPossiblePitches.addFirst(60)}
					{lThreeInputs[\useD] >= 0.5} {listOfPossiblePitches.addFirst(62)}
					{lThreeInputs[\useE] >= 0.5} {listOfPossiblePitches.addFirst(64)}
					{lThreeInputs[\useF] >= 0.5} {listOfPossiblePitches.addFirst(65)}
					{lThreeInputs[\useG] >= 0.5} {listOfPossiblePitches.addFirst(67)}
					{lThreeInputs[\useA] >= 0.5} {listOfPossiblePitches.addFirst(69)}
					{lThreeInputs[\useB] >= 0.5} {listOfPossiblePitches.addFirst(71)};
					root = listOfPossiblePitches.choose;
					if ((root == 60) || (root == 65) || (root == 67), {majorChord = [root, root + 4, root + 7]; chord = majorChord});
					if ((root == 62) || (root == 64) || (root == 69), {minorChord = [root, root + 3, root + 7]; chord = minorChord});
					pitchSet = [chord[0], chord[1], chord[2]];

					newPitchSet = [chord[0], chord[1], chord[2]];
					if (newPitchSet.notEmpty, {
						if (bassPitches != newPitchSet, {
							oldBassPitches = [bassPitches[0], bassPitches[1], bassPitches[2]];
							bassPitches = newPitchSet;
							newBassPitches = bassPitches ;

							if (newBassPitches.notEmpty, {
								moreBassPitches = [newBassPitches[0], newBassPitches[1], newBassPitches[2]];
								newBassPitches = newBassPitches.drop(3);
								musicalMode = "waitingToHearTheChanges";
								numOfBassChords = numOfBassChords + 1;
								potentialChangesOnBass = nil;
								^["addMorePitchesToBass", moreBassPitches.asString, "Bajo", ");",oldBassPitches.asString, "nota: " ];
							});
						}, { musicalMode = "waitingToHearTheChanges";
							potentialChangesOnBass = nil;

						});
					}, { musicalMode = "waitingToHearTheChanges";
						potentialChangesOnBass = nil;
					});
				}, { musicalMode = "waitingToHearTheChanges";
					potentialChangesOnBass = nil;
				});
			});

			if(potentialChangesOnBass == "holdBass", {
				if (~soloTimbal.notNil, {
					musicalMode = "waitingToHearTheChanges";
					^["holdBass", "toca", "Bajo", ");","detener"];
				}, { musicalMode = "waitingToHearTheChanges";
					potentialChangesOnBass = nil;
				});
			}, { musicalMode = "waitingToHearTheChanges";
				potentialChangesOnBass = nil;
			});

			if(potentialChangesOnBass == "endingBass", {
				if((lThreeInputs[\loudness] <= 0.25) && (luisIsSounding == true) && (firstPattern != nil) && (secondPattern != nil), {
					pastBajoDb = currentMelDb;
					currentMelDb = currentMelDb - 1;
					newBajoDb = pastBajoDb;
					musicalMode = "waitingToHearTheChanges";
					^["endingBass", pastBajoDb.asString, "Bajo", ");", newBajoDb.asString, "db:"];
				}, {musicalMode = "waitingToHearTheChanges";});
			}, {musicalMode = "waitingToHearTheChanges";
			});
		});

		//////////////////////////////potential changes on Pad//////////////////////
		if (musicalMode == "changePad", {
			potentialChangesOnPad = ["tryDifferentInstrumentOnPad", "addMoreChordsToPad",/* "tryDifferentDurationOnPad",*/ "tryDifferentPanOnPad", "endingPad"].choose;
			["potentialChangesOnPad: " ++ potentialChangesOnPad].postln;

			if(potentialChangesOnPad == "addMoreChordsToPad", {
				if (Oye.sensor[3] < 1 , {

					//update available scales from l3 Inputs..
					listOfPossiblePitches = Array.new;
					pitchCase =  case
					{lThreeInputs[\useC] >= 0.5} {listOfPossiblePitches.addFirst(60)}
					{lThreeInputs[\useD] >= 0.5} {listOfPossiblePitches.addFirst(62)}
					{lThreeInputs[\useE] >= 0.5} {listOfPossiblePitches.addFirst(64)}
					{lThreeInputs[\useF] >= 0.5} {listOfPossiblePitches.addFirst(65)}
					{lThreeInputs[\useG] >= 0.5} {listOfPossiblePitches.addFirst(67)}
					{lThreeInputs[\useA] >= 0.5} {listOfPossiblePitches.addFirst(69)}
					{lThreeInputs[\useB] >= 0.5} {listOfPossiblePitches.addFirst(71)};
					root = listOfPossiblePitches.choose;
					if ((root == 60) || (root == 65) || (root == 67), {majorChord = [root, root + 4, root + 7]; chord = majorChord});
					if ((root == 62) || (root == 64) || (root == 69), {minorChord = [root, root + 3, root + 7]; chord = minorChord});
					pitchSet = [chord[0], chord[1], chord[2]];

					if (newPitchSet.notEmpty, {
						if (padPitches != newPitchSet, {
							oldPadChordPitches = [padPitches[0], padPitches[1], padPitches[2]];
							padPitches = newPitchSet;
							newPadPitches = padPitches;
							if (newPadPitches.notEmpty, {
								moreChordsPitches = [newPadPitches[0],newPadPitches[1], newPadPitches[2]];
								newPadPitches = newPadPitches.drop(3);
								musicalMode = "waitingToHearTheChanges";
								numOfPadChords = numOfPadChords + 1;
								potentialChangesOnPad = nil;
								^["addMoreChordsToPad", moreChordsPitches.asString, "Teclado", ");", oldPadChordPitches.asString, "nota: "];
							});
						}, { musicalMode = "waitingToHearTheChanges";
							potentialChangesOnPad = nil;
						});
					}, { musicalMode = "waitingToHearTheChanges";
						potentialChangesOnPad = nil;
					});
				}, {oldPadChordPitches
					^["deleteChordsFromPad", moreChordsPitches.asString, "Teclado", ");", oldPadChordPitches.asString, "nota: "];});
			});

			if(potentialChangesOnPad == "tryDifferentInstrumentOnPad", {
				instrumentsAvailForPad = ['padE', 'pad2E', ]; 		// instruments  for Pad
				instrumentsAvailForPad.remove(instrumentsAlreadyUsedInPad[0]);
				newInstrumentForPad = instrumentsAvailForPad.choose; //choose from that list the availables one
				//save list of instruments as oldPadInstruments
				instrumentsAlreadyUsedInPad = instrumentsAlreadyUsedInPad.addFirst(newInstrumentForPad);
				musicalMode = "waitingToHearTheChanges";
				potentialChangesOnPad = nil;
				^["tryDifferentInstrumentOnPad", "'" ++ newInstrumentForPad.asString ++ "'", "Teclado", ");", "'" ++ instrumentsAlreadyUsedInPad[1].asString ++ "'", "inst: "];
			});

			if(potentialChangesOnPad == "tryDifferentDurationOnPad", {
				durationsAvailForPad = [0.5, 1]; 		// durations commonly used in Pad
				durationsAvailForPad.remove(durationsAlreadyUsedInPad[0]);
				newDurationsForPad = durationsAvailForPad.choose; //choose from that list the available ones
				//save list of instruments as oldPadInstruments
				durationsAlreadyUsedInPad = durationsAlreadyUsedInPad.addFirst(newDurationsForPad);
				musicalMode = "waitingToHearTheChanges";
				potentialChangesOnPad = nil;
				^["tryDifferentDurationOnPad", newDurationsForPad.asString, "Teclado", ");", durationsAlreadyUsedInPad[1].asString, "dur: "];
			});

			if(potentialChangesOnPad == "tryDifferentPanOnPad", {
				panAvailForPad = [-1, 0, 1];
				panAvailForPad.remove(panAlreadyUsedInPad[0]);
				newPanForPad = panAvailForPad.choose;
				//save list of instruments as oldPadInstruments
				panAlreadyUsedInPad = panAlreadyUsedInPad.addFirst(newPanForPad);
				musicalMode = "waitingToHearTheChanges";
				potentialChangesOnPad = nil;
				^["tryDifferentPanOnPad", newPanForPad.asString, "Teclado", ");", panAlreadyUsedInPad[1].asString, "pan: "];
			});

			if(potentialChangesOnPad == "endingPad", {
				if((lThreeInputs[\loudness] <= 0.25) && (luisIsSounding == true) && (firstPattern != nil) && (secondPattern != nil), {
					pastPadDb = currentMelDb;
					currentMelDb = currentMelDb - 1;
					newPadDb = pastPadDb;
					musicalMode = "waitingToHearTheChanges";
					^["endingPad", pastPadDb.asString, "Teclado", ");", newPadDb.asString, "db:"];
				}, {musicalMode = "waitingToHearTheChanges";});
			});
		});

		//////////////////////////////potential changes for Ritmo///////////////////////////////
		if (musicalMode == "changeRitmo", {
			potentialChangesOnRitmo = ["addKick", "addGuira", "addCowbell", "tryDifferentPanOnGuira", "tryDifferentPanOnKick", "tryDifferentPanOnCowbell"].choose;
			["potentialChangesOnRitmo: " ++ potentialChangesOnRitmo].postln;

			if(potentialChangesOnRitmo == "addKick", {
				if (hasKick == false, {
					hasKick = true;
					musicalMode = "waitingToHearTheChanges";
					potentialChangesOnRitmo = nil;
					^["addKick", "," ++ "\n" ++ "bomboDb: -20,", "Ritmo", ");", ",",  ","];
				}, {musicalMode = "waitingToHearTheChanges";
				});
			});

			if(potentialChangesOnRitmo == "addCowbell", {
				if (hasCowbell == false, {
					hasCowbell= true;
					musicalMode = "waitingToHearTheChanges";
					potentialChangesOnRitmo = nil;
					^["addCowbell",  "," ++ "\n" ++ "campanaDb: -20,","Ritmo", ");", ",",  ","];
				}, {musicalMode = "waitingToHearTheChanges";
				});
			});

			if(potentialChangesOnRitmo == "addGuira", {
				if (hasGuira == false, {
					dbAlreadyUsedInGuira = -60;
					hasGuira= true;
					musicalMode = "waitingToHearTheChanges";
					potentialChangesOnRitmo = nil;
					^["addGuira", "," ++ "\n" ++ "guiraDb: -60,", "Ritmo", ");", ",",  ","];
				}, {musicalMode = "waitingToHearTheChanges";
				});
			});

			if(potentialChangesOnRitmo == "tryDifferentPanOnKick", {
				if (hasKick == true, {
					panAvailForKick = [-1, 0, 1];
					panAvailForKick.remove(panAlreadyUsedInKick[0]);
					newPanForKick = panAvailForKick.choose;
					panAlreadyUsedInKick = panAlreadyUsedInKick.addFirst(newPanForKick);
					musicalMode = "waitingToHearTheChanges";
					potentialChangesOnRitmo = nil;
					^["tryDifferentPanOnKick", newPanForKick.asString, "Ritmo", ");", panAlreadyUsedInKick[1].asString, "bomboPan: "];
				}, {musicalMode = "waitingToHearTheChanges"});
			});

			if(potentialChangesOnRitmo == "tryDifferentPanOnGuira", {
				if (hasGuira == true, {
					panAvailForGuira = [-1, 0, 1];
					panAvailForGuira.remove(panAlreadyUsedInGuira[0]);
					newPanForGuira = panAvailForGuira.choose;
					panAlreadyUsedInGuira = panAlreadyUsedInGuira.addFirst(newPanForGuira);
					musicalMode = "waitingToHearTheChanges";
					potentialChangesOnRitmo = nil;
					^["tryDifferentPanOnGuira", newPanForGuira.asString, "Ritmo", ");", panAlreadyUsedInGuira[1].asString, "guiraPan: "];
				}, {musicalMode = "waitingToHearTheChanges"});
			});

			if(potentialChangesOnRitmo == "tryDifferentPanOnCowbell", {
				if (hasCowbell == true, {
					panAvailForCowbell = [-1, 0, 1];
					panAvailForCowbell.remove(panAlreadyUsedInCowbell[0]);
					newPanForCowbell= panAvailForCowbell.choose;
					panAlreadyUsedInCowbell = panAlreadyUsedInCowbell.addFirst(newPanForCowbell);
					musicalMode = "waitingToHearTheChanges";
					potentialChangesOnRitmo = nil;
					^["tryDifferentPanOnCowbell", newPanForCowbell.asString, "Ritmo", ");", panAlreadyUsedInCowbell[1].asString, "campanaPan: "];
				}, {musicalMode = "waitingToHearTheChanges"});
			});
		});

		/////////////////////////////waiting after each decision//////////////////////
		if (musicalMode == "waitingToHearTheChanges", {
			if (secondPattern.isNil, {
				musicalMode = ["addSecondPattern", "change" ++ firstPattern].choose;
			}, {musicalMode = ["change" ++ firstPattern, "change" ++ secondPattern].choose;
			});
			^["wait", Main.elapsedTime + 10.rand];
		});
	}
	*sensor {
		~maxDensity = 0.75;
		~energy = 0;
		loudnessL3 = loudnessL3;
		~density = 0;
		numOfBassChords = numOfBassChords;
		numOfPadChords = numOfPadChords;
		^[loudnessL3, ~energy, numOfBassChords, numOfPadChords ];
	}

	*translateDecision {
		if(decision[0] == "wait", {
			waitEnd = decision[1];
			^"waiting";
		});

		if (decision[0] == "presentYourself", {
			Oye.addInsertOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});
		if (decision[0] == "makeBajo", {
			Oye.addInsertOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if (decision[0] == "makePad", {
			Oye.addInsertOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if (decision[0] == "makeCowbell", {
			Oye.addInsertOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if (decision[0] == "makeKick", {
			Oye.addInsertOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});


		if (decision[0] == "makeGuira", {
			Oye.addInsertOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if (decision[0] == "makeMelody", {
			Oye.addInsertOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		////////////////////////////////changes on melody//////////////////////
		//inserting new chords on melody
		if(decision[0] == "addMorePitchesToMelody", {
			"addMorePitchesToMelody".postln;
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addInsertCommaAfterSetOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addInsertNewStuffInPreviousStatementOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addAdjustFormatOfTextOpToueue(decision[1], decision[2]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		//try different instrument on Mel
		if(decision[0] == "tryDifferentInstrumentOnMelody", {
			"tryDifferentInstrumentOnMelody".postln;
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addDeleteOpToQueue(decision[4]);
			Oye.addReplaceTextOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		//try different dur on Mel
		if(decision[0] == "tryDifferentDurationOnMelody", {
			"tryDifferentDurationOnMelody".postln;
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addDeleteOpToQueue(decision[4]);
			Oye.addReplaceTextOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		//change Pseq to Prand on Mel
		if(decision[0] == "changePseqToPrandOnMelody", {
			"changePseqToPrandOnMelody".postln;
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addDeleteOpToQueue(decision[4]);
			Oye.addReplaceTextOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if(decision[0] == "changeReleaseOnMel", {
			"changeReleaseOnMel".postln;
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addDeleteOpToQueue(decision[4]);
			Oye.addReplaceTextOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if(decision[0] == "tryDifferentPanOnMel", {
			"tryDifferentPanOnMel".postln;
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addDeleteOpToQueue(decision[4]);
			Oye.addReplaceTextOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		/////////////ending mel //////////////
		if(decision[0] == "endingMel", {
			"endingMel".postln;
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addDeleteOpToQueue(decision[4]);
			Oye.addReplaceTextOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		///////////////////////////////////changes on Bass///////////////////////////////////////////
		if(decision[0] == "addMorePitchesToBass", {
			"addMorePitchesToBass".postln;
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addInsertCommaAfterSetOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addInsertNewStuffInPreviousStatementOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addAdjustFormatOfTextOpToueue(decision[1], decision[2]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if(decision[0] == "holdBass", {
			"holdBass".postln;
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addInsertCommaAfterSetOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addInsertNewStuffInPreviousStatementOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addAdjustFormatOfTextOpToueue(decision[1], decision[2]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if(decision[0] == "replacingStuffOnBass", {
			// queue up a bunch of typing operations (insert, replace, delete, move cursor, evaluation, etc)
			Oye.addDeleteOpToQueue(decision[2]);
			Oye.addReplaceTextOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if(decision[0] == "endingBajo", {
			"endingBajo".postln;
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addDeleteOpToQueue(decision[4]);
			Oye.addReplaceTextOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		///////////////////////////////////changes on Pad///////////////////////////////////////////
		//inserting more chords
		if(decision[0] == "addMoreChordsToPad", {
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addInsertCommaAfterSetOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addInsertNewStuffInPreviousStatementOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addAdjustFormatOfTextOpToueue(decision[1], decision[2]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		//try different instrument on Pad
		if(decision[0] == "tryDifferentInstrumentOnPad", {
			"tryDifferentInstrumentOnPad".postln;
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addDeleteOpToQueue(decision[4]);
			Oye.addReplaceTextOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		//try different durations on Pad
		if(decision[0] == "tryDifferentDurationOnPad", {
			"tryDifferentDurationOnPad".postln;
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addDeleteOpToQueue(decision[4]);
			Oye.addReplaceTextOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		//try different durations on Pad
		if(decision[0] == "tryDifferentPanOnPad", {
			"tryDifferentPanOnPad".postln;
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addDeleteOpToQueue(decision[4]);
			Oye.addReplaceTextOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if(decision[0] == "endingPad", {
			"endingPad".postln;
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addDeleteOpToQueue(decision[4]);
			Oye.addReplaceTextOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		///////////////////////////changes in Ritmo//////////////////////////////////////////////////
		if(decision[0] == "addKick", {
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addDeleteOpToQueue(decision[4]);
			Oye.addReplaceTextOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});



		if(decision[0] == "addGuira", {
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addDeleteOpToQueue(decision[4]);
			Oye.addReplaceTextOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});


		if(decision[0] == "addCowbell", {
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addDeleteOpToQueue(decision[4]);
			Oye.addReplaceTextOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if(decision[0] == "tryDifferentPanOnGuira", {
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addDeleteOpToQueue(decision[4]);
			Oye.addReplaceTextOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if(decision[0] == "tryDifferentPanOnKick", {
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addDeleteOpToQueue(decision[4]);
			Oye.addReplaceTextOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});

		if(decision[0] == "tryDifferentPanOnCowbell", {
			Oye.addUpdateThingsToFindOpToQueue(decision[1], decision[2], decision[3], decision[4], decision[5]);
			Oye.addLocateEndingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addLocateVarOpToQueue(decision[1], decision[2], decision[3], decision[4]);
			Oye.addLocateThingOpToQueue(decision[1], decision[2], decision[3], decision[4] );
			Oye.addDeleteOpToQueue(decision[4]);
			Oye.addReplaceTextOpToQueue(decision[1]);
			Oye.addEvaluateOpToQueue;
			^"typing";
		});
	}

	*addInsertOpToQueue {arg decision;
		queueOfTypingOp = queueOfTypingOp.addFirst(["insert", decision, decision]);
	}

	*addInsertNewStuffInPreviousStatementOpToQueue {arg whatToAdd, inWhichInstrument,  whereIsLastParenthesisOfThatInstrument, afterWhatThing;
		queueOfTypingOp = queueOfTypingOp.addFirst(["insertNewStuffInPreviousStatement", whatToAdd, inWhichInstrument,  whereIsLastParenthesisOfThatInstrument, afterWhatThing]);
	}

	*addInsertCommaAfterSetOpToQueue {arg whatToAdd, inWhichInstrument,  whereIsLastParenthesisOfThatInstrument, afterWhatThing;
		queueOfTypingOp = queueOfTypingOp.addFirst(["insertCommaAfterSet", whatToAdd, inWhichInstrument, whereIsLastParenthesisOfThatInstrument,afterWhatThing]);
	}

	*addLocateVarOpToQueue {arg whatToAdd, inWhichInstrument,  whereIsLastParenthesisOfThatInstrument, afterWhatThing;
		queueOfTypingOp = queueOfTypingOp.addFirst(["locateVar", whatToAdd, inWhichInstrument, whereIsLastParenthesisOfThatInstrument,afterWhatThing]);
	}

	*addLocateThingOpToQueue {arg whatToAdd, inWhichInstrument,  whereIsLastParenthesisOfThatInstrument, afterWhatThing;
		queueOfTypingOp = queueOfTypingOp.addFirst(["locateThing", whatToAdd, inWhichInstrument, whereIsLastParenthesisOfThatInstrument,afterWhatThing]);
	}

	*addLocateEndingOpToQueue {arg whatToAdd, inWhichInstrument,  whereIsLastParenthesisOfThatInstrument, afterWhatThing;
		queueOfTypingOp = queueOfTypingOp.addFirst(["locateEnding", whatToAdd, inWhichInstrument, whereIsLastParenthesisOfThatInstrument,afterWhatThing]);
	}

	*addUpdateThingsToFindOpToQueue {arg whatToAdd, inWhichInstrument,  whereIsLastParenthesisOfThatInstrument, afterWhatThing, afterWhatVar;
		queueOfTypingOp = queueOfTypingOp.addFirst(["updateThingsToFind", whatToAdd, inWhichInstrument, whereIsLastParenthesisOfThatInstrument,afterWhatThing, afterWhatVar]);
	}

	*addAdjustFormatOfTextOpToueue {arg stuff;
		queueOfTypingOp = queueOfTypingOp.addFirst(["adjustFormatOfText", stuff, stuff ]);
	}

	*addEvaluateOpToQueue {
		queueOfTypingOp = queueOfTypingOp.addFirst(["evaluate"]);
	}

	*addReplaceTextOpToQueue {arg decision;
		queueOfTypingOp = queueOfTypingOp.addFirst(["replace", decision]);
	}

	*addDeleteOpToQueue {arg decision;
		queueOfTypingOp = queueOfTypingOp.addFirst(["delete", decision]);
	}

	*addDisplayCursorAtEndOfTextOpToQueue {
		queueOfTypingOp = queueOfTypingOp.addFirst(["cursorAtEndOfText", Oye.displayCursorAtEndOfTextOp]);
	}

	*insertOp {
		if (op[1].notEmpty, {
			currentDocument.text = currentDocument.text ++ op[1].first;
			Oye.displayCursorAtEndOfTextOp;
			op[1] =  op[1].drop(1);
		})
	}

	*replaceOp {
		if (op[1].notEmpty, {
			currentDocument.insertText(op[1].first.asString, findThingToErase + contador);
			Oye.displayCursorWhenReplacingOp;
			op[1] =  op[1].drop(1);
		})
	}
	*updateThingsToFindOp {
		if (op[1].notEmpty, {
			inWhichInstrument = currentDocument.text.find(op[2]);
			whereIsLastParenthesisOfThatInstrument = currentDocument.text.findAll(op[3]);
			whereIsVarToFind = currentDocument.text.findAll(op[5]);

			if ((op[2] == "Bajo") || (op[2] == "Mel") || (op[2] == "Ritmo"), {
				if ((op[4].first == $[ && op[4].last == $]), {
					op[4] =  op[4].drop(1); op[4] =  op[4].drop(-1);
				});
			});
			whereIsThingToFind =  currentDocument.text.findAll(op[4]);
		});

		op[1] = [];
	}

	*locateEndingOp {
		if (op[1].notEmpty, {
			if (whereIsLastParenthesisOfThatInstrument.size > 1, {
				if ((whereIsLastParenthesisOfThatInstrument[0] > inWhichInstrument) &&
					(whereIsLastParenthesisOfThatInstrument[0] < whereIsLastParenthesisOfThatInstrument[1]),
					{whereIsLastParenthesisOfThatInstrument = whereIsLastParenthesisOfThatInstrument[0];
						op[1] = [];
					},{whereIsLastParenthesisOfThatInstrument = whereIsLastParenthesisOfThatInstrument.drop(1);
				});
			}, {whereIsLastParenthesisOfThatInstrument = whereIsLastParenthesisOfThatInstrument[0];
				op[1] = [];
			});
		});
	}

	*locateVarOp {

		if (op[1].notEmpty, {
			if (whereIsVarToFind.size > 1, {
				if ((whereIsVarToFind[0] >  inWhichInstrument) && (whereIsVarToFind[0] < whereIsLastParenthesisOfThatInstrument), {
					whereIsVarToFind = whereIsVarToFind[0];
					op[1] = [];
				}, {whereIsVarToFind = whereIsVarToFind.drop(1);
				});
			}, {whereIsVarToFind = whereIsVarToFind[0];
				op[1] = [];
			});
		});
	}

	*locateThingOp {
		if (op[1].notEmpty, {
			if(whereIsThingToFind.size > 1, {
				if ((whereIsThingToFind[0] > whereIsVarToFind) && (whereIsThingToFind[0] <
					whereIsLastParenthesisOfThatInstrument), {
					whereIsThingToFind = whereIsThingToFind[0];
					op[1] = [];
				}, {whereIsThingToFind = whereIsThingToFind.drop(1);
				});
			}, {whereIsThingToFind = whereIsThingToFind[0];
				op[1] = [];
			});
		});
	}

	*insertCommaAfterSetOp {
		if (op[1].notEmpty, {
			if(op[2] == "Pad", {
				currentDocument = currentDocument.insertText(", r,", (whereIsThingToFind + (op[4].size)));
			});
			if(op[2] == "Bajo", {
				currentDocument = currentDocument.insertText(",", (whereIsThingToFind + (op[4].size-2)));
			});
			if(op[2] == "Mel", {
				currentDocument = currentDocument.insertText(",", (whereIsThingToFind + (op[4].size)));
			});
		});
		op[1] = [];
	}

	*insertNewStuffInPreviousStatementOp {
		if (op[1].notEmpty, {
			if ((op[2] == "Bajo") || (op[2] == "Melodia") || (op[2] == "Ritmo"), {
				if (op[1].first.asString == "[", {op[1] =  op[1].drop(1);});
				if (op[1].last.asString == "]", {op[1] =  op[1].drop(-1);});
			});

			if(op[2] == "Pad", {
				currentDocument.insertText (
					op[1].first.asString, (whereIsThingToFind + (op[4].size + 4)) + (contador));
				Oye.displayCursorWhenInsertingNewStuffOp;
				op[1] =  op[1].drop(1);
			});

			if(op[2] == "Bajo", {
				currentDocument.insertText (
					op[1].first.asString, (whereIsThingToFind + (op[4].size-1)) + (contador));
				Oye.displayCursorWhenInsertingNewStuffOp;

				op[1] =  op[1].drop(1);
			});

			if(op[2] == "Mel", {
				currentDocument.insertText (
					op[1].first.asString, (whereIsThingToFind + (op[4].size+1)) + (contador));
				Oye.displayCursorWhenInsertingNewStuffOp;

				op[1] =  op[1].drop(1);
			});
		});
	}

	*displayCursorAtEndOfTextOp {
		currentDocument = currentDocument.insertText("|", (currentDocument.text.size));
		0.15.wait;
		currentDocument.text = currentDocument.text.replace("|", "");
		0.15.wait;
	}

	*displayCursorWhenReplacingOp {
		currentDocument =
		currentDocument.insertText("|", (findThingToErase + contador+1)); //replace thing to erase with cursor
		0.15.wait;
		currentDocument.text = currentDocument.text.replace("|", ""); //move cursor forward
		contador = contador + 1;
		0.15.wait;
	}

	*displayCursorWhenDeletingOp {
		currentDocument = currentDocument.insertText("|", (findThingToErase + op[1].size)); //replace thing to erase with cursor
		0.15.wait;
		currentDocument.text = currentDocument.text.replace("|", ""); //move cursor backwards and replace the thing to erase
		0.15.wait;
	}

	*displayCursorWhenInsertingNewStuffOp {
		if (op[2] == "Pad", {
			currentDocument = currentDocument.insertText("|", (whereIsThingToFind + (op[4].size + 4)) + (contador));
			0.15.wait;
			currentDocument.text = currentDocument.text.replace("|", ""); //move cursor backwards and replace the thing to erase
			contador = contador + 1;
			0.15.wait;
		}, {
			currentDocument = currentDocument.insertText("|", (whereIsThingToFind + (op[4].size -1)) + (contador));
			0.15.wait;
			currentDocument.text = currentDocument.text.replace("|", ""); //move cursor backwards and replace the thing to erase
			contador = contador + 1;
			0.15.wait;
		});

	}

	*evaluateOp {
		op = nil;
		whereIsThingToFind = nil;
		inWhichInstrument = nil;
		whereIsLastParenthesisOfThatInstrument = nil;
		locateDoubleSpacesBeforeSqBracket = nil;
		currentDocument.text = currentDocument.text ++ "\n";
		//op[1].interpret;
		contador = 0;
		currentDocument.currentLine;
		currentDocument.text.interpret;
	}

	*deleteOp {
		if (op[1].notEmpty, {
			text = currentDocument.text;
			findThingToErase = whereIsThingToFind; //currentDocument.text.findBackwards(op[1]); //find index
			Oye.displayCursorWhenDeletingOp;
			text.removeAt((findThingToErase + (op[1].size-1)));
			currentDocument.text = text;
			op[1] = op[1].drop(-1);
		})
	}

	*adjustFormatOfTextOp  {
		if (op[1].notEmpty,{
			text = currentDocument.text;
			spacesBeforeSqBracket = "  ]";
			locateDoubleSpacesBeforeSqBracket = currentDocument.text.findAll(spacesBeforeSqBracket);
			op[1] = [];
		});

		if (locateDoubleSpacesBeforeSqBracket.notNil, {
			if (op[2].notEmpty,  {
				text.removeAt(locateDoubleSpacesBeforeSqBracket[0] + 1);
				currentDocument.text = text;
				op[2] = [];
			})
		});
	}

	*doTyping {
		// op op
		if(op[0] == "insert", {Oye.insertOp;});
		if(op[0] == "insertNewStuffInPreviousStatement", {Oye.insertNewStuffInPreviousStatementOp;});
		if(op[0] == "replace", {Oye.replaceOp;});
		if(op[0] == "delete", {Oye.deleteOp;});
		if(op[0] == "cursorAtEndOfText", {Oye.displayCursorAtEndOfTextOp;});
		if(op[0] == "displayCursorWhenDeleting", {Oye.displayCursorWhenDeletingOp;});
		if(op[0] == "displayCursorWhenReplacing", {Oye.displayCursorWhenReplacingOp;});
		if(op[0] == "insertCommaAfterSet", {Oye.insertCommaAfterSetOp;});
		if(op[0] == "locateVar", {Oye.locateVarOp;});
		if(op[0] == "locateThing", {Oye.locateThingOp;});
		if(op[0] == "locateEnding", {Oye.locateEndingOp;});
		if(op[0] == "updateThingsToFind", {Oye.updateThingsToFindOp;});
		if(op[0] == "adjustFormatOfText", {Oye.adjustFormatOfTextOp;});
		if(op[0] == "displayCursorWhenInsertingNewStuffOp", {Oye.displayCursorWhenInsertingNewStuffOp;});
		if(op[0] == "evaluate", {Oye.evaluateOp;});
	}

	*whichState {
		currentTime = Main.elapsedTime;
		if (state == "waiting", {
			"im waiting".postln;
			Oye.displayCursorAtEndOfTextOp;
			if (currentTime > waitEnd, {state = "deciding"});
		});

		if (state == "deciding", {
			decision = Oye.decide;
			"im deciding".postln;
			state = Oye.translateDecision(decision);
		});

		if (state == "typing", {
			"im typing".postln;
			if (queueOfTypingOp.isEmpty, {state = "deciding"},{
				//op = queueOfTypingOp.pop;//pop returns the earliest item in the queue
				op = if (op.isNil, {queueOfTypingOp.pop;}, {
					if (op.notNil, {
						if ((queueOfTypingOp.size >= 1 && op[1].isEmpty),
							{queueOfTypingOp.pop;}, {op = op});
					})
				});
				Oye.doTyping(op);
			});
		});
	}

	*slidersL3 {
		var makeMelodyString, makeBajoString, makePadString,   makeCowBellString,   makeKickString,  makeGuiraString,  loudnessL3String,  useCString, useCSharpString,  useDString,  usedSharpString,  useeString,  usefString,  usefSharpString,usegString,  usegSharpString,  useaString, useaSharpString,  usebString,  energyL3String;

		w = Window.new("Oye: L3 Inputs testing Sliders", Rect(170,-300, 600, 420)).front;
		makeMelodyString=StaticText(w,Rect(20,20,150,20)); 	makeMelodyString.string= "makeMelody"; mMW = NumberBox(w, Rect(20, 40, 150, 20)); mMS = Slider(w, Rect(20, 60, 150, 20));
		makeBajoString=StaticText(w,Rect(20,80,150,20)); makeBajoString.string= "makeBajo";	mBW = NumberBox(w, Rect(20,100,150,20)); mBS = Slider(w, Rect(20, 120, 150, 20));
		makePadString=StaticText(w,Rect(20,140,150,20)); makePadString.string= "makeTeclado/synthesizer"; mPW = NumberBox(w, Rect(20,160,150,20)); 	mPS = Slider(w, Rect(20, 180, 150, 20));
		makeCowBellString=StaticText(w,Rect(20,200,150,20)); 	makeCowBellString.string= "makeCowBell"; 	mCBW = NumberBox(w, Rect(20, 220, 150, 20)); 	mCBS = Slider(w, Rect(20, 240, 150, 20));
		makeKickString=StaticText(w,Rect(20,260,150,20)); 	makeKickString.string= "makeKick"; mKW = NumberBox(w, Rect(20, 280,150,20)); mKS = Slider(w, Rect(20, 300, 150, 20));
		makeGuiraString=StaticText(w,Rect(20,320,150,20)); makeGuiraString.string= "makeGuira"; mGW = NumberBox(w, Rect(20, 340, 150, 20)); 	mGS = Slider(w, Rect(20, 360, 150, 20));
		loudnessL3String=StaticText(w,Rect(20,380,150,20)); loudnessL3String.string= "loudnessL3"; loudnessL3W = NumberBox(w, Rect(20, 400, 150, 20)); loudnessL3S = Slider(w, Rect(20, 420, 150, 20));
		useCString=StaticText(w,Rect(200,20,150,20)); useCString.string= "use c"; usecW = NumberBox(w, Rect(200, 40, 150, 20)); usecS = Slider(w, Rect(200, 60, 150, 20));
		useCSharpString=StaticText(w,Rect(200,80,150,20)); useCSharpString.string= "use c#"; usecsW = NumberBox(w, Rect(200, 100, 150, 20)); usecsS = Slider(w, Rect(200, 120, 150, 20));
		useDString=StaticText(w,Rect(200, 140,150,20)); useDString.string= "use d"; usedW = NumberBox(w, Rect(200, 160, 150, 20)); usedS = Slider(w, Rect(200, 180, 150, 20));
		usedSharpString=StaticText(w,Rect(200,200,150,20)); usedSharpString.string= "use d#"; usedsW = NumberBox(w, Rect(200, 220, 150, 20)); 	usedsS = Slider(w, Rect(200, 240, 150, 20));
		useeString=StaticText(w,Rect(200,260,150,20)); useeString.string= "use e"; useeW = NumberBox(w, Rect(200, 280, 150, 20)); 	useeS = Slider(w, Rect(200, 300, 150, 20));
		usefString=StaticText(w,Rect(200,320,150,20)); usefString.string= "use f"; usefW = NumberBox(w, Rect(200, 340, 150, 20)); usefS = Slider(w, Rect(200, 360, 150, 20));
		usefSharpString=StaticText(w,Rect(400,20,150,20)); usefSharpString.string= "use f#"; usefsW = NumberBox(w, Rect(400, 40, 150, 20)); usefsS = Slider(w, Rect(400, 60, 150, 20));
		usegString=StaticText(w,Rect(400,80,150,20)); usegString.string= "use g";	usegW = NumberBox(w, Rect(400, 100, 150, 20)); usegS = Slider(w, Rect(400, 120, 150, 20));
		usegSharpString=StaticText(w,Rect(400,140,150,20)); usegSharpString.string= "use g#"; gsW = NumberBox(w, Rect(400, 160, 150, 20)); 	usegsS = Slider(w, Rect(400, 180, 150, 20));
		useaString=StaticText(w,Rect(400,200,150,20)); useaString.string= "use a"; useaW = NumberBox(w, Rect(400, 220, 150, 20)); useaS = Slider(w, Rect(400, 240, 150, 20));
		useaSharpString=StaticText(w,Rect(400,260,150,20)); 	useaSharpString.string= "use a#"; useasW = NumberBox(w, Rect(400, 280, 150, 20)); useasS = Slider(w, Rect(400, 300, 150, 20));
		usebString=StaticText(w,Rect(400,320,150,20)); usebString.string= "use b"; usebW = NumberBox(w, Rect(400, 340, 150, 20)); 	usebS = Slider(w, Rect(400, 360, 150, 20));
		energyL3String=StaticText(w,Rect(400,380,150,20)); energyL3String.string= "energy"; energyL3W = NumberBox(w, Rect(400, 400, 150, 20)); energyL3S = Slider(w, Rect(400, 420, 150, 20));

	}

	*receiveL1OutputsFromNet {arg sliders = false, netReady = isNetReady, print = showNetInput;
		if (netReady == true, {
			OSCdef(\loudness, {arg msg;
				loudnessL1  = msg[3].linlin(0,64, 0,1);
			}, "/loudness");
			OSCdef (\lOneOutputs, {arg msg;
				if (print == true, {msg.postln});
				lOneOutputs  = Dictionary.new;
				lOneOutputs[\mel] = msg[1].clip(0,1);
				lOneOutputs[\pad] = msg[2].clip(0,1);
				lOneOutputs[\bajo] = msg[3].clip(0,1);
				lOneOutputs[\guira] = msg[4].clip(0,1);
				lOneOutputs[\cowbell] = msg[5].clip(0,1);
				lOneOutputs[\kick] = msg[6].clip(0,1);
				lOneOutputs[\c] = msg[7].clip(0,1);
				lOneOutputs[\cs] = msg[8].clip(0,1);
				lOneOutputs[\d] = msg[9].clip(0,1);
				lOneOutputs[\ds] = msg[10].clip(0,1);
				lOneOutputs[\e] = msg[11].clip(0,1);
				lOneOutputs[\f] = msg[12].clip(0,1);
				lOneOutputs[\fs] = msg[13].clip(0,1);
				lOneOutputs[\g] = msg[14].clip(0,1);
				lOneOutputs[\gs] = msg[15].clip(0,1);
				lOneOutputs[\a] = msg[16].clip(0,1);
				lOneOutputs[\as] = msg[17].clip(0,1);
				lOneOutputs[\b] = msg[18].clip(0,1);
				lOneOutputs[\energy] = msg[19].clip(0,1);

				///////////////mapping L1 input to sliders//////////
				{melW.value = msg[1].clip(0,1); melS.value = msg[1].clip(0,1)}.defer;
				{padW.value = msg[2].clip(0,1); padS.value = msg[2].clip(0,1)}.defer;
				{bassW.value = msg[3].clip(0,1); bassS.value = msg[3].clip(0,1)}.defer;
				{guiraW.value = msg[4].clip(0,1); guiraS.value = msg[4].clip(0,1)}.defer;
				{cowbellW.value = msg[5].clip(0,1); cowbellS.value = msg[5].clip(0,1)}.defer;
				{kickW.value = msg[6].clip(0,1); kickS.value = msg[6].clip(0,1)}.defer;
				{cW.value = msg[7].clip(0,1); cS.value = msg[7].clip(0,1)}.defer;
				{csW.value = msg[8].clip(0,1); csS.value = msg[8].clip(0,1)}.defer;
				{dW.value = msg[9].clip(0,1); dS.value = msg[9].clip(0,1)}.defer;
				{dsW.value = msg[10].clip(0,1); dsS.value = msg[10].clip(0,1)}.defer;
				{eW.value = msg[11].clip(0,1); eS.value = msg[11].clip(0,1)}.defer;
				{fW.value = msg[12].clip(0,1); fS.value = msg[12].clip(0,1)}.defer;
				{fsW.value = msg[13].clip(0,1); fsS.value = msg[13].clip(0,1)}.defer;
				{gW.value = msg[14].clip(0,1); gS.value = msg[14].clip(0,1)}.defer;
				{gsW.value = msg[15].clip(0,1); gsS.value = msg[15].clip(0,1)}.defer;
				{aW.value = msg[16].clip(0,1);  aS.value = msg[16].clip(0,1)}.defer;
				{asW.value = msg[17].clip(0,1);  asS.value = msg[17].clip(0,1)}.defer;
				{bW.value = msg[18].clip(0,1); bS.value = msg[18].clip(0,1)}.defer;
				{energyW.value = msg[19].clip(0,1); energyS.value = msg[19].clip(0,1)}.defer;
			}, "/n");
		},  {lOneOutputs  = Dictionary.new;
			lOneOutputs = lOneOutputs.putAll(Dictionary[\kick -> 0, \guira -> 0, \cowbell -> 0, \bajo -> 0, \pad -> 0, \mel -> 0,  \c -> 0, \cs -> 0, \d -> 0, \ds -> 0, \e -> 0, \f -> 0,\fs -> 0,\g -> 0, \gs -> 0, \a -> 0, \as -> 0, \b -> 0]);
			loudnessL1 = 0;
		});

		//////////////Sliders////////////////
		if (sliders, {
			var melString, padString, bassString, cowbellString, kickString, guiraString, cString, cSharpString, dString, dSharpString, eString, fString, fSharpString, gString, gSharpString, aString, aSharpString, bString, energyString;
			w = Window.new("Oye: Layer 1", Rect(-800, -300, 600, 420)).front;
			melString=StaticText(w,Rect(20,20,150,20)); melString.string= "melody"; melW = NumberBox(w, Rect(20,40,150,20)); melS = Slider(w, Rect(20, 60, 150, 20));
			padString=StaticText(w,Rect(20,140,150,20)); padString.string= "Teclado/synthesizer"; padW = NumberBox(w, Rect(20,160,150,20)); padS = Slider(w, Rect(20, 180, 150, 20));
			bassString=StaticText(w,Rect(20,80,150,20)); 	bassString.string= "bass"; 	bassW = NumberBox(w, Rect(20,100,150,20)); 	bassS = Slider(w, Rect(20, 120, 150, 20));
			cowbellString=StaticText(w,Rect(20,200,150,20));  cowbellString.string= "cowbell"; cowbellW = NumberBox(w, Rect(20, 220, 150, 20)); 	cowbellS = Slider(w, Rect(20, 240, 150, 20));
			kickString=StaticText(w,Rect(20,260,150,20)); kickString.string= "kick"; kickW = NumberBox(w, Rect(20, 280, 150, 20));	kickS = Slider(w, Rect(20, 300, 150, 20));
			guiraString=StaticText(w,Rect(20,320,150,20)); 	guiraString.string= "guira";	guiraW = NumberBox(w, Rect(20, 340, 150, 20)); guiraS = Slider(w, Rect(20, 360, 150, 20));
			cString=StaticText(w,Rect(200,20,150,20)); cString.string= "c"; cW = NumberBox(w, Rect(200, 40, 150, 20)); cS = Slider(w, Rect(200, 60, 150, 20));
			cSharpString=StaticText(w,Rect(200,80,150,20)); cSharpString.string= "c#"; csW = NumberBox(w, Rect(200, 100, 150, 20)); csS = Slider(w, Rect(200, 120, 150, 20));
			dString=StaticText(w,Rect(200, 140,150,20)); dString.string= "d"; dW = NumberBox(w, Rect(200, 160, 150, 20)); dS = Slider(w, Rect(200, 180, 150, 20));
			dSharpString=StaticText(w,Rect(200,200,150,20)); dSharpString.string= "d#"; dsW = NumberBox(w, Rect(200, 220, 150, 20)); 	dsS = Slider(w, Rect(200, 240, 150, 20));
			eString=StaticText(w,Rect(200,260,150,20)); eString.string= "e"; eW = NumberBox(w, Rect(200, 280, 150, 20)); 	eS = Slider(w, Rect(200, 300, 150, 20));
			fString=StaticText(w,Rect(200,320,150,20)); fString.string= "f"; fW = NumberBox(w, Rect(200, 340, 150, 20)); fS = Slider(w, Rect(200, 360, 150, 20));
			fSharpString=StaticText(w,Rect(400,20,150,20)); fSharpString.string= "f#"; fsW = NumberBox(w, Rect(400, 40, 150, 20)); fsS = Slider(w, Rect(400, 60, 150, 20));
			gString=StaticText(w,Rect(400,80,150,20)); 	gString.string= "g";	gW = NumberBox(w, Rect(400, 100, 150, 20)); gS = Slider(w, Rect(400, 120, 150, 20));
			gSharpString=StaticText(w,Rect(400,140,150,20)); gSharpString.string= "g#"; gsW = NumberBox(w, Rect(400, 160, 150, 20)); 	gsS = Slider(w, Rect(400, 180, 150, 20));
			aString=StaticText(w,Rect(400,200,150,20)); aString.string= "a"; aW = NumberBox(w, Rect(400, 220, 150, 20)); aS = Slider(w, Rect(400, 240, 150, 20));
			aSharpString=StaticText(w,Rect(400,260,150,20)); 	aSharpString.string= "a#"; asW = NumberBox(w, Rect(400, 280, 150, 20)); asS = Slider(w, Rect(400, 300, 150, 20));
			bString=StaticText(w,Rect(400,320,150,20)); bString.string= "b"; bW = NumberBox(w, Rect(400, 340, 150, 20)); 	bS = Slider(w, Rect(400, 360, 150, 20));
			energyString=StaticText(w,Rect(400,380,150,20)); energyString.string= "energy"; energyW = NumberBox(w, Rect(400, 400, 150, 20)); energyS = Slider(w, Rect(400, 420, 150, 20));
		});
	}

	*l2 {//connecting l1 to l3
		//~l3 = 1 - ~l1;
		makeKick = 1 - lOneOutputs[\kick];
		makeGuira = 1 - lOneOutputs[\guira];
		makeCowBell = 1 - lOneOutputs[\cowbell];
		makeBajo = 1 - lOneOutputs[\bajo];
		makeMel = 1 - lOneOutputs[\mel];
		makePad = 1 - lOneOutputs[\pad];
		loudnessL3 = loudnessL1;
		useC = 1 -  lOneOutputs[\c];
		useCsharp = 1 - lOneOutputs[\cs];
		useD = 1 -	lOneOutputs[\d] ;
		useDsharp = 1 - lOneOutputs[\ds];
		useE = lOneOutputs[\e];
		useF =	lOneOutputs[\f];
		useFsharp = lOneOutputs[\fs];
		useG = lOneOutputs[\g];
		useGsharp = lOneOutputs[\gs];
		useA = lOneOutputs[\a];
		useAsharp = lOneOutputs[\as];
		useB = lOneOutputs[\b];
		energyL3 = lOneOutputs[\energy];

		lThreeInputs = (makeKick: makeKick, makeGuira: makeGuira, makeCowBell: makeCowBell, makeBajo: makeBajo, makePad: makePad, 	makeMelody: makeMel,  loudness: loudnessL3, useC: useC, useCsharp: useCsharp, useD: useD, useDsharp: useDsharp, useE:useE, useF: useF, useFsharp: useFsharp, useG: useG, useGsharp: useGsharp, useA: useA, useAsharp:useAsharp, useB: useB, energy:energyL3 );

		if (mapSlidersL3, {
			{mKW.value = makeKick; mKS.value = makeKick}.defer;
			{mGW.value = makeGuira; mGS.value = makeGuira}.defer;
			{mCBW.value = makeCowBell; mCBS.value = makeCowBell}.defer;
			{mBW.value = makeBajo; mBS.value = makeBajo}.defer;
			{mPW.value = makePad; mPS.value = makePad}.defer;
			{mMW.value = makeMel; mMS.value = makeMel}.defer;
			{loudnessL3W.value = loudnessL3; loudnessL3S.value = loudnessL3}.defer;
			{usecW.value = useC; usecS.value = useC}.defer;
			{usecsW.value = useCsharp; usecsS.value = useCsharp}.defer;
			{usedW = useD; usedS = useD}.defer;
			{usedsW = useDsharp;	usedsS = useDsharp;}.defer;
			{useeW = useE; 	useeS =useE;}.defer;
			{usefW = useF; usefS = useF}.defer;
			{usefsW =useFsharp; usefsS =useFsharp}.defer;
			{usegW = useG; usegS =useG}.defer;
			{usegsW = useGsharp;	usegsS = useGsharp}.defer;
			{useaW =useA; useaS =useA}.defer;
			{useasW =useAsharp; useasS = useAsharp}.defer;
			{usebW = useB;	usebS = useB}.defer;
			{energyL3W = energyL3; energyL3S =energyL3} .defer;

		});

	}

	*stop {
		var closeDoc = currentDocument.close("/home/lui/cacharpo.scd");
		var killWindows = w.close;
	}

	/////////////////////////////////normalizing functions/////////////////////////////////////////

	*scaleRelativeToAmplitude {
		arg x,y, threshold = (-40);
		var z = y.clip(threshold.dbamp, 300.dbamp);
		^ (x/z);
	}

	*outOfRange {
		arg x, y, threshold= (-40), outOfRange = (-1);
		var a = x * (y > (threshold.dbamp)); //  x or 0
		var b = (y <= (threshold.dbamp)) * outOfRange;
		^a+b;
	}

	/////////////////////////////////training function/////////////////////////////////////////
	*analyseSound {
		arg bufferMode = false, port = 57800, performanceBus = 4, pathToAudio = "~/path", printFeatures = false, printOutputs = false, allowSendFeatures = true, dKickOutput = Env.new([-1,-1], [30]), dHiHatsOutput = Env.new([-1,-1], [30]), 	dSnareOutput = Env.new([-1,-1], [30]), dGuiraOutput = Env.new([-1,-1], [30]), dTimbalOutput = Env.new([-1,-1], [30]), 	dCowbellOutput = Env.new([-1,-1], [30]), dPadOutput = Env.new([-1,-1], [30]), dBassOutput = Env.new([-1,-1], [30]), 	dMelOutput = Env.new([-1,-1], [30]), dLoudnessOutput = Env.new([-1,-1], [30]),	dDensityOutput = Env.new([-1,-1], [30]), loopSample = 0,  doneAction = 0, 	dCOutput = Env.new([-1,-1], [30]), dCsharpOutput = Env.new([-1,-1], [30]), dDOutput = Env.new([-1,-1], [30]), dDsharpOutput = Env.new([-1,-1], [30]), dEOutput = Env.new([-1,-1], [30]), dFOutput = Env.new([-1,-1], [30]), dFsharpOutput = Env.new([-1,-1], [30]), dGOutput = Env.new([-1,-1], [30]), dGsharpOutput = Env.new([-1,-1], [30]), dAOutput = Env.new([-1,-1], [30]), dAsharpOutput = Env.new([-1,-1], [30]), dBOutput = Env.new([-1,-1], [30]), dEnergyOutput = Env.new([-1,-1], [30]);
		toThinkingModule = NetAddr.new("localhost", port);
		b = Bus.control(s, 12);

		SynthDef (\analyseSound, {
			var in0 = 	if (bufferMode == true, {in0 = PlayBuf.ar(2, Buffer.read(s, pathToAudio), BufRateScale.kr(Buffer.read(s, pathToAudio)),1, 0, loopSample, doneAction:2);
			}, {in0 = In.ar(bus:performanceBus, numChannels:2);
			});
			var in = Mix.ar(in0); //mixing to one channel (mono)
			var fft0 = FFT(Buffer.alloc(s, 1024,2), in);
			var fft1 = PV_Copy(fft0, Buffer.alloc(s, 1024,2));
			var fft2 = PV_Copy(fft0,  Buffer.alloc(s, 1024,2));
			var fft3 = PV_Copy(fft0,  Buffer.alloc(s, 1024,2));
			var fft4 = PV_Copy(fft0,  Buffer.alloc(s, 1024,2));

			//////////////////////feature extraction Ugens//////////////////
			var loudness = Loudness.kr(fft0); //loudness = amplitude = intensity
			var pitch = Pitch.kr(in, initFreq:in, minFreq:16); //pitch detection in Hertz
			var amplitude = Amplitude.kr(in);
			var centroid = Oye.outOfRange(SpecCentroid.kr(fft1), amplitude); //amount of brightness = amount of treble freqs
			var onsets = Onsets.kr(fft2,0.3,\magsum,1, 0.0001,1); //detecting intitial impulse of a sound (transient)
			var zeroCrossing = Oye.outOfRange(ZeroCrossing.kr(A2K.kr(in)), amplitude); //detecting variation or changes in the signal
			var specFlatness = Oye.outOfRange(SpecFlatness.kr(fft3), amplitude);// how "noise-like is a sound" (wikipedia), how tonal or noisy is, how close is a sound to being a noise, 0 = pure sinusoid to 1 = white noise
			var specPcile = Oye.outOfRange(SpecPcile.kr(fft4, 0.95), amplitude); //amount of skweness (assimetry) of a wave// cumulative distribution of the freq spectrum// in which band (high, mid or low) is the energy located
			var filteringVeryLowFreqs = Oye.outOfRange(Amplitude.kr(LPF.ar(in, 60)), amplitude); //filtering freqs as Rhythm 20hz and below
			var filteringLowFreqs = Oye.outOfRange(Amplitude.kr (BPF.ar(in, 120, 1.3)), amplitude); //filtering low freqs between 20 and 100hz
			var filteringMidFreqs = Oye.outOfRange(Amplitude.kr(BPF.ar(in, 240, 1.3)), amplitude); //filtering mid freq between 100 and 500hz
			var filteringHighFreqs = Oye.outOfRange(Amplitude.kr(HPF.ar(in,480)), amplitude); //filtering high freq above 501hz
			var cFilter =  Oye.scaleRelativeToAmplitude(Amplitude.kr(Mix.ar(BPF.ar(in,[24,36,48,60,72,84,96].midicps,0.008))), amplitude);//Filtering pitch class of C
			var csFilter = Oye.scaleRelativeToAmplitude(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+1).midicps,0.008))), amplitude); //... C#
			var dFilter = Oye.scaleRelativeToAmplitude(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+2).midicps,0.008))), amplitude); //... D
			var dsFilter = Oye.scaleRelativeToAmplitude(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+3).midicps,0.008))), amplitude); //... D#
			var eFilter = Oye.scaleRelativeToAmplitude(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+4).midicps,0.008))), amplitude); //... E
			var fFilter = Oye.scaleRelativeToAmplitude(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+5).midicps,0.008))), amplitude); //... F
			var fsFilter = Oye.scaleRelativeToAmplitude(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+6).midicps,0.008))), amplitude); //... F#
			var gFilter = Oye.scaleRelativeToAmplitude(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+7).midicps,0.008))), amplitude); //... G
			var gsFilter = Oye.scaleRelativeToAmplitude(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+8).midicps,0.008))), amplitude); //... G#
			var aFilter = Oye.scaleRelativeToAmplitude(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+9).midicps,0.008))), amplitude); //... A
			var asFilter = Oye.scaleRelativeToAmplitude(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+10).midicps,0.008))), amplitude); //... A#
			var bFilter = Oye.scaleRelativeToAmplitude(Amplitude.kr(Mix.ar(BPF.ar(in,([24,36,48,60,72,84,96]+11).midicps,0.008))), amplitude); //... B

			////////////////////desired outputs/////////////////
			var desiredKick = EnvGen.kr(dKickOutput, doneAction: doneAction);
			var desiredHihats = EnvGen.kr(dHiHatsOutput, doneAction: doneAction);
			var desiredSnare = EnvGen.kr(dSnareOutput, doneAction: doneAction);
			var desiredGuira = EnvGen.kr(dGuiraOutput, doneAction: doneAction);
			var desiredTimbal = EnvGen.kr(dTimbalOutput, doneAction: doneAction);
			var desiredCowbell = EnvGen.kr(dCowbellOutput, doneAction: doneAction);
			var desiredPad = EnvGen.kr(dPadOutput, doneAction: doneAction);
			var desiredBass = EnvGen.kr(dBassOutput, doneAction: doneAction);
			var desiredMel = EnvGen.kr(dMelOutput, doneAction: doneAction);
			var desiredLoudness = EnvGen.kr(dLoudnessOutput, doneAction: doneAction);
			var desiredDensity = EnvGen.kr(dDensityOutput, doneAction: doneAction);
			var desiredC = EnvGen.kr(dCOutput, doneAction: doneAction);
			var desiredCsharp = EnvGen.kr(dCsharpOutput, doneAction: doneAction);
			var desiredD = EnvGen.kr(dDOutput, doneAction: doneAction);
			var desiredDsharp = EnvGen.kr(dDsharpOutput, doneAction: doneAction);
			var desiredE = EnvGen.kr(dEOutput, doneAction: doneAction);
			var desiredF = EnvGen.kr(dFOutput, doneAction: doneAction);
			var desiredFsharp = EnvGen.kr(dFsharpOutput, doneAction: doneAction);
			var desiredG = EnvGen.kr(dGOutput, doneAction: doneAction);
			var desiredGsharp = EnvGen.kr(dGsharpOutput, doneAction: doneAction);
			var desiredA = EnvGen.kr(dAOutput, doneAction: doneAction);
			var desiredAsharp = EnvGen.kr(dAsharpOutput, doneAction: doneAction);
			var desiredB = EnvGen.kr(dBOutput, doneAction: doneAction);
			var desiredEnergy = EnvGen.kr(dEnergyOutput, doneAction: doneAction);

			///////////////////////sending sound features to a list////////////////////////////
			var features = [/*3*/loudness, /*4*/pitch[0], /*5*/centroid, /*6*/filteringVeryLowFreqs, /*7*/ filteringLowFreqs, /*8*/filteringMidFreqs, /*9*/filteringHighFreqs, /*10*/ cFilter, /*11*/csFilter, /*12*/ dFilter, /*13*/ dsFilter,
				/*14*/eFilter, /*15*/fFilter, /*16*/ fsFilter, /*17*/ gFilter, /*18*/ gsFilter, /*19*/aFilter, /*20*/asFilter, /*21*/ bFilter, /*22*/ onsets, /*23*/ zeroCrossing, /*24*/ specFlatness, /*25*/ specPcile, /*26*/ desiredKick, /*27*/desiredHihats, /*28*/ desiredSnare, 	/*29*/ desiredGuira, /*30*/ desiredTimbal, /*31*/ desiredCowbell, /*32*/ desiredPad, /*33*/ desiredBass, /*34*/desiredMel, /*35*/ desiredLoudness, /*36*/ desiredC, /*37*/ desiredCsharp, /*38*/desiredD, /*39*/ desiredDsharp, /*40*/ desiredE, /*41*/ desiredF, /*42*/ desiredFsharp, /*43*/ desiredG, /*44*/ desiredGsharp, /*45*/ desiredA, /*46*/ desiredAsharp, /*47*/ desiredB, /*48*/ desiredEnergy];
			SendReply.kr(Impulse.kr(20), "/features", features);
			SendReply.kr(Impulse.kr(20), "/loudness", loudness);
			Out.kr(b.index, [cFilter, csFilter, dFilter, dsFilter, eFilter, fFilter, fsFilter, gFilter, gsFilter, aFilter, asFilter, bFilter]);
			Out.ar(0, in0);
		}).play;

		//receiving features and adding them to a list
		OSCdef (\feat, {arg msg, time;
			d = Dictionary.new;
			d[\time] = time;
			/*1*/d[\loudness] = msg[3].linlin(0,64, -1,1);//0 - 64 sones
			/*2*/d[\pitch] = msg[4].linlin(16, 2900, -1,1);//set to a max of 2900 hz
			/*3*/d[\centroid] = msg[5].linlin(0, 15000, -1,1); //0 - 15000 hz
			/*4*/d[\filteringVeryLowFreqs] = msg[6].linlin(0,0.1, -1,1);
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
			/*21*/d[\zeroCrossing] = msg[23].linlin(0,160, -1,1); // 0 - 160hz
			/*22*/d[\specFlatness] = msg[24];//.linlin(0,1, -1,1); //0 -> pure sine, 1-> white noise
			/*23*/d[\specPcile] = msg[25].linlin(21,20000, -1,1); //21 - 20000 hz
			////////////////////////////////////desired outputs///////////////////////////////////////////////
			/*24*/d[\desiredKick] = msg[26];
			/*27*/d[\desiredGuira] = msg[29];
			/*28*/d[\desiredCowbell] = msg[31];
			/*29*/d[\desiredPad] = msg[32];
			/*30*/d[\desiredBass] = msg[33];
			/*31*/d[\desiredMel] = msg[34];
			/*32*/d[\desiredC] = msg[36];
			/*33*/d[\desiredCsharp] = msg[37];
			/*34*/d[\desiredD] = msg[38];
			/*35*/d[\desiredDsharp] = msg[39];
			/*36*/d[\desiredE] = msg[40];
			/*37*/d[\desiredF] = msg[41];
			/*38*/d[\desiredFsharp] = msg[42];
			/*39*/d[\desiredG] = msg[43];
			/*40*/d[\desiredGsharp] = msg[44];
			/*41*/d[\desiredA] = msg[45];
			/*42*/d[\desiredAsharp] = msg[46];
			/*43*/d[\desiredB] = msg[47];
			/*44*/d[\desiredEnergy] = msg [48];

			/////////////////////////////////////////////////printing Info//////////////////////////////////
			if (printFeatures, {Oye.printFeatures});
			if (printOutputs, {Oye.printDesiredOutputs});

			/////////////////////////////////////////sending features to Nets//////////////////////////////
			if (allowSendFeatures, {Oye.sendFeaturesToNets});

		}, "/features";
		);
	}

	*printFeatures {
		["Features" ++ ["centroid:  " ++ d[\centroid].asFloat, "onsets: "++ d[\onsets].asFloat, "zeroCross: " ++ d[\zeroCrossing].asFloat, "SpecFlat: "++ d[\specFlatness].asFloat, 	"specPercentile: " ++ d[\specPcile].asFloat,  "VLowFreqs:  " ++d[\filteringVeryLowFreqs].asFloat, "lowFreqs: " ++ d[\filteringLowFreqs].asFloat, "midFreqs: " ++ d[\filteringMidFreqs].asFloat, "highFreqs: " ++ d[\filteringHighFreqs].asFloat, "C: " ++ d[\cFilter].asFloat, "C#: " ++ d[\csFilter].asFloat, "D: " ++ d[\dFilter].asFloat, "D#: " ++ d[\dsFilter].asFloat, 	"E: " ++ d[\eFilter].asFloat, "F: " ++ d[\fFilter].asFloat, "F#: " ++ d[\fsFilter].asFloat, "G: " ++ d[\gFilter].asFloat, "G#: " ++ d[\gsFilter].asFloat, "A: " ++ d[\aFilter].asFloat, "A#: " ++ d[\asFilter].asFloat, "B: " ++ d[\bFilter].asFloat, "loudness " ++ d[\loudness].asFloat]].postln;
	}

	*printDesiredOutputs{
		["desiredOutputs "	++ ["kick " ++ d[\desiredKick].asFloat, "guira " ++ d[\desiredGuira].asFloat,"cowbell " ++ d[\desiredCowbell].asFloat, "Teclado " ++ d[\desiredPad].asFloat, "bass " ++ d[\desiredBass].asFloat, 	"Mel " ++ d[\desiredMel].asFloat, 	"C: " ++ d[\desiredC].asFloat,  "C#: " ++ d[\desiredCsharp].asFloat,  "D: " ++ d[\desiredD].asFloat,"D#: " ++ d[\desiredDsharp].asFloat,  "E: " ++ d[\desiredE].asFloat,  "F: " ++ d[\desiredF].asFloat,  "Fsharp: " ++  d[\desiredFsharp].asFloat,  "G: " ++ d[\desiredG].asFloat,  "Gsharp: " ++ d[\desiredGsharp].asFloat,  "A: " ++ d[\desiredA].asFloat,  "Asharp: " ++ d[\desiredAsharp].asFloat,  "B: " ++ d[\desiredB].asFloat], "energy: " ++d[\desiredEnergy].asFloa].postln;
	}

	*sendFeaturesToNets {
		toThinkingModule.sendMsg("/featuresToNet", d[\centroid].asFloat,   d[\specFlatness].asFloat,  d[\specPcile].asFloat, d[\zeroCrossing].asFloat, d[\onsets].asFloat, d[\filteringVeryLowFreqs].asFloat,  d[\filteringLowFreqs].asFloat, d[\filteringMidFreqs].asFloat, d[\filteringHighFreqs].asFloat, d[\cFilter].asFloat, d[\csFilter].asFloat, d[\dFilter].asFloat, d[\dsFilter].asFloat, d[\eFilter].asFloat, d[\fFilter].asFloat, d[\fsFilter].asFloat, d[\gFilter].asFloat, d[\gsFilter].asFloat,	d[\aFilter].asFloat, d[\asFilter].asFloat, d[\bFilter].asFloat, d[\loudness],  d[\onsets].asFloat, d[\filteringHighFreqs].asFloat,d[\desiredKick].asFloat, 	d[\desiredGuira].asFloat, d[\desiredCowbell].asFloat, d[\desiredPad].asFloat, d[\desiredBass].asFloat, d[\desiredMel].asFloat,	 d[\desiredC].asFloat,   d[\desiredCsharp].asFloat,  d[\desiredD].asFloat, d[\desiredDsharp].asFloat, d[\desiredE].asFloat,  d[\desiredF].asFloat, d[\desiredFsharp].asFloat, d[\desiredG].asFloat,  d[\desiredGsharp].asFloat, d[\desiredA].asFloat,  d[\desiredAsharp].asFloat,  d[\desiredB].asFloat, d[\desiredEnergy].asFloat);
	}
}


