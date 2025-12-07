package com.javarush.island.aleinik.service;

import com.javarush.island.aleinik.config.Parameters;
import com.javarush.island.aleinik.config.Sector;
import com.javarush.island.aleinik.config.SpeciesConfig;
import com.javarush.island.aleinik.entity.island.Cell;
import com.javarush.island.aleinik.entity.island.Island;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;
import com.javarush.island.aleinik.entity.lifeforms.LifeFormFactory;
import com.javarush.island.aleinik.entity.lifeforms.animals.AnimalGroup;
import com.javarush.island.aleinik.entity.lifeforms.plants.PlantGroup;

import java.util.Map;
import java.util.Set;

public class ReproducingService implements GameService {
    private static final double ANIMAL_REPRODUCTION_FACTOR = 2.0;
    private static final double PLANT_REPRODUCTION_FACTOR = 2.0;

    private static final double ANIMAL_NEW_GROUP_THRESHOLD = 0.25;

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
                        } else if (PlantGroup.class.isAssignableFrom(type)) {
                            reproducePlants(type, groups);
                        }
                    }

                } finally {
                    cell.getLock().unlock();
                }
            }
        }
    }

    private void reproducePlants(Class<? extends LifeForm> aClass, Set<LifeForm> groups) {
        Parameters params = config.getClassParameters(aClass);

        double weightPerPlant = params.getSpecieWeight();
        double maxGroupWeight = params.getMaxGroupSize() * weightPerPlant;
        double maxCellCapacity = params.getMaxPerCell() * weightPerPlant;

        double totalWeight = groups.stream()
                .mapToDouble(LifeForm::getTotalWeight)
                .sum();

        double free = maxCellCapacity - totalWeight;
        if (free <= 0) return;

        double growth = PLANT_REPRODUCTION_FACTOR * free;
        if (growth <= 0) return;

        double leftover = growth;

        for (LifeForm group : groups) {
            if (leftover <= 0) break;

            double current = group.getTotalWeight();
            double freeInGroup = maxGroupWeight - current;

            if (freeInGroup > 0) {
                double take = Math.min(freeInGroup, leftover);
                group.updateWeightAndCount(current + take);
                leftover -= take;
            }
        }
    }




    private void reproduceAnimal(Class<? extends LifeForm> aClass, Set<LifeForm> lifeForms) {
        Parameters params = config.getClassParameters(aClass);

        double weightPerAnimal = params.getSpecieWeight();
        double maxWeightPerGroup = weightPerAnimal * params.getMaxGroupSize();

        double leftover = 0;

        for (LifeForm group : lifeForms) {
            leftover += processOneGroup(group, maxWeightPerGroup);
        }

        for (LifeForm group : lifeForms) {
            if (leftover <= 0) break;

            if (group.getTotalWeight() < maxWeightPerGroup) {
                leftover = allocateWeightToGroups(group, leftover, maxWeightPerGroup);
            }
        }
        double newbornWeight = leftover * ANIMAL_REPRODUCTION_FACTOR;

        createNewGroupIfPossible(
                aClass,
                lifeForms,
                newbornWeight,
                maxWeightPerGroup,
                params.getMaxGroupsPerCell(),
                ANIMAL_NEW_GROUP_THRESHOLD
        );
    }


    private double processOneGroup(LifeForm group, double maxWeightPerGroup) {
        double weight = group.getTotalWeight();

        if (weight <= maxWeightPerGroup) {
            group.updateWeightAndCount(weight);
            return 0;
        }

        group.updateWeightAndCount(maxWeightPerGroup);
        return weight - maxWeightPerGroup;
    }

    private double allocateWeightToGroups(LifeForm group, double leftover, double maxWeightPerGroup) {
        double current = group.getTotalWeight();
        double free = maxWeightPerGroup - current;

        double take = Math.min(free, leftover);
        group.updateWeightAndCount(current + take);

        return leftover - take;
    }

    private void createNewGroupIfPossible(
            Class<? extends LifeForm> aClass,
            Set<LifeForm> lifeForms,
            double leftover,
            double maxWeightPerGroup,
            int maxGroupPerCell,
            double threshold
    ) {
        while (leftover > threshold * maxWeightPerGroup && lifeForms.size() < maxGroupPerCell) {

            double take = Math.min(leftover, maxWeightPerGroup);

            LifeForm newGroup = factory.createSingleGroup(aClass, take);
            if (newGroup == null) break;

            lifeForms.add(newGroup);
            leftover -= newGroup.getTotalWeight();
        }
    }
}

