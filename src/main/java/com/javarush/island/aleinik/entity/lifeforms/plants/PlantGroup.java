package com.javarush.island.aleinik.entity.lifeforms.plants;

import com.javarush.island.aleinik.entity.island.Cell;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;

public abstract class PlantGroup extends LifeForm {

    protected PlantGroup(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight);
    }

    @Override
    public boolean isDead() {
        return currentGroupNumber <= 0;
    }


}
