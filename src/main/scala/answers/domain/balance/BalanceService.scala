package answers.domain.balance

import answers.domain.user.User
import cats.data.OptionT
import cats.effect.IO

case class BalanceService(repository: BalanceRepository) {
  def getFor(user: User): OptionT[IO, Int] = repository.getFor(user)
}
