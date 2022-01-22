package infrastructure.config.balance

import cats.data.Reader
import core.Has
import domain.balance.BalanceRepository
import infrastructure.balance.BalanceInMemoryRepository

object BalanceRepositoryConfig {
  def live: Reader[Any, Has[BalanceRepository]] =
    Reader { _ => Has.succeed(new BalanceInMemoryRepository()) }
}
