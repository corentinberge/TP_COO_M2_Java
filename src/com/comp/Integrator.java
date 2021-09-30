package com.comp;

import com.evenement.Event;

public class Integrator extends Component{

    //Attributes
    private Double step;
    private Double X;

    //Functions
    public Integrator(){}

    public Integrator(String n, int[] s, int c, double t, double x){
        name = n;
        S = s;
        current = c;
        tr = t;
        ta = t;
        tn = t;
        tl = 0.;
        e = 0.;
        step = t;
        X = x;
    }

    public double get_step(){
        return step;
    }

    public void set_tr(Double t) {
        if (current == 1){
            tr = t + step;
        }
        tr = t;
    }

    public void intern(Event ev) {
        if(current == 1){
            current = 2;
            X += e*ev.val("Derivative");
        }
        else if (current == 2){
            current = 1;
        }
    }

    public void extern(Event ev) {
        if((current == 1) && (ev.get_str("Derivative"))){
            X += e*ev.val("Derivative");
            current = 2;
        }
    }

    public void output(Event ev) {
        if (current == 2){
            //ev.transmit("Derivative",X);
        }
    }

    public double time() {
        if(current == 1){
            return step;
        }
        return 0.;
    }

    public double get_X(){
        return X;
    }

    public void conflict(Event ev) {
        extern(ev);
        return;
    }

}
