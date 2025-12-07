package com.javarush.island.aleinik.entity.lifeforms.animals;

import com.javarush.island.aleinik.annotations.SpeciesInfo;

@SpeciesInfo(specieWeight = 300, maxPerCell = 20, speedLimit = 4, foodRequiredKg = 50, maxGroupSize = 10)
public class Deer extends AnimalGroup {
    public Deer(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }
}
