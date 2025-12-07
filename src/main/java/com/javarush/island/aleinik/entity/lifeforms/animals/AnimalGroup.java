package com.javarush.island.aleinik.entity.lifeforms.animals;

import com.javarush.island.aleinik.entity.island.Cell;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;
import com.javarush.island.aleinik.interfaces.Eatable;
import com.javarush.island.aleinik.interfaces.Movable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AnimalGroup extends LifeForm implements Eatable, Movable {
    protected final double foodRequiredKg;

    protected AnimalGroup(int currentGroupNumber, int specieWeight, double foodRequiredKg) {
        super(currentGroupNumber, specieWeight);
        this.foodRequiredKg = foodRequiredKg;
    }

    @Override
    public boolean move(Cell current, Cell target, int maxGroupsPerCell) {
        if (!target.getLock().tryLock()) {
            return false;
        }
        try {
            Set<LifeForm> targetSet = target.getInhabitants().computeIfAbsent(this.getClass(), k -> new HashSet<>());
            if (targetSet.size() >= maxGroupsPerCell) {
                return false;
            }
            targetSet.add(this);
            return true;

        } finally {
            target.getLock().unlock();
        }
    }

    protected double getRequiredKg() {
        return currentGroupNumber * foodRequiredKg;
    }

    @Override
    public Set<LifeForm> eat(Map<Class<? extends LifeForm>, Integer> diet,
                             Map<Class<? extends LifeForm>, Set<LifeForm>> inhabitants) {

        Set<LifeForm> deadPrey = new HashSet<>();
        double requiredFood = getRequiredKg(); // сколько нужно съесть

        for (Map.Entry<Class<? extends LifeForm>, Integer> entry : diet.entrySet()) {
            Class<? extends LifeForm> preyClass = entry.getKey();
            int chance = entry.getValue();

            if (requiredFood <= 0 || this.isDead())
                break;

            Set<LifeForm> preySet = inhabitants.get(preyClass);
            if (preySet == null || preySet.isEmpty())
                continue;

            Iterator<LifeForm> iterator = preySet.iterator();

            while (iterator.hasNext() && requiredFood > 0 && !this.isDead()) {

                LifeForm prey = iterator.next();
                if (!successfulHunt(chance)) {
                    this.loseEnergy(foodRequiredKg);
                    requiredFood -= foodRequiredKg;
                    if (requiredFood < 0) requiredFood = 0;
                    continue;
                }

                double preyWeight = prey.getTotalWeight();

                if (preyWeight > requiredFood) {
                    prey.updateWeightAndCount(preyWeight - requiredFood);
                    this.totalWeight += requiredFood;
                    requiredFood = 0;

                    if (prey.isDead()) {
                        deadPrey.add(prey);
                    }

                } else {
                    this.totalWeight += preyWeight;
                    requiredFood -= preyWeight;
                    prey.updateWeightAndCount(0);

                    if (prey.isDead()) {
                        deadPrey.add(prey);
                    }
                }
            }
        }

        return deadPrey;
    }


    private static boolean successfulHunt(Integer chance) {
        int roll = ThreadLocalRandom.current().nextInt(100);
        return roll < chance;
    }


}
