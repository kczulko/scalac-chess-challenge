package algorithm

import com.typesafe.scalalogging.Logger
import model.Piece.Candidate
import model.{Chessboard, Dim}

import scala.annotation.tailrec
import scala.collection.parallel.ParSet

class Solver {

  private val logger = Logger[Solver]

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
        logger.info(s"Placing piece... There are still ${others.length} pieces left to place...")
        val nextLevelResults = intermediateResults.flatMap(toNextLevelResults(first, _))
        loop(nextLevelResults, others)
      }
      case _ => intermediateResults
    }

    loop(ParSet(Chessboard(Set(), dim)), piecesToPlace).seq.toSeq
  }
}
