package algorithm

import model.Piece.Candidate
import model.{Chessboard, Dim}

import scala.annotation.tailrec

class Solver {
  def findSolutions(piecesToPlace: List[Candidate], dim: Dim): Seq[Chessboard] = {
    def toNextLevelResults(candidate: Candidate, chessboard: Chessboard): Seq[Chessboard] =
      chessboard.unaffectedPositions
        .map(candidate(_))
        .filterNot(_.attacksAnyOf(chessboard.pieces))
        .map(chessboard place _)

    @tailrec
    def loop(intermediateResults: List[Chessboard], candidates: List[Candidate]): Seq[Chessboard] = candidates match {
      case first :: others => {
        val nextLevelResults = intermediateResults.flatMap(toNextLevelResults(first, _)).distinct
        loop(nextLevelResults, others)
      }
      case _ => intermediateResults
    }

    loop(List(Chessboard(Set(), dim)), piecesToPlace)
  }
}
