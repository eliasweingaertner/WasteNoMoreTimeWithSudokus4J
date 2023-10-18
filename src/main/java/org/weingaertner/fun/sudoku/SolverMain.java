package org.weingaertner.fun.sudoku;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.weingaertner.fun.sudoku.model.Grid;
import org.weingaertner.fun.sudoku.solver.IterativeSolver;
import org.weingaertner.fun.sudoku.solver.RecursiveSolver;
import org.weingaertner.fun.sudoku.solver.Solver;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
public class SolverMain {

    @Parameter(description = "<The sudoku file to solve>",required = true)
    String sudokuFile;

    @Parameter(names = "--iterative", description = "Use the iterative solver", required = false)
    boolean useIterativeSolver = false;

    @Parameter(names = {"-h","--help"}, help = true)
    private boolean help;

    private Solver sudokuSolver;

    @SneakyThrows
    public static void main(String[] args) {
        SolverMain solverMain = new SolverMain();

        JCommander jct = JCommander.newBuilder()
                .addObject(solverMain)
                .build();
        String jarName = getCurrentJarName();
        String programName = "java -jar "+jarName;
        jct.setProgramName(programName);

        try {
            jct.parse(args);
        } catch (ParameterException e) {
            log.error("Invalid Syntax: {}", e.getMessage());
            jct.usage();
            System.exit(1);
        }



        if (solverMain.help) {
            jct.usage();
        } else {
            solverMain.run();
        }
    }

    private static String getCurrentJarName() throws URISyntaxException {
        String jarPath = SolverMain.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath();
        File file = new File(jarPath);
        String jarName = file.getName();
        return jarName;
    }

    public void run() throws IOException {

        this.sudokuSolver = useIterativeSolver ? new IterativeSolver() : new RecursiveSolver();
        log.info("Using Solver: {}", this.sudokuSolver.getClass().getName());
        log.info("Solving sudoku file: {}", sudokuFile);

        String sudokuString = FileUtils.readFileToString(new File(sudokuFile), StandardCharsets.UTF_8);
        Grid grid = Grid.fromString(sudokuString);

        log.info("Solving Sudoku: \n {}", grid.toString());
        Grid solution = sudokuSolver.solve(grid);

        if (Objects.nonNull(solution)) {
            log.info("Solution found: \n{}", solution.toString());
            log.info("Solution time: {}ms", sudokuSolver.getSolvingTime());
        } else {
            log.info("No solution found");
        }

    }
}
