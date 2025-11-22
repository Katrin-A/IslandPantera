package com.javarush.island.aleinik.application;

import com.javarush.island.aleinik.config.Constants;
import com.javarush.island.aleinik.config.Sector;
import com.javarush.island.aleinik.config.SpeciesConfig;
import com.javarush.island.aleinik.entity.island.Island;
import com.javarush.island.aleinik.entity.lifeforms.LifeFormFactory;
import com.javarush.island.aleinik.utils.ClassScanner;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;

import static com.javarush.island.aleinik.config.Constants.*;
import static java.lang.Math.min;

public class Initializer {

    private SpeciesConfig speciesConfig;

    public Island getIsland() {
        return island;
    }

    private Island island;
    private LifeFormFactory factory;

    public List<Sector> getSectors() {
        return sectors;
    }

    private List<Sector> sectors = new ArrayList<>();

    //TODO: use view to set statistics

//    private final View view;

//    public GameRunner(View view) {
//        this.view = view;
//    }

    public Initializer() {
    }

    public void init() {
        setConfig();
        initializeFactory();
        createIsland();
        initializeSectors(island);

    }

    public Result run() {

        //TODO: get View , get configuration
        // Create map and start simulation
        // Print result every second
        return null;
    }

    private void setConfig() {
        //TODO: remove sout
        System.out.println("Setting up Config");
        List<Class<?>> classes = ClassScanner.getClasses(Constants.LIFE_FORMS_PACKAGE);
        speciesConfig = SpeciesConfig.getSpeciesConfig();
        speciesConfig.addSpec(classes);

    }

    public void createIsland() {
        island = new Island(ISLAND_WIDTH, ISLAND_LENGTH, factory, speciesConfig.getAllParameters().keySet());
    }

    public void initializeFactory() {
        factory = new LifeFormFactory(speciesConfig);
    }

    public void initializeSectors(Island island) {
        int islandLength = island.getIsland().length;
        int step = islandLength / NUMBER_OF_THREADS;
        int start = 0;
        while (start < islandLength) {
            int end = min(start + step - 1, islandLength - 1);
            sectors.add(new Sector(start, end));
            start = end + 1;
        }
    }
}
