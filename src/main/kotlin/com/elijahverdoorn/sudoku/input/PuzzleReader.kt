package com.elijahverdoorn.sudoku.input

import com.elijahverdoorn.sudoku.model.Cell
import com.elijahverdoorn.sudoku.model.Puzzle
import java.io.File

class PuzzleReader(
    val fileLocation: String?
) {
    val file: File = File(fileLocation?:FILE_LOCATION)
    val puzzleSize: Int
    var puzzle: Puzzle

    init {
        puzzleSize = file.readLines().first().length
        puzzle = Puzzle(size = puzzleSize)
        parsePuzzle()
    }

    fun parsePuzzle() {
        var rowBoxCount = 0
        puzzle.cells = file.readLines().mapIndexed { rowIndex, s ->
            val row = s.mapIndexed { columnIndex, c ->
                val cell = Cell(confirmedValue = if (c.isDigit()) {
                    Character.getNumericValue(c)
                } else {
                    null
                },
                        column = columnIndex,
                        row = rowIndex,
                        boxId = (columnIndex / puzzle.boxSize) + rowBoxCount,
                        currentCandidateValue = null
                )
                cell
            }.toMutableList()
            if ((rowIndex + 1) % puzzle.boxSize == 0 && rowIndex != 0) {
                rowBoxCount += puzzle.boxSize
            }
            row
        }.toMutableList()
    }

    companion object {
        const val FILE_LOCATION = "./input.txt"
    }
}