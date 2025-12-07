package com.javarush.island.aleinik.entity.lifeforms.plants;

import com.javarush.island.aleinik.entity.lifeforms.LifeForm;

public abstract class PlantGroup extends LifeForm {

    protected PlantGroup(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight);
    }

    @Override
    public boolean isDead() {
        return this.currentGroupNumber <= 0;
    }

    @Override
    public void updateWeightAndCount(double totalWeight) {
        this.totalWeight = totalWeight;
        this.currentGroupNumber = Math.max(1, (int)Math.round(totalWeight / specieWeight));
    }


}
