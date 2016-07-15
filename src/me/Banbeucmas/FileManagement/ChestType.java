package me.Banbeucmas.FileManagement;

import java.util.Random;

/**
 * Chest Type enums
 */
public enum ChestType {
    NORMAL(1),
    HIDDEN(1),
    UNIQUE(2),
    RARE(5),
    EPIC(7),
    LEGENDARY(10),
    LUCKY(new Random().nextInt(10) + 1),
    UNLUCKY(1);

    public int point;
    ChestType(int point){
        this.point = point;
    }

    public int getPoint(){
        return point;
    }
}
