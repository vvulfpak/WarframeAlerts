package com.sjonsen.application;

public class Util {

    
    public static String replaceWithWhitespace(String item, String... args){
        String temp = item;
        for(int i = 0; i < args.length; i++){
            temp = temp.replace(args[i], "");
        }
        return temp;
    }
    
}
