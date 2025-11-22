package com.javarush.island.aleinik.entity.island;

import com.javarush.island.aleinik.entity.lifeforms.LifeForm;
import com.javarush.island.aleinik.entity.lifeforms.LifeFormFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cell {
    public final Lock lock = new ReentrantLock();
    private final LifeFormFactory factory;
    private Map<Class<? extends LifeForm>, Set<LifeForm>> inhabitants = new HashMap();
    private final int row;
    private final int column;


    //TODO: make inhabitants a class  + set should be a Linked or Tree set, sorted by group Quantity

    public Map<Class<? extends LifeForm>, Set<LifeForm>> getInhabitants() {
        return inhabitants;
    }

    public Lock getLock() {
        return lock;
    }

    public Cell(int row, int column, LifeFormFactory factory, Set<Class<? extends LifeForm>> classes) {
        this.row = row;
        this.column = column;
        this.factory = factory;
        initialize(classes);
    }



    private void initialize(Set<Class<? extends LifeForm>> speciesTypes) {
        for (Class<? extends LifeForm> specieType : speciesTypes) {
            Set<? extends LifeForm> lifeForms = factory.initializeSpecies(specieType);
            inhabitants.put(specieType, new HashSet<>(lifeForms));
        }
    }

}
