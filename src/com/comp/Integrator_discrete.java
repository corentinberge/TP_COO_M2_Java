package com.comp;

import com.evenement.Event;

public class Integrator_discrete extends Component{

    private double X;
    private double ti;
    private double tf;

    public void set_tr(Double t) {
        if (current == 1) {
            tr = Double.POSITIVE_INFINITY;
        } else {
            tr = 0.;
        }
    }

    public void intern(Event ev) {
        if(current == 2){
            current = 1;
        }
    }

    public void extern(Event ev) {
        if ((current == 1) && (ev.get_str("Derivative"))){
            double del = tf - ti;
            X
        }
    }

    public void output(Event ev) {

    }

    public double time() {
        return 0;
    }

    public void conflict(Event ev) {

    }
}
