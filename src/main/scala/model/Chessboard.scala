package model

case class Chessboard(pieces: List[Piece]) {
  def place(piece: Piece): Chessboard = Chessboard(piece :: pieces)
  def canPlace(candidate: Piece): Boolean = {
    pieces forall { piece =>
      { piece attacks candidate equals false } &&
        { candidate attacks piece equals false }
    }
  }
}
