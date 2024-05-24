package actors

import blockchain.Transaction

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class Node(nodeId: String, blockchain: Blockchain) {
  //private var currMiner: Miner = null
  val broker: TransactionManager = TransactionManager()

  def addTransaction(t: Transaction): Unit = {
    broker.addTransaction(t)
  }
//  def updateBlockchain() = {
//    currMiningAction.
//  }

  def mine(): Miner = {
    val lastHash = blockchain.getBlock.hash
    val miner = Miner(lastHash)
    val action = for {
      proof <- miner.action
      trx <- Future(Transaction("coinbase", nodeId, 1))
      _ <- Future(addTransaction(trx))
      _ <- Future.apply(blockchain.chainNewBlock(proof, Seq(trx)))
    } yield ()
    miner
  }
}
