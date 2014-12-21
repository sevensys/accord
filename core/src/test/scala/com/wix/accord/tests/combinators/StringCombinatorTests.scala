/*
  Copyright 2013-2014 Wix.com

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package com.wix.accord.tests.combinators

import com.wix.accord.combinators._

class StringCombinatorTests extends CombinatorTestSpec {

  "StartsWith combinator" should {
    "successfully validate a string that starts with the specified prefix" in {
      val left = "ham and eggs"
      val validator = new StartsWith( "ham" )
      validator( left ) should be( aSuccess )
    }

    "render a correct rule violation" in {
      val left = "eggs and ham"
      val validator = new StartsWith( "ham" )
      validator( left ) should failWith( Constraints.StartsWith( "ham" ) )
    }
  }

  "EndsWith combinator" should {
    "successfully validate a string that ends with the specified prefix" in {
      val left = "ham and eggs"
      val validator = new EndsWith( "eggs" )
      validator( left ) should be( aSuccess )
    }

    "render a correct rule violation" in {
      val left = "eggs and ham"
      val validator = new EndsWith( "eggs" )
      validator( left ) should failWith( Constraints.EndsWith( "eggs" ) )
    }
  }

  "MatchesRegex combinator" should {
    "successfully validate a string that partially matches specified pattern if partial match is allowed" in {
      val left = "ham and eggs"
      val validator = new MatchesRegex( """\s+eggs""".r.pattern, partialMatchAllowed = true )
      validator( left ) should be( aSuccess )
    }

    "fail to validate a string that partially matches specified pattern if partial match is not allowed" in {
      val left = "ham and eggs"
      val validator = new MatchesRegex( """\s+eggs""".r.pattern, partialMatchAllowed = false )
      validator( left ) should be( aFailure )
    }

    "successfully validate a string that fully matches specified pattern even if partial match is not allowed" in {
      val left = "ham and eggs"
      val validator = new MatchesRegex( """.*\s+eggs""".r.pattern, partialMatchAllowed = false )
      validator( left ) should be( aSuccess )
    }

    "render a correct rule violation on partial match failure" in {
      val left = "eggs and ham"
      val r = """\s+eggs""".r
      val validator = new MatchesRegex( r.pattern, partialMatchAllowed = true )
      validator( left ) should failWith( Constraints.MatchRegex( r.pattern ) )
    }

    "render a correct rule violation on full match failure" in {
      val left = "eggs and ham"
      val r = """\s+eggs""".r
      val validator = new MatchesRegex( r.pattern, partialMatchAllowed = false )
      validator( left ) should failWith( Constraints.FullyMatchRegex( r.pattern ) )
    }
  }
}
