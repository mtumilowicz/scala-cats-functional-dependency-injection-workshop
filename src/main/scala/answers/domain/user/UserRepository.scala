package answers.domain.user

trait UserRepository {
  def getById(id: String): Option[User]
}
