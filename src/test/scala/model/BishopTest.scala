package model

import org.scalatest.{FlatSpec, Matchers}

class BishopTest extends FlatSpec with Matchers {

  behavior of "Bishop piece"

  "attacks" should "return true when Bishop and other piece are places on the same " in {
    Bishop(4,4) attacks Bishop(4,4) shouldBe true
  }

  it should "return true for all pieces placed in the same diagonal" in {
    val bishop = Bishop(4,3)
    val piecesOnTheSameDiagonal = List(
      Bishop(2,1),
                  Bishop(3,2),
                              bishop,
                                     Bishop(5,4)
    )

    piecesOnTheSameDiagonal.map(bishop attacks) should contain only true
  }

  it should "return true for all pieces placed in the same anti-diagonal" in {
    val bishop = Bishop(4,2)
    val piecesOnTheSameAntiDiagonal = List(
                                          Bishop(2,4),
                              Bishop(3,3),

      Bishop(5,1)
    )

    piecesOnTheSameAntiDiagonal.map(bishop attacks) should contain only true
  }

  it should "return false for all pieces placed in safe positions" in {
    val bishop = Bishop(3,2)
    val piecesOnSafePositions = List(
                  Bishop(2,2),            Bishop(2,4),
      Bishop(3,1),            Bishop(3,3),
                  Bishop(4,2),
      Bishop(5,1)
    )

    piecesOnSafePositions.map(bishop attacks) should contain only false
  }
}
