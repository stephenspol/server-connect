package com.stephenspol.server.connect.util;

public class Statistic {
    
    private int categoryID;
    private int statisticID;
    private int value;

    public Statistic(int categoryID, int statisticID, int value) {
        this.categoryID = categoryID;
        this.statisticID = statisticID;
        this.value = value;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public int getStatisticID() {
        return statisticID;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Category ID: " + categoryID + ", Statistic ID: " + statisticID + ", Value: " + value;
    }
}