package common

import izumi.reflect.Tag


final case class Has[A](map: Map[String, Any])

object Has {
  implicit class HasOps[Self <: Has[_]](self: Self) {
    def get[A](implicit ev: Self <:< Has[A], classTag: Tag[A]): A =
      self.map(classTag.toString).asInstanceOf[A]

    def ++[That <: Has[_]](that: That): Self with That =
      Has(self.map ++ that.map).asInstanceOf[Self with That]
  }
  def succeed[A](value: A)(implicit classTag: Tag[A]): Has[A] =
    Has(Map(classTag.toString -> value))
}
