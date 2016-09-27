
import java.lang.System.nanoTime

import algorithm.Solver
import com.typesafe.scalalogging.Logger
import model.Piece.Candidate
import model._
import scopt.OptionParser

object Main {
  private[this] case class Args(rows: Int = 0,
                                cols: Int = 0,
                                print: Int = 0,
                                rooks: Int = 0,
                                kings: Int = 0,
                                queens: Int = 0,
                                knights: Int = 0,
                                bishops: Int = 0)

  private[this] class ArgsParser extends OptionParser[Args]("Scalac's chess challenge") {
    private[this] def withNumberValidator(failurePredicate: Int => Boolean, failureMessage: String): (Int) => Either[String, Unit] = {
      case i if failurePredicate(i) => failure(failureMessage)
      case _ => success
    }

    private[this] def createIntOpt(optName: String)
                                    (failurePredicate: Int => Boolean, failureMsg: String)
                                    (copyFun: (Int, Args) => Args)
                                    (text: String = s"""number of $optName""") =
      opt[Int](optName)
        .validate { withNumberValidator(failurePredicate, failureMsg) }
        .text(text)
        .action(copyFun)

    createIntOpt("rows")(_ <= 0, "rows must be > 0")((v, args) => args.copy(rows = v))() required()
    createIntOpt("cols")(_ <= 0, "rows must be > 0")((v, args) => args.copy(cols = v))() required()
    createIntOpt("kings")(_ < 0, "kings must be >= 0")((v, args) => args.copy(kings = v))() optional()
    createIntOpt("rooks")(_ < 0, "rooks must be >= 0")((v, args) => args.copy(rooks = v))() optional()
    createIntOpt("queens")(_ < 0, "queens must be >= 0")((v, args) => args.copy(queens = v))() optional()
    createIntOpt("bishops")(_ < 0, "bishops must be >= 0")((v, args) => args.copy(bishops = v))() optional()
    createIntOpt("knights")(_ < 0, "knights must be >= 0")((v, args) => args.copy(knights = v))() optional()
    createIntOpt("print")(_ < 0, "print must be >= 0")((v, args) => args.copy(print = v)) (
      "number of boards to print"
    ) optional()
    help("help") text "prints this usage"
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
      case Some(arguments) =>
        val (candidates, dim) = mkAlgorithmParams(arguments)
        val (solutions, elapsedSec) = measureExecution {
          solver.findSolutions(candidates, dim)
        }
        logger.info(s"Found ${solutions.length} solutions in $elapsedSec seconds")
        logger.info(s"Printing no more than ${arguments.print} solutions: \n${
          solutions
            .take(arguments.print)
            .map(_.asString)
            .mkString("\n\n")
        }")
      case _ =>
    }
  }
}
