package com.javarush.island.aleinik.service;

import com.javarush.island.aleinik.config.Sector;
import com.javarush.island.aleinik.config.SpeciesConfig;
import com.javarush.island.aleinik.entity.island.Cell;
import com.javarush.island.aleinik.entity.island.Island;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;
import com.javarush.island.aleinik.interfaces.Movable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MovingService implements GameService {
    private static final int[][] DIRECTIONS = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };
    private final SpeciesConfig config;

    public MovingService(SpeciesConfig config) {
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

                    for (Map.Entry<Class<? extends LifeForm>, Set<LifeForm>> entry : inhabitants.entrySet()) {
                        Class<? extends LifeForm> aClass = entry.getKey();
                        Set<LifeForm> lifeForms = entry.getValue();

                        Iterator<LifeForm> iterator = lifeForms.iterator();
                        while (iterator.hasNext()) {
                            LifeForm lifeForm = iterator.next();
                            if (!(lifeForm instanceof Movable)) {
                                continue;
                            }
                            int speed = config.getAllParameters().get(aClass).getSpeedLimit();
                            List<Cell> directions = getPossibleDirections(island, cell, speed);
                            int maxGroupsPerCell = config.getClassParameters(aClass).getMaxGroupsPerCell();
                            boolean moved = false;
                            for (int attempt = 0; attempt < 3 && !moved; attempt++) {
                                Cell target = getRandomDirection(directions);
                                moved = ((Movable) lifeForm).move(cell, target, maxGroupsPerCell);
                            }
                            if (moved) {
                                iterator.remove();
                            }
                        }
                    }
                } finally {
                    cell.getLock().unlock();
                }
            }
        }
    }

    private Cell getRandomDirection(List<Cell> possibleDirections) {
        int roll = ThreadLocalRandom.current().nextInt(possibleDirections.size());
        return possibleDirections.get(roll);
    }

    private List<Cell> getPossibleDirections(Island island, Cell currentLocation, int speedLimit) {
        List<Cell> directions = new ArrayList<>();

        Cell[][] map = island.getIslandMap();
        int rows = map.length;
        int cols = map[0].length;

        int startRow = currentLocation.getRow();
        int startCol = currentLocation.getColumn();
        for (int[] d : DIRECTIONS) {
            for (int step = 1; step <= speedLimit; step++) {
                int newRow = (startRow + d[0] * step + rows) % rows;
                int newCol = (startCol + d[1] * step + cols) % cols;
                directions.add(map[newRow][newCol]);
            }
        }
        return directions;
    }

}
