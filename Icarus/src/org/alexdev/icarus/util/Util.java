package org.alexdev.icarus.util;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

import org.alexdev.icarus.pooling.ThreadPooling;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

public class Util {

    private static Wini configuration;
    private static SecureRandom secureRandom;
    private static Wini habboConfig;

    public static void load() throws InvalidFileFormatException, IOException {
        configuration = new Wini(new File("icarus.properties"));
        habboConfig =  new Wini(new File("habbohotel.properties"));
        secureRandom = new SecureRandom();
    }
    
    public boolean isNullOrEmpty(String param) { 
        return param == null || param.trim().length() == 0;
    }
    
    public static String filterInput(String input) {
        
        input = input.replace((char)10, ' ');
        input = input.replace((char)11, ' ');
        input = input.replace((char)12, ' ');
        input = input.replace((char)13, ' ');
        input = input.replace((char)14, ' ');
        return input;
    }
    
    public static Wini getConfiguration() {
        return configuration;
    }

    public static SecureRandom getRandom() {
        return secureRandom;
    }

    public static Wini getHabboConfig() {
        return habboConfig;
    }
}
