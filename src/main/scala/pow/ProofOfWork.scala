package pow

import crypto.HashAlgorithms.sha256Hash

// The PoW algorithm is required to mine the blocks composing the blockchain.
// The idea is to solve a cryptographic puzzle that is hard to solve,
//  but easy to verify having the proof.
object ProofOfWork {
  private val nZeros = 5
  println(s"nZeros: ${nZeros}")

  // The higher N, the harder to find the proof
  def isValidProof(lastHash: String, proof: Long): Boolean = {
    val guess = lastHash ++ proof.toString
    val guessHash = sha256Hash(guess)
    val result = (guessHash take nZeros) == "0" * nZeros
    result
  }
}