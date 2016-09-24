package model

import model.Position.tupleToPosition
import org.scalatest.{FlatSpec, Matchers}

class DimTest extends FlatSpec with Matchers {

  behavior of "Dim class"

  "toPositionSeq" should "return all expected positions" in {
    Dim(3,3).toPositionSeq should contain theSameElementsInOrderAs Seq(
      (1,1), (1,2), (1,3),
      (2,1), (2,2), (2,3),
      (3,1), (3,2), (3,3)
    ).map(tupleToPosition)
  }
}
