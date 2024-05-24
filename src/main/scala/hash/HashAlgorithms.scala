package hash

import java.math.BigInteger
import java.security.MessageDigest

object HashAlgorithms {
  def sha256Hash(value: String): String = {
    val magnitude = MessageDigest.getInstance("SHA-256").digest(value.getBytes("UTF-8"))
    val bigint = new BigInteger(1, magnitude)
    val str = String.format("%064x", bigint)
//    println(s"value: ${value}\n" +
//      s"\tmagnitude: ${magnitude.toSeq.mkString(" ")}\n" +
//      s"\tbigint: ${bigint}\n" +
//      s"\tstr: ${str}")
    str
  }
}
