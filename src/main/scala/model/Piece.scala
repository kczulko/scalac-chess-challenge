package model

object Piece {
  type Candidate = (Position) => Piece with Product with Serializable
}

trait Piece {
  def position: Position
  def attacks(other: Piece): Boolean = attacks(other.position)
  def attacks(otherPosition: Position): Boolean
  def attacksAnyOf(others: Iterable[Piece]): Boolean = others exists attacks
}

case class King(position: Position) extends Piece {
  override def attacks(otherPosition: Position): Boolean = {
    lazy val rowDistance = position rowDistanceBetween otherPosition
    lazy val colDistance = position colDistanceBetween otherPosition

    rowDistance <= 1 && colDistance <= 1
  }
}

case class Queen(position: Position) extends Piece {
  override def attacks(otherPosition: Position): Boolean = {
    { Bishop(position) attacks otherPosition } ||
    { Rook(position) attacks otherPosition }
  }
}

case class Bishop(position: Position) extends Piece {
  override def attacks(otherPosition: Position): Boolean = {
    val rowDistance = position rowDistanceBetween otherPosition
    val colDistance = position colDistanceBetween otherPosition
    rowDistance == colDistance
  }
}

case class Rook(position: Position) extends Piece {
  override def attacks(otherPosition: Position): Boolean = {
    otherPosition.row == position.row ||
      otherPosition.col == position.col
  }
}

case class Knight(position: Position) extends Piece {
  override def attacks(otherPosition: Position): Boolean = {
    lazy val rowDistance = position rowDistanceBetween otherPosition
    lazy val colDistance = position colDistanceBetween otherPosition

    (otherPosition equals position) ||
    (colDistance == 1 && rowDistance == 2) ||
    (colDistance == 2 && rowDistance == 1)
  }
}
