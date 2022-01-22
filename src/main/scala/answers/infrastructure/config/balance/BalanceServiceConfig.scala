package answers.infrastructure.config.balance

import cats.data.Reader
import app.core.Has
import app.domain.balance.{BalanceRepository, BalanceService}

object BalanceServiceConfig {
  def live: Reader[Has[BalanceRepository], Has[BalanceService]] =
    Reader { repo => Has.succeed(BalanceService(repo.get)) }
}
