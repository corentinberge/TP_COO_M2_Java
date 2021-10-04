package com.comp;

import com.evenement.Event;

public class Integrator_discrete extends Component{

    private double X;
    private double ti;
    private double coef;
    private double step;
    private double delta = 0.1;

    public Integrator_discrete(){}

    public Integrator_discrete(String n, int[] s, int c, double t, double x,double co){
        name = n;
        S = s;
        current = 1;
        e = 0.;
        tl = 0.;
        X = 0;
        coef = co;
        ti = 0.;
        step = Math.abs(delta/Math.abs(coef));
        tr = step;
        ta = step;
        tn = step;
    }

    public Integrator_discrete(String n, int[] s, int c, double t, double x,double co,double st){
        name = n;
        S = s;
        current = 1;
        e = 0.;
        tl = 0.;
        X = 0;
        coef = co;
        ti = 0.;
        step = st;
        tr = step;
        ta = step;
        tn = step;
    }

    public void set_tr(Double t) {
        if (current == 1) {
            tr = step;
        } else {
            tr = 0.;
        }
    }

    public void intern(Event ev) {
        if(current == 1){
            X += step*coef;
            current = 2;
            ev.transmit(name,X);
        }
        else if(current == 2){
            current = 1;
        }
    }

    public void extern(Event ev) {
        if ((current == 1) && (ev.get_str("Adder"))){
            if(coef != 0){
                X += step*coef;
            }
            coef = ev.val("Adder");
            if(coef != 0){
                step = Math.abs(delta/Math.abs(coef));
                delta = delta * Math.signum(coef);
            }
            else{
                step = Double.POSITIVE_INFINITY;
            }
            current = 2;
            ev.set("Adder",Boolean.FALSE);
        }
        else if ((current == 1) && (ev.get_str("Derivative_1"))){
            if(coef != 0){
                X += e*coef;
            }
            coef = ev.val("Derivative_1");
            if(coef != 0){
                step = Math.abs(delta/Math.abs(coef));
                delta = delta * Math.signum(coef);
            }
            else{
                step = Double.POSITIVE_INFINITY;
            }
            current = 2;
            ev.set("Derivative_1",Boolean.FALSE);
            System.out.print("Je suis passé par ici ! \n");
        }
        else if ((current == 1) && (ev.get_str("Cons"))) {
            coef = ev.val("Cons");
            step = Math.abs(delta / Math.abs(coef));
            delta = delta * Math.signum(coef);
            current = 2;
            ev.set("Cons", Boolean.FALSE);
        }
    }

    public void output(Event ev) {
        if(current == 2){
            ev.transmit(name,X);
        }
    }

    public double time() {
        if(current == 1){
            return step;
        }
        return 0.;
    }

    public void conflict(Event ev) {
        extern(ev);
        return;
    }

    public double get_X(){
        return X;
    }

    public double get_coef(){return coef;}
}
