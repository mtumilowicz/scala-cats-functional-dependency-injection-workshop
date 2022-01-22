package app.infrastructure.config.user

import app.core.Has
import app.domain.user
import app.domain.user.{UserRepository, UserService}
import cats.data.Reader

object UserServiceConfig {
  def live: Reader[Has[UserRepository], Has[UserService]] =
    Reader { repo => Has.succeed(user.UserService(repo.get)) }
}
