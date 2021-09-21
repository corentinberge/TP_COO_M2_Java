package com.evenement;

import java.util.ArrayList;
import java.util.List;

public class Event {

    //Attributes
    private List<String> name = new ArrayList<String>();
    private List<Boolean> value = new ArrayList<Boolean>();

    //Functions
    public Event() {}

    public void add(String n,Boolean v){
        name.add(n);
        value.add(v);
    }

    public void set(String n,Boolean v){
        for(int i = 0;i<name.size();i++){
            if(n.equals(name.get(i))){
                value.set(i,v);
            }
        }
    }

    public Boolean get(String n){
        for(int i = 0;i<name.size();i++){
            if(n.equals(name.get(i))){
                return (Boolean) value.get(i);
            }
        }
        return null;
    }

    public int size(){
        return name.size();
    }

    public void reset(){
        for(int i = 0;i<size();i++){
            value.set(i,Boolean.FALSE);
        }
    }
}
