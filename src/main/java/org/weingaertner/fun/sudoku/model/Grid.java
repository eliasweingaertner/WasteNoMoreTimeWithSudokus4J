package org.weingaertner.fun.sudoku.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.StringReader;
import java.util.List;
import java.util.Objects;

@Slf4j
/**
 * This class models a Sudoku Grid
 */
public class Grid {
    @Getter
    private final int size;
    @Getter
    private byte[] numbers;

    @Getter
    @Setter
    private GridConstraints gridConstraints;

    public Grid(int size) {
        numbers =new byte[size*size];
        this.size = size;
    }

    public Grid(int size, byte[] data) {
        this.size = size;
        numbers = data;
    }

    public Grid clone() {
        byte[] clone = new byte[size*size];
        System.arraycopy(numbers,0,clone,0,numbers.length);
        return new Grid(size, clone);
    }

    public void set(int row, int column, byte value) {
        numbers[row*size+column] = value;
    }

    public byte get(int row, int column) {
        return numbers[row*size+column];
    }

    public GridCoordinates nextEmptyField() {
        for (int i=0;i<numbers.length;i++) {
            if (numbers[i]==0) {
                int row = i/getSize();
                int col = i%getSize();
                return new GridCoordinates(row,col);
            }
        }
        return null;
    }

    public String toString() {
        String topBottomLine = "|" + "‒".repeat(4 * size - 1) + "|\n";
        String result = topBottomLine;
        for (int row = 0; row < size; row++) {
            String rowLine = "";
            for (int column = 0; column < size; column++) {
                String value = get(row,column) != 0 ? Byte.toString(get(row,column)) : " ";
                rowLine = rowLine + "| " + value + " ";
            }
            result = result + rowLine + "|\n";
        }
        result = result + topBottomLine;
        return result;
    }

    public static Grid fromString(String s) {
        List<String> lines = IOUtils.readLines(new StringReader(s));
        int size = Byte.parseByte(lines.get(0));
        lines.remove(0);
        Grid grid = new Grid(size);
        int row = 0;
        for (String line : lines) {
            for (int column = 0; column < size; column++) {
                String token = Character.toString(line.charAt(column));
                if (NumberUtils.isDigits(token)) {
                    grid.set(row, column, Byte.parseByte(token));
                } else if (token.equals(" ") || token.equals("X")) {
                    grid.set(row, column, (byte) 0);
                } else {
                    throw new RuntimeException("Illegal character in sudoku:" + token);
                }
            }
            row++;
            if (row > size) {
                throw new RuntimeException("Too many lines in file!");
            }
        }
        return grid;

    }

    public boolean isCompletelyFilled() {
        return Objects.isNull(nextEmptyField());
    }

    @Data
    @AllArgsConstructor
    @ToString
    public class GridCoordinates {
        int row;
        int col;
    }

}
