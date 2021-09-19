package com.comp;

import com.evenement.Event;

import java.util.List;

public class Processor extends Component {

    //Functions
    public Processor(){}

    public Processor(String n, int[] s, int c, double t){
        name = n;
        S = s;
        current = c;
        ta = t;
    }

    public static double get_tr(){
        if (current == 1){
            tr = Double.POSITIVE_INFINITY;
        }
        else if (current == 2){
            tr = 1;
        }
        return tr;
    }

    public static void intern(){
        if (current == 2){
            current = 1;
        }
    }

    public static void extern(Event ev){
        if ((current == 1) && (ev.get("req"))){
            current = 2;
        }
    }

    public static double time(){
        if (current == 1){
            return Double.POSITIVE_INFINITY;
        }
        else if (current == 2){
            return ta;
        }
        return 0.;
    }

    public static void output(Event ev){
        if(current == 2){
            current = 1;
        }
    }
}
