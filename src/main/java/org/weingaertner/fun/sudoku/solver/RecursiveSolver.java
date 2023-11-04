package org.weingaertner.fun.sudoku.solver;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.weingaertner.fun.sudoku.model.Grid;
import org.weingaertner.fun.sudoku.model.GridConstraints;

import java.util.Objects;
import java.util.Set;

@Slf4j
public class RecursiveSolver implements Solver{

    int iterations;
    @Getter
    int maxDepth;
    @Getter
    int deadEnds;

    private Long solvingTime;

    @Override
    public Grid solve(Grid partialGrid) {

        this.iterations = 0;
        this.maxDepth = 0;
        this.deadEnds=0;

        long timeStarted = System.currentTimeMillis();
        GridConstraints gridConstraints = GridConstraints.fromGrid(partialGrid);
        partialGrid.setGridConstraints(gridConstraints);
        Grid result =  solve(partialGrid, 0);
        solvingTime = System.currentTimeMillis()-timeStarted;

        return result;
    }

    @Override
    public Long getSolvingTime() {
        return solvingTime;
    }

    @Override
    public int getIterations() {
        return iterations;
    }

    public Grid solve(Grid partialGrid,  int depth) {

        iterations++;

        if (depth>this.maxDepth) {
            maxDepth=depth;
        }

        if (partialGrid.isCompletelyFilled()) {
            return partialGrid;
        }

        Grid.GridCoordinates nextEmptyField = partialGrid.nextEmptyField();
        int row = nextEmptyField.getRow();
        int col = nextEmptyField.getCol();

        GridConstraints gridConstraints = partialGrid.getGridConstraints();
        Set<Byte> possibleFieldValues = gridConstraints.getPossibleValuesForField(row, col);

        int nextDepth=depth+1;

        for (Byte possibleValue: possibleFieldValues) {

            byte oldVal=partialGrid.get(row,col);

            partialGrid.set(row,col,possibleValue);
            gridConstraints.setValue(row,col,possibleValue);
            Grid candidate = solve(partialGrid,nextDepth);

            if (Objects.nonNull(candidate)) {
                return candidate;
            } else {
                gridConstraints.unsetValue(row,col,possibleValue);
                partialGrid.set(row,col,oldVal);
            }
        }

        deadEnds++;
        return null;

    }

}
