module Thinking where
import Sound.OSC.FD
import Sound.OSC.Transport.FD.UDP
import Control.Monad
import Control.Applicative
import Control.Monad.Loops

inputsPlusOutputs = 10
numberOfInputs = 9
numberOfOutputs = 1
port = 57005
delay = 40 -- 20 frames per secon

data TrainingSample = TrainingSample {inputs :: [Float], outputs :: [Float]}

instance Show TrainingSample where
  show (TrainingSample xs ys) = "sample " ++ (show xs) ++ " -> " ++ (show ys)

instance OSC TrainingSample where
  toPacket (TrainingSample xs ys) = Packet_Message $ message "/train" ds
    where ds = (map d_put xs) ++ (map d_put ys)
  fromPacket (Packet_Message (Message "/train" ds)) | length ds == inputsPlusOutputs = TrainingSample <$> xs <*> ys
    where xs = mapM d_get $ take numberOfInputs ds
          ys = mapM d_get $ drop numberOfInputs ds
  fromPacket _ = Nothing

main = do
  let t = udpServer "127.0.0.1" port
  withTransport t oscHandler2


oscHandler :: Transport t => t -> IO ()
oscHandler t =  do
    x <- (waitFor t fromPacket :: IO TrainingSample)
  --  x <- (recvOSC t :: IO (Maybe TrainingSample))
    --x <- recvMessage t
    putStr $ show x
--    close t


f :: [TrainingSample] -> TrainingSample -> [TrainingSample]
f[] _ = error "oops - empty list"
f oldList x = x:(init oldList)

oscHandler2 :: Transport t => t -> IO ()
oscHandler2 t = iterateM_ (g t) $ replicate delay emptyTrainingSample

g :: Transport t => t -> [TrainingSample] -> IO [TrainingSample]
g t oldState = do
    x <- (waitFor t fromPacket :: IO TrainingSample)
    let newState = f oldState x
    putStr $ showSample newState
    return newState

showSample :: [TrainingSample] -> String
showSample xs = "fromList " ++ show is ++ " --> fromList " ++ show os ++ "," ++ "\n"
  where is = concat $ map inputs xs
        os = outputs (head xs)

emptyTrainingSample :: TrainingSample
emptyTrainingSample = TrainingSample (replicate numberOfInputs 0.0) (replicate numberOfOutputs 0.0)

flattenSample :: [TrainingSample] -> [Float]
flattenSample ((TrainingSample xs ys):zs) = xs ++ ys ++ (flattenSample zs)
flattenSample [] = []
