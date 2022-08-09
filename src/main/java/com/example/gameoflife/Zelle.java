package com.example.gameoflife;

/**
 * Zelle von GameOfLife, welche entweder tot oder am leben sein kann.
 */
public class Zelle {
    private boolean isAlive;

    public Zelle(boolean isAlive){
        this.isAlive=isAlive;
    }

    public Zelle(){
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
