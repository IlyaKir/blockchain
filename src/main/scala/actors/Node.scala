package actors

import blockchain.{CoinbaseTransactionInput, Transaction, TransactionOutput}
import crypto.ECKeysGenerator

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class Node(nodeId: String,
                startProof: Long,
                blockchain: Blockchain,
                nodeManager: NodeManager) {
  val (privateKey, publicKey) = ECKeysGenerator.generateKeys()

  private var currMiner: Option[Miner] = None

  // returns new miner
  def blockchainUpdated(): Unit = {
    println(s"Blockchain updated: ${nodeId}")
    currMiner.foreach(_.stop())
    //mine()
  }

  def mine(): Future[Unit] = {
    println(s"Start mining: ${nodeId}")
    val lastHash = blockchain.getBlock.hash
    println(s"LASTHASH = ${lastHash}")
    val miner = Miner(nodeId, lastHash, startProof)
    val action = for {
      proof <- miner.proof
      trx <- Future { Transaction(
          toNodeId = nodeId,
          input = new CoinbaseTransactionInput(),
          output = TransactionOutput(publicKey = publicKey, satoshi = 100)
        ) }
      _ <- Future(blockchain.transactionManager.addTransaction(trx))
      _ <- Future(blockchain.chainNewBlock(proof))
      _ <- Future {
        nodeManager.getAllNodes.filter(_.nodeId != nodeId).foreach(_.blockchainUpdated())
      }
    } yield ()
    currMiner = Some(miner)
    action
  }
}
