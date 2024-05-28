package scripts

import crypto.Signature
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey

/**
 * Pay 2 Public Key
 */
case class P2PK(publicKey: BCECPublicKey) extends Script {
  override def unlock(signature: Signature): Boolean = {
    signature.verify(publicKey)
  }
}

