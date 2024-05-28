package crypto

import org.bouncycastle.jcajce.provider.asymmetric.ec.{BCECPrivateKey, BCECPublicKey}
import org.bouncycastle.jce.provider.BouncyCastleProvider

import java.security.{KeyPairGenerator, SecureRandom, Security}

object ECKeysGenerator {
  private val algorithm: String = "EC"

  def generateKeys(ellipticCurve: EllipticCurve = Secp256k1): (BCECPrivateKey, BCECPublicKey) = {
    // Add BouncyCastle as a Security Provider
    Security.addProvider(new BouncyCastleProvider())

    // Generate EC key pair for `parameterSpec`
    val keyPairGenerator = KeyPairGenerator.getInstance(algorithm, BouncyCastleProvider.PROVIDER_NAME)
    keyPairGenerator.initialize(ellipticCurve.ecGetParameterSpec, new SecureRandom())
    val keyPair = keyPairGenerator.generateKeyPair()

    (keyPair.getPrivate.asInstanceOf[BCECPrivateKey],
      keyPair.getPublic.asInstanceOf[BCECPublicKey])
  }
}
