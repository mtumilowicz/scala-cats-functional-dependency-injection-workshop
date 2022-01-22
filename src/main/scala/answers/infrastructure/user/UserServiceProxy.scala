package answers.infrastructure.user

import cats.data.ReaderT
import app.core.Has
import app.domain.user.{User, UserService}

object UserServiceProxy {
  def getById(id: String): ReaderT[Option, Has[UserService], User] =
    ReaderT { repo => repo.get.getById(id) }
}
