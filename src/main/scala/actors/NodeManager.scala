package actors

import scala.collection.mutable

case class NodeManager() {
  private val nodes: mutable.Set[Node] = mutable.Set()

  def getAllNodes: Seq[Node] = nodes.toSeq
  def addNode(node: Node*): Unit = nodes.addAll(node)
  def deleteNode(node: Node): Boolean = nodes.remove(node)
}
