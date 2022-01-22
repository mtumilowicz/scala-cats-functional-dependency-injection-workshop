package app.domain.user

import cats.data.Kleisli
import cats.effect.IO

case class UserService(repository: UserRepository) {
  def getById(id: String): IO[Option[User]] = IO { repository.getById(id) }
}
