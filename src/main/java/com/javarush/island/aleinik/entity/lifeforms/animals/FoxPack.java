package com.javarush.island.aleinik.entity.lifeforms.animals;


import com.javarush.island.aleinik.annotations.SpeciesInfo;
import com.javarush.island.aleinik.entity.island.Cell;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;
import com.javarush.island.aleinik.interfaces.Eat;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@SpeciesInfo(specieWeight = 8, maxPerCell = 60, speedLimit = 2, foodRequiredKg = 2, maxGroupSize = 15)
public class FoxPack extends AnimalGroup {

    public FoxPack(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }


}

