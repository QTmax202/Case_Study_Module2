package Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    public static final String PHONE_REGEX = "^[(](\\+84)[)][-][0-9]{9,}$";
    private static final String EMAIL_REGEX = "^[a-z][a-z0-9]{0,9}\\.[a-z0-9]{1,10}@[a-z]+\\.(com|vn)+$";

    public boolean validatePhone(String regex) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(regex);
        return matcher.matches();
    }

    public boolean validateEmail(String regex) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(regex);
        return matcher.matches();
    }

    public boolean validateSearch(String keyword, String regex) {
        keyword = keyword.toLowerCase();
        String PHONE_NAME_REGEX = ".*" + keyword + ".*";
        Pattern pattern = Pattern.compile(PHONE_NAME_REGEX);
        Matcher matcher = pattern.matcher(regex.toLowerCase());
        return matcher.matches();
    }
}