package org.weingaertner.fun.sudoku.solver;

import org.weingaertner.fun.sudoku.model.Grid;

public interface Solver {
    /**
     * Solves a partial sudoku grid
     * @param partialGrid
     * @return The fully solved grid or null if no solution was found
     */
    Grid solve(Grid partialGrid);

    /**
     * Time needed for the solver execution in milliseconds
     * @return processing time in miliseconds
     */
    Long getSolvingTime();

    /**
     * Number of solver iterations
     * @return
     */
    int getIterations();
}
