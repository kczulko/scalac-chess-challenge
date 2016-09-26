package algorithm

import model.Piece.Candidate
import model.{Chessboard, Dim}

import scala.annotation.tailrec
import scala.collection.parallel.ParSet

class Solver {
  def findSolutions(piecesToPlace: List[Candidate], dim: Dim): Seq[Chessboard] = {
    def toNextLevelResults(candidate: Candidate, chessboard: Chessboard): Seq[Chessboard] =
      chessboard.unaffectedPositions
        .map(candidate(_))
        .collect({
          case thisPiece if thisPiece attacksAnyOf chessboard.pieces equals false => chessboard place thisPiece
        })

    @tailrec
    def loop(intermediateResults: ParSet[Chessboard], candidates: List[Candidate]): ParSet[Chessboard] = candidates match {
      case first :: others => {
        println(s"Starting iteration. There are still ${others.length} iterations left...")
        val nextLevelResults = intermediateResults.flatMap(toNextLevelResults(first, _))
        println(s"Iteration finished. Processed ${intermediateResults.size} chessboards.")
        loop(nextLevelResults, others)
      }
      case _ => intermediateResults
    }

    loop(ParSet(Chessboard(Set(), dim)), piecesToPlace).seq.toSeq
  }
}
