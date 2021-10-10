package com.comp;

import com.evenement.Event;

public class Connector extends Component{

    //Functions
    public Connector(String n, int c, double x){
        name = n;
        current = c;
        tr = x;
        ta = x;
        tn = x;
    }

    public void intern(Event ev) {
        if(current == 2){
            current = 1;
        }
    }

    public void extern(Event ev) {
        if((current == 1)&&(ev.get_str("Neg"))){
            ev.set("Neg",Boolean.FALSE);
            current = 2;
        }
    }

    public void output(Event ev) {
        ev.set("change",Boolean.TRUE);
    }

    public double time() {
        if(current == 2){
            return 0.;
        }
        return Double.POSITIVE_INFINITY;
    }

    public void conflict(Event ev) {
        intern(ev);
    }
}
