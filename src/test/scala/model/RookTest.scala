package model

import org.scalatest.{FlatSpec, Matchers}

class RookTest extends FlatSpec with Matchers {

  behavior of "Rook class"

  "attacks" should "return true when other pieces are in the same row" in {
    val rook = Rook(Position(1,1))
    val piecesUnderAttack = List(
      Rook(Position(1,4)), Rook(Position(1,5)), Rook(Position(1,7)), Rook(Position(1,17))
    )

    piecesUnderAttack.map(rook attacks _) should contain only true
  }

  it should "return true when other pieces are in the same column" in {
    val rook = Rook(Position(1,1))
    val piecesUnderAttack = List(
      Rook(Position(3,1)), Rook(Position(2,1)), Rook(Position(8,1)), Rook(Position(17,1))
    )

    piecesUnderAttack.map(rook attacks _) should contain only true
  }

  it should "return false for pieces places in different columns and rows" in {
    val rook = Rook(Position(1,1))
    val piecesUnderAttack = List(
      Rook(Position(3,3)), Rook(Position(2,2)), Rook(Position(6,7)), Rook(Position(9,5))
    )

    piecesUnderAttack.map(rook attacks _) should contain only false
  }
}
