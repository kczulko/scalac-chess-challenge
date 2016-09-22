package model

case class Position(row: Int, col: Int) {
  assert(row > 0)
  assert(col > 0)
}
