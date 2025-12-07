package com.javarush.island.aleinik.entity.lifeforms.animals;

import com.javarush.island.aleinik.annotations.SpeciesInfo;

@SpeciesInfo(specieWeight = 700, maxPerCell = 10, speedLimit = 3, foodRequiredKg = 100, maxGroupSize = 5)
public class Buffaloes extends AnimalGroup{

    public Buffaloes(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }
}
