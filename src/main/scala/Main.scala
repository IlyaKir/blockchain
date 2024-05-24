import actors.Node

object Main extends App {
  def main(): Unit = {
    val node1 = Node("id1")
    node1.mine()
    node1.mine()
    node1.mine()
    val block = node1.blockchain.getBlock
  }

  main()
}