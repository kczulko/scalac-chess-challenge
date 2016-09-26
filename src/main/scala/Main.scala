
import java.lang.System.nanoTime

import algorithm.Solver
import com.typesafe.scalalogging.Logger
import model.Piece.Candidate
import model._
import scopt.OptionParser

object Main {
  private[this] case class Args(rows: Int = 0,
                  cols: Int = 0,
                  queens: Int = 0,
                  rooks: Int = 0,
                  knights: Int = 0,
                  kings: Int = 0,
                  bishops: Int = 0)

  private[this] class ArgsParser extends OptionParser[Args]("Scalac's chess challenge") {
    private[this] def withNumberValidator(failurePredicate: Int => Boolean, failureMessage: String): (Int) => Either[String, Unit] = {
      case i if failurePredicate(i) => failure(failureMessage)
      case _ => success
    }

    private[this] def createDimOpt(dimName: String)(copyFun: (Int, Args) => Args) =
      opt[Int](dimName)
        .validate { withNumberValidator(_ < 0, s"$dimName must be > 0") }
        .text(s"amount of chessboard $dimName")
        .action(copyFun)
        .required()

    private[this] def createPieceOpt(pieceName: String)(copyFun: (Int, Args) => Args) =
      opt[Int](pieceName)
        .validate { withNumberValidator(_ < 0, s"$pieceName number must be >= 0") }
        .text(s"amount of $pieceName")
        .action(copyFun)
        .optional()

    createDimOpt("rows")((v, args) => args.copy(rows = v))
    createDimOpt("cols")((v, args) => args.copy(cols = v))
    createPieceOpt("kings")((v, args) => args.copy(kings = v))
    createPieceOpt("queens")((v, args) => args.copy(queens = v))
    createPieceOpt("bishops")((v, args) => args.copy(bishops = v))
    createPieceOpt("rooks")((v, args) => args.copy(rooks = v))
    createPieceOpt("knights")((v, args) => args.copy(knights = v))
  }

  private val logger = Logger("main")

  def mkAlgorithmParams(args: Args): (List[Candidate], Dim) = {
    val candidates =
      List.fill(args.queens)(Queen) ++
      List.fill(args.rooks)(Rook) ++
      List.fill(args.bishops)(Bishop) ++
      List.fill(args.kings)(King) ++
      List.fill(args.knights)(Knight)

    (candidates, Dim(args.rows, args.cols))
  }

  def measureExecution[A](block: => A): (A, Double) = {
    val nanoCoef = 1e-9
    val start = nanoTime()
    val result = block
    val stop = nanoTime()
    (result, (stop - start)*nanoCoef)
  }

  def main(args: Array[String]) {
    val parser = new ArgsParser
    val solver = new Solver

    parser.parse(args, Args()) match {
      case Some(arguments) => {
        val (candidates, dim) = mkAlgorithmParams(arguments)
        val (solutions, elapsedSec) = measureExecution {
          solver.findSolutions(candidates, dim)
        }
        logger.info(s"Found ${solutions.length} solutions in $elapsedSec seconds")
      }
      case _ =>
    }
  }
}
