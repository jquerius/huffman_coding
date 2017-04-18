case class InternalNode(left: Node, right: Node, f: Int) extends Node {
  override def getFreq(): Int = { this.f }

  override def toString: String = { "<" + this.f + ">" }
}
