import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.Source
import java.io._

import scala.compat.Platform

/**
  * Created by zacharymikel on 4/13/17.
  */
class Encoder(
               var inputFileName: String = "",
               var outputFileName: String = "",
               var debug: Boolean = false) {

  var currentByte: Int = 0
  var bitsFilled: Int = 0

  var fileContent: String = ""
  def getContent: String = fileContent


  var startTime: Long = 0


  /**
    * Logging function
    * @param s
    */
  def log(s: String) {
    if(debug) {
      println(s)
      startTime = Platform.currentTime
    }
  }

  /**
    * Logging function
    */
  def endLog(): Unit = {
    if(debug) println("Finished in " + (Platform.currentTime - startTime) + " ms")
  }


  /**
    * Responsible for encoding the input file and building up a code tree
    * to pass along to the decoder.
    *
    * @return
    */
  def encodeFile(): Node = {

    log("Opening file " + inputFileName + "...")
    fileContent = Source.fromFile(inputFileName).mkString
    val content: String = fileContent + "□"
    endLog()

    log("Building frequency table...")
    val tuples = List(getFrequenciesFromFile(content).toSeq.sortBy(_._2):_*)
    endLog()

    log("Building a list of nodes from the freq table... ")
    val list = mutable.Buffer[Node]()
    for((k, v) <- tuples) {
      list.append(LeafNode(k, v))
    }
    endLog()

    log("Building code tree...")
    val tree: Node = buildCodeTree(list)
    val table = buildCodeTable(Option(tree), new ListBuffer[Int], new mutable.HashMap[Char, ListBuffer[Int]])
    endLog()

    log("Encoding file...")
    writeToFile(content, table)
    endLog()
    tree
  }


  /**
    * Builds a frequency map from the characters in the file
    * @param fileContent
    * @return
    */
  def getFrequenciesFromFile(fileContent: String): mutable.HashMap[Char, Int] = {

    val frequencyTable: mutable.HashMap[Char, Int] = new mutable.HashMap[Char, Int]()

    // loop through each line in the file
    fileContent.foreach(c => {
      if (frequencyTable.getOrElse(c, 0) == 0) {
        frequencyTable.put(c, 0)
      }
      frequencyTable.update(c, frequencyTable(c) + 1)
    })
    frequencyTable
  }


  /**
    * Gets the Huffman Code Tree from the frequency map.
    * @param freqTable
    * @return
    */
  def buildCodeTree(freqTable: mutable.Buffer[Node]): Node = {

    val left = freqTable.remove(0)
    val right = freqTable.remove(0)
    val root = InternalNode(left, right, left.getFreq() + right.getFreq())
    freqTable += root

    while(freqTable.size > 1) {
      val left = freqTable.remove(0)
      val right = freqTable.remove(0)
      freqTable += InternalNode(left, right, left.getFreq() + right.getFreq())
    }

    freqTable.remove(0)
  }


  /**
    * Builds a hashmap of characters and their corresponding encodings.
    * @param node
    * @param binary
    * @param table
    * @return
    */
  def buildCodeTable(node: Option[Node], binary: ListBuffer[Int], table: mutable.HashMap[Char, ListBuffer[Int]]): mutable.HashMap[Char, ListBuffer[Int]] = {

    node.get match {
      case node: LeafNode => table.put(node.c, binary)
      case node: InternalNode => {
        var left = ListBuffer[Int]()
        left ++= binary += 0

        var right = ListBuffer[Int]()
        right ++= binary += 1

        buildCodeTable(Option(node.left), left, table)
        buildCodeTable(Option(node.right), right, table)
      }
    }

    table
  }

  /**
    * Encodes the specified file content to the file.
    * @param fileContent
    * @param table
    */
  def writeToFile(fileContent: String, table: mutable.HashMap[Char, ListBuffer[Int]]) {

    val output = new FileOutputStream(outputFileName)

    fileContent.foreach((c: Char) => {
      val list = table.get(c)
      for(i <- list.get) write(i, output)
    })

    while(bitsFilled != 0) {
      write(0, output)
    }

    output.close()
  }


  /**
    * Writes a bit to the file.
    * @param i
    * @param output
    */
  def write(i: Int, output: FileOutputStream) {
    currentByte = (currentByte << 1) | i
    bitsFilled += 1

    if(bitsFilled == 8) {
      output.write(currentByte)
      currentByte = 0
      bitsFilled = 0
    }
  }

}
