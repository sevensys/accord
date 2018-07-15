package mx.sevensys.validator

import java.time.Duration
import java.time.temporal.Temporal

package object java8 {
  case class before[T <: Temporal](right:T) extends Rule
  case class after[T <: Temporal](right:T) extends Rule
  case class within[T <: Temporal](of:T,duration:Duration) extends Rule
}
