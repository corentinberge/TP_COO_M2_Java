package com.comp;

import java.util.ArrayList;
import java.util.List;

public class Component {

    //Attributes
    protected static String name;
    protected static int[] S;
    protected static int current;
    protected static double ta;
    protected static double e;
    protected static double tr;
    private static double tl;
    private static double tn;

    //Functions
    public Component() {}

    public static void set_e(double E){
        if(E >= ta){
            e = E-ta;
        }
        e = E;
    }

    public static void set_tr(){
        tr = ta - e;
    }

    public static String get_name(){
        return name;
    }
}
