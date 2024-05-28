package actors

import blockchain.{Block, GenesisBlock}
import pow.ProofOfWork

import scala.util.{Failure, Success, Try}

case class Blockchain(private var lastBlock: Block = GenesisBlock,
                      transactionManager: TransactionManager = TransactionManager()) {
  def getBlock: Block = lastBlock
  def chainNewBlock(proof: Long): Block = {
    if (ProofOfWork.isValidProof(getBlock.hash, proof)) {
      println(s"hash found: ${getBlock.hash}")
      checkAllTransactions()
      lastBlock = lastBlock.addNewBlock(proof, transactionManager.getTransactions)
      transactionManager.clearTransactions()
    }
    lastBlock
  }

  // TODO check message
  // prevTrx -> JSON -> SHA-256 -> compare to `message`

  private def checkAllTransactions(): Unit = {
    transactionManager.getTransactions.filter(_.input.vout != -1).foreach { trx =>
      println("checking transaction")
      val verified = Try {
        val (txid, vout) = (trx.input.txid, trx.input.vout)
        // TODO FIX
        val transaction = getBlock.transactions.find(_.txid == txid).head
        println(s"signature s: ${trx.input.signature.s}")
        println(s"signature r: ${trx.input.signature.r}")
        println(s"publicKey: ${transaction.output.publicKey.getQ.toString}")

        trx.input.signature.verify(transaction.output.publicKey)
      }
      verified match {
        case Failure(exception) => println(s"exception: ${exception.getMessage}")
        case Success(value) => println(s"verified: ${value}")
      }
    }
  }
}
