package com.javarush.island.aleinik.service;

import com.javarush.island.aleinik.config.Parameters;
import com.javarush.island.aleinik.config.Sector;
import com.javarush.island.aleinik.config.SpeciesConfig;
import com.javarush.island.aleinik.entity.island.Cell;
import com.javarush.island.aleinik.entity.island.Island;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;
import com.javarush.island.aleinik.entity.lifeforms.LifeFormFactory;
import com.javarush.island.aleinik.entity.lifeforms.animals.AnimalGroup;

import java.util.Map;
import java.util.Set;

import static java.lang.Math.floor;
import static java.lang.Math.min;

public class ReproducingService implements GameService {
    private static final double ANIMAL_REPRODUCTION_FACTOR = 1.5;
    private static final double PLANT_REPRODUCTION_FACTOR = 0.2;

    private static final double ANIMAL_NEW_GROUP_THRESHOLD = 0.25;
    private static final double PLANT_NEW_GROUP_THRESHOLD = 0.5;

    private final SpeciesConfig config;
    private final LifeFormFactory factory;

    public ReproducingService(SpeciesConfig config, LifeFormFactory factory) {
        this.config = config;
        this.factory = factory;
    }

    @Override
    public void performTask(Island island, Sector sector) {
        Cell[][] map = island.getIslandMap();

        for (int row = sector.startRow(); row < sector.endRow(); row++) {
            for (int col = 0; col < map[0].length; col++) {
                Cell cell = map[row][col];
                cell.getLock().lock();
                try {
                    Map<Class<? extends LifeForm>, Set<LifeForm>> inhabitants = cell.getInhabitants();
                    for (Map.Entry<Class<? extends LifeForm>, Set<LifeForm>> entry : inhabitants.entrySet()) {
                        Class<? extends LifeForm> type = entry.getKey();
                        Set<LifeForm> groups = entry.getValue();

                        if (AnimalGroup.class.isAssignableFrom(type)) {
                            reproduceAnimal(type, groups);
                        } else {
                            reproducePlants(type, groups);
                        }


                    }
                } finally {
                    cell.getLock().unlock();
                }
            }
        }
    }

    private void reproducePlants(Class<? extends LifeForm> aClass, Set<LifeForm> lifeForms) {

        Parameters parameters = config.getClassParameters(aClass);

        double weightPerPlant = parameters.getSpecieWeight();
        double maxWeightPerGroup = parameters.getMaxGroupSize() * weightPerPlant;
        double maxCellCapacity = parameters.getMaxPerCell() * weightPerPlant;
        double totalWeight = lifeForms.stream()
                .mapToDouble(LifeForm::getTotalWeight)
                .sum();

        double growth = PLANT_REPRODUCTION_FACTOR * totalWeight * (1 - totalWeight / maxCellCapacity);

        if (growth <= 0) return;

        double leftover = growth;
        for (LifeForm group : lifeForms) {
            if (leftover <= 0) break;

            double current = group.getTotalWeight();
            if (current < maxWeightPerGroup) {

                double free = maxWeightPerGroup - current;
                double take = Math.min(free, leftover);

                group.updateWeightAndCount(current + take);
                leftover -= take;
            }
        }
        createNewGroupIfPossible(aClass, lifeForms, leftover, maxWeightPerGroup, parameters.getMaxGroupsPerCell(), PLANT_NEW_GROUP_THRESHOLD);
    }


    private void reproduceAnimal(Class<? extends LifeForm> aClass, Set<LifeForm> lifeForms) {
        Parameters parameters = config.getClassParameters(aClass);
        double weightPerAnimal = parameters.getSpecieWeight();
        double maxWeightPerGroup = weightPerAnimal * parameters.getMaxGroupSize();

        double leftover = 0.0;
        for (LifeForm lifeForm : lifeForms) {
            leftover += processOneGroup(lifeForm, maxWeightPerGroup);
        }

        for (LifeForm group : lifeForms) {
            if (leftover <= 0) break;
            if (group.getTotalWeight() < maxWeightPerGroup) {
                leftover = allocateWeightToGroups(group, leftover, maxWeightPerGroup);
            }
        }
        double newbornWeight = leftover * ANIMAL_REPRODUCTION_FACTOR;
        createNewGroupIfPossible(aClass, lifeForms, newbornWeight, maxWeightPerGroup, parameters.getMaxGroupsPerCell(), ANIMAL_NEW_GROUP_THRESHOLD);

    }

    private double processOneGroup(LifeForm group, double maxWeightPerGroup) {

        double weight = group.getTotalWeight();
        if (weight <= maxWeightPerGroup) {
            group.updateWeightAndCount(weight);
            return 0.0;
        }

        group.updateWeightAndCount(maxWeightPerGroup);
        return weight - maxWeightPerGroup;
    }

    private double allocateWeightToGroups(LifeForm group, double leftover, double maxWeightPerGroup) {
        double groupWeight = group.getTotalWeight();
        double free = maxWeightPerGroup - groupWeight;
        double take = Math.min(free, leftover);
        group.updateWeightAndCount(groupWeight + take);

        return leftover - take;
    }

    private void createNewGroupIfPossible(Class<? extends LifeForm> aClass,
                                          Set<LifeForm> lifeForms,
                                          double leftover,
                                          double maxWeightPerGroup,
                                          int maxGroupPerCell,
                                          double newGroupThreshold) {

        while (leftover > maxWeightPerGroup * newGroupThreshold && lifeForms.size() < maxGroupPerCell) {
            double take = Math.min(maxWeightPerGroup, leftover);
            LifeForm newGroup = factory.createSingleGroup(aClass, take);
            if (newGroup != null) {
                leftover -= newGroup.getTotalWeight();
                lifeForms.add(newGroup);
            } else {
                break;
            }

        }

    }


}
