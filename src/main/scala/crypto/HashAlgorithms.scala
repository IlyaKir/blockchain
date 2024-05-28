package crypto

import java.math.BigInteger
import java.security.MessageDigest

object HashAlgorithms {
  private val sha256: String = "SHA-256"
  private val utf8: String = "UTF-8"

  def sha256Hash(value: String): String = {
    val magnitude = MessageDigest.getInstance(sha256).digest(value.getBytes(utf8))
    val bigint = new BigInteger(1, magnitude)
    String.format("%064x", bigint)
  }
}
