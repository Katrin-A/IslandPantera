package com.javarush.island.aleinik.entity.lifeforms.animals;

import com.javarush.island.aleinik.annotations.SpeciesInfo;

@SpeciesInfo(specieWeight = 70, maxPerCell = 140, speedLimit = 3, foodRequiredKg = 15, maxGroupSize = 20)
public class Sheep extends AnimalGroup {
    public Sheep(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }

}
