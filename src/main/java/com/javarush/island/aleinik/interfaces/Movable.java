package com.javarush.island.aleinik.interfaces;
import com.javarush.island.aleinik.entity.island.Cell;

@FunctionalInterface
public interface Movable {

    boolean move(Cell start, Cell target, int maxGroupCountPerCell);
}
