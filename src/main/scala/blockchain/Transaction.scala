package blockchain

import crypto.{HashAlgorithms, Signature}
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey

import scala.util.Random

import io.circe.Encoder
import io.circe.generic.semiauto._
import io.circe.generic.auto._
import io.circe.syntax._

// When your Bitcoin wallet tells you that you have 10,000 satoshi balance,
// it really means that you have 10,000 satoshi`s waiting in one or more UTXOs.
/**
 * Register movement of coins between two entities
 */
case class Transaction(toNodeId: String, // TODO TEMP
                       input: TransactionInput,
                       output: TransactionOutput,
//                       inputs: Seq[TransactionInput],
//                       outputs: Seq[TransactionOutput],
                       version: Int = 1) {
  val lockTime: Long = System.currentTimeMillis()
  // HASH256([version][inputs][outputs][lockTime])
  val txid: String = HashAlgorithms.sha256Hash(version.toString ++
    input.asJson.toString() ++
    output.asJson.toString() ++
    lockTime.toString)
}
object Transaction {
  implicit val encode: Encoder[Transaction] = deriveEncoder[Transaction]
}

case class TransactionInput(txid: String,
                            signature: Signature, // temp P2PK
//                            scriptSig: String,
                            vout: Int = 0)
object TransactionInput {
  implicit val encode: Encoder[TransactionInput] = deriveEncoder[TransactionInput]
}

class CoinbaseTransactionInput extends TransactionInput(
  txid = s"coinbase_${Random.nextInt()}",
  signature = Signature(1, 1, "1"),
  vout = -1
)

case class TransactionOutput(publicKey: BCECPublicKey,
                             //scriptPubKey: Script,
                             satoshi: Short) {
  /**
   * Anyone who can satisfy the conditions of that pubKey script can spend up satoshi`s.
   */
//  def pubKeyScript(scriptSig: String): Boolean = {
//    // `privateKey` -> `publicKey` -> `publicKeyHash`
//    // `receiverPublicKeyHash` == `publicKeyHash`
//    ???
//  }
}
object TransactionOutput {
  implicit val encodePublicKey: Encoder[BCECPublicKey] = Encoder.encodeString.contramap[BCECPublicKey](_.toString())
  implicit val encode: Encoder[TransactionOutput] = deriveEncoder[TransactionOutput]
}