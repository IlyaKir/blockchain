package actors

import pow.ProofOfWork

import scala.annotation.tailrec
import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

case class Miner(nodeId: String, hash: String, startProof: Long = 0) {
  val proof: Future[Long] = start
  private var isStopped: Boolean = false

  def stop(): Unit = isStopped = true
  def isValidProof(proof: Long): Boolean = ProofOfWork.isValidProof(hash, proof)

  private def start: Future[Long] = {
    Future(mine()).flatMap {
      case proof if proof > 0 => Future.successful(proof)
      case _ => Future.failed {
        println(s"Miner ${nodeId} is stopped")
        new Throwable("Miner is stopped")
      }
    }
  }

  @tailrec
  private def mine(proof: Long = startProof): Long = {
    if (isStopped) return -1
    else {
      if (isValidProof(proof)) proof
      else mine(proof + 1)
    }
  }
}