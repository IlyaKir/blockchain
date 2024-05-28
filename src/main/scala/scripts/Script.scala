package scripts

import crypto.Signature

trait Script {
  def unlock(signature: Signature): Boolean
}
