package me.Banbeucmas.FileManagement;

/**
 * Created by DELL on 6/24/2016.
 */
public enum ChestType {
    NORMAL(1),
    HIDDEN(1),
    UNIQUE(2),
    RARE(5),
    EPIC(7),
    LEGENDARY(10);


    public int point;
    ChestType(int point){
        this.point = point;
    }

    public int getPoint(){
        return point;
    }
}
