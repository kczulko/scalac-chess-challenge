package model

import org.scalatest.{FlatSpec, Matchers}

class QueenTest extends FlatSpec with Matchers {

  behavior of "Queen piece"

  "attacks" should "return true for pieces situated on the same row" in {
    val queen = Queen(2,1)
    val piecesOnTheSameRow = List(
      Queen(2,3), Queen(2,4), Queen(2,6), Queen(2,7), Queen(2,9)
    )

    piecesOnTheSameRow.map(queen attacks) should contain only true
  }

  it should "return true for pieces situated on the same column" in {
    val queen = Queen(2,1)
    val piecesOnTheSameColumn = List(
      Queen(3,1),
      Queen(4,1),
      Queen(5,1),
      Queen(6,1),
      Queen(14,1)
    )

    piecesOnTheSameColumn.map(queen attacks) should contain only true
  }

  it should "return true for pieces situated on the same diagonal" in {
    val queen = Queen(2,1)
    val piecesOnTheSameDiagonal = List(
      Queen(3,2),
                 Queen(4,3),
                            Queen(5,4),
                                       Queen(6,5),
                                                   Queen(12,11)
    )

    piecesOnTheSameDiagonal.map(queen attacks) should contain only true
  }

  it should "return true for pieces situated on the same anti-diagonal" in {
    val queen = Queen(7,1)
    val piecesOnTheSameAntiDiagonal = List(
                                       Queen(3,5),
                            Queen(4,4),
                 Queen(5,3),
      Queen(6,2)
    )

    piecesOnTheSameAntiDiagonal.map(queen attacks) should contain only true
  }

  it should "return false when pieces are neither on the same diagonal, anti-diagonal, row nor column" in {
    val queen = Queen(3,2)
    val piecesOnTheSameAntiDiagonal = List(
      Queen(1,1),             Queen(1,3),
                                          Queen(2,4),

                                          Queen(4,4)
    )

    piecesOnTheSameAntiDiagonal.map(queen attacks) should contain only false
  }

  it should "return true when other piece is on the same position" in {
    Queen(4,4) attacks Queen(4,4) shouldBe true
  }

}
