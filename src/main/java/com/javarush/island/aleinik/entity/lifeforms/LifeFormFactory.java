package com.javarush.island.aleinik.entity.lifeforms;

import com.javarush.island.aleinik.config.Parameters;
import com.javarush.island.aleinik.config.SpeciesConfig;
import com.javarush.island.aleinik.entity.island.Cell;
import com.javarush.island.aleinik.entity.lifeforms.animals.AnimalGroup;
import com.javarush.island.aleinik.entity.lifeforms.plants.PlantGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.javarush.island.aleinik.config.Constants.INITIAL_ANIMAL_PERCENTAGE;
import static com.javarush.island.aleinik.config.Constants.INITIAL_PLANT_PERCENTAGE;

public class LifeFormFactory {
    //TODO create validation for class type
    //TODO create try-catch blocks instead of throwing it in the method signature

    private final SpeciesConfig speciesConfig;


    public LifeFormFactory(SpeciesConfig config) {
        speciesConfig = config;
    }

    public <T extends LifeForm> Set<T> initializeSpecies(Class<T> aClass) {
        Set<T> lifeFormSet = new HashSet<>();
        Parameters parameters = speciesConfig.getClassParameters(aClass);
        if (!LifeForm.class.isAssignableFrom(aClass)) {
            throw new IllegalArgumentException("Class must extend LifeForm");
        }

        List<Integer> initialQuantity = getInitialQuantity(aClass);
        for (Integer integer : initialQuantity) {
            try {
                // TODO: we have to check that this constructor has a required parameters
                T animalGroup = aClass.getDeclaredConstructor(int.class, int.class, double.class)
                        .newInstance(
                                integer,
                                parameters.getSpecieWeight(),
                                parameters.getFoodRequiredKg()
                        );
                lifeFormSet.add(animalGroup);

            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return lifeFormSet;
    }

    private <T extends LifeForm> List<Integer> getInitialQuantity(Class<T> aClass) {
        List<Integer> initialSpeciesPerGroup = new ArrayList<>();

        int maxPerCell = speciesConfig.getClassParameters(aClass).getMaxPerCell();
        int maxGroupSize = speciesConfig.getClassParameters(aClass).getMaxGroupSize();

        int initialSpeciePercentage;
        if (AnimalGroup.class.isAssignableFrom(aClass)) {
            initialSpeciePercentage = INITIAL_ANIMAL_PERCENTAGE;
        } else if (PlantGroup.class.isAssignableFrom(aClass)) {
            initialSpeciePercentage = INITIAL_PLANT_PERCENTAGE;
        } else {
            throw new IllegalArgumentException("Unknown LifeForm class: " + aClass.getName());
        }

        int initialTotalAnimals = Math.round(maxPerCell * initialSpeciePercentage / 100f);
        //TODO: maybe better do a math calculation instead of for loop, but then how do I form a List?
        while (initialTotalAnimals > 0) {
            if (initialTotalAnimals < maxGroupSize) {
                initialSpeciesPerGroup.add(initialTotalAnimals);
                break;
            }
            initialSpeciesPerGroup.add(maxGroupSize);

            initialTotalAnimals = initialTotalAnimals - maxGroupSize;
        }
        return initialSpeciesPerGroup;
    }
}
