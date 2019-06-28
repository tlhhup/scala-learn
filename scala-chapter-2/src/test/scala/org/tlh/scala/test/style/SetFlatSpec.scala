package org.tlh.scala.test.style

import org.scalatest.FlatSpec

/**
  * BDD：行为驱动开发<br>
  * the test names must be written in a specification style: "X should Y," "A must B,"
  * <br>
  * Created by hu ping on 6/27/2019
  * <p>
  */
class SetFlatSpec extends FlatSpec {

  "An empty Set" should "have size 0" in {
    assert(Set.empty.size == 0)
  }

  it should "produce NoSuchElementException when head is invoked" in {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }

}
