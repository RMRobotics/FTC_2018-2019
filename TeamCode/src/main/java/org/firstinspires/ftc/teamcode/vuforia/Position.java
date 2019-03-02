package org.firstinspires.ftc.teamcode.vuforia;

/*
Created on 2/8/19 by Neal Machado
Simple Position Class
 */

public class Position {

    /**********INSTANCE VARIABLES**********/
    private double tX, tY, tZ, rX, rY, rZ;

    /**********CONSTRUCTORS**********/
    public Position(){
        this(0, 0, 0, 0, 0, 0);
    }

    public Position(double tX, double tY, double tZ, double rX, double rY, double rZ) {
        this.tX = tX;
        this.tY = tY;
        this.tZ = tZ;
        this.rX = rX;
        this.rY = rY;
        this.rZ = rZ;
    }

    public Position(Position position){
        tX = position.gettX();
        tY = position.gettY();
        tZ = position.gettZ();
        rX = position.getrX();
        rY = position.getrY();
        rZ = position.getrY();
    }

    /**********METHODS**********/
    public boolean equals(Position pos){
        return (tX == pos.gettX()) && (tY == pos.gettY()) && (tZ == pos.gettZ()) && (rX == pos.getrX()) && (rY == pos.getrY()) && (rZ == pos.getrZ());
    }

    /**********MUTATORS**********/
    public void settX(double tX){ this.tX = tX; }
    public void settY(double tY){ this.tY = tY; }
    public void settZ(double tZ){ this.tZ = tZ; }
    public void setrX(double rX){ this.rX = rX; }
    public void setrY(double rY){ this.rY = rY; }
    public void setrZ(double rZ){ this.rZ = rZ; }

    /**********ACCESSORS**********/
    public double gettX(){ return tX; }
    public double gettY(){ return tY; }
    public double gettZ(){ return tZ; }
    public double getrX(){ return rX; }
    public double getrY(){ return rY; }
    public double getrZ(){ return rZ; }

}
