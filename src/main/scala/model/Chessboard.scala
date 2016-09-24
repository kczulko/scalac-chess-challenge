package model

case class Chessboard(pieces: List[Piece], dim: Dim) {
  def place(piece: Piece): Chessboard = Chessboard(piece :: pieces, dim)

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
}
