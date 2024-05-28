import actors.{Blockchain, Node, NodeManager, TransactionManager}
import blockchain.{Transaction, TransactionInput, TransactionOutput}
import crypto.{ECKeysGenerator, Signature}

object Main extends App {
  private val blockchain = Blockchain()
  private val nodeManager = NodeManager()

  val node1 = Node("id1", 0, blockchain, nodeManager)
  val node2 = Node("id2", 1000000000, blockchain, nodeManager)
  nodeManager.addNode(node1, node2)

  node1.mine()
  node2.mine()
  Thread.sleep(5000)
  println("After sleeping 10s")

  val trxToNodeId = blockchain.getBlock.transactions.head.toNodeId
  if (trxToNodeId == node1.nodeId) {
    val prevTxid = blockchain.getBlock.transactions.head.txid
    val newTrx = Transaction(
      toNodeId = node2.nodeId,
      input = TransactionInput(
        txid = prevTxid,
        signature = Signature.apply(
          privateKey = node1.privateKey,
          message = prevTxid
        )),
      output = TransactionOutput(
        publicKey = node2.publicKey,
        satoshi = 100
      )
    )
    node1.blockchain.transactionManager.addTransaction(newTrx)
  }
  else {
    val prevTxid = blockchain.getBlock.transactions.head.txid
    val newTrx = Transaction(
      toNodeId = node1.nodeId,
      input = TransactionInput(
        txid = prevTxid,
        signature = Signature.apply(
          privateKey = node2.privateKey,
          message = prevTxid
        )),
      output = TransactionOutput(
        publicKey = node1.publicKey,
        satoshi = 100
      )
    )
    node2.blockchain.transactionManager.addTransaction(newTrx)
  }
  val action1 = node1.mine()
  val action2 = node2.mine()
  Thread.sleep(10000)

  println("Finish")
}