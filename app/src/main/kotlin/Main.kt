val rows = 20
val cols = 30

var grid = Array(rows) { BooleanArray(cols) { false } }

fun clone() = grid.map { it.clone() }.toTypedArray()

fun neighbor(y: Int, x: Int, buffer: Array<BooleanArray>): Int {
    val directions = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)
    return directions.count { (dy, dx) -> buffer[(y + dy + rows) % rows][(x + dx + cols) % cols] }
}

fun set(y: Int, x: Int, alive: Boolean, buffer: Array<BooleanArray>) = if (alive) neighbor(y, x, buffer) in 2..3 else neighbor(y, x, buffer) == 3

fun step(buffer: Array<BooleanArray>) = buffer.forEachIndexed { y, row -> row.forEachIndexed { x, _ -> grid[y][x] = set(y, x, buffer[y][x], buffer) } }

fun str() = "\u001b[H\u001b[2J" + grid.joinToString("\n") { it.joinToString(" ") { if (it) "#" else "-" } }

fun draw() = println(str()).also { Thread.sleep(500) }

fun game() {
    grid[0][1] = true
    grid[1][2] = true
    grid[2][0] = true
    grid[2][1] = true
    grid[2][2] = true
    while (true) {
        draw()
        step(clone())
    }
}

fun main() = game()
