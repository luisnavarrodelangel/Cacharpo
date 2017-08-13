import AI.HNN.FF.Network
import Numeric.LinearAlgebra
import Language.Haskell.Interpreter as Hint
import Data.Time.Clock
import System.IO

main :: IO ()
main = do
  startTime <- getCurrentTime
  putStrLn $ show startTime
  putStrLn "loading cross validation samples"
  s' <- loadCrossValSamples
  putStrLn "Cross validation samples loaded"
  putStrLn "loading training samples"
  s <- loadTrainingSamples
  putStrLn "training samples loaded"
  startTrainingTime <- getCurrentTime
  putStrLn $ "training " ++ (show startTrainingTime)
  train 1000000 s s' [40] "cowbellUltimate_Net-0.001_" 0.001
  endTime <- getCurrentTime
  let elapsed = diffUTCTime endTime startTime
  putStrLn $ "training completed in: " ++ (show elapsed)

train :: Int -> Samples Double -> Samples Double -> [Int] -> String -> Double -> IO (Network Double)
train n s s' hidden prefix rate = do
  newNetwork <- createNetwork nInputs hidden nOutputs
  train' s s' 1 n prefix rate newNetwork
  where
    nInputs = (length . toList . fst) (s!!0)
    nOutputs = (length . toList . snd) (s!!0)

train' :: Samples Double -> Samples Double -> Int -> Int -> String -> Double -> Network Double -> IO (Network Double)
train' _ _ _ 0 _ _ n = return n
train' s s' countingUp countingDown prefix rate n = do
  let n' = trainNTimes 1 rate tanh tanh' n s
  let error = quadError tanh n' s :: Double
  let error' = quadError tanh n' s' :: Double
  let filename = prefix ++ (show countingUp) ++ ".nn"
  saveNetwork ("/home/lui/Dropbox/McMaster/MRP/ANN/nets/" ++ filename) n'
  putStrLn $ filename ++ " error " ++ (show (error/fromIntegral nSamples)) ++ " cross-Val " ++ (show (error'/fromIntegral nSamples'))
  train' s s' (countingUp+1) (countingDown-1) prefix rate n'
  where
    nSamples = length s
    nSamples' = length s'


loadTrainingSamples :: IO (Samples Double)
loadTrainingSamples = do
  h <- openFile "cowbellAug12Samples.txt" ReadMode
  mainLoop h

loadCrossValSamples :: IO (Samples Double)
loadCrossValSamples = do
  h <- openFile "cowbellAug12Samples.txt" ReadMode
  mainLoop h

mainLoop :: Handle -> IO (Samples Double)
mainLoop h = do
  ineof <- hIsEOF h
  if ineof
    then return []
    else do
      putStr "."
      s <- hGetLine h
      s' <- hintSample' $ init s
      -- putStrLn $ show s'
      theRest <- mainLoop h
      return (s' ++ theRest)



hintSample' :: String -> IO [Sample Double]
hintSample' x = do
  y <- hintSample x
  return $ f y
  where
    f (Left _) = []
    f (Right z) = [z]

hintSample :: String -> IO (Either InterpreterError (Sample Double))
hintSample x = Hint.runInterpreter $ do
  -- Hint.set [languageExtensions := [OverloadedStrings]]
  Hint.setImports ["Prelude","AI.HNN.FF.Network","Numeric.LinearAlgebra"]
  Hint.interpret x (Hint.as::Sample Double)
