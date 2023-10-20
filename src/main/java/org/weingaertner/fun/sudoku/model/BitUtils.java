package org.weingaertner.fun.sudoku.model;
public class BitUtils {
    public static boolean isBitSet(int constraint, int bit) {
        return (((constraint>>bit) & 1) == 1);
    }

    public static int setNthBitInInt(int constraint, int bit) {
        int result = constraint | (1 << bit);
        return result;
    }
}
