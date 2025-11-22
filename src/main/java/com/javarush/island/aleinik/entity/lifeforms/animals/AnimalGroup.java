package com.javarush.island.aleinik.entity.lifeforms.animals;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;
import com.javarush.island.aleinik.interfaces.Eat;

public abstract class AnimalGroup extends LifeForm implements Eat {
    protected final double foodRequiredKg;

    protected AnimalGroup(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight);
        this.foodRequiredKg = foodRequiredKg;
    }

    @Override
    public boolean isDead() {
        //TODO: create logic where you calculate howm any species
        return getCurrentGroupNumber() <= 0;
    }
}
