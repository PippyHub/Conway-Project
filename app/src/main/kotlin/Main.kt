import java.io.File

typealias Grid = Array<BooleanArray>

val rows = 20
val cols = 30

val directions = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)

fun neighbor(y: Int, x: Int, buffer: Grid): Int = directions.count { (dy, dx) -> buffer[(y + dy + rows) % rows][(x + dx + cols) % cols] }

fun set(y: Int, x: Int, buffer: Grid) = if (buffer[y][x]) neighbor(y, x, buffer) in 2..3 else neighbor(y, x, buffer) == 3

fun step(buffer: Grid): Grid = Array(rows) { y -> BooleanArray(cols) { x -> set(y, x, buffer) } }

fun render(grid: Grid) = "\u001b[H\u001b[2J" + grid.joinToString("\n") { it.joinToString(" ") { if (it) "#" else "-" } }

fun game(grid: Grid): Unit = println(render(grid)).also { Thread.sleep(1000) }.let { game(step(grid)) }

fun grid(file: File): Grid = Array(rows) { BooleanArray(cols) { false } }
  .also { grid -> file.readLines().map { it.split(",").map(String::toInt) }.forEach { (y, x) -> grid[y][x] = true } }

fun main() = game(grid(File("src/main/resources/grid")))
