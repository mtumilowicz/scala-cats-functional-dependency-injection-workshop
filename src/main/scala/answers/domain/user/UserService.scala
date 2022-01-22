package answers.domain.user

import cats.data.OptionT
import cats.effect.IO

case class UserService(repository: UserRepository) {
  def getById(id: String): OptionT[IO, User] = repository.getById(id)
}
