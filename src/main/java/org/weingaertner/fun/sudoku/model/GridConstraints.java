package org.weingaertner.fun.sudoku.model;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.BitSet;


@AllArgsConstructor
@Slf4j
public class GridConstraints extends Object{

    private final int gridSize;

    private BitSet[] rowConstraints;
    private BitSet[] columnConstraints;
    private BitSet[] subsquareConstraints;

    public BitSet getRowConstraint(int row) {
        return rowConstraints[row];
    }

    public BitSet getColumnConstraint(int col) {
        return columnConstraints[col];
    }

    public BitSet getSubSquareConstraint(int subsquare) {
        return subsquareConstraints[subsquare];
    }

    public static GridConstraints fromGrid(Grid grid) {
        int size = grid.getSize();

        BitSet[] rowConstraints = new BitSet[size];
        BitSet[] columnConstraints = new BitSet[size];
        BitSet[] subsquareConstraints = new BitSet[size];

        //Initialize Row and Column constraints
        for (int k=0;k<size;k++) {

            rowConstraints[k] = new BitSet(size);
            for (int l=0;l<size;l++) {
                rowConstraints[k].set(grid.get(k, l));
            }

            columnConstraints[k] = new BitSet(size);
            for (int l=0;l<size;l++) {
                columnConstraints[k].set(grid.get(l, k));
            }

        }

        //Initialize Subsquare Constraints
        int subsquareSize = (int) Math.sqrt(size);
        int subSquare=0;
        for (int subRowOffset=0;subRowOffset<size;subRowOffset=subRowOffset+subsquareSize) {
            for (int subColOffset=0;subColOffset<size;subColOffset=subColOffset+subsquareSize) {
                subsquareConstraints[subSquare] = new BitSet(size);
                for (int i=0;i<subsquareSize;i++) {
                    for (int j=0;j<subsquareSize;j++) {
                        subsquareConstraints[subSquare].set(grid.get(subRowOffset+i,subColOffset+j));
                    }
                }
                subSquare++;
            }
        }

        return new GridConstraints(grid.getSize(),rowConstraints,columnConstraints, subsquareConstraints);

    }

    public String toString() {

        String result = "Row Constraints: ";
        for (BitSet rowConstraint : rowConstraints) {
            result = result + rowConstraint;
        }

        result = result+ "\nColumn Constraints: ";
        for (BitSet colConst : columnConstraints) {
            result = result + colConst;
        }

        result = result+ "\nSubsquare Constraints: ";
        for (BitSet subconst : subsquareConstraints) {
            result = result + subconst;
        }

        return result;
    }

}
