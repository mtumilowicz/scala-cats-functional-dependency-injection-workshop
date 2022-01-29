package answers.infrastructure.user

import common.Has
import answers.domain.user.{User, UserService}
import cats.data.{Kleisli, OptionT}
import cats.effect.IO

object UserServiceProxy {
  def getById(id: String): Kleisli[OptionT[IO, *], Has[UserService], User] =
    Kleisli { _.get.getById(id) }
}
