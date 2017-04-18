import java.io.File

/**
  * Created by zacharymikel on 4/13/17.
  */
object HuffmanDriver {

  var debug: Boolean = false

  def main(args: Array[String]): Unit = {

    val inputFileName = args(0)
    val outputFileName = args(1)
    debug = args(2).toBoolean

    val start = System.currentTimeMillis()

    logInfo("==============================================")
    logInfo("SCALA HUFFMAN ENCODER")
    logInfo("==============================================\n\n")

    logInfo("Input file: " + inputFileName)
    logInfo("Output file: " + outputFileName)

    logInfo("\n\nStarting encoder...")
    logDebug("----------------------------------------------")
    var encoder: Encoder = new Encoder(inputFileName, outputFileName, debug)
    val codeTree: Node = encoder.encodeFile()
    logDebug("----------------------------------------------\n\n")
    logInfo("Encoding finished.\n\n")

    logInfo("Starting decoder...")
    logDebug("----------------------------------------------")
    var decoder: Decoder = new Decoder(outputFileName, codeTree, debug)
    decoder.decodeFile()
    logDebug("----------------------------------------------")
    logInfo("Decoding finished.\n\n")


    logInfo("Total time elapsed: " + (System.currentTimeMillis() - start) + " ms")
    logInfo("input file size: " + new File(inputFileName).length() + " bytes")
    logInfo("output file size: " + new File(outputFileName).length() + " bytes")

    val eq = new EqualityCheck()
    eq.check(encoder.getContent, decoder.getContent)
    println(eq.getStatusMessage)
  }

  def logInfo(string: String): Unit = println(string)
  def logDebug(string: String): Unit = {
    if(debug) println(string)
  }

}
