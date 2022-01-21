package domain

case class UserService(repository: UserRepository) {
  def getById(id: String): Option[User] = repository.getById(id)
}
