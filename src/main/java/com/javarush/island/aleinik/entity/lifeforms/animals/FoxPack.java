package com.javarush.island.aleinik.entity.lifeforms.animals;


import com.javarush.island.aleinik.annotations.SpeciesInfo;
import com.javarush.island.aleinik.entity.island.Cell;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;
import com.javarush.island.aleinik.interfaces.Eat;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@SpeciesInfo(specieWeight = 8, maxPerCell = 60, speedLimit = 2, foodRequiredKg = 2, maxGroupSize = 15)
public class FoxPack extends AnimalGroup implements Eat {

    //TODO: make it a TreeMap with Comparable by Integers (persentage to eat)
    private final Map<Class<? extends LifeForm>, Integer> foodMap = Map.of(
            DuckFlock.class, 50
    );

    public Map<Class<? extends LifeForm>, Integer> getFoodMap() {
        return foodMap;
    }

    public FoxPack(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight, foodRequiredKg);
    }


    @Override
    public boolean eat(Cell cell) {
        double requiredFood = currentGroupNumber * foodRequiredKg;
        cell.getLock().lock();
        try {
            //TODO: should be a class because this will be easier to get here
            Map<Class<? extends LifeForm>, Set<LifeForm>> cellResidents = cell.getInhabitants();
            Set<LifeForm> lifeForms = cellResidents.get(DuckFlock.class);

            if (lifeForms == null || lifeForms.isEmpty()) {
                return false;
            }

            Iterator<LifeForm> iterator = lifeForms.iterator();

            while (iterator.hasNext() && requiredFood > 0) {
                LifeForm duckFlock = iterator.next();
                double duckWeight = duckFlock.getTotalWeight();

                if (duckWeight <= requiredFood) {
                    requiredFood -= duckWeight;
                    totalWeight += duckWeight;
                    iterator.remove();
                } else {
                    totalWeight += requiredFood;
                    duckFlock.updateTotalWeight(duckWeight-requiredFood);
                    requiredFood = 0;

                    if(duckFlock.isDead()){
                        iterator.remove();
                    }

                }
            }

        } finally {
            cell.getLock().unlock();
        }

        return requiredFood<=0;
    }

}
