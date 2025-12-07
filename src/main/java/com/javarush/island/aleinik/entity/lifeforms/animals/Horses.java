package com.javarush.island.aleinik.entity.lifeforms.animals;

import com.javarush.island.aleinik.annotations.SpeciesInfo;

@SpeciesInfo(specieWeight = 400, maxPerCell = 20, speedLimit = 4, foodRequiredKg = 60, maxGroupSize = 10)
public class Horses extends AnimalGroup{

    public Horses(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }
}
