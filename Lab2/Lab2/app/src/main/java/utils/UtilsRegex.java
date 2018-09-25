package utils;

import android.widget.EditText;

import java.util.regex.Pattern;

public class UtilsRegex {
    /**
     * Classe qui permet de saisir les REGEX
     */
    public static String NOM = "^[a-zA-Z]{1}[a-z]*";
    public static String AGE = "^[1-9]{1}[0-9]{1}";
    public static String NUMERO = "^[1-9][0-9]{0,4}";
    public static String CODEPOSTAL = "[a-zA-Z]{1}[1-9]{1}[a-zA-Z]{1}[-]?[0-9]{1}[a-zA-Z]{1}[0-9]{1}";
    public static String RUE = "[a-zA-Z]{2,}[-| ]?[a-zA-Z]*";
    public static String EMAIL = "^[a-zA-Z]{1}[a-zA-Z0-9]*[@]{1}[a-z0-9]{2,}[.]{1}[a-zA-Z]{2,4}";
}
