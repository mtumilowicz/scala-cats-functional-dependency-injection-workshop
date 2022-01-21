package infrastructure

import domain.{User, UserRepository}

class UserInMemoryRepository extends UserRepository {
  override def getById(id: String): Option[User] =
    if (id.nonEmpty) Some(User(id)) else None
}
