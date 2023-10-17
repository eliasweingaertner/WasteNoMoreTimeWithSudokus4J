package org.weingaertner.fun.sudoku.model;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class GridTest {

    @Test
    public void testGridFromFile() throws IOException {
        String wildcat = new String(IOUtils.resourceToByteArray("/wildcat1.txt"));
        Grid grid = Grid.fromString(wildcat);
        assertEquals(8,grid.get(1,1));
        assertEquals(0,grid.get(0,0));
        assertEquals(7,grid.get(8,0));
        log.info("Grid parsed: \n{}", grid.toString());

        log.info("First emptyField: {}", grid.nextEmptyField());
    }

}