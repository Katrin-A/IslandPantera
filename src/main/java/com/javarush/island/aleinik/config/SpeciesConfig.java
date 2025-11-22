package com.javarush.island.aleinik.config;

import com.javarush.island.aleinik.annotations.SpeciesInfo;
import com.javarush.island.aleinik.entity.lifeforms.LifeForm;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpeciesConfig {
    private final Map<Class<? extends LifeForm>, Parameters> parameters;
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

    public Map<Class<? extends LifeForm>, Parameters> getAllParameters(){
        return parameters;
    }

    public void addSpec(List<Class<?>> lifeForms) {
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
}
