package model

import org.scalatest.{FlatSpec, Matchers}

class RookTest extends FlatSpec with Matchers {

  behavior of "Rook piece"

  "attacks" should "return true when other pieces are in the same row" in {
    val rook = Rook(1,1)
    val piecesUnderAttack = List(
      Rook(1,4), Rook(1,5), Rook(1,7), Rook(1,17)
    )

    piecesUnderAttack.map(rook attacks) should contain only true
  }

  it should "return true when other pieces are in the same column" in {
    val rook = Rook(1,1)
    val piecesUnderAttack = List(
      Rook(3,1), Rook(2,1), Rook(8,1), Rook(17,1)
    )

    piecesUnderAttack.map(rook attacks) should contain only true
  }

  it should "return false for pieces places in different columns and rows" in {
    val rook = Rook(1,1)
    val piecesUnderAttack = List(
      Rook(3,3), Rook(2,2), Rook(6,7), Rook(9,5)
    )

    piecesUnderAttack.map(rook attacks) should contain only false
  }
}
