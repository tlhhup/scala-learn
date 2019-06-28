package org.tlh.scala.test.base

import org.scalatest._

/**
  * not trait, for speedier compiles
  * <br>
  * Created by hu ping on 6/27/2019
  * <p>
  */
abstract class UnitSpec extends FlatSpec with Matchers with OptionValues with Inside with Inspectors{

}
