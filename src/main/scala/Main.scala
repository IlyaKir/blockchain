import actors.{Blockchain, Node}

object Main extends App {
  private val blockchain = Blockchain()

  def main(): Unit = {
    val node1 = Node("id1", blockchain)
    val miner = node1.mine()
    Thread.sleep(2000)
    println("Stopping")
    miner.stop()

    println("Finish")
    Thread.sleep(5000)
  }

  main()
}