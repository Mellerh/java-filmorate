package ru.yandex.practicum.filmorate.validation.interfaceValidators;

public interface ValidationInterface<T> {

    boolean postValidation(T dtoObject);

    boolean putValidation(T dtoObject);

}
