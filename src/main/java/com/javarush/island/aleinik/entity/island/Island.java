package com.javarush.island.aleinik.entity.island;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;
import com.javarush.island.aleinik.entity.lifeforms.LifeFormFactory;

import java.util.Set;

public class Island {
    private final Cell[][] island;
    //TODO we might not need this variable to be stored
    private LifeFormFactory factory;

    public Island(int width, int length, LifeFormFactory factory, Set<Class<? extends LifeForm>> species) {
        this.island =  new Cell[width][length];
        this.factory = factory;
        initializeIsland(species);
    }

    public Cell[][] getIsland() {
        return island;
    }

    private void initializeIsland(Set<Class<? extends LifeForm>> species)  {
        for (int row = 0; row < island.length; row++) {
            System.out.println("creating cells");
            for (int column = 0; column < island[0].length; column++) {
                island[row][column] = new Cell(row, column, factory, species);
            }
        }

    }
}
