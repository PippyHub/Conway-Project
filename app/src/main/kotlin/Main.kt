typealias Grid = Array<BooleanArray>

val rows = 20
val cols = 30

val directions = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)

fun neighbor(y: Int, x: Int, buffer: Grid): Int = directions.count { (dy, dx) -> buffer[(y + dy + rows) % rows][(x + dx + cols) % cols] }

fun set(y: Int, x: Int, buffer: Grid) = if (buffer[y][x]) neighbor(y, x, buffer) in 2..3 else neighbor(y, x, buffer) == 3

fun step(buffer: Grid): Grid = Array(rows) { y -> BooleanArray(cols) { x -> set(y, x, buffer) } }

fun string(grid: Grid) = "\u001b[H\u001b[2J" + grid.joinToString("\n") { it.joinToString(" ") { if (it) "#" else "-" } }

fun draw(grid: Grid) = println(string(grid)).also { Thread.sleep(500) }

fun game(grid: Grid): Unit = draw(grid).let { game(step(grid)) }

fun createGrid(): Grid {
    return Array(rows) { y ->
        BooleanArray(cols) { x ->
            when {
                y == 0 && x == 2 -> true
                y == 1 && x == 0 -> true
                y == 1 && x == 2 -> true
                y == 2 && x == 1 -> true
                y == 2 && x == 2 -> true
                else -> false
            }
        }
    }
}

fun main() = game(createGrid())

//fun render(grid: Grid) = "\u001b[H\u001b[2J" + grid.joinToString("\n") { it.joinToString(" ") { if (it) "#" else "-" } }


//fun game(grid: Grid): Grid = println(render(grid)).also { Thread.sleep(500) }.let { game(step(grid)) }

//import java.io.File

//val file = File("source/main/resources/grid")

//fun createGrid(): Grid = Array(rows) { BooleanArray(cols) { false } }
//  .also { grid -> file.readLines().map { it.split(",").map(String::toInt) }.forEach { (y, x) -> grid[y][x] = true } }
