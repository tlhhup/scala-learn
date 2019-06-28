package org.tlh.scala.test.style

import org.scalatest.{Matchers, PropSpec}
import org.scalatest.prop.TableDrivenPropertyChecks

import scala.collection.{BitSet, mutable}

/**
  * 属性测试
  * PropSpec is perfect for teams that want to write tests exclusively in terms of property checks; also a good choice for writing the occasional test matrix when a different style trait is chosen as the main unit testing style.
  * <br>
  * Created by hu ping on 6/27/2019
  * <p>
  */
class SetPropSpec extends PropSpec with TableDrivenPropertyChecks with Matchers {

  val examples = Table(
    "set",
    BitSet.empty,
    mutable.HashSet.empty[Int],
    mutable.TreeSet.empty[Int]
  )

  property("an empty Set should have size 0") {
    forAll(examples) { set =>
      set.size should be
      0
    }
  }

  property("invoking head on an empty Set should produce NoSuchElementException") {
    forAll(examples) {
      set => {
        a[NoSuchElementException] should be thrownBy {
          set.head
        }
      }
    }
  }

}
