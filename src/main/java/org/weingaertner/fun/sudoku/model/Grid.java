package org.weingaertner.fun.sudoku.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.StringReader;
import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class Grid {
    @Getter
    private final int size;
    @Getter
    private byte[][] numbers;

    public Grid clone() {
        byte[][] clone = new byte[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                clone[row][col] = this.numbers[row][col];
            }
        }

        return new Grid(size, clone);
    }


    public void set(int row, int column, byte value) {
        numbers[row][column] = value;
    }

    public byte get(int row, int column) {
        return numbers[row][column];
    }

    public GridCoordinates nextEmptyField() {
        for (int row = 0; row < getSize(); row++) {
            for (int col = 0; col < getSize(); col++) {
                if (get(row, col) == 0) {
                    return new GridCoordinates(row, col);
                }
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
                String value = (this.numbers[row][column] != 0) ? Byte.toString(this.numbers[row][column]) : " ";
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
        Grid grid = new Grid(size, new byte[size][size]);
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
