package answers.infrastructure.user

import answers.domain.user.{User, UserRepository}
import cats.data._
import cats.effect.IO
import cats.implicits._

class UserInMemoryRepository extends UserRepository {
  override def getById(id: String): OptionT[IO, User] =
    (if (id == "existing") Some(User(id)) else None ).toOptionT
}
