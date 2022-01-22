package infrastructure.user

import domain.user.{User, UserRepository}

class UserInMemoryRepository extends UserRepository {
  override def getById(id: String): Option[User] =
    if (id == "existing") Some(User(id)) else None
}
