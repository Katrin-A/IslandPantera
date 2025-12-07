package com.javarush.island.aleinik.application;

import com.javarush.island.aleinik.config.Constants;
import com.javarush.island.aleinik.config.Sector;
import com.javarush.island.aleinik.config.SpeciesConfig;
import com.javarush.island.aleinik.entity.island.Island;
import com.javarush.island.aleinik.entity.lifeforms.LifeFormFactory;
import com.javarush.island.aleinik.utils.ClassScanner;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.javarush.island.aleinik.config.Constants.*;
import static java.lang.Math.min;

public class Initializer {

    @Getter
    private SpeciesConfig speciesConfig;

    @Getter
    private Island island;

    @Getter
    private List<Sector> sectors = new ArrayList<>();

    public LifeFormFactory getFactory() {
        return factory;
    }

    private LifeFormFactory factory;


    public Initializer() {
    }

    public void init() {
        setConfig();
        initializeFactory();
        createIsland();
        initializeSectors(island);

    }

    private void setConfig() {
        Set<Class<?>> classes = ClassScanner.getClasses(Constants.LIFE_FORMS_PACKAGE);
        speciesConfig = SpeciesConfig.getSpeciesConfig();
        speciesConfig.addSpec(classes);
        speciesConfig.loadFoodDiet();
    }

    public void createIsland() {
        island = new Island(ISLAND_WIDTH, ISLAND_LENGTH, factory, speciesConfig.getExitingLifeForms());
    }

    public void initializeFactory() {
        factory = new LifeFormFactory(speciesConfig);
    }

    public void initializeSectors(Island island) {
        int islandLength = island.getIslandMap().length;
        int step = islandLength / NUMBER_OF_THREADS;
        int start = 0;
        while (start < islandLength) {
            int end = min(start + step - 1, islandLength - 1);
            sectors.add(new Sector(start, end));
            start = end + 1;
        }
    }

}
