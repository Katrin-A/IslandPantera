package com.javarush.island.aleinik.entity.lifeforms.animals;
import com.javarush.island.aleinik.annotations.SpeciesInfo;

@SpeciesInfo(specieWeight = 1, maxPerCell = 200, speedLimit = 4, foodRequiredKg = 0.15, maxGroupSize = 50)
public class DuckFlock extends AnimalGroup {

    public DuckFlock(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }

}
