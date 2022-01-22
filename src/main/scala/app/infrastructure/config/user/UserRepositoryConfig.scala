package app.infrastructure.config.user

import app.domain.user.UserRepository
import app.infrastructure.user.UserInMemoryRepository
import cats.data.Reader
import common.Has

object UserRepositoryConfig {
  def live: Reader[Any, Has[UserRepository]] =
    Reader { _ => Has.succeed(new UserInMemoryRepository()) }
}
