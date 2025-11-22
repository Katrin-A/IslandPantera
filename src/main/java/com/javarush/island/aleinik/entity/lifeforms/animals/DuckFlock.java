package com.javarush.island.aleinik.entity.lifeforms.animals;
import com.javarush.island.aleinik.annotations.SpeciesInfo;
import com.javarush.island.aleinik.entity.island.Cell;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;
import com.javarush.island.aleinik.entity.lifeforms.plants.Grass;

import java.util.Map;

@SpeciesInfo(specieWeight = 1, maxPerCell = 200, speedLimit = 4, foodRequiredKg = 0.15, maxGroupSize = 50)
public class DuckFlock extends AnimalGroup {



    //TODO: make it a TreeMap with Comparable by Integers (persentage to eat)
    private final Map<Class<? extends LifeForm>, Integer>  foodMap = Map.of(
         Grass.class, 100
    );


    public DuckFlock(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }


    @Override
    public boolean eat(Cell cell) {
        return false;
    }
}
