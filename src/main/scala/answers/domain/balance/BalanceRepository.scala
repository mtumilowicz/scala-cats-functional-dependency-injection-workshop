package answers.domain.balance

import answers.domain.user.User

trait BalanceRepository {
  def getFor(user: User): Option[Int]
}
