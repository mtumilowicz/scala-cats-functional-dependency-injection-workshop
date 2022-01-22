package app.infrastructure.user

import app.core.Has
import app.domain.user.{User, UserService}
import cats.data.Kleisli

object UserServiceProxy {
  def getById(id: String): Kleisli[Option, Has[UserService], User] =
    Kleisli { _.get.getById(id) }
}
