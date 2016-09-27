package model

object Piece {
  type Candidate = (Position) => Piece with Product with Serializable
}

trait Piece {
  def identifier: Char
  def position: Position
  def attacks(other: Piece): Boolean = attacks(other position)
  def attacks(otherPosition: Position): Boolean
  def attacksAnyOf(others: Iterable[Piece]): Boolean = others exists attacks
}

case class King(position: Position) extends Piece {
  override def attacks(otherPosition: Position): Boolean = {
    lazy val rowDistance = position rowDistanceBetween otherPosition
    lazy val colDistance = position colDistanceBetween otherPosition

    rowDistance <= 1 && colDistance <= 1
  }

  override def identifier: Char = 'K'
}

case class Queen(position: Position) extends Piece {
  private lazy val bishop = Bishop(position)
  private lazy val rook = Rook(position)

  override def attacks(otherPosition: Position): Boolean = {
    { bishop attacks otherPosition } ||
    { rook attacks otherPosition }
  }

  override def identifier: Char = 'Q'
}

case class Bishop(position: Position) extends Piece {
  override def attacks(otherPosition: Position): Boolean = {
    val rowDistance = position rowDistanceBetween otherPosition
    val colDistance = position colDistanceBetween otherPosition
    rowDistance == colDistance
  }

  override def identifier: Char = 'B'
}

case class Rook(position: Position) extends Piece {
  override def attacks(otherPosition: Position): Boolean = {
    otherPosition.row == position.row ||
      otherPosition.col == position.col
  }

  override def identifier: Char = 'R'
}

case class Knight(position: Position) extends Piece {
  override def attacks(otherPosition: Position): Boolean = {
    lazy val rowDistance = position rowDistanceBetween otherPosition
    lazy val colDistance = position colDistanceBetween otherPosition

    (otherPosition equals position) ||
    (colDistance == 1 && rowDistance == 2) ||
    (colDistance == 2 && rowDistance == 1)
  }

  override def identifier: Char = 'N'
}
