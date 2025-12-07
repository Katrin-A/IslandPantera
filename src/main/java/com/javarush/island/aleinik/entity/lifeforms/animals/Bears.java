package com.javarush.island.aleinik.entity.lifeforms.animals;

import com.javarush.island.aleinik.annotations.SpeciesInfo;

@SpeciesInfo(specieWeight = 500, maxPerCell = 5, speedLimit = 2, foodRequiredKg = 80, maxGroupSize = 3)
public class Bears extends AnimalGroup {
    public Bears(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }
}
