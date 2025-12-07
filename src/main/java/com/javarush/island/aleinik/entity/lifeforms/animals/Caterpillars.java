package com.javarush.island.aleinik.entity.lifeforms.animals;


import com.javarush.island.aleinik.annotations.SpeciesInfo;

@SpeciesInfo(specieWeight = 1, maxPerCell = 200, speedLimit = 0, foodRequiredKg = 0.15, maxGroupSize = 40)
public class Caterpillars extends AnimalGroup {

    public Caterpillars(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        //TODO: make mice weight little - species weight should be double not int!
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }
}
