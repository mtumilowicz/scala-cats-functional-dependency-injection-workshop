package answers.infrastructure.balance

import common.Has
import answers.domain.balance.BalanceService
import answers.domain.user.User
import cats.data.{Kleisli, OptionT}
import cats.effect.IO

object BalanceServiceProxy {
  def getFor(user: User): Kleisli[OptionT[IO, *], Has[BalanceService], Int] =
    Kleisli { _.get.getFor(user) }
}
