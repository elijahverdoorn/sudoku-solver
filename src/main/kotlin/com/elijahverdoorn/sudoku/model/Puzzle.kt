package com.elijahverdoorn.sudoku.model

data class Puzzle(
    val size: Int,
    val boxSize: Int = Math.sqrt(size.toDouble()).toInt(),
    var cells: MutableList<MutableList<Cell>> = emptyList<MutableList<Cell>>().toMutableList()
) {
    // The number of unconfirmed cells
    val emptyCells: Int
        get() = cells.flatten().count { it.confirmedValue == null }

    val isSolved: Boolean
        get() = emptyCells == 0

    // Print the puzzle, with optional lines
    fun print(lines: Boolean) {
        if (lines) {
            prettyPrint()
        } else {
            cells.forEach {
                it.forEach {
                    print(it.confirmedValue ?: " ")
                }
                println()
            }
        }
    }

    // Print the puzzle, with lines
    private fun prettyPrint() {
        cells.forEachIndexed { rowIndex, row ->
            if (rowIndex % boxSize == 0) {
                // print the horizontal lines
                println("-".repeat(row.size + (boxSize - 1)))
            }
            row.forEachIndexed { columnIndex, cell ->
                if (columnIndex % boxSize == 0 && columnIndex != 0) {
                    // print the vertical line
                    print("|")
                }
                print(cell.confirmedValue?:" ")
            }
            println()
        }
    }

    // Solve the puzzle using a backtracking methodology
    fun solve() {
        val cellList = cells.flatten().filter { !it.isGiven }
        var i = 0
        while (i < cellList.size) {
            cellList[i].apply {
                // place the first possible value in the cell
                if (currentCandidateValue == null) {
                    currentCandidateValue = 1
                } else {
                    currentCandidateValue?.let {
                        currentCandidateValue = it + 1
                    }
                }
                // check if the current candidate is allowed in that location
                val peerCells = cells.flatten().filter { (it.row == this.row || it.column == this.column || it.boxId == this.boxId) && (it.confirmedValue != null || it.currentCandidateValue != null) && (it != this) }
                val peerValues = peerCells.map { it.confirmedValue ?: it.currentCandidateValue }.distinct()
                if (peerValues.contains(this.currentCandidateValue)) {
                    // found a cell in the puzzle that contains the value that we're currently using as a candidate
                    // this means that the current candidate is invalid
                    if (this.currentCandidateValue == size) {
                        // we've exhausted all the possible values
                        // there are no remaining possible values for this cell
                        // leave this cell blank
                        this.currentCandidateValue = null

                        // move back to the previous cell
                        i--
                    }
                } else {
                    // there are no violations
                    // advance to the next cell and repeat the whole process
                    i++
                }
            }
        }
        // We've solved the puzzle, so confirm all the values
        cellList.forEach { it.confirmedValue = it.currentCandidateValue }

    }
}
