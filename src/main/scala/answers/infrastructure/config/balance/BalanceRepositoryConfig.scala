package answers.infrastructure.config.balance

import cats.data.Reader
import app.core.Has
import app.domain.balance.BalanceRepository
import app.infrastructure.balance.BalanceInMemoryRepository

object BalanceRepositoryConfig {
  def live: Reader[Any, Has[BalanceRepository]] =
    Reader { _ => Has.succeed(new BalanceInMemoryRepository()) }
}
