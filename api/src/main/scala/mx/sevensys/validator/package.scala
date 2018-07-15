package mx.sevensys

import java.util.regex.Pattern

package object validator {
  trait Rule
  case class isTrue() extends Rule
  case class isFalse() extends Rule
  case class empty() extends Rule
  case class notEmpty() extends Rule
  case class distinct() extends Rule
  case class in[T](set: Set[ T ]) extends Rule
  case class fail() extends Rule
  case class isNull() extends Rule
  case class notNull() extends Rule
  case class equalsTo[T](to:T) extends Rule
  case class notEqualsTo[T](to:T) extends Rule
  case class anInstanceOf(clazz:String) extends Rule
  case class notAnInstanceOf(clazz:String) extends Rule
  case class greaterThan[T](value:T) extends Rule
  case class greaterThanOrEquals[T](value:T) extends Rule
  case class lesserThan[T](value:T) extends Rule
  case class lesserThanOrEquals[T](value:T) extends Rule
  case class equivalentTo[T](value:T) extends Rule
  case class inRangeInclusive[T](lower:T, upper:T) extends Rule
  case class inRangeExclusive[T](lower:T, upper:T) extends Rule
  case class startsWith(prefix:String) extends Rule
  case class endsWith(suffix:String) extends Rule
  case class notBlank() extends Rule
  case class blank() extends Rule
  case class matchesRegexp(pattern:Pattern, partial:Boolean) extends Rule

  trait ViolationInfo
  case class duplicate(duplicates:String) extends ViolationInfo

}
