package com.javarush.island.aleinik.view;

import com.javarush.island.aleinik.entity.lifeforms.LifeForm;
import com.javarush.island.aleinik.entity.lifeforms.animals.*;
import com.javarush.island.aleinik.entity.lifeforms.plants.Grass;


import java.util.Map;

public class Icons {
    public static final Map<Class<? extends LifeForm>, String> ICONS = Map.ofEntries(
            Map.entry(Wolves.class, "🐺"),
            Map.entry(Foxes.class, "🦊"),
            Map.entry(Bears.class, "🐻"),
            Map.entry(Deer.class, "🦌"),
            Map.entry(Boars.class, "🐗"),
            Map.entry(Goats.class, "🐐"),
            Map.entry(Sheep.class, "🐑"),
            Map.entry(Rabbits.class, "🐇"),
            Map.entry(Mice.class, "🐁"),
            Map.entry(Ducks.class, "🦆"),
            Map.entry(Eagles.class, "🦅"),
            Map.entry(Boas.class, "🐍"),
            Map.entry(Caterpillars.class, "🐛"),
            Map.entry(Grass.class, "🌿")
    );
}

