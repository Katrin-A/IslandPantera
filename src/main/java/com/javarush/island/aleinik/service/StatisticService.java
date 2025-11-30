package com.javarush.island.aleinik.service;

import com.javarush.island.aleinik.config.Sector;
import com.javarush.island.aleinik.config.SpeciesConfig;
import com.javarush.island.aleinik.entity.island.Cell;
import com.javarush.island.aleinik.entity.island.Island;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class StatisticService implements GameService {


    @Override
    public void performTask(Island island, Sector sector) {

    }

    public void printGlobalStatistics(SpeciesConfig config, Island island, String serviceName) {
        System.out.println("\n--- STATISTICS AFTER " + serviceName + " ---");
        Map<Class<? extends LifeForm>, Integer> stats = getStats(config, island);
        printTable(stats);
    }

    private Map<Class<? extends LifeForm>, Integer> getStats(SpeciesConfig config, Island island) {
        Map<Class<? extends LifeForm>, Integer> stats = new HashMap<>();
        for (Class<? extends LifeForm> exitingLifeForm : config.getExitingLifeForms()) {
            stats.put(exitingLifeForm, 0);
        }

        Cell[][] currentIsland = island.getIslandMap();
        for (Class<? extends LifeForm> exitingLifeForm : config.getExitingLifeForms()) {
            stats.put(exitingLifeForm, 0);
        }

        for (Cell[] cells : currentIsland) {
            for (Cell cell : cells) {
                try {
                    cell.getLock().lock();
                    Map<Class<? extends LifeForm>, Set<LifeForm>> inhabitants = cell.getInhabitants();
                    inhabitants.forEach((aClass, lifeForms) -> {
                        int totalInCell = lifeForms.stream()
                                .mapToInt(LifeForm::getCurrentGroupNumber)
                                .sum();

                        stats.merge(aClass, totalInCell, Integer::sum);

                    });

                } finally {
                    cell.getLock().unlock();
                }
            }

        }
        return stats;
    }

    //TODO: move this code to the ConsoleView
    private void printTable(Map<Class<? extends LifeForm>, Integer> stats) {
        Map<String, Integer> printable = new LinkedHashMap<>();
        stats.forEach((cls, count) -> printable.put(cls.getSimpleName(), count));

        int nameWidth = printable.keySet().stream()
                .mapToInt(String::length)
                .max()
                .orElse(10);
        int valueWidth = printable.values().stream()
                .mapToInt(v -> String.valueOf(v).length())
                .max()
                .orElse(5);
        String separator = "┌" +
                "─".repeat(nameWidth + 2) + "┬" +
                "─".repeat(valueWidth + 2) + "┐";

        String middleSeparator = "├" +
                "─".repeat(nameWidth + 2) + "┼" +
                "─".repeat(valueWidth + 2) + "┤";
        String footer = "└" +
                "─".repeat(nameWidth + 2) + "┴" +
                "─".repeat(valueWidth + 2) + "┘";
        System.out.println(separator);
        System.out.printf("│ %-" + nameWidth + "s │ %" + valueWidth + "s │\n",
                "Species", "Count");

        System.out.println(middleSeparator);
        printable.forEach((name, count) -> {
            System.out.printf("│ %-" + nameWidth + "s │ %" + valueWidth + "d │\n",
                    name, count);
        });

        System.out.println(footer);
    }

}
