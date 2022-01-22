package answers.domain.balance

import answers.domain.user.User

case class BalanceService(repository: BalanceRepository) {
  def getFor(user: User): Option[Int] = repository.getFor(user)
}
