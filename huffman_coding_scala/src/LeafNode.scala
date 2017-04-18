case class LeafNode(c: Char, f: Int) extends Node {

  override def getFreq(): Int = { this.f }
}



