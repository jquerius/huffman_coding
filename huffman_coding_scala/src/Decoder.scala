import java.io.FileInputStream

import scala.collection.mutable

class Decoder (var inputFileName: String = "", root: Node, var debug: Boolean = false) {

  var inputStream = new FileInputStream(inputFileName)
  var currentByte: Int = 0
  var bitsLeft: Int = 0

  var fileContent: String = ""
  def getContent: String = fileContent

  /**
    * Decodes the given file into a string builder, and flushes the builder upon the
    * last character of the file.
    */
  def decodeFile() {

    var sb: StringBuilder = new mutable.StringBuilder()
    while(true) {
      val symbol = nextSymbol().getOrElse({
        fileContent = sb.toString()
        if(debug) {
          println(fileContent)
        }
        return
      })
      sb += symbol
    }
  }

  /**
    * Reads the next char symbol from the file. This is done by
    * navigating the Code Tree for every bit in the file.
    * @return
    */
  def nextSymbol(): Option[Char] = {

    var currentNode = root
    while(true) {

      currentNode match {

        case internal: InternalNode => {
          var next: Node = internal.left
          val direction = readBit()
          if (direction == 1) next = internal.right
          currentNode = next
        }

        case leaf: LeafNode => return if(leaf.c != '□') Option(leaf.c) else None
      }
    }
    None
  }

  /**
    * Reads a single bit from the file by isolating the LSB in the
    * current byte.
    * @return
    */
  def readBit(): Int = {

    if(currentByte == -1) return -1

    if(bitsLeft == 0) {
      currentByte = inputStream.read()
      if(currentByte == -1) return -1
      bitsLeft = 8
    }

    bitsLeft -= 1
    (currentByte >>> bitsLeft) & 1
  }
}
