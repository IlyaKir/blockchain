package crypto

import org.bouncycastle.crypto.params.ECDomainParameters
import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec

import java.security.spec.ECGenParameterSpec

trait EllipticCurve {
  val spec: String
  val ecGetParameterSpec: ECGenParameterSpec
  val curve: ECDomainParameters
}

object Secp256k1 extends EllipticCurve {
  val spec: String = "secp256k1"
  val ecGetParameterSpec = new ECGenParameterSpec(spec)

  private val curveParams: ECNamedCurveParameterSpec =
    ECNamedCurveTable.getParameterSpec(spec)
  val curve: ECDomainParameters = new ECDomainParameters(
    curveParams.getCurve,
    curveParams.getG,
    curveParams.getN,
    curveParams.getH
  )
}
