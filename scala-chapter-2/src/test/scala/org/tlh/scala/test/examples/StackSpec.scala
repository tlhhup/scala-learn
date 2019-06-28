package org.tlh.scala.test.examples

import java.util
import java.util.EmptyStackException

import org.scalatest.FlatSpec

/**
  * <br>
  * Created by hu ping on 6/27/2019
  * <p>
  */
class StackSpec extends FlatSpec {

  // subject + verb(should, must, or can) + sentence
  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new util.Stack[Int]
    stack.push(1)
    stack.push(2)
    assert(stack.pop() === 2)
    assert(stack.pop() === 1)
  }

  // 使用it 引用前一个subject
  it should "throw EmptyStackException if an empty stack is popped" in {
    val emptyStack = new util.Stack[String]
    assertThrows[EmptyStackException] {
      emptyStack.pop()
    }
  }

}
