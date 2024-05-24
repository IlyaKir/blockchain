package actors

import blockchain.{GenesisBlock, Transaction}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

case class Node(nodeId: String) {
  val broker: TransactionManager = TransactionManager()
  val blockchain: Blockchain = Blockchain(GenesisBlock)

  def addTransaction(t: Transaction): Unit = {
    broker.addTransaction(t)
  }

  def mine(): Unit = {
    val lastHash = blockchain.getBlock.hash
    val proof = Await.result(Miner(lastHash).action, Duration.Inf)
    val transaction = Transaction("coinbase", nodeId, 1)
    addTransaction(transaction)
    blockchain.chainNewBlock(proof, Seq(transaction))
  }
}
