package com.stephenspol.server.connect.util.recipe;

public abstract class Recipe {

    protected String type;
    protected String id;

    protected Recipe(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String getID() {
        return id;
    }

    @Override
    public String toString() {
        return "Type: " + type + " ID: " + id;
    }
}
