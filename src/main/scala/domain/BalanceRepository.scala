package domain

trait BalanceRepository {
  def getFor(user: User): Option[Int]
}
