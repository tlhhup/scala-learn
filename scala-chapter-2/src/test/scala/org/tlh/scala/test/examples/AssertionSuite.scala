package org.tlh.scala.test.examples

import org.scalatest.FunSuite

/**
  * <br>
  * Created by hu ping on 6/27/2019
  * <p>
  */
class AssertionSuite extends FunSuite {

  test("Assert") {
    val left = 2
    val right = 1
    assert(left == right)
  }

  test("Expected results") {
    assume(2 > 3)
    // 如果条件为真则执行，否则不执行
    val a = 5
    val b = 2
    assertResult(2) {
      a - b
    }
  }

  test("Forcing cancelations") {
    cancel()
  }

  ignore("Tag test") {
    assert(1 == 1)
  }

}
