package answers.infrastructure.balance

import answers.domain.balance.BalanceRepository
import answers.domain.user.User
import cats.data._
import cats.effect.IO
import cats.implicits._

class BalanceInMemoryRepository extends BalanceRepository {
  override def getFor(user: User): OptionT[IO, Int] = Some(5).toOptionT
}
