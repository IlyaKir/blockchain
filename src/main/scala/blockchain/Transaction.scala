package blockchain

/**
 * Register movement of coins between two entities
 */
case class Transaction(from: String,
                       to: String,
                       amountOfCoins: Long)