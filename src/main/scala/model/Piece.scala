package model

trait Piece {
  def position: Position
  def attacks(other: Piece): Boolean
}

case class King(position: Position) extends Piece {
  override def attacks(other: Piece): Boolean = {
    val rowDelta = math.abs(other.position.row - position.row)
    val colDelta = math.abs(other.position.col - position.col)

    rowDelta <= 1 && colDelta <= 1
  }
}

case class Queen(position: Position) extends Piece {
  def attacks(other: Piece): Boolean = {
    { Bishop(position) attacks other } ||
    { Rook(position) attacks other }
  }
}

case class Bishop(position: Position) extends Piece {
  override def attacks(other: Piece): Boolean = {
    val d = position.distanceBetween(other.position)
    d.rowDistance == d.colDistance
  }
}

case class Rook(position: Position) extends Piece {
  override def attacks(other: Piece): Boolean = {
    other.position.row == position.row ||
      other.position.col == position.col
  }
}

case class Knight(position: Position) extends Piece {
  override def attacks(other: Piece): Boolean = {
    val d = position.distanceBetween(other.position)
    (other.position equals position) ||
    (d.colDistance == 1 && d.rowDistance == 2) ||
    (d.colDistance == 2 && d.rowDistance == 1)
  }
}
