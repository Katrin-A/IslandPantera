package com.javarush.island.aleinik.interfaces;

import com.javarush.island.aleinik.entity.island.Cell;

@FunctionalInterface
public interface Reproducible {

    public boolean reproduce(Cell cell);
}
