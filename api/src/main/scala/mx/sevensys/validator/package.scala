package mx.sevensys

import java.util.regex.Pattern

package object validator {
  case class Info(name:String, parameters:Seq[String]=Seq.empty)
  trait Rule{
    def info:Info
  }
  case class isTrue() extends Rule{
    override def info=Info("isTrue")
  }
  case class isFalse() extends Rule {
    override def info=Info("isFalse")
  }
  case class empty() extends Rule{
    override def info=Info("empty")
  }
  case class notEmpty() extends Rule{
    override def info=Info("notEmpty")
  }
  case class distinct() extends Rule{
    override def info=Info("distinct")
  }
  case class in[T](set: Set[ T ]) extends Rule{
    override def info=Info("in",set.map(t=>s"$t").toSeq)
  }
  case class fail() extends Rule{
    override def info=Info("fail")
  }
  case class isNull() extends Rule{
    override def info=Info("isNull")
  }
  case class notNull() extends Rule{
    override def info=Info("notNull")
  }
  case class equalsTo[T](to:T) extends Rule{
    override def info=Info("equalsTo",Seq(s"$to"))
  }
  case class notEqualsTo[T](to:T) extends Rule{
    override def info=Info("notEqualsTo",Seq(s"$to"))
  }
  case class anInstanceOf(clazz:String) extends Rule{
    override def info=Info("anInstanceOf",Seq(s"$clazz"))
  }
  case class notAnInstanceOf(clazz:String) extends Rule{
    override def info=Info("notAnInstanceOf",Seq(s"$clazz"))
  }
  case class greaterThan[T](value:T) extends Rule{
    override def info=Info("greaterThan",Seq(s"$value"))
  }
  case class greaterThanOrEquals[T](value:T) extends Rule{
    override def info=Info("greaterThanOrEquals",Seq(s"$value"))
  }
  case class lesserThan[T](value:T) extends Rule{
    override def info=Info("lesserThan",Seq(s"$value"))
  }
  case class lesserThanOrEquals[T](value:T) extends Rule{
    override def info=Info("lesserThanOrEquals",Seq(s"$value"))
  }
  case class equivalentTo[T](value:T) extends Rule{
    override def info=Info("equivalentTo",Seq(s"$value"))
  }
  case class inRangeInclusive[T](lower:T, upper:T) extends Rule{
    override def info=Info("inRangeInclusive",Seq(s"$lower",s"$upper"))
  }
  case class inRangeExclusive[T](lower:T, upper:T) extends Rule{
    override def info=Info("inRangeExclusive",Seq(s"$lower",s"$upper"))
  }
  case class startsWith(prefix:String) extends Rule{
    override def info=Info("startsWith",Seq(s"$prefix"))
  }
  case class endsWith(suffix:String) extends Rule{
    override def info=Info("endsWith",Seq(s"$suffix"))
  }
  case class notBlank() extends Rule{
    override def info=Info("notBlank")
  }
  case class blank() extends Rule{
    override def info=Info("blank")
  }
  case class matchesRegexp(pattern:Pattern, partial:Boolean) extends Rule{
    override def info=Info("regexp",Seq(s"$pattern",s"$partial"))
  }

  trait ViolationInfo{
    def info:Info
  }
  case class duplicate(duplicates:String) extends ViolationInfo{
    override def info=Info("duplicates",Seq(s"$duplicates"))
  }

}
