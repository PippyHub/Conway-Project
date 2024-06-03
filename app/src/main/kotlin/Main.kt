import java.io.File
typealias Grid = List<List<Boolean>>
val rows = 40
val cols = 60
val directions = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)
fun neighbor(y: Int, x: Int, buffer: Grid): Int = directions.count { (dy, dx) -> buffer.get((y + dy + rows) % rows).get((x + dx + cols) % cols) }
fun set(y: Int, x: Int, grid: Grid): Boolean = if (grid[y][x]) neighbor(y, x, grid) in 2..3 else neighbor(y, x, grid) == 3
fun step(grid: Grid): Grid = List(rows) { y -> List(cols) { x -> set(y, x, grid) } }
fun render(grid: Grid): String = grid.joinToString("\n") { row -> row.joinToString(" ") { if (it) "#" else "-" } }
fun game(grid: Grid): Unit = println("\u001b[H\u001b[2J" + render(grid)).also { Thread.sleep(100) }.let { game(step(grid)) }
fun grid(file: File): Grid = List(rows) { y -> List(cols) { x -> file.readLines().map { it.split(",").map(String::toInt) }.any { it[0] == y && it[1] == x } } }
fun main() = game(grid(File("src/main/resources/grid")))
