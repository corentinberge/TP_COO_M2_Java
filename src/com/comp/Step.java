package com.comp;

import com.evenement.Event;

public class Step extends Component{

    //Attributes
    private double xi;
    private double xf;
    private double step;

    //Functions
    public Step(String n, double min , double max, double t){
        name = n;
        xi = min;
        xf = max;
        current = 1;
        tr = t;
        ta = t;
        tn = t;
        e = 0.;
        tl = 0.;
        step = t;
    }

    public void intern(Event ev) {
        if(current == 1){
            current = 2;
        }
    }

    public void extern(Event ev) {}

    public void output(Event ev){
        ev.transmit(name,xf);
    }

    public double time() {
        if (current == 1){
            return step;
        }
        else{
            return Double.POSITIVE_INFINITY;
        }
    }

    public double get_xi(){
        return xi;
    }

    public void conflict(Event ev) {}
}
