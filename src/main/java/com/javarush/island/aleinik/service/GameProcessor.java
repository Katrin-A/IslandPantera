package com.javarush.island.aleinik.service;
import com.javarush.island.aleinik.config.Sector;
import com.javarush.island.aleinik.entity.island.Island;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import static com.javarush.island.aleinik.config.Constants.NUMBER_OF_THREADS;

public class GameProcessor {
    List<GameService> services = new ArrayList<>();
    ExecutorService executorService;
    ScheduledExecutorService gameLoopScheduler;


    private final List<Sector> sectors;
    private final Island island;

    public GameProcessor(Island island, List<Sector> sectors) {
        this.island = island;
        this.sectors = sectors;
    }

    public void init(){
        services.add(new EatingService());
        services.add(new MovingService());
        executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        gameLoopScheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public void start(){
            gameLoopScheduler.scheduleAtFixedRate(() -> {
                tick();

            }, 1, 1, TimeUnit.SECONDS);
    }
    private void tick(){
        for (GameService service : services) {
            parallelInvoke(service);
        }
    }

    private void parallelInvoke(GameService service){
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



}
