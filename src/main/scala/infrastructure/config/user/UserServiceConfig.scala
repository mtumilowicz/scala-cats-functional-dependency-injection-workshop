package infrastructure.config.user

import cats.data.Reader
import core.Has
import domain.user
import domain.user.{UserRepository, UserService}

object UserServiceConfig {
  def live: Reader[Has[UserRepository], Has[UserService]] =
    Reader { repo => Has.succeed(user.UserService(repo.get)) }
}
