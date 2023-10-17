package org.weingaertner.fun.sudoku.solver;

import org.weingaertner.fun.sudoku.model.Grid;

public interface Solver {
    /**
     * Solves a partial sudoku grid
     * @param partialGrid
     * @return The fully solved grid or null if no solution was found
     */
    Grid solve(Grid partialGrid);

    Long getSolvingTime();
}
