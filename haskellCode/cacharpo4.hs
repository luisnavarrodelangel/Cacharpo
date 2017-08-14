module Thinking where
import AI.HNN.FF.Network
import Numeric.LinearAlgebra
import Sound.OSC.FD
import Sound.OSC.Transport.FD.UDP
import Control.Monad
import Control.Applicative
import Control.Monad.Loops
import qualified Data.Vector as Data.Vector
import Numeric.LinearAlgebra.Data
import GHC.Float


numberOfInputs = 43
numOfRolesInputs = 9
numOfPitchRecognitionInputs = 12
numOfEnergyInputs = 3
port = 57604
delay = 40 -- 20 frames per second

data InputsToNetworks = InputsToNetworks {rolesInputs :: [Float], pitchRecognitionInputs :: [Float], energyInputs :: [Float]}


instance Show InputsToNetworks where
  show (InputsToNetworks rs ps es) = "rolesInputs " ++ (show rs) ++ "pitchRecognitionInputs " ++ (show ps) ++ "energyInputs " ++ (show es)

instance OSC InputsToNetworks where
  toPacket (InputsToNetworks rs ps es) = Packet_Message $ message "/featuresToNet" ds
    where ds = (map d_put rs) ++ (map d_put ps) ++ (map d_put es) 
  fromPacket (Packet_Message (Message "/featuresToNet" ds)) | length ds == numberOfInputs = InputsToNetworks <$> rs <*> ps <*> es
    where rs = mapM d_get $ take numOfRolesInputs ds
          ps = mapM d_get $ take numOfPitchRecognitionInputs $ drop numOfRolesInputs ds
          es = mapM d_get $ take numOfEnergyInputs $ drop (numOfRolesInputs + numOfPitchRecognitionInputs) ds
  fromPacket _ = Nothing

describeNet :: Network Float -> IO ()
describeNet n = do
  let m = matrices n
  let l = Data.Vector.length m
  let r = Numeric.LinearAlgebra.Data.rows (m Data.Vector.! 0)
  let c = Numeric.LinearAlgebra.Data.cols (m Data.Vector.! 0)
  putStrLn $ "vectorLength: " ++ (show l) ++ " rows: " ++ (show r) ++ " cols: " ++ (show c)

main = do
  melNet <- loadNetwork "/home/lui/Dropbox/McMaster/MRP/ANN/nets/melUltimate_Net-0.001_610.nn"
  tecladoNet <- loadNetwork "/home/lui/Dropbox/McMaster/MRP/ANN/nets/tecladoUltimate_Net-0.001_285.nn"
  bajoNet <- loadNetwork "/home/lui/Dropbox/McMaster/MRP/ANN/nets/bajoUltimate_Net-0.001_283.nn"
  guiroNet <- loadNetwork "/home/lui/Dropbox/McMaster/MRP/ANN/nets/guiroUltimate_Net-0.001_87.nn"
  cowbellNet <- loadNetwork "/home/lui/Dropbox/McMaster/MRP/ANN/nets/cowbellUltimate_Net-0.001_559.nn"
  kickNet <- loadNetwork "/home/lui/Dropbox/McMaster/MRP/ANN/nets/kickUltimate_Net-0.001_1285.nn"
  pitchRecognitionNet <- loadNetwork "/home/lui/Dropbox/McMaster/MRP/ANN/nets/c-b+chCmajorScale_Net-0.001_2182.nn"
  energyNet <- loadNetwork "/home/lui/Dropbox/McMaster/MRP/ANN/nets/energy_Net-0.001_1554.nn"
  putStrLn "Cacharpo status: listening"
  let t = udpServer "127.0.0.1" port
  m <- openUDP "127.0.0.1" 57120
  withTransport t (oscHandler2 m melNet tecladoNet bajoNet guiroNet cowbellNet kickNet pitchRecognitionNet energyNet)


f :: [InputsToNetworks] -> InputsToNetworks -> [InputsToNetworks]
f[] _ = error "oops - empty list"
f oldList x = x:(init oldList)

oscHandler2 :: Transport t => t -> Network Float -> Network Float -> Network Float -> Network Float -> Network Float -> Network Float -> Network Float -> Network Float -> t -> IO ()
oscHandler2 m melNet tecladoNet bajoNet guiroNet cowbellNet kickNet pitchRecognitionNet energyNet t = iterateM_ (g t m melNet tecladoNet bajoNet guiroNet cowbellNet kickNet pitchRecognitionNet energyNet) $ replicate delay emptyInputsToNetworks

g :: Transport t => t -> t -> Network Float -> Network Float -> Network Float -> Network Float -> Network Float -> Network Float -> Network Float -> Network Float -> [InputsToNetworks] -> IO [InputsToNetworks]
g t m melNet tecladoNet bajoNet guiroNet cowbellNet kickNet pitchRecognitionNet energyNet oldState = do
    x <- (waitFor t fromPacket :: IO InputsToNetworks)
    let newState = f oldState x


    let outMel = output melNet tanh $ justInputRoles newState 
    let outMel' = fmap Float $ toList outMel
    
    let outTeclado =  output tecladoNet tanh $ justInputRoles newState 
    let outTeclado' = fmap Float $ toList outTeclado

    let outBajo =  output bajoNet tanh $ justInputRoles newState 
    let outBajo' = fmap Float $ toList outBajo

    let outGuiro =  output guiroNet tanh $ justInputRoles newState 
    let outGuiro' = fmap Float $ toList outGuiro

    let outCowbell =  output cowbellNet tanh $ justInputRoles newState 
    let outCowbell' = fmap Float $ toList outCowbell

    let outKick =  output kickNet tanh $ justInputRoles newState 
    let outKick' = fmap Float $ toList outKick

    let outPitchRecognition = output pitchRecognitionNet tanh $ justInputPitchRecognition newState
    let outPitchRecognition' = fmap Float $ toList outPitchRecognition

    let outEnergy = output energyNet tanh $ justInputEnergy newState
    let outEnergy' = fmap Float $ toList outEnergy
 
    let out = outMel' ++ outTeclado' ++ outBajo' ++ outGuiro' ++ outCowbell' ++ outKick' ++ outPitchRecognition' ++ outEnergy'
    sendMessage m $ message "/n" out
    --print $ outLoudness' ++ outRoles'
    --print outLoudness'
    --print outRoles'
   -- putStr $ justInputRoles newState
   -- putStr $ showSampleLoudness newState
    return newState


justInputRoles :: [InputsToNetworks] -> Vector Float
justInputRoles xs = fromList $ concat $ map rolesInputs xs 

justInputPitchRecognition :: [InputsToNetworks] -> Vector Float
justInputPitchRecognition xs = fromList $ concat $ map pitchRecognitionInputs xs


justInputEnergy :: [InputsToNetworks] -> Vector Float
justInputEnergy xs = fromList $ concat $ map energyInputs xs

emptyInputsToNetworks :: InputsToNetworks
emptyInputsToNetworks = InputsToNetworks (replicate numOfRolesInputs 0.0) (replicate numOfPitchRecognitionInputs 0.0)  (replicate numOfEnergyInputs 0.0) 
flattenSample :: [InputsToNetworks] -> [Float]
flattenSample ((InputsToNetworks rs ps es):zs) = rs ++ ps ++ es ++ (flattenSample zs)
flattenSample [] = []
