package org.tlh.scala.test.style

import org.scalatest.FreeSpec

/**
  * Because it gives absolute freedom (and no guidance) on how specification text should be written, FreeSpec is a good choice for teams experienced with BDD and able to agree on how to structure the specification text.
  * <br>
  * Created by hu ping on 6/27/2019
  * <p>
  */
class SetFreeSpec extends FreeSpec {

  "A Set" - {
    "when empty" - {
      "should have size 0" in {
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
