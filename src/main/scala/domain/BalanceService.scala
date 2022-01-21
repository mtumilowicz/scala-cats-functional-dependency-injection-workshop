package domain

case class BalanceService(repository: BalanceRepository) {
  def getFor(user: User): Option[Int] = repository.getFor(user)
}
