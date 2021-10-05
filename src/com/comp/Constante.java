package com.comp;

import com.evenement.Event;

public class Constante extends Component {

    //Attributes
    private double x;

    //Functions
    public Constante(){}

    public Constante(String n, double X){
        name = n;
        x = X;
        tr = 0.;
    }

    public void init(Event ev){
        output(ev);
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
        return;
    }

    public void output(Event ev) {
        ev.transmit(name,x);
    }

    public double time() {
        return 0;
    }

    public void conflict(Event ev) {
        return;
    }

    public double get_x(){
        return x;
    }
}
