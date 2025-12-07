package com.javarush.island.aleinik.entity.lifeforms.animals;


import com.javarush.island.aleinik.annotations.SpeciesInfo;

@SpeciesInfo(specieWeight = 60, maxPerCell = 140, speedLimit = 3, foodRequiredKg = 60, maxGroupSize = 20)
public class Goats extends AnimalGroup{

    public Goats(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }
}
