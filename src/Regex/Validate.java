package Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    public static final String PHONE_REGEX = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";
//    private static final String EMAIL_REGEX = "^[a-z][a-z0-9]{0,9}\\.[a-z0-9]{1,10}@[a-z]+\\.(com|vn)+$";
    private static final String EMAIL_REGEX1 = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public boolean validatePhone(String regex) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(regex);
        return matcher.matches();
    }

    public boolean validateEmail(String regex) {
//        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Pattern pattern1 = Pattern.compile(EMAIL_REGEX1);
        Matcher matcher = pattern1.matcher(regex);
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