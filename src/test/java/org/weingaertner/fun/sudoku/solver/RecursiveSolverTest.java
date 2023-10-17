package org.weingaertner.fun.sudoku.solver;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.weingaertner.fun.sudoku.model.Grid;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
public class RecursiveSolverTest {

    @Test
    public void shouldSolveSudoku() throws IOException {

        String wildcat = new String(IOUtils.resourceToByteArray("/inkala.txt"));
        Grid grid = Grid.fromString(wildcat);

        log.info("Partial Grid: \n{}",grid);

        RecursiveSolver solver = new RecursiveSolver();
        log.info("Solving...");
        Grid solution = solver.solve(grid);
        log.info("Result: \n{}",solution);

        log.info("Iterations needed {}", solver.getIterations());
        log.info("Maximum search depth: {}", solver.getMaxDepth());
        log.info("Dead Ends: {}", solver.getDeadEnds());
        log.info("Solving time: {} ms", solver.getSolvingTime());


    }

}