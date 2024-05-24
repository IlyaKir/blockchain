package pow

import hash.HashAlgorithms.sha256Hash

import scala.annotation.tailrec

// The PoW algorithm is required to mine the blocks composing the blockchain.
// The idea is to solve a cryptographic puzzle that is hard to solve,
//  but easy to verify having the proof.
object ProofOfWork {
  private val nZeros: Int = 5

  def start(lastHash: String = "", proof: Long = 0): Long = {
    action(lastHash, proof)
  }

  @tailrec
  private def action(lastHash: String, proof: Long): Long = {
    if (isValidProof(lastHash, proof)) {
      proof
    } else
      action(lastHash, proof + 1)
  }

  // The higher N, the harder to find the proof
  def isValidProof(lastHash: String, proof: Long): Boolean = {
    val guess = lastHash ++ proof.toString
    val guessHash = sha256Hash(guess)
    val result = (guessHash take nZeros) == "0" * nZeros
    if (result) println(s"proof: ${proof}\n" +
      s"guessHash: ${guessHash}") else ()
    result
  }
}