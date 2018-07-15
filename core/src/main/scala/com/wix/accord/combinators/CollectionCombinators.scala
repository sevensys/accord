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

package com.wix.accord.combinators

import com.wix.accord.{BaseValidator, NullSafeValidator, Validator}
import com.wix.accord.ViolationBuilder._
import mx.sevensys.validator._
import scala.collection.GenTraversableOnce


/** Combinators that operate on collections and collection-like structures. */
trait CollectionCombinators {
  import scala.language.implicitConversions

  /** A structural type representing any object that can be empty. */
  type HasEmpty = { def isEmpty: Boolean }

  /**
    * An implicit conversion to enable any collection-like object (e.g. strings, options) to be handled by the
    * [[com.wix.accord.combinators.CollectionCombinators.Empty]]
    * and [[com.wix.accord.combinators.CollectionCombinators.NotEmpty]] combinators.
    *
    * [[java.lang.String]] does not directly implement `isEmpty` (in practice it is implemented in
    * [[scala.collection.IndexedSeqOptimized]], via an implicit conversion and an inheritance stack), and this is
    * a case where the Scala compiler does not always infer structural types correctly. By requiring
    * a view bound from `T` to [[scala.collection.GenTraversableOnce]] we can force any collection-like structure
    * to conform to the structural type [[com.wix.accord.combinators.HasEmpty]], and by requiring
    * a view bound from `T` to [[com.wix.accord.combinators.HasEmpty]] at the call site (e.g.
    * [[com.wix.accord.dsl.empty]]) we additionally support any class that directly conforms to the
    * structural type as well.
    *
    * @param gto An object that is, or is implicitly convertible to, [[scala.collection.GenTraversableOnce]].
    * @tparam T The type that conforms, directly or implicitly, to [[com.wix.accord.combinators.HasEmpty]].
    * @return The specified object, strictly-typed as [[com.wix.accord.combinators.HasEmpty]].
    */
  implicit def genericTraversableOnce2HasEmpty[ T ]( gto: T )( implicit ev: T => GenTraversableOnce[_] ): HasEmpty = gto

  /** A validator that operates on objects that can be empty, and succeeds only if the provided instance is
    * empty.
    * @tparam T A type that implements `isEmpty: Boolean` (see [[com.wix.accord.combinators.HasEmpty]]).
    * @see [[com.wix.accord.combinators.NotEmpty]]
    */
  class Empty[ T <: AnyRef ]( implicit ev: T => HasEmpty )
    extends NullSafeValidator[ T ]( _.isEmpty, _ -> "must be empty"->empty() )

  /** A validator that operates on objects that can be empty, and succeeds only if the provided instance is ''not''
    * empty.
    * @tparam T A type that implements `isEmpty: Boolean` (see [[com.wix.accord.combinators.HasEmpty]]).
    * @see [[com.wix.accord.combinators.Empty]]
    */
  class NotEmpty[ T <: AnyRef ]( implicit ev: T => HasEmpty )
    extends NullSafeValidator[ T ]( !_.isEmpty, _ -> "must not be empty"->notEmpty() )

  /** A validator that succeeds only if the provided collection has no duplicate elements. */
  object Distinct extends Validator[ Traversable[_] ] {
    def apply( coll: Traversable[_] ) =
      if ( coll == null )
        Validator.nullFailure
      else {
        val duplicates = coll.groupBy( identity ).filter( _._2.size > 1 ).keys
        result(
          duplicates.isEmpty,
          coll -> duplicates.mkString( "is not a distinct set; duplicates: [", ", ", "]")->distinct()->duplicate(duplicates.mkString(","))
        )
      }
  }
  /** A validator that succeeds only if the object exists in the target collection. */
  case class In[ T ]( set: Set[ T ], prefix: String )
    extends BaseValidator[ T ]( set.contains, v => v -> set.mkString( s"$prefix $v, expected one of: [", ", ", "]" )->in(set) )
}
