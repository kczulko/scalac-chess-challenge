import scopt.OptionDef

case class Args(rows: Int = 0,
                cols: Int = 0,
                queens: Int = 0,
                rooks: Int = 0,
                knights: Int = 0,
                kings: Int = 0,
                bishops: Int = 0)

object Main {

  def withNumberValidator(failurePredicate: Int => Boolean, failureMessage: String): (Int) => Either[String, Unit] = {
    case i if failurePredicate(i) => Left(failureMessage)
    case _ => Right()
  }

  def main(args: Array[String]) {

    val parser = new scopt.OptionParser[Args]("Scalac's chess challenge") {
      def createPieceOpt(pieceName: String)(copyFun: (Int, Args) => Args): OptionDef[Int, Args] = {
        opt[Int](pieceName)
          .validate { withNumberValidator(_ < 0, s"${pieceName} number must be >= 0") }
          .action { copyFun }
          .optional()
          .text(s"amount of ${pieceName}")
      }

      opt[Int]("rows")
        .validate { withNumberValidator(_ <= 0, "rows must be greater than 0") }
        .action((v, args) => args.copy(rows = v))
        .text("number of chessboard rows")
        .required()

      opt[Int]("cols")
        .validate { withNumberValidator(_ <= 0, "cols must be greater than 0") }
        .action((v, args) => args.copy(cols = v))
        .text("number of chessboard cols")
        .required()

      createPieceOpt("queens")((v, args) => args.copy(queens = v))
      createPieceOpt("rooks")((v, args) => args.copy(rooks = v))
      createPieceOpt("knights")((v, args) => args.copy(knights = v))
      createPieceOpt("knights")((v, args) => args.copy(knights = v))
      createPieceOpt("kings")((v, args) => args.copy(kings = v))
      createPieceOpt("bishops")((v, args) => args.copy(bishops = v))
    }

    parser.parse(Seq("--rows", "3", "--cols", "2", "--queens", "-4"), Args()) match {
      case Some(args) => println(args)
      case _ =>
    }
  }
}
