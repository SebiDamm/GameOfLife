package com.example.gameoflife;

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
