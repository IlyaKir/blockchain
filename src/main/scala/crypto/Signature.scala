package crypto

import org.bouncycastle.crypto.params.{ECPrivateKeyParameters, ECPublicKeyParameters}
import org.bouncycastle.crypto.signers.ECDSASigner
import org.bouncycastle.jcajce.provider.asymmetric.ec.{BCECPrivateKey, BCECPublicKey}

import io.circe.Encoder
import io.circe.generic.semiauto._

case class Signature(r: BigInt, s: BigInt, messageHash: String) {
  /**
   * Verify the signature using the public key
   */
  def verify(publicKey: BCECPublicKey, ellipticCurve: EllipticCurve = Secp256k1): Boolean = {
    val verifier = new ECDSASigner()
    val publicKeyParams = new ECPublicKeyParameters(publicKey.getQ, ellipticCurve.curve)

    verifier.init(false, publicKeyParams)
    verifier.verifySignature(messageHash.getBytes, r.bigInteger, s.bigInteger)
  }
}

object Signature {
  def apply(privateKey: BCECPrivateKey,
            message: String,
            ellipticCurve: EllipticCurve = Secp256k1): Signature = {
    // Hash the message using SHA-256
    val messageHash = HashAlgorithms.sha256Hash(message)

    // Create a signature using the private key
    val signer = new ECDSASigner()
    val privateKeyParams = new ECPrivateKeyParameters(privateKey.getD, ellipticCurve.curve)

    signer.init(true, privateKeyParams)
    val Array(r, s) = signer.generateSignature(messageHash.getBytes)

    // Concatenate r and s to form the signature
    //Hex.toHexString(r.toByteArray ++ s.toByteArray)
    Signature(r, s, messageHash)
  }

  // Custom encoders for BigInt
  implicit val encodeBigInt: Encoder[BigInt] = Encoder.encodeString.contramap[BigInt](_.toString)

  // Encoder for Signature
  implicit val encodeSignature: Encoder[Signature] = deriveEncoder[Signature]
}
