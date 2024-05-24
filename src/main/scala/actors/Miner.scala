package actors

import pow.ProofOfWork

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class Miner(hash: String) {
  val action: Future[Long] = Future(ProofOfWork.start(hash))

  def isValidProof(proof: Long): Boolean = {
    ProofOfWork.isValidProof(hash, proof)
  }
}
