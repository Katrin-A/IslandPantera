package com.javarush.island.aleinik.config;

public class Parameters {
    private final int specieWeight;
    private final int maxPerCell;
    private final int speedLimit;
    private final double foodRequiredKg;
    private final int maxGroupSize;

    public int getSpecieWeight() {
        return specieWeight;
    }

    public int getMaxPerCell() {
        return maxPerCell;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public int getMaxGroupSize() {
        return maxGroupSize;
    }

    public double getFoodRequiredKg() {
        return foodRequiredKg;
    }

    public Parameters(int specieWeight, int maxNumber, int speedLimit, double foodRequiredKg, int maxGroupSize) {
        this.specieWeight = specieWeight;
        this.maxPerCell = maxNumber;
        this.speedLimit = speedLimit;
        this.foodRequiredKg = foodRequiredKg;
        this.maxGroupSize = maxGroupSize;
    }
}

