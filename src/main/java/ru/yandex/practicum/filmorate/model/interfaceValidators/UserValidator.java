package ru.yandex.practicum.filmorate.model.interfaceValidators;

import ru.yandex.practicum.filmorate.model.User;

public class UserValidator implements ValidationInterface<User> {

    @Override
    public boolean postValidation(User newUser) {
        return true;
    }

    @Override
    public boolean putValidation(User updatedUser) {
        return true;
    }

}
