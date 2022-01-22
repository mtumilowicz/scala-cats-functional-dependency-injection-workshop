package app.domain.user

import cats.effect.IO

case class UserService(repository: UserRepository) {
  def getById(id: String): Option[User] = repository.getById(id)
}
