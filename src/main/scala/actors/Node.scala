package actors

import blockchain.Transaction

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class Node(nodeId: String,
                startProof: Long,
                blockchain: Blockchain,
                nodeManager: NodeManager) {
  private var currMiner: Option[Miner] = None
  val broker: TransactionManager = TransactionManager()

  def addTransaction(t: Transaction): Unit = {
    broker.addTransaction(t)
  }

  // returns new miner
  def blockchainUpdated(): Miner = {
    println(s"Blockchain updated: ${nodeId}")
    currMiner.foreach(_.stop())
    mine()
  }

  def mine(): Miner = {
    println(s"Start mining: ${nodeId}")
    val lastHash = blockchain.getBlock.hash
    val miner = Miner(nodeId, lastHash, startProof)
    val action = for {
      proof <- miner.proof
      trx <- Future(Transaction("coinbase", nodeId, 1))
      _ <- Future(addTransaction(trx))
      _ <- Future(blockchain.chainNewBlock(proof, Seq(trx)))
      _ <- Future {
        nodeManager.getAllNodes.filter(_.nodeId != nodeId).foreach(_.blockchainUpdated())
      }
    } yield ()
    currMiner = Some(miner)
    miner
  }
}
