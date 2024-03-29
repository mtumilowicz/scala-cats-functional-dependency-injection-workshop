package common

import izumi.reflect.Tag


final case class Has[A](map: Map[String, Any])

object Has {
  implicit class HasOps[Self <: Has[_]](self: Self) {
    def get[A](implicit ev: Self <:< Has[A], tag: Tag[A]): A =
      self.map(tag.toString).asInstanceOf[A]

    def ++[That <: Has[_]](that: That): Self with That =
      Has(self.map ++ that.map).asInstanceOf[Self with That]
  }
  def succeed[A](value: A)(implicit tag: Tag[A]): Has[A] =
    Has(Map(tag.toString -> value))
}
