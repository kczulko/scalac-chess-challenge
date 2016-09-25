package model

case class Chessboard(pieces: Set[Piece], dim: Dim) {
  lazy val unaffectedPositions = pieces.foldLeft(dim.toPositionSeq) {
    (positions, piece) => positions.filter(piece attacks _ equals false)
  }

  def place(piece: Piece): Chessboard = Chessboard(pieces + piece, dim)

  def canPlace(candidate: Piece): Boolean = {
    candidate.position.row <= dim.rows &&
    candidate.position.col <= dim.cols &&
      {
        pieces forall { piece =>
        { piece attacks candidate equals false } &&
          { candidate attacks piece equals false }
        }
      }
  }

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
