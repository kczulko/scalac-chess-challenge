
object Main {
  private[this] case class Args(rows: Int = 0,
                  cols: Int = 0,
                  queens: Int = 0,
                  rooks: Int = 0,
                  knights: Int = 0,
                  kings: Int = 0,
                  bishops: Int = 0)

  private[this] class ArgsParser extends scopt.OptionParser[Args]("Scalac's chess challenge") {
    private[this] def withNumberValidator(failurePredicate: Int => Boolean, failureMessage: String): (Int) => Either[String, Unit] = {
      case i if failurePredicate(i) => failure(failureMessage)
      case _ => success
    }

    private[this] def createDimOpt(dimName: String)(copyFun: (Int, Args) => Args) =
      opt[Int](dimName)
        .validate { withNumberValidator(_ < 0, s"${dimName} must be > 0") }
        .text(s"amount of chessboard $dimName")
        .action(copyFun)
        .required()

    private[this] def createPieceOpt(pieceName: String)(copyFun: (Int, Args) => Args) =
      opt[Int](pieceName)
        .validate { withNumberValidator(_ < 0, s"${pieceName} number must be >= 0") }
        .text(s"amount of ${pieceName}")
        .action { copyFun }
        .optional()

    createDimOpt("rows")((v, args) => args.copy(rows = v))
    createDimOpt("cols")((v, args) => args.copy(cols = v))
    createPieceOpt("kings")((v, args) => args.copy(kings = v))
    createPieceOpt("queens")((v, args) => args.copy(queens = v))
    createPieceOpt("bishops")((v, args) => args.copy(bishops = v))
    createPieceOpt("rooks")((v, args) => args.copy(rooks = v))
    createPieceOpt("knights")((v, args) => args.copy(knights = v))
  }

  def main(args: Array[String]) {
    val parser = new ArgsParser

    parser.parse(args, Args()) match {
      case Some(arguments) => println(arguments)
      case _ =>
    }
  }
}
