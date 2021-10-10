package com.comp;

import com.evenement.Event;

public class Constante extends Component {

    //Attributes
    private double x;

    //Functions
    public Constante(String n, double X){
        name = n;
        x = X;
        tr = 0.;
    }

    public void set_tr(Double t) {
        if(current == 1){
            tr = 0.;
        }
        tr = Double.POSITIVE_INFINITY;
    }

    public void intern(Event ev) {
        if(current == 1){
            current = 2;
        }
    }

    public void extern(Event ev) {
        if(current == 1){
            current = 2;
        }
    }

    public void output(Event ev) {
        ev.transmit(name,x);
    }

    public double time() {
        if(current == 1){
            return 0;
        }
        return Double.POSITIVE_INFINITY;
    }

    public void conflict(Event ev) {}
}
