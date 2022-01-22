package answers.infrastructure.user

import answers.domain.user.{User, UserRepository}
import cats.data.OptionT
import cats.effect.IO

class UserInMemoryRepository extends UserRepository {
  override def getById(id: String): OptionT[IO, User] =
    IO { if (id == "existing") Some(User(id)) else None }
}
