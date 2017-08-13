import AI.HNN.FF.Network
import Numeric.LinearAlgebra
import Language.Haskell.Interpreter as Hint
import System.IO
import Data.Binary (Binary(..),encode,decode)
import qualified Data.ByteString.Lazy as B
import Codec.Compression.Zlib (compress, decompress)
import Data.Functor ((<$>))
--import Control.Applicative
import Foreign.Storable (Storable)
import Data.Vector.Binary

main :: IO ()
main = parseSamplesAndSave "oneSample.txt" "oneSample.bin"


parseSamplesAndSave :: String -> String -> IO ()
parseSamplesAndSave f1 f2 = do
  s <- parseSamples f1
  B.writeFile f2 $ (compress . encode) s

parseSamples :: String -> IO (Samples Double)
parseSamples f = do
  h <- openFile f ReadMode
  parseLoop h

parseLoop :: Handle -> IO (Samples Double)
parseLoop h = do
  ineof <- hIsEOF h
  if ineof
    then return []
    else do
      putStr "."
      s <- hGetLine h
      s' <- hintSample' $ init s
      -- putStrLn $ show s'
      theRest <- parseLoop h
      return (s' ++ theRest)

loadBinarySamples :: (Storable a, Element a, Binary a) => String -> IO (Samples a)
loadBinarySamples f = return . decode . decompress =<< B.readFile f

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
