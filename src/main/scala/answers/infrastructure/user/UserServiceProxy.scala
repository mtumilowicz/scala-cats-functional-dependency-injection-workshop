package answers.infrastructure.user

import cats.data.{Kleisli, OptionT, ReaderT}
import app.core.Has
import app.domain.user.{User, UserService}
import cats.effect.IO

object UserServiceProxy {
  def getById(id: String): Kleisli[({type L[a] = OptionT[IO, a]})#L, Has[UserService], User] =
    ReaderT { repo => repo.get.getById(id) }
}
