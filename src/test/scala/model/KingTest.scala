package model

import org.scalatest.{FlatSpec, Matchers}

class KingTest extends FlatSpec with Matchers {

  behavior of "King class"

  val othersUnderAttack = List(
    King(Position(2,2)), King(Position(2,3)), King(Position(2,4)),
    King(Position(3,2)),                      King(Position(3,4)),
    King(Position(4,2)), King(Position(4,3)), King(Position(4,4))
  )

  "attacks" should "return true for all other pieces which are in King's neighbourhood" in {
    val king = King(Position(3,3))
    othersUnderAttack.map(king attacks _) should contain only true
  }

  it should "return false for pieces that are positioned on 'safe' places" in {
    val king = King(Position(3,3))

    val safePieces = List(
      King(Position(1,1)), King(Position(1,2)), King(Position(1,3)), King(Position(1,4)), King(Position(1,5)),
      King(Position(2,1)),                                                                King(Position(2,5)),
      King(Position(3,1)),                                                                King(Position(3,5)),
      King(Position(4,1)),                                                                King(Position(4,5)),
      King(Position(5,1)), King(Position(5,2)), King(Position(5,3)), King(Position(5,4)), King(Position(5,5))
    )

    safePieces.map(king attacks _) should contain only false
  }

  it should "return true when other piece is on the same position" in {
    val position = Position(3,3)
    King(position) attacks King(position) shouldBe true
  }

/*  it should "return false for other pieces placed within the chessboard" in {
    val king = King(Position(3,3))
    val attackedPositions = othersUnderAttack.map(_.position)

    forAll { (row: Int, col: Int) =>
      whenever(row > 0 && col > 0) {
        val position = Position(row, col)
        if (attackedPositions contains position) {
          king attacks King(position) shouldBe true
        } else {
          king attacks King(position) shouldBe false
        }
      }
    }
  }*/
}
