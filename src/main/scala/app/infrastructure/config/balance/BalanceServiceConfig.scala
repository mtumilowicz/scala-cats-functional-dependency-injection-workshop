package app.infrastructure.config.balance

import app.domain.balance.{BalanceRepository, BalanceService}
import cats.data.Reader
import common.Has

object BalanceServiceConfig {
  def live: Reader[Has[BalanceRepository], Has[BalanceService]] =
    Reader { repo => Has.succeed(BalanceService(repo.get)) }
}
