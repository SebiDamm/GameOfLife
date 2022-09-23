package com.example.gameoflife;

/**
 * Zelle von GameOfLife, welche entweder tot oder am leben sein kann.
 */
public class Cell {

    private final int x;
    private final int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Cell[] getNeighbours() {
        return new Cell[]{
                new Cell(x - 1, y - 1), new Cell(x, y - 1), new Cell(x + 1, y - 1),
                new Cell(x - 1, y), new Cell(x + 1, y),
                new Cell(x - 1, y + 1), new Cell(x, y + 1), new Cell(x + 1, y + 1),
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        final Cell cell = (Cell) obj;

        return (this.x == cell.x && this.y == cell.y);
    }

    @Override
    public int hashCode() {
        return 97927 * x + y;
    }
}
