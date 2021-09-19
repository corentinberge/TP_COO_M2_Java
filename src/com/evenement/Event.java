package com.evenement;

import java.util.List;

public class Event {

    //Attributes
    private static List name;
    private static List value;

    //Functions
    public Event() {}

    public static void add(String n,Boolean v){
        name.add(n);
        value.add(v);
    }

    public static void set(String n,Boolean v){
        for(int i = 0;i<name.size();i++){
            if(n.equals(name.get(i))){
                value.set(i,v);
            }
        }
    }

    public static Boolean get(String n){
        for(int i = 0;i<name.size();i++){
            if(n.equals(name.get(i))){
                return (Boolean) value.get(i);
            }
        }
        return null;
    }

    public static int size(){
        return name.size();
    }
}
