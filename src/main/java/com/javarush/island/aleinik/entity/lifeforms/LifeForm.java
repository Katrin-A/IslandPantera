package com.javarush.island.aleinik.entity.lifeforms;

import com.javarush.island.aleinik.annotations.SpeciesInfo;
import lombok.Getter;

@SpeciesInfo(specieWeight = 0, maxPerCell = 0)
public abstract class LifeForm {

    protected final int specieWeight;
    @Getter
    protected int currentGroupNumber;
    @Getter
    protected double totalWeight;

    protected LifeForm(int currentGroupNumber, int specieWeight) {
        this.currentGroupNumber = currentGroupNumber;
        this.specieWeight = specieWeight;
        initTotalWeight();
    }

    protected void initTotalWeight() {
        this.totalWeight = specieWeight * currentGroupNumber;
    }

    public void loseEnergy(double amount) {
        this.totalWeight -= amount;
        if (this.totalWeight < 0) this.totalWeight = 0;
        this.currentGroupNumber = (int) (this.totalWeight / specieWeight);
    }

    public void updateWeightAndCount(double totalWeight) {
        this.totalWeight = totalWeight;
        this.currentGroupNumber = (int) (this.totalWeight / specieWeight);
    }

    public boolean isDead() {
        return getCurrentGroupNumber() <= 0;
    }


}
