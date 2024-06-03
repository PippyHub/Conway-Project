val directions = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)
fun neighbor(y: Int, x: Int, grid: List<List<Boolean>>): Int = directions.count { (dy, dx) -> grid.get((y + dy + 40) % 40).get((x + dx + 60) % 60) }
fun set(y: Int, x: Int, grid: List<List<Boolean>>): Boolean = if (grid[y][x]) neighbor(y, x, grid) in 2..3 else neighbor(y, x, grid) == 3
fun evolve(grid: List<List<Boolean>>): List<List<Boolean>> = List(40) { y -> List(60) { x -> set(y, x, grid) } }
fun render(grid: List<List<Boolean>>): String = grid.joinToString("\n") { row -> row.joinToString(" ") { if (it) "#" else "-" } }
fun grid(f: java.io.File): List<List<Boolean>> = List(40) { y -> List(60) { x -> f.readLines().map { it.split(",").map(String::toInt) }.any { it[0] == y && it[1] == x } } }
fun game(grid: List<List<Boolean>>): Unit = println("\u001b[H\u001b[2J" + render(grid)).also { Thread.sleep(100) }.apply { if (grid.flatten().any { it }) game(evolve(grid)) }
fun main() = game(grid(java.io.File("src/main/resources/grid")))
