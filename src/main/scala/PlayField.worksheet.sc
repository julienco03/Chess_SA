val eol = sys.props("line.separator")

def first_last_row(x_size:Int = 8, first:String = "/", last:String = "\\") = first + ("-" * 3 + "+") * (x_size - 1) + ("-" * 3) + last + eol

def border_row(x_size:Int = 8) = ("+" + "-" * 3) * x_size + "+" + eol

def cell_row(x_size:Int = 8) = "|   " * (x_size) + "|" + eol

def play_field(x_size:Int = 8, y_size:Int = 8) =
    first_last_row(x_size) + cell_row(x_size) + (border_row(x_size) + cell_row(x_size)) * (y_size - 1) + first_last_row(x_size, "\\", "/")

play_field(8, 8)