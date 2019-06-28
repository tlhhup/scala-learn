package org.tlh.scala.test.style

import org.scalatest.FunSpec

/**
  * FunSpec's nesting and gentle guide to structuring text (with describe and it) provides an excellent general-purpose choice for writing specification-style tests.
  * <br>
  * Created by hu ping on 6/27/2019
  * <p>
  */
class SetFunSpec extends FunSpec {

  describe("A Set") {
    describe("when empty") {
      it("should have size 0") {
        assert(Set.empty.size == 0)
      }
      it("should produce NoSuchElementException when head is invoked") {
        assertThrows[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }

}
