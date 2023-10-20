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
        GridConstraints gridConstraints = GridConstraints.fromGrid(grid);

        assertEquals(getBitVectorForByteArray(new byte[]{2,6,7,1}), gridConstraints.getRowConstraint(0));
        assertEquals(getBitVectorForByteArray(new byte[]{8,2,1,4}), gridConstraints.getRowConstraint(3));
        assertEquals(getBitVectorForByteArray(new byte[]{4,6,2,9}), gridConstraints.getRowConstraint(4));
        assertEquals(getBitVectorForByteArray(new byte[]{7,3,1,8}), gridConstraints.getRowConstraint(8));

        assertEquals(getBitVectorForByteArray(new byte[]{8,9,2,5,4}), gridConstraints.getColumnConstraint(1));
        assertEquals(getBitVectorForByteArray(new byte[]{2,1,6,3}), gridConstraints.getColumnConstraint(3));
        assertEquals(getBitVectorForByteArray(new byte[]{6,7,5,1}), gridConstraints.getColumnConstraint(4));
        assertEquals(getBitVectorForByteArray(new byte[]{1,8,4,6}), gridConstraints.getColumnConstraint(8));

        for (int row=0;row<3;row++) {
            for (int col=0;col<3;col++) {
                int sc = gridConstraints.getSubSquareConstraint(row,col);
                assertEquals(getBitVectorForByteArray(new byte[]{6,1,8,9}), sc);
            }
        }

        for (int row=0;row<3;row++) {
            for (int col=3;col<6;col++) {
                int sc = gridConstraints.getSubSquareConstraint(row,col);
                assertEquals(getBitVectorForByteArray(new byte[]{2,6,7,4}), sc);
            }
        }

        int row=4;
        int col=4;
        log.info("All available values for row={}, col={} are {}",row,col,gridConstraints.getPossibleValuesForField(row,col));

    }

    private int getBitVectorForByteArray(byte[] bytes) {
        int result = 0;
        result |= 1 << 0;
        for (Byte b: bytes) {
            result |= 1 << b;
        }
        return result;
    }

}