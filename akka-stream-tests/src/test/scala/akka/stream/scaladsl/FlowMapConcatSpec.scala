/**
 * Copyright (C) 2014 Typesafe Inc. <http://www.typesafe.com>
 */
package akka.stream.scaladsl

import akka.stream.MaterializerSettings
import akka.stream.testkit.AkkaSpec
import akka.stream.testkit.ScriptedTest

class FlowMapConcatSpec extends AkkaSpec with ScriptedTest {

  val settings = MaterializerSettings(system)
    .withInputBuffer(initialSize = 2, maxSize = 16)
    .withFanOutBuffer(initialSize = 1, maxSize = 16)

  "A MapConcat" must {

    "map and concat" in {
      val script = Script(
        Seq(0) -> Seq(),
        Seq(1) -> Seq(1),
        Seq(2) -> Seq(2, 2),
        Seq(3) -> Seq(3, 3, 3),
        Seq(2) -> Seq(2, 2),
        Seq(1) -> Seq(1))
      TestConfig.RandomTestRange foreach (_ ⇒ runScript(script, settings)(_.mapConcat(x ⇒ (1 to x) map (_ ⇒ x))))
    }

  }

}
