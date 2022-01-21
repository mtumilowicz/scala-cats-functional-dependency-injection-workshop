package infrastructure

import cats.data.{Reader, ReaderT}
import core.Has
import domain.{User, UserRepository, UserService}

object UserServiceConfig {
  def live: Reader[Has[UserRepository], Has[UserService]] =
    Reader { repo => Has.succeed(UserService(repo.get)) }
}

object UserServiceProxy {
  def getById(id: String): ReaderT[Option, Has[UserService], User] =
    ReaderT { repo => repo.get.getById(id) }
}