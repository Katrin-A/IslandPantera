package com.javarush.island.aleinik.entity.lifeforms.animals;


import com.javarush.island.aleinik.annotations.SpeciesInfo;

@SpeciesInfo(specieWeight = 8, maxPerCell = 60, speedLimit = 2, foodRequiredKg = 4, maxGroupSize = 15)
public class Foxes extends AnimalGroup{

    public Foxes(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }


}

