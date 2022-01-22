package app.infrastructure.config.balance

import app.core.Has
import app.domain.balance.BalanceRepository
import app.infrastructure.balance.BalanceInMemoryRepository
import cats.data.Reader

object BalanceRepositoryConfig {
  def live: Reader[Any, Has[BalanceRepository]] =
    Reader { _ => Has.succeed(new BalanceInMemoryRepository()) }
}
