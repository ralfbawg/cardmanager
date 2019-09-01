package com.ralf.cardmanager.system;

public enum SpType {
    DIVVY("divvy"), YIPIAOLIAN("yipiaolian");

    private String name;

    SpType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
