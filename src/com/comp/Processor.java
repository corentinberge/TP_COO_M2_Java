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

    public double get_tr(){
        if (current == 1){
            ta = Double.POSITIVE_INFINITY;
        }
        else if (current == 2){
            ta = 1;
        }
        return ta;
    }

    public void intern(){
        if (current == 2){
            current = 1;
        }
    }

    public void extern(Event ev){
        if ((current == 1) && (ev.get("req"))){
            current = 2;
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
        }
    }

    public String get_name(){
        return name;
    }
}
