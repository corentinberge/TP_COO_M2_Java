package com.comp;

import com.evenement.Event;

import java.util.List;

public class Generator extends Component {

    //Functions
    public Generator(String n, int c, double t){
        super(n,t);
        current = c;
        ta = 2;
    }

    public void intern(Event ev){}

    public void extern(Event ev){}

    public double time(){
        return 2.;
    }

    public void output(Event ev){
        ev.set("job",Boolean.TRUE);
    }

    public String get_name(){
        return name;
    }

    public void conflict(Event ev){}
}
