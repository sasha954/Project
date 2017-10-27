package ua.nure.fomenko.final_project.constants;

import java.util.regex.Pattern;

/**
 * Created by fomenko on 25.08.2017.
 */
public final class Patterns {

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    public static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-zА-Яа-я0-9]{3,30}$");
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])([a-zA-Z0-9]{5,64})$");



}
