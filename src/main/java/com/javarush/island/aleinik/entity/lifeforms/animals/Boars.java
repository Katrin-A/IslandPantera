package com.javarush.island.aleinik.entity.lifeforms.animals;

import com.javarush.island.aleinik.annotations.SpeciesInfo;

@SpeciesInfo(specieWeight = 400, maxPerCell = 50, speedLimit = 2, foodRequiredKg = 50, maxGroupSize = 10)
public class Boars extends AnimalGroup{

    public Boars(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }
}
