package com.tamarbunturi.trello.config;

public class ConfigReader {

    private ConfigReader(){
    }
    private static String getRequiredEnv(String name){
        String value = System.getenv(name);

        if(value == null || value.isBlank()){
            throw new IllegalStateException(
                    "Missing required Environment: " + name);
        }
        return value;
    }
    public static String getApiKey(){
        return getRequiredEnv("TRELLO_KEY");
    }
    public static String getToken(){
        return getRequiredEnv("TRELLO_TOKEN");
    }
}
