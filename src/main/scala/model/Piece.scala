package model

object Piece {
  type Candidate = (Position) => Piece with Product with Serializable
}

trait Piece {
  def position: Position
  def attacks(other: Piece): Boolean
}

case class King(position: Position) extends Piece {
  override def attacks(other: Piece): Boolean = {
    lazy val rowDistance = position.rowDistanceBetween(other position)
    lazy val colDistance = position.colDistanceBetween(other position)

    rowDistance <= 1 && colDistance <= 1
  }
}

case class Queen(position: Position) extends Piece {
  override def attacks(other: Piece): Boolean = {
    { Bishop(position) attacks other } ||
    { Rook(position) attacks other }
  }
}

case class Bishop(position: Position) extends Piece {
  override def attacks(other: Piece): Boolean = {
    val rowDistance = position.rowDistanceBetween(other position)
    val colDistance = position.colDistanceBetween(other position)
    rowDistance == colDistance
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
    lazy val rowDistance = position.rowDistanceBetween(other position)
    lazy val colDistance = position.colDistanceBetween(other position)

    (other.position equals position) ||
    (colDistance == 1 && rowDistance == 2) ||
    (colDistance == 2 && rowDistance == 1)
  }
}
