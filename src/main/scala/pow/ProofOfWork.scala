package pow

import hash.HashAlgorithms.sha256Hash

// The PoW algorithm is required to mine the blocks composing the blockchain.
// The idea is to solve a cryptographic puzzle that is hard to solve,
//  but easy to verify having the proof.
object ProofOfWork {
  private val nZeros = scala.util.Properties.envOrElse("complexity", "5").toInt
  println(s"nZeros: ${nZeros}")

  // The higher N, the harder to find the proof
  def isValidProof(lastHash: String, proof: Long): Boolean = {
//    if (proof % 10000 == 0) println(proof / 10000)
    val guess = lastHash ++ proof.toString
    val guessHash = sha256Hash(guess)
    val result = (guessHash take nZeros) == "0" * nZeros
//    if (result) println(s"proof: ${proof}\n" +
//      s"guessHash: ${guessHash}") else ()
    result
  }
}