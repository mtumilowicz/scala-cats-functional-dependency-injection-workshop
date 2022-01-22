package answers.infrastructure.config.balance

import answers.core.Has
import answers.domain.balance.{BalanceRepository, BalanceService}
import cats.data.Reader

object BalanceServiceConfig {
  def live: Reader[Has[BalanceRepository], Has[BalanceService]] =
    Reader { repo => Has.succeed(BalanceService(repo.get)) }
}
