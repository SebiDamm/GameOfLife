package com.example.gameoflife;

/**
 * Zelle von GameOfLife, welche entweder tot oder am leben sein kann.
 */
public class Cell {
    private boolean isAlive;

    public Cell(boolean isAlive){
        this.isAlive=isAlive;
    }

    public Cell(){
        this.isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void comeToLife() {
        isAlive = true;
    }

    public void kill(){
        isAlive = false;
    }

}
