package answers.infrastructure.config.user

import cats.data.Reader
import app.core.Has
import app.domain.user
import app.domain.user.{UserRepository, UserService}

object UserServiceConfig {
  def live: Reader[Has[UserRepository], Has[UserService]] =
    Reader { repo => Has.succeed(user.UserService(repo.get)) }
}
