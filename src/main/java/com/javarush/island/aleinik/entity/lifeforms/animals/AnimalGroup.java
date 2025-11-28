package com.javarush.island.aleinik.entity.lifeforms.animals;

import com.javarush.island.aleinik.entity.island.Cell;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;
import com.javarush.island.aleinik.interfaces.Eat;
import com.javarush.island.aleinik.interfaces.Move;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AnimalGroup extends LifeForm implements Eat, Move {
    protected final double foodRequiredKg;

    protected AnimalGroup(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight);
        this.foodRequiredKg = foodRequiredKg;
    }

    @Override
    public boolean move(Map<Class<? extends LifeForm>, Set<LifeForm>> inhabitants) {
        return false;
    }


    @Override
    public boolean isDead() {
        return getCurrentGroupNumber() <= 0;
    }

    protected double getRequiredKg() {
        return currentGroupNumber * foodRequiredKg;
    }

    @Override
    public boolean eat(Map<Class<? extends LifeForm>, Integer> diet,
                       Map<Class<? extends LifeForm>, Set<LifeForm>> inhabitants) {

        double[] requiredFood = {getRequiredKg()};

        diet.forEach((prayClass, chance) -> {
            if (requiredFood[0] <= 0) return;
            Set<LifeForm> praySet = inhabitants.get(prayClass);
            if (praySet == null || praySet.isEmpty()) return;

            Iterator<LifeForm> iterator = praySet.iterator();
            while (iterator.hasNext() && requiredFood[0] > 0) {
                if (!successfulHunt(chance)) continue;

                LifeForm pray = iterator.next();
                double prayWeight = pray.getTotalWeight();
                if (prayWeight <= requiredFood[0]) {
                    requiredFood[0] -= prayWeight;
                    totalWeight += prayWeight;
                    iterator.remove();
                } else {
                    totalWeight += requiredFood[0];
                    pray.updateTotalWeight(prayWeight - requiredFood[0]);
                    requiredFood[0] = 0;

                    if (pray.isDead()) {
                        iterator.remove();
                    }
                }
            }
        });
        return requiredFood[0] <= 0;
    }

    private static boolean successfulHunt(Integer chance) {
        int roll = ThreadLocalRandom.current().nextInt(100);
        return roll < chance;
    }


}
