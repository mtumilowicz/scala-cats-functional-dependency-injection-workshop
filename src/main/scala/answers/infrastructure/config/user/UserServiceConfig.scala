package answers.infrastructure.config.user

import answers.core.Has
import answers.domain.user
import answers.domain.user.{UserRepository, UserService}
import cats.data.Reader

object UserServiceConfig {
  def live: Reader[Has[UserRepository], Has[UserService]] =
    Reader { repo => Has.succeed(user.UserService(repo.get)) }
}
