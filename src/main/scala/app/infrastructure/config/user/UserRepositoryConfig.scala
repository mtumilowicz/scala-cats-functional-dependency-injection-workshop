package app.infrastructure.config.user

import cats.data.Reader
import app.core.Has
import app.domain.user.UserRepository
import app.infrastructure.user.UserInMemoryRepository

object UserRepositoryConfig {
  def live: Reader[Any, Has[UserRepository]] =
    Reader { _ => Has.succeed(new UserInMemoryRepository()) }
}
