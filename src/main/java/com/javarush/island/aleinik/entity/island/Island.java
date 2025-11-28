package com.javarush.island.aleinik.entity.island;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;
import com.javarush.island.aleinik.entity.lifeforms.LifeFormFactory;

import java.util.Set;

import static java.util.Arrays.copyOfRange;

public class Island {
    private final Cell[][] island;
    private LifeFormFactory factory;

    public Island(int width, int length, LifeFormFactory factory, Set<Class<? extends LifeForm>> species) {
        this.island =  new Cell[width][length];
        this.factory = factory;
        initializeIsland(species);
    }


    private void initializeIsland(Set<Class<? extends LifeForm>> species)  {
        for (int row = 0; row < island.length; row++) {
            for (int column = 0; column < island[0].length; column++) {
                island[row][column] = new Cell(row, column, factory, species);
            }
        }

    }

    public Cell[][] getIslandMap() {
        return island;
    }
}
