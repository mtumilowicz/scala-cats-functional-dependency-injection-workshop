package infrastructure

import cats.data.{Reader, ReaderT}
import core.Has
import domain.{BalanceRepository, User, UserRepository, UserService}

object UserServiceConfig {
  def live: Reader[Has[UserRepository], Has[UserService]] =
    Reader { repo => Has.succeed(UserService(repo.get)) }
}

object UserRepositoryConfig {
  def live: Reader[Any, Has[UserRepository]] =
    Reader { _ => Has.succeed(new UserInMemoryRepository()) }
}

object UserServiceProxy {
  def getById(id: String): ReaderT[Option, Has[UserService], User] =
    ReaderT { repo => repo.get.getById(id) }
}