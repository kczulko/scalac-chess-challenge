package model

object Position {
  implicit def tupleToPosition(tuple: (Int, Int)): Position = Position(tuple._1, tuple._2)
}

case class Position(row: Int, col: Int) {
  assert(row > 0)
  assert(col > 0)

  def rowDistanceBetween(other: Position): Int =  math.abs(row - other.row)
  def colDistanceBetween(other: Position): Int =  math.abs(col - other.col)
}
