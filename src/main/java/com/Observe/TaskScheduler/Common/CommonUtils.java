package com.Observe.TaskScheduler.Common;




public class CommonUtils {

    public static boolean isNumeric(String num){
        try{
            Integer x = Integer.valueOf(num);
        } catch(NumberFormatException nume){
            return false;
        }
        return true;
    }

    public static boolean isOneTimeExecution(String type){
        return type.equalsIgnoreCase("a");
    }
}
