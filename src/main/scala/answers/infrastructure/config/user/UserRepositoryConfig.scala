package answers.infrastructure.config.user

import common.Has
import answers.domain.user.UserRepository
import answers.infrastructure.user.UserInMemoryRepository
import cats.data.Reader

object UserRepositoryConfig {
  def live: Reader[Any, Has[UserRepository]] =
    Reader { _ => Has.succeed(new UserInMemoryRepository()) }
}
