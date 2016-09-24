package model

import org.scalatest.{FlatSpec, Matchers}

class ChessboardTest extends FlatSpec with Matchers {

  behavior of "Chessboard class"

  it should "allow to place piece on safe position for Dim{3,3} and {King, King, Rook} configuration" in {
    Chessboard(List(King(1,1), King(1,3))) canPlace Rook(3,2) shouldBe true
    Chessboard(List(King(1,1), King(3,1))) canPlace Rook(2,3) shouldBe true
    Chessboard(List(King(1,3), King(3,3))) canPlace Rook(2,1) shouldBe true
    Chessboard(List(King(3,1), King(3,3))) canPlace Rook(1,2) shouldBe true
  }

  it should "forbid to place a piece on attacked position Dim{3,3} and {King, King, Rook} configuration" in {
    Chessboard(List(King(1,1), King(1,3))) canPlace Rook(3,1) shouldBe false
    Chessboard(List(King(1,1), King(3,1))) canPlace Rook(2,2) shouldBe false
    Chessboard(List(King(1,3), King(3,3))) canPlace Rook(3,1) shouldBe false
    Chessboard(List(King(3,1), King(3,3))) canPlace Rook(3,2) shouldBe false
    Chessboard(List(King(3,1), King(3,3))) canPlace Rook(3,3) shouldBe false
  }
}
