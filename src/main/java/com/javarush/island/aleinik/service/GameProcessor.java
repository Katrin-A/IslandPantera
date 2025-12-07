package com.javarush.island.aleinik.service;

import com.javarush.island.aleinik.config.Sector;
import com.javarush.island.aleinik.config.SpeciesConfig;
import com.javarush.island.aleinik.entity.island.Island;
import com.javarush.island.aleinik.entity.lifeforms.LifeFormFactory;
import com.javarush.island.aleinik.view.View;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static com.javarush.island.aleinik.config.Constants.NUMBER_OF_THREADS;

public class GameProcessor {
    private List<GameService> services = new ArrayList<>();
    private ExecutorService executorService;
    private ScheduledExecutorService gameLoopScheduler;
    private StatisticService statisticsService;
    private View view;


    private final SpeciesConfig config;
    private final List<Sector> sectors;
    private final Island island;
    private final LifeFormFactory factory;
    private volatile boolean isRunning = true;


    public GameProcessor(Island island, List<Sector> sectors, SpeciesConfig config, LifeFormFactory factory, View view) {
        this.island = island;
        this.sectors = sectors;
        this.config = config;
        this.factory = factory;
        this.view = view;
    }

    public void init() {
        services.add(new EatingService(config));
        services.add(new ReproducingService(config, factory));
        services.add(new MovingService(config));
        statisticsService = new StatisticService();

        executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        gameLoopScheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        gameLoopScheduler.scheduleWithFixedDelay(this::tick, 1, 1, TimeUnit.SECONDS);
    }

    private void tick() {
        if (!isRunning) {
            shutdown();
            return;

        }
        for (GameService service : services) {
            view.render(island, "Before " + service.getClass().getSimpleName());
            parallelInvoke(service);
            view.render(island, "After " + service.getClass().getSimpleName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void parallelInvoke(GameService service) {
        List<Callable<Void>> tasks = new ArrayList<>();
        for (Sector sector : sectors) {
            tasks.add(() -> {
                service.performTask(island, sector);
                return null;
            });
        }

        try {
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdown() {
        isRunning = false;
        gameLoopScheduler.shutdown();
        executorService.shutdown();

        try {
            gameLoopScheduler.awaitTermination(2, TimeUnit.SECONDS);
            executorService.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
        }
    }


}
