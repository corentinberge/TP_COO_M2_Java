package com.comp;

import com.evenement.Event;

public class Processor extends Component {

    //Attributes
    private int c = 0;

    //Functions
    public Processor(){}

    public Processor(String n, int[] s, int c, double t){
        super(n,t);
        S = s;
        current = c;
        ta = 3;
    }

    public void intern(Event ev){
        if (current == 2){
            current = 1;
            tn = e + Double.POSITIVE_INFINITY;
        }
    }

    public void extern(Event ev){
        if ((current == 1) && (ev.get_str("req"))){
            current = 2;
            tn = e + ta;
        }
    }

    public double time(){
        if (current == 1){
            return Double.POSITIVE_INFINITY;
        }
        else if (current == 2){
            return tr;
        }
        return 0.;
    }

    public void output(Event ev){
        if(current == 2){
            current = 1;
            tn = e +  Double.POSITIVE_INFINITY;
            ev.set("done",Boolean.TRUE);
        }
    }

    public void conflict(Event ev){
        if (c == 0){
            extern(ev);
            c++;
        }
        else{
            intern(ev);
            c--;
        }
    }

    public String get_name(){
        return name;
    }

    public void set_tr(Double t){
        if(current == 1){
            tr = Double.POSITIVE_INFINITY;
        }
        else if(current == 2){
            tr = tn - t;
        }
    }
}
