package com.javarush.island.aleinik.entity.lifeforms.plants;

import com.javarush.island.aleinik.annotations.SpeciesInfo;

@SpeciesInfo(specieWeight = 1, maxPerCell = 200, maxGroupSize = 50)
public class Grass extends PlantGroup {

    public Grass(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }


}
