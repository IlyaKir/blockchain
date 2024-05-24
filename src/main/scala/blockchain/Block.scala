package blockchain

import hash.HashAlgorithms

trait Block {
  val index: Int // current height of chain
  val hash: String
  val previousBlock: Block
  val proof: Long
  val transactions: Seq[Transaction]
  val createdAt: Long

  def addNewBlock(proof: Long, transactions: Seq[Transaction]): ChainedBlock = {
    ChainedBlock(
      index = this.index + 1,
      previousBlock = this,
      proof = proof,
      transactions = transactions
    )
  }
}

case object GenesisBlock extends Block {
  val index: Int = 0
  val hash: String = ""
  val previousBlock: Block = null
  val proof: Long = -1L
  val transactions: Seq[Transaction] = Seq.empty
  val createdAt: Long = System.currentTimeMillis()
}

case class ChainedBlock(index: Int,
                        previousBlock: Block,
                        proof: Long,
                        transactions: Seq[Transaction]) extends Block {
  val createdAt: Long = System.currentTimeMillis()
  val hash: String = HashAlgorithms
    .sha256Hash(s"${previousBlock.hash}$createdAt")
}