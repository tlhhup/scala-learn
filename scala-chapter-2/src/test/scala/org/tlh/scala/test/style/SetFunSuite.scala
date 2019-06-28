package org.tlh.scala.test.style

import java.util.NoSuchElementException

import org.scalatest.FunSuite

/**
  * Created by hu ping on 6/27/2019
  * <p>
  */
class SetFunSuite extends FunSuite {

  test("An empty Set should have size 0") {
    assert(Set.empty.size == 0)
  }

  test("Invoking head on an empty Set should produce NoSuchElementException") {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }

}
