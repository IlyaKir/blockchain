package actors

import blockchain.{Block, GenesisBlock, Transaction}
import pow.ProofOfWork

case class Blockchain(private var lastBlock: Block = GenesisBlock) {
  def getBlock: Block = lastBlock
  def chainNewBlock(proof: Long, transactions: Seq[Transaction]): Block = {
    if (ProofOfWork.isValidProof(getBlock.hash, proof)) lastBlock = lastBlock.addNewBlock(proof, transactions)
    lastBlock
  }
}
