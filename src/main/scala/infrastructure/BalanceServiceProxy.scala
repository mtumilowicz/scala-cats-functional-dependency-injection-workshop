package infrastructure

import cats.data.{Reader, ReaderT}
import core.Has
import domain.{BalanceRepository, BalanceService, User}

object BalanceServiceConfig {
  def live: Reader[Has[BalanceRepository], Has[BalanceService]] =
    Reader { repo => Has.succeed(BalanceService(repo.get)) }
}

object BalanceRepositoryConfig {
  def live: Reader[Any, Has[BalanceRepository]] =
    Reader { _ => Has.succeed(new BalanceInMemoryRepository()) }
}

object BalanceServiceProxy {
  def getFor(user: User): ReaderT[Option, Has[BalanceService], Int] =
    ReaderT { repo => repo.get.getFor(user) }
}