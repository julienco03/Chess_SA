/**
 *  8x8 Grid
 * /---+---+---+---+---+---+---+---\
 * |###|###|###|###|###|###|###|###|
 * |---+---+---+---+---+---+---+---|
 * |###|###|###|###|###|###|###|###|
 * |---+---+---+---+---+---+---+---|
 * |   |   |   |   |   |   |   |   |
 * |---+---+---+---+---+---+---+---|
 * |   |   |   |   |   |   |   |   |
 * |---+---+---+---+---+---+---+---|
 * |   |   |   |   |   |   |   |   |
 * |---+---+---+---+---+---+---+---|
 * |   |   |   |   |   |   |   |   |
 * |---+---+---+---+---+---+---+---|
 * |###|###|###|###|###|###|###|###|
 * |---+---+---+---+---+---+---+---|
 * |###|###|###|###|###|###|###|###|
 * \---+---+---+---+---+---+---+---/
 * 1x1 Grid
 * /---\
 * |   |
 * \---/
 * 2x2 Grid
 * /---+---\
 * |   |   |
 * |---+---|
 * |   |   |
 * \---+---/
*/

val eol = sys.props("line.separator")

def first_last_row(x_size:Int = 8, first:String = "/", last:String = "\\") = first + ("-" * 3 + "+") * (x_size - 1) + ("-" * 3) + last + eol

def boarder_row(x_size:Int = 8) = ("+" + "-" * 3) * x_size + "+" + eol

def cell_row(x_size:Int = 8) = "|   " * (x_size) + "|" + eol

def mesh(x_size:Int = 8, y_size:Int = 8) =
  if (x_size >= 1 && y_size >= 1) {
    first_last_row(x_size) + cell_row(x_size) + (boarder_row(x_size) + cell_row(x_size)) * (y_size - 1) + first_last_row(x_size, "\\", "/")
  } else {
    ""
  }

val play_field = mesh(8,8)