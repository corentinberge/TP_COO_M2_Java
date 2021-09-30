package com.evenement;

import java.util.ArrayList;
import java.util.List;

public class Event {

    //Attributes
    private List<String> name = new ArrayList<String>();
    private List<Boolean> TF = new ArrayList<Boolean>();
    private List<Double> value = new ArrayList<Double>();

    //Functions
    public Event() {}

    public void add(String n,Boolean v){
        name.add(n);
        TF.add(v);
        value.add(0.);
    }

    public void set(String n,Boolean v){
        for(int i = 0;i<name.size();i++){
            if(n.equals(name.get(i))){
                TF.set(i,v);
            }
        }
    }

    public void transmit(String n,Double d){
        for(int i = 0;i<name.size();i++){
            if(n.equals(name.get(i))){
                TF.set(i,Boolean.TRUE);
                value.set(i,d);
            }
        }
    }

    public Boolean get_str(String n){
        for(int i = 0;i<name.size();i++){
            if(n.equals(name.get(i))){
                return (Boolean) TF.get(i);
            }
        }
        return null;
    }

    public int size(){
        return name.size();
    }

    public void reset(){
        for(int i = 0;i<size();i++){
            TF.set(i,Boolean.FALSE);
            value.set(i,0.);
        }
    }

    public double val(String n) {
        for (int i = 0; i < name.size(); i++) {
            if (n.equals(name.get(i))) {
                return value.get(i);
            }
        }
        return 0.;
    }
}
