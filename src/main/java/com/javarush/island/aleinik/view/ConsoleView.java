package com.javarush.island.aleinik.view;

import com.javarush.island.aleinik.config.SpeciesConfig;
import com.javarush.island.aleinik.entity.island.Cell;
import com.javarush.island.aleinik.entity.island.Island;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;


import java.util.*;

public class ConsoleView extends View{

    private final SpeciesConfig config;

    public ConsoleView(SpeciesConfig config) {
        this.config = config;
    }
    @Override
    public void render(Island island, String title) {
        clearScreen();
        System.out.println("\n===== " + title + " =====\n");

        printMap(island);
        System.out.println();
        printStatistics(island);
        System.out.println();
        printLegend();
    }
    private void printMap(Island island) {
        Cell[][] map = island.getIslandMap();

        for (Cell[] row : map) {
            StringBuilder sb = new StringBuilder();

            for (Cell cell : row) {
                sb.append(renderCell(cell));
            }

            System.out.println(sb);
        }
    }

    private String renderCell(Cell cell) {
        Map<Class<? extends LifeForm>, Set<LifeForm>> inhabitants = cell.getInhabitants();

        if (inhabitants.isEmpty()) return " . ";

        // Находим самый многочисленный класс в клетке
        Class<? extends LifeForm> mainClass = null;
        int maxCount = 0;

        for (var entry : inhabitants.entrySet()) {
            int count = entry.getValue()
                    .stream()
                    .mapToInt(LifeForm::getCurrentGroupNumber)
                    .sum();

            if (count > maxCount) {
                maxCount = count;
                mainClass = entry.getKey();
            }
        }

        String icon = Icons.ICONS.getOrDefault(mainClass, "?");

        return " " + icon + " ";
    }
    private void printStatistics(Island island) {
        Map<Class<? extends LifeForm>, Integer> stats = new LinkedHashMap<>();

        for (Class<? extends LifeForm> type : config.getExitingLifeForms()) {
            stats.put(type, 0);
        }

        // Collect counts
        for (Cell[] row : island.getIslandMap()) {
            for (Cell cell : row) {
                Map<Class<? extends LifeForm>, Set<LifeForm>> inhabitants = cell.getInhabitants();

                inhabitants.forEach((clazz, groups) -> {
                    int total = groups.stream()
                            .mapToInt(LifeForm::getCurrentGroupNumber)
                            .sum();

                    stats.merge(clazz, total, Integer::sum);
                });
            }
        }
        System.out.println("=== Global Statistics ===");

        for (var entry : stats.entrySet()) {
            String icon = Icons.ICONS.getOrDefault(entry.getKey(), "?");
            System.out.printf("%s %-15s : %d\n",
                    icon,
                    entry.getKey().getSimpleName(),
                    entry.getValue()
            );
        }
    }

    private void printLegend() {
        System.out.println("\n=== Legend ===");

        Icons.ICONS.forEach((cls, icon) ->
                System.out.println(icon + " — " + cls.getSimpleName())
        );
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

