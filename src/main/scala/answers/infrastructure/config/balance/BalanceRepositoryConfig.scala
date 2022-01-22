package answers.infrastructure.config.balance

import answers.core.Has
import answers.domain.balance.BalanceRepository
import answers.infrastructure.balance.BalanceInMemoryRepository
import cats.data.Reader

object BalanceRepositoryConfig {
  def live: Reader[Any, Has[BalanceRepository]] =
    Reader { _ => Has.succeed(new BalanceInMemoryRepository()) }
}
