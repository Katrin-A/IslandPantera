package com.javarush.island.aleinik.entity.island;

import com.javarush.island.aleinik.entity.lifeforms.LifeForm;
import com.javarush.island.aleinik.entity.lifeforms.LifeFormFactory;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cell {
    @Getter
    public final Lock lock = new ReentrantLock();
    private final LifeFormFactory factory;
    @Getter
    private final Map<Class<? extends LifeForm>, Set<LifeForm>> inhabitants = new HashMap<>();

    @Getter
    private final int row;
    @Getter
    private final int column;

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
