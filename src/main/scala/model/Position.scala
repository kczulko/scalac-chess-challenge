package model

object Position {
  implicit def tupleToPosition(tuple: (Int, Int)): Position = Position(tuple._1, tuple._2)
}

case class Position(row: Int, col: Int) {
  assert(row > 0)
  assert(col > 0)

  def distanceBetween(other: Position): {val rowDistance: Int; val colDistance: Int} =
    new { lazy val rowDistance = math.abs(row - other.row); lazy val colDistance = math.abs(col - other.col) }
}
