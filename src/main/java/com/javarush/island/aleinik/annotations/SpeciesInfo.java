package com.javarush.island.aleinik.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited()
public @interface SpeciesInfo {
    int specieWeight();

    int maxPerCell();

    int speedLimit() default 0;

    double foodRequiredKg() default 0.0;

    int maxGroupSize() default 1;
}
