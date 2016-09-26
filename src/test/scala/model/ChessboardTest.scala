package model

import model.Position.tupleToPosition
import org.scalatest.{FlatSpec, Matchers}

class ChessboardTest extends FlatSpec with Matchers {

  behavior of "Chessboard class"

  "unaffectedPositions" should "return all positions which are not attacked by any of chessboard's pieces" in {
    val emptyChessboard = Chessboard(Set(), Dim(4,4))
    val chessboardWithQueen = emptyChessboard place Queen(1,1)
    val chessboardWithQueenAndKing = chessboardWithQueen place King(3,2)

    emptyChessboard.unaffectedPositions.length shouldBe 16
    chessboardWithQueen.unaffectedPositions shouldNot contain theSameElementsAs Seq(
      (1,1), (1,2), (1,3), (1,4), (2,1),
      (3,1), (4,1), (2,2), (3,3), (4,4)
    ).map(tupleToPosition)
    chessboardWithQueenAndKing.unaffectedPositions should contain theSameElementsAs Seq((2,4), (3,4))
      .map(tupleToPosition)
  }

  "toString" should "print chessboard situation" in {
    Chessboard(Set(King(1,1), Queen(3,3)), Dim(4,3)).toString shouldEqual "K--\n---\n--Q\n---"
    Chessboard(Set(King(1,1), Queen(3,3)), Dim(4,4)).toString shouldEqual "K---\n----\n--Q-\n----"
    Chessboard(Set(King(1,1), Queen(3,3)), Dim(4,5)).toString shouldEqual "K----\n-----\n--Q--\n-----"
  }

  "equals" should "return true when two chessboards contain the same pieces in different order" in {
    val dim = Dim(4,3)
    Chessboard(Set(King(1,1), Queen(3,3)), dim) equals {
      Chessboard(Set(Queen(3,3), King(1,1)), dim)
    } shouldBe true
  }

  "unaffectedPositions" should "return list of positions which are not under attack by any piece from chessboard" in {
    Chessboard(Set(King(1,1), Queen(3,2)), Dim(3,3)).unaffectedPositions should contain only Position(1,3)
  }
}
