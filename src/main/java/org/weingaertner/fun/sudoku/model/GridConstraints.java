package org.weingaertner.fun.sudoku.model;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Slf4j
/**
 * Models the constraints in a sudoku grid
 */
public class GridConstraints extends Object{

    private final int gridSize;
    private final int subspaceSize;

    private int[] rowConstraints;
    private int[] columnConstraints;
    private int[] subsquareConstraints;

    public int getRowConstraint(int row) {
        return rowConstraints[row];
    }

    public int getColumnConstraint(int col) {
        return columnConstraints[col];
    }

    public int getSubSquareConstraint(int row, int col) {
        int index = getSubsquareIndex(row, col);
        return subsquareConstraints[index];
    }

    private int getSubsquareIndex(int row, int col) {
        int index=(Math.floorDiv(row,subspaceSize))*subspaceSize+(Math.floorDiv(col,subspaceSize));
        return index;
    }

    public int getAppliedContstaintsForField(int row, int col) {
        int rowConstraint= getRowConstraint(row);
        int colConstraint = getColumnConstraint(col);
        int subspaceConstraint = getSubSquareConstraint(row,col);

        int result = rowConstraint | colConstraint | subspaceConstraint;

        return result;
    }

    public GridConstraints getUpdatedGridContraints(int row, int col, int value) {
        int[] clonedRowConstraints = new int[gridSize];
        int[] clonedColumnConstraints = new int[gridSize];
        int[] clonedSubsquareConstraints = new int[gridSize];

        System.arraycopy(rowConstraints,0,clonedRowConstraints,0,rowConstraints.length);
        System.arraycopy(columnConstraints,0,clonedColumnConstraints,0,columnConstraints.length);
        System.arraycopy(subsquareConstraints,0,clonedSubsquareConstraints,0,subsquareConstraints.length);

        clonedRowConstraints[row] = BitUtils.setNthBitInInt(rowConstraints[row],value);
        clonedColumnConstraints[col] = BitUtils.setNthBitInInt(columnConstraints[col],value);
        int subgridIndex = getSubsquareIndex(row,col);
        clonedSubsquareConstraints[subgridIndex] = BitUtils.setNthBitInInt(subsquareConstraints[subgridIndex],value);

        return new GridConstraints(gridSize,subspaceSize,clonedRowConstraints,clonedColumnConstraints, clonedSubsquareConstraints);

    }


    public void setValue(int row, int col, int value) {
        this.rowConstraints[row]=BitUtils.setNthBitInInt(this.rowConstraints[row], value);
        this.columnConstraints[col]=BitUtils.setNthBitInInt(this.columnConstraints[col],value);
        int subgridIndex = getSubsquareIndex(row,col);
        this.subsquareConstraints[subgridIndex]=BitUtils.setNthBitInInt(this.subsquareConstraints[subgridIndex],value);
    }

    public void unsetValue(int row, int col, int value) {
        this.rowConstraints[row]=BitUtils.unsetNthBitInInt(this.rowConstraints[row], value);
        this.columnConstraints[col]=BitUtils.unsetNthBitInInt(this.columnConstraints[col],value);
        int subgridIndex = getSubsquareIndex(row,col);
        this.subsquareConstraints[subgridIndex]=BitUtils.unsetNthBitInInt(this.subsquareConstraints[subgridIndex],value);
    }

    public Set<Byte> getPossibleValuesForField(int row, int col) {
        int allConstraints = getAppliedContstaintsForField(row,col);
        int index=0;
        HashSet<Byte> result=new HashSet();

        for (byte i=0;i<gridSize+1;i++) {
            if (!BitUtils.isBitSet(allConstraints,i)) {
                result.add(i);
            }
        }

        return result;
    }

    public static GridConstraints fromGrid(Grid grid) {
        int size = grid.getSize();

        int[] rowConstraints = new int[size];
        int[] columnConstraints = new int[size];
        int[] subsquareConstraints = new int[size];

        //Initialize Row and Column constraints
        for (int k=0;k<size;k++) {

            rowConstraints[k] = 0;
            for (int l=0;l<size;l++) {
                int bitToSet = grid.get(k, l);
                rowConstraints[k]= BitUtils.setNthBitInInt(rowConstraints[k],bitToSet);
            }

            columnConstraints[k] = 0;
            for (int l=0;l<size;l++) {
                int bitToSet=grid.get(l, k);
                columnConstraints[k]= BitUtils.setNthBitInInt(columnConstraints[k],bitToSet);
            }

        }

        //Initialize Subsquare Constraints
        int subsquareSize = (int) Math.sqrt(size);
        int subSquare=0;
        for (int subRowOffset=0;subRowOffset<size;subRowOffset=subRowOffset+subsquareSize) {
            for (int subColOffset=0;subColOffset<size;subColOffset=subColOffset+subsquareSize) {
                subsquareConstraints[subSquare] = 0;
                for (int i=0;i<subsquareSize;i++) {
                    for (int j=0;j<subsquareSize;j++) {
                        int bitToSet = grid.get(subRowOffset+i,subColOffset+j);
                        subsquareConstraints[subSquare]= BitUtils.setNthBitInInt(subsquareConstraints[subSquare],bitToSet);
                    }
                }
                subSquare++;
            }
        }

        return new GridConstraints(grid.getSize(),subsquareSize,rowConstraints,columnConstraints, subsquareConstraints);
    }


    public String toString() {

        String result = "Row Constraints: ";
        for (int rowConstraint : rowConstraints) {
            result = result + toConstraintString(rowConstraint);
        }

        result = result+ "\nColumn Constraints: ";
        for (int colConst : columnConstraints) {
            result = result + toConstraintString(colConst);
        }

        result = result+ "\nSubsquare Constraints: ";
        for (int subconst : subsquareConstraints) {
            result = result + toConstraintString(subconst);
        }

        return result;
    }

    private String toConstraintString(int constraint) {

        Set<Integer> setBits = new HashSet<>();

        for (int i=0;i<gridSize+1;i++) {
            if (BitUtils.isBitSet(constraint,i)) {
                setBits.add(i);
            }
        }

        return setBits.toString();

    }

}
