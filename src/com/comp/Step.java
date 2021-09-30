package com.comp;

import com.evenement.Event;

public class Step extends Component{

    //Attributes
    private double xi;
    private double xf;


    //Functions
    public Step(){}

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
    }

    public void set_tr(Double t) {
        if(current == 1){
            tr = tr - t;
        }
        else if(current == 2){
            tr = Double.POSITIVE_INFINITY;
        }
    }

    public void intern(Event ev) {
        if(current == 1){
            current = 2;
            ev.transmit("Adder",xf);
        }
        return;
    }

    public void extern(Event ev) {
        return;
    }

    public void output(Event ev){
        if(current == 1){
            ev.transmit(name,xi);
        }
        else if (current == 2){
            ev.transmit(name,xf);
        }
        return;
    }

    public double time() {
        if (current == 1){
            return tr;
        }
        else{
            return Double.POSITIVE_INFINITY;
        }
    }

    public double get_xi(){
        return xi;
    }

    public void conflict(Event ev) {
        return ;
    }
}
