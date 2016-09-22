package model

abstract class Piece(position: Position)

case class King(p: Position) extends Piece(p)

case class Queen(p: Position) extends Piece(p)

case class Bishop(p: Position) extends Piece(p)

case class Rook(p: Position) extends Piece(p)

case class Knight(p: Position) extends Piece(p)
