package org.tlh.scala.test.style

import org.scalatest.{FeatureSpec, GivenWhenThen}

/**
  * 用于验收测试
  * Trait FeatureSpec is primarily intended for acceptance testing, including facilitating the process of programmers working alongside non-programmers to define the acceptance requirements.
  * <br>
  * Created by hu ping on 6/27/2019
  * <p>
  */
class TVFeatureSpec extends FeatureSpec with GivenWhenThen {

  info("As a TV set owner")
  info("I want to be able to turn the TV on and off")
  info("So I can watch TV when I want")
  info("And save energy when I'm not watching TV")

  // 测试的feature
  feature("TV power button") {
    scenario("User presses power button when TV is off") {//场景

      Given("a TV set that is switched off")//预设条件
      val tv = new TVSet
      assert(!tv.isOn)

      When("the power button is pressed")
      tv.pressPowerButton()

      Then("the TV should switch on")
      assert(tv.isOn)
    }

    scenario("User presses power button when TV is on") {

      Given("a TV set that is switched on")
      val tv = new TVSet
      tv.pressPowerButton()
      assert(tv.isOn)

      When("the power button is pressed")
      tv.pressPowerButton()

      Then("the TV should switch off")
      assert(!tv.isOn)
    }
  }

}

class TVSet {
  private var on: Boolean = false

  def isOn: Boolean = on

  def pressPowerButton(): Unit = {
    on = !on
  }
}
