package com.elijahverdoorn.sudoku.model

data class Cell (
    val column: Int,
    val row: Int,
    val boxId: Int,
    var confirmedValue: Int?,
    var currentCandidateValue: Int?
) {
    val isGiven: Boolean
        get() = confirmedValue != null
}
