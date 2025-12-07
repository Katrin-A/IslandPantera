package com.javarush.island.aleinik.entity.lifeforms.animals;
import com.javarush.island.aleinik.annotations.SpeciesInfo;


@SpeciesInfo(specieWeight = 50, maxPerCell = 30, speedLimit = 3, foodRequiredKg = 8, maxGroupSize = 10)
public class WolfPack extends AnimalGroup{
    public WolfPack(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }
}
