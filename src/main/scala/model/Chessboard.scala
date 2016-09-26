package model

case class Chessboard(pieces: Set[Piece], dim: Dim, private val availablePositions: Option[Seq[Position]] = None) {
  lazy val unaffectedPositions = availablePositions getOrElse {
    pieces.foldLeft(dim.toPositionSeq) {
      (positions, piece) => positions.filterNot { piece attacks }
    }
  }

  def place(piece: Piece): Chessboard = Chessboard(
    pieces + piece,
    dim,
    Some(unaffectedPositions.filterNot { piece attacks })
  )

  override def toString: String = {
    def pieceToString(piece: Piece): String = piece match {
      case p: King => "K"
      case p: Queen => "Q"
      case p: Rook => "R"
      case p: Bishop => "B"
      case p: Knight => "M"
      case _ => "?"
    }

    def toRowString(row: Seq[Position]) =
      for {
        position <- row
      } yield pieces.find(_.position equals position)
                    .map(pieceToString)
                    .getOrElse("-")

    dim.toPositionSeq.sliding(dim.cols, dim.cols)
      .map(toRowString)
      .map(_ mkString)
      .mkString("\n")
  }
}
