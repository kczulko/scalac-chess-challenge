package model

case class Dim(rows: Int, cols: Int) {
  assert(rows > 0)
  assert(cols > 0)
}
