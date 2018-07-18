package mx.sevensys.validator
import org.joda.time._

package object joda {
   case class before[T <: ReadableInstant](right:T) extends Rule {
    override def info=Info("before",Seq(s"$right"))
  }
  case class after[T <: ReadableInstant](right:T) extends Rule {
    override def info=Info("after",Seq(s"$right"))
  }
  case class within[T <: ReadableInstant](of:T,duration:ReadableDuration) extends Rule {
    override def info=Info("within",Seq(s"$of",s"$duration"))
  }
}
