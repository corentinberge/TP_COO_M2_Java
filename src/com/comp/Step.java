package com.comp;

import com.evenement.Event;

public class Step extends Component{

    //Attributes
    private double xi;
    private double xf;
    private double ts;


    //Functions
    public Step(){}

    public Step(String n, Double d, Double a, Double occ){
        name = n;
        xi = d;
        xf = a;
        ts = occ;
    }

    public double sw(Double t){
        if(t >= ts){
            return xf;
        }
        else{
            return xi;
        }
    }

    public double get_ts(){
        return ts;
    }

    public void set_tr(Double t) {
        return;
    }

    public void intern(Event ev) {
        return;
    }

    public void extern(Event ev) {
        return;
    }

    public void output(Event ev) {
        return;
    }

    public double time() {
        return 0;
    }

    public void conflict(Event ev) {
        return;
    }
}
