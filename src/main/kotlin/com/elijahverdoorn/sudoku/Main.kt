package com.elijahverdoorn.sudoku

import com.elijahverdoorn.sudoku.input.PuzzleReader

fun main() {
    val p = PuzzleReader(fileLocation = "/Users/everdoorn/dev/sudoku-solver/res/puzzle-9-2.txt").puzzle
    println("Input:")
    p.print(true)

    println("Solved:")
    p.solve()
    p.print(true)
}
