package mx.sevensys.validator

import java.time.Duration
import java.time.temporal.Temporal

package object java8 {
  case class before[T <: Temporal](right:T) extends Rule{
    override def info=Info("before",Seq(s"$right"))
  }
  case class after[T <: Temporal](right:T) extends Rule{
    override def info=Info("after",Seq(s"$right"))
  }
  case class within[T <: Temporal](of:T,duration:Duration) extends Rule{
    override def info=Info("within",Seq(s"$of",s"$duration"))
  }
}
