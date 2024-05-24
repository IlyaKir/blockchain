package actors

import blockchain.Transaction

case class TransactionManager() {
  private var pendingTransactions: Seq[Transaction] = Seq.empty

  def addTransaction(t: Transaction): Unit = pendingTransactions = pendingTransactions :+ t
  def getTransactions: Seq[Transaction] = pendingTransactions
  def clearTransactions: Unit = pendingTransactions = Seq.empty
}
