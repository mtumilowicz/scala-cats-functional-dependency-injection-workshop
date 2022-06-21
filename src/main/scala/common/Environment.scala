package common

import cats.Id
import cats.data.Kleisli

object Environment {

  def compose[E[_], R, O](program: Kleisli[E, R, O],
                          appLayer: Kleisli[Id, Any, R]):
  Kleisli[Id, Any, E[O]] =
    for {
      env <- appLayer
    } yield program.apply(env)
}
