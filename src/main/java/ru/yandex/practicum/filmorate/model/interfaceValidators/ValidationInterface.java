package ru.yandex.practicum.filmorate.model.interfaceValidators;

public interface ValidationInterface<T> {

    boolean postValidation(T dtoObject);

    boolean putValidation(T dtoObject);

}
