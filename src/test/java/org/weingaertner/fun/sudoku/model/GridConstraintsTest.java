package org.weingaertner.fun.sudoku.model;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class GridConstraintsTest {

    @Test
    public void testGridConstraintFromFile() throws IOException {
        String wildcat = new String(IOUtils.resourceToByteArray("/wildcat1.txt"));
        Grid grid = Grid.fromString(wildcat);
        log.info(grid.toString());
        int l=0;
        for (int i=0;i<1900000;i++) {
            GridConstraints gridConstraints = GridConstraints.fromGrid(grid);
            //log.info(gridConstraints.toString());
            l = l + gridConstraints.getRowConstraint(0).length();
        }
        log.info("{}",l);

    }

}