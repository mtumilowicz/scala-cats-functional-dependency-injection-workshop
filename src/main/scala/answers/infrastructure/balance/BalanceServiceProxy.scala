package answers.infrastructure.balance

import answers.core.Has
import answers.domain.balance.BalanceService
import answers.domain.user.User
import cats.data.{Kleisli, OptionT}
import cats.effect.IO

object BalanceServiceProxy {
  def getFor(user: User): Kleisli[({type L[a] = OptionT[IO, a]})#L, Has[BalanceService], Int] =
    Kleisli { _.get.getFor(user) }
}
