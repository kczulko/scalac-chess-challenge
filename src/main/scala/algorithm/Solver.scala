package algorithm

import model.Piece.Candidate
import model.{Chessboard, Dim}

class Solver {
  def findSolutions(piecesToPlace: List[Candidate], dim: Dim): Seq[Chessboard] = {
    def loop(solutions: List[Chessboard], candidates: List[Candidate], currentChessboard: Chessboard): Seq[Chessboard] =
      candidates match {
        case first :: others => {
          for {
            position <- currentChessboard.unaffectedPositions
            newChessboard = currentChessboard.place(first(position)) if currentChessboard.canPlace(first(position))
            solution <- loop(solutions, others, newChessboard)
          } yield solution
        }
        case _ => currentChessboard :: solutions
      }

    loop(List(), piecesToPlace, Chessboard(Set(), dim)).distinct
  }
}
