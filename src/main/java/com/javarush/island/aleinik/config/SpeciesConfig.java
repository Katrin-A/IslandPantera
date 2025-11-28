package com.javarush.island.aleinik.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.island.aleinik.annotations.SpeciesInfo;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

import static com.javarush.island.aleinik.config.Constants.DIET_MAP_PATH;

public class SpeciesConfig {
    private final Map<Class<? extends LifeForm>, Parameters> parameters;

    public Set<Class<? extends LifeForm>> getLifeFormsCollection() {
        return parameters.keySet();
    }

    private final Map<Class<?>, Map<Class<?>, Integer>> dietMap = new HashMap<>();
    private static SpeciesConfig config;

    private SpeciesConfig() {
        parameters = new HashMap<>();
    }


    public static SpeciesConfig getSpeciesConfig() {
        if (config == null) {
            config = new SpeciesConfig();
        }
        return config;
    }

    public Parameters getClassParameters(Class<? extends LifeForm> aClass) {
        return parameters.get(aClass);
    }

    public Map<Class<? extends LifeForm>, Parameters> getAllParameters() {
        return parameters;
    }

    public void addSpec(Set<Class<?>> lifeForms) {
        for (Class<?> aClass : lifeForms) {
            if (aClass == null
                    || !LifeForm.class.isAssignableFrom(aClass)
                    || Modifier.isAbstract(aClass.getModifiers())) {
                continue;
            }
            SpeciesInfo info = aClass.getAnnotation(SpeciesInfo.class);
            if (info != null) {

                Parameters p = new Parameters(
                        info.specieWeight(),
                        info.maxPerCell(),
                        info.speedLimit(),
                        info.foodRequiredKg(),
                        info.maxGroupSize()
                );
                //TODO: remove sout
                System.out.println("Adding class " + aClass.getSimpleName());

                parameters.put((Class<? extends LifeForm>) aClass, p);

            }
        }
    }

    public void loadFoodDiet() {
        Map<String, Map<String, Integer>> raw = loadJsonFile();
        Map<String, Class<?>> nameToClass = getNameToClassMap();

        raw.forEach((predatorName, preyMapRaw) -> {
            Class<?> predatorClass = nameToClass.get(predatorName);
            if (predatorClass == null) {
                return;
            }
            Map<Class<?>, Integer> preyMap =
                    preyMapRaw.entrySet().stream()
                            .map(entry -> Map.entry(nameToClass.get(entry.getKey()), entry.getValue()))
                            .filter(entry -> entry.getKey() != null)
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    Map.Entry::getValue
                            ));
            if (!preyMap.isEmpty()) {
                dietMap.put(predatorClass, preyMap);
            }

        });
    }

    private Map<String, Class<?>> getNameToClassMap() {
        return getLifeFormsCollection()
                .stream()
                .collect(Collectors.toMap(
                        Class::getSimpleName,
                        c -> c
                ));
    }

    private Map<String, Map<String, Integer>> loadJsonFile() {
        Map<String, Map<String, Integer>> raw;
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(DIET_MAP_PATH);
            if (!file.exists()) {
                throw new RuntimeException("Diet file not found at: " + file.getAbsolutePath());
            }
            TypeReference<Map<String, Map<String, Integer>>> typeReference = new TypeReference<>() {
            };
            raw = mapper.readValue(file, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return raw;
    }

    public Map<Class<? extends LifeForm>, Integer> getDiet(Class<?> aClass) {
        return (Map<Class<? extends LifeForm>, Integer>) (Map<?, ?>) dietMap.get(aClass);
    }

    public Set<Class <? extends LifeForm>> getExitingLifeForms(){
        return parameters.keySet();
    }
}
