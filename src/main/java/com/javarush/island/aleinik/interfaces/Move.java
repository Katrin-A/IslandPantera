package com.javarush.island.aleinik.interfaces;

import com.javarush.island.aleinik.entity.island.Cell;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;

import java.util.Map;
import java.util.Set;

@FunctionalInterface
public interface Move {

    public boolean move(Map<Class<? extends LifeForm>, Set<LifeForm>> inhabitants);
}
