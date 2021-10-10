package com.comp;

import com.evenement.Event;

public class Buffer extends Component {

    //Attributes
    private int q = 0;

    //Functions
    public Buffer(String n, int c, double t){
        super(n,t);
        current = c;
        ta = 0;
    }

    public void intern(Event ev){
        if(current == 2){
            q--;
            current = 3;
            tn = e + Double.POSITIVE_INFINITY;
        }
    }

    public void extern(Event ev){
        if ((current == 1) && (ev.get_str("job"))){
            q++;
            current = 2;
            tn = e + ta;
        }
        else if ((current == 3) && (q == 0) && (ev.get_str("done"))){
            current = 1;
            tn = e + Double.POSITIVE_INFINITY;
        }
        else if ((current == 3) && (q>0) && (ev.get_str("done"))){
            current = 2;
            tn = e + ta;
        }
        else if ((current == 3) && (ev.get_str("job"))){
            q++;
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
            ev.set("req",Boolean.TRUE);
        }
    }

    public void conflict(Event ev){
        extern(ev);
        intern(ev);
    }

    public int get_q(){
        return q;
    }
}
