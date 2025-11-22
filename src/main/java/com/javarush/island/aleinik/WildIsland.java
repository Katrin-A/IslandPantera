package com.javarush.island.aleinik;

import com.javarush.island.aleinik.application.Initializer;
import com.javarush.island.aleinik.service.GameProcessor;

public class WildIsland {
    public static void main(String[] args) {

        //TODO: dont forget about how you're going to run the application

        //TODO: move all the method from gameRunner into the run method of the class
        Initializer initializer = new Initializer();
        initializer.init();

        GameProcessor processor = new GameProcessor(
                initializer.getIsland(),
                initializer.getSectors()
        );
        processor.init();
        processor.start();

    }
}
