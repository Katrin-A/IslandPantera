package com.javarush.island.aleinik.entity.lifeforms;

import com.javarush.island.aleinik.annotations.SpeciesInfo;

@SpeciesInfo(specieWeight = 0, maxPerCell = 0)
public abstract class LifeForm {

    protected final int specieWeight;
    protected int currentGroupNumber;
    protected double totalWeight;

    protected LifeForm(int currentGroupNumber, int specieWeight) {
        this.currentGroupNumber = currentGroupNumber;
        this.specieWeight = specieWeight;
        initTotalWeight();
    }

    protected void initTotalWeight() {
        this.totalWeight = specieWeight * currentGroupNumber;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public int getCurrentGroupNumber() {
        return currentGroupNumber;
    }

    public void updateTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
        this.currentGroupNumber = (int) (this.totalWeight / specieWeight);
    }

    public double getCurrentWeightPerAnimal() {
        return (double) totalWeight / currentGroupNumber;
    }

    public boolean isDead() {
        return false;
    }


}
