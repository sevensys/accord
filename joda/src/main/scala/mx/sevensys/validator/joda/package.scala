package mx.sevensys.validator
import org.joda.time._

package object joda {
  case class before[T <: ReadableInstant](right:T) extends Rule
  case class after[T <: ReadableInstant](right:T) extends Rule
  case class within[T <: ReadableInstant](of:T,duration:ReadableDuration) extends Rule
}
