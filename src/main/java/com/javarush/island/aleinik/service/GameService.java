package com.javarush.island.aleinik.service;

import com.javarush.island.aleinik.config.Sector;
import com.javarush.island.aleinik.entity.island.Island;

public interface GameService {

    void performTask(Island island, Sector sector);
}
