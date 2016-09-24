package model

case class Dim(rows: Int, cols: Int) {
  assert(rows > 0)
  assert(cols > 0)

  lazy val toPositionSeq: Seq[Position] = for {
    row <- 1 to rows
    col <- 1 to cols
  } yield Position(row, col)
}

