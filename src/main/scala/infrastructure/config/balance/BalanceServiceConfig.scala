package infrastructure.config.balance

import cats.data.Reader
import core.Has
import domain.balance.{BalanceRepository, BalanceService}

object BalanceServiceConfig {
  def live: Reader[Has[BalanceRepository], Has[BalanceService]] =
    Reader { repo => Has.succeed(BalanceService(repo.get)) }
}
