package algorithm

import model.Piece.Candidate
import model.{Chessboard, Dim}

class Solver {
  def findSolutions1(piecesToPlace: List[Candidate], dim: Dim): Seq[Chessboard] = {
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

  def findSolutions(piecesToPlace: List[Candidate], dim: Dim): Seq[Chessboard] = {
    def toNextLevelResults(candidate: Candidate, chessboard: Chessboard): Seq[Chessboard] =
      chessboard.unaffectedPositions
        .map(candidate(_))
        .filter(chessboard canPlace _)
        .map(chessboard place _)

    def loop(intermediateResults: List[Chessboard], candidates: List[Candidate]): Seq[Chessboard] = candidates match {
      case first :: others => {
        val nextLevelResults = intermediateResults.flatMap(toNextLevelResults(first, _))
        loop(nextLevelResults, others)
      }
      case _ => intermediateResults.distinct
    }

    loop(List(Chessboard(Set(), dim)), piecesToPlace)
  }
}
