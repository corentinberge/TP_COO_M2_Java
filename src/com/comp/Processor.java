package com.comp;

import com.evenement.Event;

import java.util.List;

public class Processor extends Component {

    //Functions
    public Processor(){}

    public Processor(String n, int[] s, int c, double t){
        super(n,t);
        S = s;
        current = c;
    }

    public void intern(Event ev){
        if (current == 2){
            current = 1;
            tn = tl + Double.POSITIVE_INFINITY;
        }
    }

    public void extern(Event ev){
        if ((current == 1) && (ev.get("req"))){
            current = 2;
            tn = e + 1;
        }
    }

    public double time(){
        if (current == 1){
            return Double.POSITIVE_INFINITY;
        }
        else if (current == 2){
            return ta;
        }
        return 0.;
    }

    public void output(Event ev){
        if(current == 2){
            current = 1;
            tn = tl +  Double.POSITIVE_INFINITY;
        }
    }

    public void conflict(Event ev){
        return;
    }

    public String get_name(){
        return name;
    }

    public void set_tr(Double t){
        if(current == 1){
            tr = Double.POSITIVE_INFINITY;
        }
        else if(current == 2){
            tr = 1;
        }
    }
}
