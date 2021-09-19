package com.comp;

import com.evenement.Event;

public class Buffer extends Component {

    //Attributes
    private static int q = 0;

    //Functions
    public Buffer(){}

    public Buffer(String n, int[] s, int c, double t){
        super(n,t);
        S = s;
        current = c;
    }

    public double get_tr(){
        if (current == 1){
            ta = Double.POSITIVE_INFINITY;
        }
        else if (current == 2){
            ta = 0;
        }
        else if (current == 3){
            ta = Double.POSITIVE_INFINITY;
        }
        return ta;
    }

    public void intern(){
        if(current == 2){
            q--;
            current = 3;
        }
    }

    public void extern(Event ev){
        if ((current == 1) && (ev.get("job"))){
            q++;
            current = 2;
        }
        else if ((current == 3) && (ev.get("job"))){
            q++;
        }
        else if ((current == 3) && (q>0) && (ev.get("done"))){
            current = 2;
        }
        else if ((current == 3) && (q == 0) && (ev.get("done"))){
            current = 1;
        }
    }

    public double time(){
        if ((current == 1) || (current == 3)){
            return Double.POSITIVE_INFINITY;
        }
        return 0.;
    }

    public void output(Event ev){
        if(current == 2){
            ev.set("get",Boolean.TRUE);
        }
    }

    public static int get_q(){
        return q;
    }

    public String get_name(){
        return name;
    }
}
