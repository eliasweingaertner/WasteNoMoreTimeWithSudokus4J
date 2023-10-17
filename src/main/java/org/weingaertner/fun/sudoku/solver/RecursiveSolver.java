package org.weingaertner.fun.sudoku.solver;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.weingaertner.fun.sudoku.model.Grid;
import org.weingaertner.fun.sudoku.model.GridConstraints;

import java.util.Objects;
import java.util.Set;

@Slf4j
public class RecursiveSolver implements Solver{

    @Getter
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
        Grid result =  solve(partialGrid,0);
        solvingTime = System.currentTimeMillis()-timeStarted;

        return result;
    }

    @Override
    public Long getSolvingTime() {
        return solvingTime;
    }


    public Grid solve(Grid partialGrid, int depth) {

        iterations++;

        if (depth>this.maxDepth) {
            maxDepth=depth;
        }

        if (partialGrid.isCompletelyFilled()) {
            return partialGrid;
        }

        Grid.GridCoordinates nextEmptyField = partialGrid.nextEmptyField();
        GridConstraints gridConstraints = GridConstraints.fromGrid(partialGrid);
        Set<Byte> possibleFieldValues = gridConstraints.getPossibleValuesForField(nextEmptyField.getRow(), nextEmptyField.getCol());

        int nextDepth=depth+1;

        for (Byte possibleValue: possibleFieldValues) {
            Grid nextGrid = partialGrid.clone();
            nextGrid.set(nextEmptyField.getRow(), nextEmptyField.getCol(),possibleValue);
            Grid found = solve(nextGrid,nextDepth);
            if (Objects.nonNull(found)) {
                return found;
            }

        }

        deadEnds++;
        return null;



    }

}
