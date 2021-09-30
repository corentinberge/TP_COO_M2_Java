package com.comp;

import com.evenement.Event;

import java.util.List;

public class Generator extends Component {

    //Functions
    public Generator(){}

    public Generator(String n, int[] s, int c, double t){
        super(n,t);
        S = s;
        current = c;
        ta = 2;
    }

    public void intern(Event ev){
        return ;
    }

    public void extern(Event ev){
        return ;
    }

    public double time(){
        return ta;
    }

    public void output(Event ev){
        ev.set("job",Boolean.TRUE);
    }

    public String get_name(){
        return name;
    }

    public void conflict(Event ev){
        return;
    }

    public void set_tr(Double t){
        tr = tn - t;
    }
}
