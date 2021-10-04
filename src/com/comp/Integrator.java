package com.comp;

import com.evenement.Event;

public class Integrator extends Component{

    private double step = 0.01;
    private double X;
    private double coef;

    public Integrator(){};

    public Integrator(String n, int[] s, int c, double t, double x){
        name = n;
        S = s;
        current = 1;
        tr = t;
        ta = t;
        tn = t;
        e = 0.;
        tl = 0.;
        X = 0;
        coef = x;
    }

    public void set_tr(Double t) {
        if(current == 1){
            tr += step;
        }
        else if(current == 2){
            tr = 0.;
        }
    }

    public void intern(Event ev) {
        if(current == 1){
            X += step*coef;
            //System.out.print("I : instant " + e + " le couple x,c = " + X + "," + coef + "\n");
            current = 2;
        }
        else if (current == 2){
            current = 1;
        }
        return;
    }

    public void extern(Event ev) {
        if ((current == 1) && (ev.get_str("Adder"))) {
            X += step*coef;
            coef = ev.val("Adder");
            System.out.print("E : instant " + e + " le couple x,c = " + X + "," + coef + "\n");
            current = 2;
            ev.set("Adder",Boolean.FALSE);
        }
    }

    public void output(Event ev) {
        if (current == 2){
            ev.transmit("Derivative",X);
        }
    }

    public double time() {
        if(current == 1){
            return tr;
        }
        return 0.;
    }

    public void conflict(Event ev) {
        return;
    }

    public double get_X(){
        return X;
    }

    public double get_coef(){return coef;}
}
