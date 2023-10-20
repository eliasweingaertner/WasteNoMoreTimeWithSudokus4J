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

        GridConstraints partialGridConstraints = GridConstraints.fromGrid(partialGrid);
        partialGrid.setGridConstraints(partialGridConstraints);

        gridStack.add(partialGrid);

        while (!gridStack.isEmpty()) {
            Grid nextGrid = gridStack.pop();

            if (nextGrid.isCompletelyFilled()) {
                this.solvingTime = System.currentTimeMillis() - timeStarted;
                return nextGrid; //Solution found
            }

            Grid.GridCoordinates nextEmptyField = nextGrid.nextEmptyField();
            GridConstraints gridConstraints = nextGrid.getGridConstraints();
            Set<Byte> possibleFieldValues = gridConstraints.getPossibleValuesForField(nextEmptyField.getRow(), nextEmptyField.getCol());

            for (Byte possibleValue : possibleFieldValues) {
                Grid candidateGrid = nextGrid.clone();
                candidateGrid.set(nextEmptyField.getRow(), nextEmptyField.getCol(), possibleValue);
                GridConstraints candidateGridConstraints = gridConstraints.getUpdatedGridContraints(nextEmptyField.getRow(), nextEmptyField.getCol(), possibleValue);
                candidateGrid.setGridConstraints(candidateGridConstraints);
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

    @Override
    public int getIterations() {
        return iterationsNeeded;
    }

}
