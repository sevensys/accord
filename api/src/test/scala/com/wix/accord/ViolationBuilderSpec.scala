package com.wix.accord

import org.scalatest.{FlatSpec, Matchers}
import mx.sevensys.validator._

import ViolationBuilder._
class ViolationBuilderSpec extends FlatSpec with Matchers{

  "A tuple with a value, a constraint and a rule" should "become a rule violation" in {
    val ruleViolation:RuleViolation="typo"->"invalid input"->isTrue()
    ruleViolation.value shouldEqual "typo"
    ruleViolation.constraint shouldEqual "invalid input"
    ruleViolation.rule shouldEqual Some(isTrue())
  }

  "A tuple with a value, a constraint and a rule and violation info" should "become a rule violation" in {
    val ruleViolation:RuleViolation="typo"->"invalid input"->isTrue()->duplicate("typo")
    ruleViolation.value shouldEqual "typo"
    ruleViolation.constraint shouldEqual "invalid input"
    ruleViolation.rule shouldEqual Some(isTrue())
    ruleViolation.info shouldEqual Some(duplicate("typo"))
  }

}
