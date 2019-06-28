package org.tlh.scala.test.style

import org.scalatest.WordSpec

/**
  * For teams coming from specs or specs2, WordSpec will feel familiar, and is often the most natural way to port specsN tests to ScalaTest. WordSpec is very prescriptive in how text must be written, so a good fit for teams who want a high degree of discipline enforced upon their specification text.
  * <br>
  * Created by hu ping on 6/27/2019
  * <p>
  */
class SetWordSpec extends WordSpec {

  "A Set" when {//类似 match  case 的语法
    "empty" should {
      "have size 0" in {
        assert(Set.empty.size == 0)
      }
      "produce NoSuchElementException when head is invoked" in {
        assertThrows[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }

}
