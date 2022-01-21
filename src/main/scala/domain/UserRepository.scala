package domain

trait UserRepository {
  def getById(id: String): Option[User]
}