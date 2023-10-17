package org.weingaertner.fun.sudoku.solver;

import lombok.Getter;
import org.weingaertner.fun.sudoku.model.Grid;
import org.weingaertner.fun.sudoku.model.GridConstraints;


import java.util.Set;
import java.util.Stack;

public class IterativeSolver implements Solver {

    @Getter
    private int iterationsNeeded = 0;

    private Long solvingTime;


    @Override
    public Grid solve(Grid partialGrid) {

        long timeStarted = System.currentTimeMillis();

        Stack<Grid> gridStack = new Stack<>();
        gridStack.add(partialGrid);

        while (!gridStack.isEmpty()) {
            Grid nextGrid = gridStack.pop();

            if (nextGrid.isCompletelyFilled()) {
                this.solvingTime = System.currentTimeMillis() - timeStarted;
                return nextGrid; //Solution found
            }

            Grid.GridCoordinates nextEmptyField = nextGrid.nextEmptyField();
            GridConstraints gridConstraints = GridConstraints.fromGrid(nextGrid);
            Set<Byte> possibleFieldValues = gridConstraints.getPossibleValuesForField(nextEmptyField.getRow(), nextEmptyField.getCol());

            for (Byte possibleValue : possibleFieldValues) {
                Grid candidateGrid = nextGrid.clone();
                candidateGrid.set(nextEmptyField.getRow(), nextEmptyField.getCol(), possibleValue);
                gridStack.push(candidateGrid);
            }

            iterationsNeeded++;
        }

        this.solvingTime = System.currentTimeMillis() - timeStarted;

        return null;
    }

    @Override
    public Long getSolvingTime() {
        return solvingTime;
    }

}
