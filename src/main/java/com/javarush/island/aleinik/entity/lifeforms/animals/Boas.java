package com.javarush.island.aleinik.entity.lifeforms.animals;

import com.javarush.island.aleinik.annotations.SpeciesInfo;

@SpeciesInfo(specieWeight = 15, maxPerCell = 30, speedLimit = 1, foodRequiredKg = 3, maxGroupSize = 15)
public class Boas extends AnimalGroup{
    public Boas(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }
}
