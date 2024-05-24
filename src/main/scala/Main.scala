import actors.{Blockchain, Node, NodeManager}

object Main extends App {
  private val blockchain = Blockchain()
  private val nodeManager = NodeManager()

  val node1 = Node("id1", 0, blockchain, nodeManager)
  val node2 = Node("id2", 1000000000, blockchain, nodeManager)
  nodeManager.addNode(node1, node2)

  val miner1 = node1.mine()
  val miner2 = node2.mine()

  Thread.sleep(10000)
  println("Finish")
}