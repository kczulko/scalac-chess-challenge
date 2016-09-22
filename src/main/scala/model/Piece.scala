package model

abstract class Piece(position: Position)

case class Queen(p: Position) extends Piece(p)


