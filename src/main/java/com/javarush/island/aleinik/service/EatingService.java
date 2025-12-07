package com.javarush.island.aleinik.service;

import com.javarush.island.aleinik.config.Sector;
import com.javarush.island.aleinik.config.SpeciesConfig;
import com.javarush.island.aleinik.entity.island.Cell;
import com.javarush.island.aleinik.entity.island.Island;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;
import com.javarush.island.aleinik.interfaces.Eatable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EatingService implements GameService {
    private final SpeciesConfig config;

    public EatingService(SpeciesConfig config) {
        this.config = config;
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
                    Set<Class<? extends LifeForm>> emptyKeys = new HashSet<>();
                    for (Map.Entry<Class<? extends LifeForm>, Set<LifeForm>> entry : inhabitants.entrySet()) {
                        Class<? extends LifeForm> aClass = entry.getKey();
                        Set<LifeForm> lifeForms = entry.getValue();

                        Iterator<LifeForm> iterator = lifeForms.iterator();
                        double requiredFood = config.getClassParameters(aClass).getFoodRequiredKg();

                        while (iterator.hasNext()) {
                            LifeForm lifeForm = iterator.next();
                            if (lifeForm instanceof Eatable predator) {
                                Map<Class<? extends LifeForm>, Integer> diet = config.getDiet(aClass);
                                if (diet == null || diet.isEmpty()) {
                                    break;
                                }
                                Map<Class<? extends LifeForm>, Integer> availableDiet = getAvailableDiet(diet, inhabitants);
                                if (availableDiet == null || availableDiet.isEmpty()) {
                                    lifeForm.loseEnergy(requiredFood);
                                    break;
                                }
                                Set<LifeForm> deadPrey = predator.eat(availableDiet, inhabitants);

                                deadPrey.forEach(dead -> {
                                    Set<LifeForm> preySet = inhabitants.get(dead.getClass());
                                    if (preySet != null) {
                                        preySet.remove(dead);
                                    }
                                });

                                if (lifeForm.isDead()) {
                                    iterator.remove();
                                }
                            }
                        }
                        if (lifeForms.isEmpty()) {
                            emptyKeys.add(aClass);
                        }
                    }
                    emptyKeys.forEach(inhabitants::remove);
                } finally {
                    cell.getLock().unlock();
                }

            }
        }

    }

    private static Map<Class<? extends LifeForm>, Integer> getAvailableDiet(Map<Class<? extends LifeForm>, Integer> diet, Map<Class<? extends LifeForm>, Set<LifeForm>> inhabitants) {
        return diet.entrySet()
                .stream()
                .filter(entry -> inhabitants.containsKey(entry.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }
}
