package com.comp;

import com.evenement.Event;

public class Integrator_discrete2 extends Component{

    //Attributes
    private double X;
    private double ti;
    private double coef;
    private double step;
    private double delta;


    //Functions
    public Integrator_discrete2(String n, int c, double x, double co, double st, double d){
        name = n;
        e = 0.;
        tl = 0.;
        X = x;
        coef = co;
        ti = 0.;
        step = st;
        tr = step;
        ta = step;
        tn = step;
        delta = d;
        current = c;
    }

    public void intern(Event ev) {
        if(current == 1){
            X += step*coef;
            current = 2;
        }
        else if(current == 2){
            current = 1;
        }
        else if (current == 3){
            current = 2;
            X *= Math.signum(X)*0.8;
            ev.set("change",Boolean.FALSE);
        }
    }

    public void extern(Event ev){
        if((current == 1) && (ev.get_str("change"))){
            ev.set("change",Boolean.FALSE);
            current = 3;
        }
        else if ((current == 1) && (ev.get_str("Cons")) && (this.name.equals("Derivative_1")) ) {
            coef = ev.val("Cons");
            step = Math.abs(delta / Math.abs(coef));
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
        intern(ev);
    }

    public double get_X(){
        return X;
    }
}
