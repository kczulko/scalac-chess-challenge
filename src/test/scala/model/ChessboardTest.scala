package model

import org.scalatest.{FlatSpec, Matchers}

class ChessboardTest extends FlatSpec with Matchers {

  behavior of "Chessboard class"

  "canPlace" should "forbid to place a piece which is out of bound" in {
    val dim = Dim(3,3)
    Chessboard(Set(), dim) canPlace Rook(4,4) shouldBe false
  }

  it should "allow to place piece on safe position for Dim{3,3} and {King, King, Rook} configuration" in {
    val dim = Dim(3,3)
    Chessboard(Set(King(1,1), King(1,3)), dim) canPlace Rook(3,2) shouldBe true
    Chessboard(Set(King(1,1), King(3,1)), dim) canPlace Rook(2,3) shouldBe true
    Chessboard(Set(King(1,3), King(3,3)), dim) canPlace Rook(2,1) shouldBe true
    Chessboard(Set(King(3,1), King(3,3)), dim) canPlace Rook(1,2) shouldBe true
  }

  it should "forbid to place a piece on attacked position Dim{3,3} and {King, King, Rook} configuration" in {
    val dim = Dim(3,3)
    Chessboard(Set(King(1,1), King(1,3)), dim) canPlace Rook(3,1) shouldBe false
    Chessboard(Set(King(1,1), King(3,1)), dim) canPlace Rook(2,2) shouldBe false
    Chessboard(Set(King(1,3), King(3,3)), dim) canPlace Rook(3,1) shouldBe false
    Chessboard(Set(King(3,1), King(3,3)), dim) canPlace Rook(3,2) shouldBe false
    Chessboard(Set(King(3,1), King(3,3)), dim) canPlace Rook(3,3) shouldBe false
  }

  "toString" should "print chessboard situation" in {
    Chessboard(Set(King(1,1), Queen(3,3)), Dim(4,3)).toString shouldEqual "K--\n---\n--Q\n---"
    Chessboard(Set(King(1,1), Queen(3,3)), Dim(4,4)).toString shouldEqual "K---\n----\n--Q-\n----"
    Chessboard(Set(King(1,1), Queen(3,3)), Dim(4,5)).toString shouldEqual "K----\n-----\n--Q--\n-----"
  }

  "equals" should "return true when two chessboards contain the same pieces in different order" in {
    val dim: Dim = Dim(4, 3)
    Chessboard(Set(King(1,1), Queen(3,3)), dim) equals {
      Chessboard(Set(Queen(3,3), King(1,1)), dim)
    } shouldBe true
  }
}
