package model

import org.scalatest.{FlatSpec, Matchers}

class KingTest extends FlatSpec with Matchers {

  behavior of "King piece"

  "attacks" should "return true for all other pieces which are in King's neighborhood" in {
    val king = King(3,3)
    val othersUnderAttack = List(
      King(2,2), King(2,3), King(2,4),
      King(3,2),            King(3,4),
      King(4,2), King(4,3), King(4,4)
    )

    othersUnderAttack.map(king attacks) should contain only true
  }

  it should "return false for pieces that are positioned on 'safe' places" in {
    val king = King(3,3)
    val safePieces = List(
      King(1,1), King(1,2), King(1,3), King(1,4), King(1,5),
      King(2,1),                                  King(2,5),
      King(3,1),                                  King(3,5),
      King(4,1),                                  King(4,5),
      King(5,1), King(5,2), King(5,3), King(5,4), King(5,5)
    )

    safePieces.map(king attacks) should contain only false
  }

  it should "return true when other piece is on the same position" in {
    King(3,3) attacks King(3,3) shouldBe true
  }
}
