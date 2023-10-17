package org.weingaertner.fun.sudoku.solver;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.weingaertner.fun.sudoku.model.Grid;

import java.io.IOException;
@Slf4j
public class IterativeSolverTest {

    @Test
    public void shouldSolveSudoku() throws IOException {

        String wildcat = new String(IOUtils.resourceToByteArray("/inkala.txt"));
        Grid grid = Grid.fromString(wildcat);

        log.info("Partial Grid: \n{}",grid);

        IterativeSolver solver = new IterativeSolver();
        log.info("Solving...");
        Grid solution = solver.solve(grid);
        log.info("Result: \n{}",solution);

        log.info("Iterations needed {}", solver.getIterationsNeeded());
        log.info("Solving time: {}ms", solver.getSolvingTime());

    }
}
