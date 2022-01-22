package answers.domain.balance

import answers.domain.user.User
import cats.data.OptionT
import cats.effect.IO

trait BalanceRepository {
  def getFor(user: User): OptionT[IO, Int]
}
