package model

abstract class Piece(val position: Position) {
  def attacks(other: Piece): Boolean = ???
}

case class King(p: Position) extends Piece(p) {
  override def attacks(other: Piece): Boolean = {
    val rowDelta = math.abs(other.position.row - position.row)
    val colDelta = math.abs(other.position.col - position.col)

    rowDelta <= 1 && colDelta <= 1
  }
}

case class Queen(p: Position) extends Piece(p)

case class Bishop(p: Position) extends Piece(p)

case class Rook(p: Position) extends Piece(p)

case class Knight(p: Position) extends Piece(p)
