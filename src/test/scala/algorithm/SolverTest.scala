package algorithm

import model.Piece.Candidate
import model._
import org.scalatest.{FlatSpec, Matchers}

class SolverTest extends FlatSpec with Matchers {

  behavior of "SolverTest"

  val solver = new Solver

  private def queensSet(n: Int): List[Candidate] = List.fill(n)(Queen)
  private def squareDim(size: Int): Dim = Dim(size, size)

  "findSolutions" should "return expected solutions for 3x3 chessboard and {Rook, King, King} pieces set" in {
    val dim: Dim = Dim(3,3)
    solver.findSolutions(List(King, King, Rook), dim).map(_ pieces) should contain theSameElementsAs Seq(
      Set(King(1,1), King(1,3), Rook(3,2)),
      Set(King(1,1), King(3,1), Rook(2,3)),
      Set(King(1,3), King(3,3), Rook(2,1)),
      Set(King(3,3), King(3,1), Rook(1,2))
    )
  }

  it should "return expected solutions for 4x4 chessboard and {Rook, Rook, Knight, Knight, Knight, Knight} pieces set" in {
    val dim: Dim = Dim(4,4)
    val solutions = solver.findSolutions(List(Knight, Knight, Knight, Knight, Rook, Rook), dim)
    solutions.map(_ pieces) should contain allOf (
      Set(Knight(1,2), Knight(1,4), Knight(3,2), Knight(3,4), Rook(2,3), Rook(4,1)),
      Set(Knight(1,2), Knight(1,4), Knight(3,2), Knight(3,4), Rook(2,1), Rook(4,3)),
      Set(Knight(2,2), Knight(2,4), Knight(4,2), Knight(4,4), Rook(1,1), Rook(3,3)),
      Set(Knight(2,2), Knight(2,4), Knight(4,2), Knight(4,4), Rook(1,3), Rook(3,1)),
      Set(Knight(2,1), Knight(2,3), Knight(4,1), Knight(4,3), Rook(1,2), Rook(3,4)),
      Set(Knight(2,1), Knight(2,3), Knight(4,1), Knight(4,3), Rook(1,4), Rook(3,2)),
      Set(Knight(1,1), Knight(1,3), Knight(3,1), Knight(3,3), Rook(2,4), Rook(4,2)),
      Set(Knight(1,1), Knight(1,3), Knight(3,1), Knight(3,3), Rook(2,2), Rook(4,4))
    )
  }

  it should "return expected results for n-queens problem" in {
    solver.findSolutions(queensSet(4), squareDim(4)).length shouldEqual 2
    solver.findSolutions(queensSet(5), squareDim(5)).length shouldEqual 10
    solver.findSolutions(queensSet(6), squareDim(6)).length shouldEqual 4
    solver.findSolutions(queensSet(7), squareDim(7)).length shouldEqual 40
    solver.findSolutions(queensSet(8), squareDim(8)).length shouldEqual 92
  }

  it should "return empty Seq when there no possibility to find any solution" in {
    val dim: Dim = Dim(3,3)
    solver.findSolutions(List(Queen, Queen, Queen, Queen), dim) shouldBe empty
  }

}
