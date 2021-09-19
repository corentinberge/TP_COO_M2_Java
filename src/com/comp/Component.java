package com.comp;

import com.evenement.Event;

import java.util.ArrayList;
import java.util.List;

public abstract class Component {

    //Attributes
    protected String name;
    protected int[] S;
    protected int current;
    protected double ta;
    protected double e;
    protected double tr;
    private double tl = 0;
    private double tn;

    //Functions
    public Component() {}

    public Component(String n,double t){
        name = n;
        ta = t;
        tn = t;
        e = t;
        tr = ta - e;
        tn = t + tr;
    }


    public void set_e(double E){
        e = E;
    }

    public void set_tr(){
        tr = ta - e;
    }

    public void set_tn(){
        if(e>=tn){
            tn = tl + ta;
        }
    }

    public void set_tl(){
        if(e>=tn) {
            tl = tn;
        }
    }

    abstract public String get_name();

    abstract public void intern();

    abstract public void extern(Event ev);

    abstract public double get_tr();

    abstract public void output(Event ev);

    abstract public double time();
}
