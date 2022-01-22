package app.infrastructure.config.user

import app.core.Has
import app.domain.user.UserRepository
import app.infrastructure.user.UserInMemoryRepository
import cats.data.Reader

object UserRepositoryConfig {
  def live: Reader[Any, Has[UserRepository]] =
    Reader { _ => Has.succeed(new UserInMemoryRepository()) }
}
