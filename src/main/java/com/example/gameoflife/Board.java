package com.example.gameoflife;

import java.util.HashSet;

/**
 * m x n Spielbrett für GameOfLife.
 * Es wird der aktuelle Stand des Spielbretts gespeichert.
 */
public class Board {

    private HashSet<Cell> livingCells = new HashSet<>();
    private HashSet<Cell> checkedCells = new HashSet<>();
    private HashSet<Cell> nextStateRevive = new HashSet<>();
    private HashSet<Cell> nextStateKill = new HashSet<>();

    public void addCell(int x, int y) {
        livingCells.add(new Cell(x, y));
    }

    public boolean isCellAlive(Cell cell) {
        return livingCells.contains(cell);
    }

    public boolean isCellAlive(int x, int y) {
        return livingCells.contains(new Cell(x, y));
    }

    public void removeCell(int x, int y) {
        livingCells.remove(new Cell(x, y));
    }

    public int numLivingCells() {
        return livingCells.size();
    }

    public void clear() {
        livingCells.clear();
    }

    public void deleteLife(int x, int y) {
        livingCells.remove(new Cell(x, y));
    }

    public void createLife(int x, int y) {
        livingCells.add(new Cell(x, y));
    }

    private int countNeighbours(Cell cell) {
        int tmpNeighbours = 0;
        for (Cell c : cell.getNeighbours()) {
            if (isCellAlive(c)) {
                tmpNeighbours++;
            }
        }
        return tmpNeighbours;
    }

    public void nextState() {
        for (Cell cell : livingCells) {
            int numNeighbours = countNeighbours(cell);
            if (numNeighbours < 2 || numNeighbours > 3) {
                nextStateKill.add(cell);
            }
            reviveNeighboursIfPossible(cell);
        }

        for (Cell c : nextStateKill) {
            livingCells.remove(c);
        }
        livingCells.addAll(nextStateRevive);

        nextStateKill.clear();
        nextStateRevive.clear();
        checkedCells.clear();
    }

    private void reviveNeighboursIfPossible(Cell cell) {
        for (Cell c : cell.getNeighbours()) {
            if (checkedCells.contains(c) || nextStateRevive.contains(c)) {
                continue;
            }

            int count = 0;
            for (Cell n : c.getNeighbours()) {
                if (isCellAlive(n)) {
                    count++;
                }
            }

            if (count == 3) {
                nextStateRevive.add(c);
            } else {
                checkedCells.add(c);
            }
        }
    }

}
