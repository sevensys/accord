/*
  Copyright 2013-2017 Wix.com

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

package com.wix.accord.joda

import com.wix.accord.NullSafeValidator
import com.wix.accord.ViolationBuilder._
import org.joda.time._
import mx.sevensys.validator.joda._

/** Combinators that operate specifically on [[org.joda.time.Instant instants]] (and subclasses thereof). */
trait ReadableInstantCombinators {

  /** A validator that succeeds only for values that come strictly before the specified bound.
    *
    * @param bound The bound against which values are validated.
    */
  class Before[ T <: ReadableInstant ]( bound: T )
    extends NullSafeValidator[ T ]( _.compareTo( bound ) < 0, _ -> s"must be before $bound"->mx.sevensys.validator.joda.before(bound) )

  /** A validator that succeeds only for values that come strictly after before the specified bound.
    *
    * @param bound The bound against which values are validated.
    */
  class After[ T <: ReadableInstant ]( bound: T )
    extends NullSafeValidator[ T ]( _.compareTo( bound ) > 0, _ -> s"must be after $bound"->mx.sevensys.validator.joda.after(bound) )

  /** A validator that succeeds only for values that are within (i.e. before or after) a period of the specified
    * temporal (for example, "within a month of this person's birth date").
    *
    * This is essentially equivalent to `(value >= of - duration) && (value <= of + duration)`.
    *
    * @param of The desired temporal.
    * @param duration The allowed tolerance.
    * @param friendlyDuration A textual representation of the specified duration ([[org.joda.time.Duration durations]]
    *                         are rendered to ISO-8601 representations by default, which is likely not the desirable
    *                         outcome).
    */
  class Within[ T <: ReadableInstant ]( of: T, duration: ReadableDuration, friendlyDuration: => String )
    extends NullSafeValidator[ T ](
      t => of.toInstant.minus( duration ).compareTo( t ) <= 0 && of.toInstant.plus( duration ).compareTo( t ) >= 0,
      _ -> s"must be within $friendlyDuration of $of"->within(of,duration) )
}
