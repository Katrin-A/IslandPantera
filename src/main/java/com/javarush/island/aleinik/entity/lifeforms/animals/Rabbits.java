package com.javarush.island.aleinik.entity.lifeforms.animals;


import com.javarush.island.aleinik.annotations.SpeciesInfo;

@SpeciesInfo(specieWeight = 2, maxPerCell = 150, speedLimit = 2, foodRequiredKg = 4, maxGroupSize = 30)
public class Rabbits extends AnimalGroup {

    public Rabbits(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }
}
