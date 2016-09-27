package model

import org.scalatest.{FlatSpec, Matchers}

class KnightTest extends FlatSpec with Matchers {

  behavior of "Knight piece"

  "attacks" should "return true for all pieces placed on Knight's attacked positions" in {
    val knight = Knight(4,4)
    val attackedPieces = List(
                  Knight(2,3),      Knight(2,5),
      Knight(3,2),                              Knight(3,6),
                           knight,
      Knight(5,2),                              Knight(5,6),
                  Knight(6,3),      Knight(6,5)
    )

    attackedPieces.map(knight attacks) should contain only true
  }

  it should "return false for all pieces placed on safe positions" in {
    val knight = Knight(4,4)
    val attackedPieces = List(
      Knight(2,2),              Knight(2,4),              Knight(2,6),
                   Knight(3,3), Knight(3,4), Knight(3,5),
      Knight(4,2), Knight(4,3),              Knight(4,5), Knight(4,6),
                   Knight(5,3), Knight(5,4), Knight(5,5),
      Knight(6,2),              Knight(6,4),              Knight(6,6)
    )

    attackedPieces.map(knight attacks) should contain only false
  }

  it should "return true when other piece is placed on the same position" in {
    Knight(2,2) attacks Knight(2,2) shouldBe true
  }

}
