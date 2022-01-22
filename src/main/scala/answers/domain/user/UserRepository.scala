package answers.domain.user

import cats.data.OptionT
import cats.effect.IO

trait UserRepository {
  def getById(id: String): OptionT[IO, User]
}
