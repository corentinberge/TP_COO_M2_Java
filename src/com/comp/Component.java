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
    protected double tl = 0;
    protected double tn;

    //Functions
    public Component() {}

    public Component(String n,double t){
        name = n;
        e = 0;
        tn = t;
        ta = tn - tl;
        tr = ta - e;
    }

    public void set_e(double E){
        e = E;
    }

    public double get_tl(){
        return tl;
    }


    public void set_tn(double t){
        tn = t;
    }

    public void set_tl(double t){
        tl = t;
    }

    public Double get_tn(){
        return tn;
    }

    public int get_current(){
        return current;
    }

    public double get_tr(){
        return tr;
    }

    public String get_name(){
        return name;
    }

    abstract public void set_tr(Double t);

    abstract public void intern(Event ev);

    abstract public void extern(Event ev);

    abstract public void output(Event ev);

    abstract public double time();

    abstract public void conflict(Event ev);

    public double get_e(){
        return e;
    }
}
