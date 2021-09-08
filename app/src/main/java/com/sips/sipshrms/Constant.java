package com.sips.sipshrms;

public class Constant {

    static Constant constant;
    public static Constant getInstance(){
        if(constant == null){
            constant = new Constant();
        }
        return constant;
    }
    public static   String smas = "smas";
    public static    String stll = "stll";
    public  static String sips = "sips";
    public static  String login = "login";
    public static String type = "login";
}
