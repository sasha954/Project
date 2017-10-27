package ua.nure.fomenko.final_project.util;

import org.apache.log4j.Logger;
import ua.nure.fomenko.final_project.constants.MessageKeys;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.constants.Patterns;
import ua.nure.fomenko.final_project.db.entity.User;
import ua.nure.fomenko.final_project.db.entity.dto.UserDto;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by fomenko on 15.09.2017.
 */
public class ValidateUtil {
    private static final Logger LOGGER = Logger.getLogger(ValidateUtil.class);

    public boolean isValidateByRegex(String value, Pattern pattern) {
        if(value != null) {
            return pattern.matcher(value).find();
        } else {
            return false;
        }
    }

    public Map<String, String> validateRegistration(UserDto userDto) {
        Map<String, String> errors = new HashMap<>();

        if(!isValidateByRegex(userDto.getEmail(), Patterns.EMAIL_PATTERN)) {
            errors.put(Params.USER_EMAIL, MessageKeys.INVALID_EMAIL);
        }

        if(!isValidateByRegex(userDto.getPassword(), Patterns.PASSWORD_PATTERN)) {
            errors.put(Params.USER_PASSWORD, MessageKeys.INVALID_PASSWORD);
        } else {
            if(!userDto.getPassword().equals(userDto.getRepeatPassword())) {
                errors.put(Params.USER_REPEAT_PASSWORD, MessageKeys.NOT_EQUALS_PASSWORD);
            }
        }

        if(!isValidateByRegex(userDto.getFirstName(), Patterns.NAME_PATTERN)){
            errors.put(Params.USER_FIRST_NAME, MessageKeys.INVALID_FIRST_NAME);
        }

        if(!isValidateByRegex(userDto.getLastName(), Patterns.NAME_PATTERN)){
            errors.put(Params.USER_LAST_NAME, MessageKeys.INVALID_LAST_NAME);
        }

        if(!isValidateByRegex(userDto.getMiddleName(), Patterns.NAME_PATTERN)){
            errors.put(Params.USER_MIDDLE_NAME, MessageKeys.INVALID_MIDDLE_NAME);
        }

        return errors;
    }

    public Map<String, String> validateAuthorization(User user) {
        Map<String, String> errors = new HashMap<>();
        if(!isValidateByRegex(user.getEmail(), Patterns.EMAIL_PATTERN)) {
            errors.put(Params.USER_EMAIL, MessageKeys.INVALID_EMAIL);
        }

        if(!isValidateByRegex(user.getPassword(), Patterns.PASSWORD_PATTERN)) {
            errors.put(Params.USER_PASSWORD, MessageKeys.INVALID_PASSWORD);
        }

        return errors;
    }
}
