package com.comp;

import com.evenement.Event;

public class Integrator_discrete extends Component{

    private double X;
    private double ti;
    private double coef;
    private double step;
    private double delta = 0.1;

    public Integrator_discrete(String n, int c, double x,double co,double st){
        name = n;
        current = c;
        e = 0.;
        tl = 0.;
        X = x;
        coef = co;
        ti = 0.;
        step = st;
        tr = st;
        ta = st;
        tn = st;
    }

    public Integrator_discrete(String n, int c, double x,double co,double st,double d){
        name = n;
        current = c;
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
    }

    public void intern(Event ev) {
        if(current == 1){
            X += step*coef;
            if(X >= 0){
                current = 2;
            }
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
            if((this.name.equals("Derivative_2")) && (X<0) && !ev.get_str("Neg")){
                ev.set("Neg",Boolean.TRUE);
                X = 0;
                tr = Double.POSITIVE_INFINITY;
            }
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
