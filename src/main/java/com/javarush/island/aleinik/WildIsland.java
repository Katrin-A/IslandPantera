package com.javarush.island.aleinik;

import com.javarush.island.aleinik.application.Initializer;
import com.javarush.island.aleinik.entity.island.Island;
import com.javarush.island.aleinik.service.GameProcessor;
import com.javarush.island.aleinik.view.ConsoleView;
import com.javarush.island.aleinik.view.View;

public class WildIsland {
    public static void main(String[] args) {

        Initializer initializer = new Initializer();
        initializer.init();
        View view = new ConsoleView(initializer.getSpeciesConfig());

        GameProcessor processor = new GameProcessor(
                initializer.getIsland(),
                initializer.getSectors(),
                initializer.getSpeciesConfig(),
                initializer.getFactory(),
                view

        );
        processor.init();
        processor.start();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException ignored) {
        }

        processor.shutdown();

    }
}
