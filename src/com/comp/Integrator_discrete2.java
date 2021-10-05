package com.comp;

import com.evenement.Event;

public class Integrator_discrete2 extends Component{

    private double X;
    private double ti;
    private double coef;
    private double step;
    private double delta;
    private boolean it =Boolean.TRUE;

    public Integrator_discrete2(){}

    public Integrator_discrete2(String n, int[] s, int c, double t, double x, double co){
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

    public Integrator_discrete2(String n, int[] s, int c, double t, double x, double co, double st, double d){
        name = n;
        S = s;
        current = 1;
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

    public void set_tr(Double t) {
        tr = t;
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
        else if (current == 3){
            current = 2;
            X *= Math.signum(X)*0.8;
        }
    }

    public void extern(Event ev){
        if(ev.get_str("change")){
            //System.out.print("Allo ?\n");
            ev.set("change",Boolean.FALSE);
            current = 3;
        }
        else if ((current == 1) && (ev.get_str("Cons"))) {
            coef = ev.val("Cons");
            step = Math.abs(delta / Math.abs(coef));
            delta = delta * Math.signum(coef);
            current = 2;
            ev.set("Cons", Boolean.FALSE);
        }
        /*else if ((current == 1) && (this.name == "Derivative_1")){
            X += step*coef;
            current = 2;
            ev.transmit(name,X);
        }*/
    }

    public void output(Event ev) {
        System.out.print("I1 : J'affiche ma sortie \n");
        if(current == 2){
            ev.transmit(name,X);
            if((this.name == "Derivative_2") && (X<0)){
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
        //System.out.print("I1 : Je suis en conflit \n");
        intern(ev);
        //extern(ev);
        //intern(ev);
        return;
    }

    public double get_X(){
        return X;
    }

    public double get_coef(){return coef;}

    public double get_delta(){return delta;}
}
