package app.infrastructure.user

import app.domain.user.{User, UserService}
import cats.data.Kleisli
import common.Has

object UserServiceProxy {
  def getById(id: String): Kleisli[Option, Has[UserService], User] =
    Kleisli { _.get.getById(id) }
}
