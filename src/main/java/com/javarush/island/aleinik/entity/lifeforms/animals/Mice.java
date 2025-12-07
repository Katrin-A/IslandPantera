package com.javarush.island.aleinik.entity.lifeforms.animals;


import com.javarush.island.aleinik.annotations.SpeciesInfo;

@SpeciesInfo(specieWeight = 1, maxPerCell = 500, speedLimit = 1, foodRequiredKg = 3, maxGroupSize = 100)
public class Mice extends AnimalGroup {

    public Mice(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }
}
