package com.javarush.island.aleinik.interfaces;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;

import java.util.Map;
import java.util.Set;

@FunctionalInterface
public interface Eatable {

    public Set<LifeForm> eat(Map<Class<? extends LifeForm>, Integer> diet, Map<Class<? extends LifeForm>, Set<LifeForm>> inhabitants);
}
