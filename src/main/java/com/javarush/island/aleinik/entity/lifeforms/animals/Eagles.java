package com.javarush.island.aleinik.entity.lifeforms.animals;

import com.javarush.island.aleinik.annotations.SpeciesInfo;

@SpeciesInfo(specieWeight = 6, maxPerCell = 20, speedLimit = 3, foodRequiredKg = 1, maxGroupSize = 5)
public class Eagles extends AnimalGroup{
    public Eagles(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }
}
