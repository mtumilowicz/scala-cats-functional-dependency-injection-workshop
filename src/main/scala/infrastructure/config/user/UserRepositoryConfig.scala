package infrastructure.config.user

import cats.data.Reader
import core.Has
import domain.user.UserRepository
import infrastructure.user.UserInMemoryRepository

object UserRepositoryConfig {
  def live: Reader[Any, Has[UserRepository]] =
    Reader { _ => Has.succeed(new UserInMemoryRepository()) }
}
