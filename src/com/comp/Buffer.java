package com.comp;

import com.evenement.Event;

public class Buffer extends Component {

    //Attributes
    private int q = 0;

    //Functions
    public Buffer(){}

    public Buffer(String n, int[] s, int c, double t){
        super(n,t);
        S = s;
        current = c;
        ta = 0;
    }

    public void intern(Event ev){
        //System.out.print("Je suis passé ici !\n");
        if(current == 2){
            //System.out.print("Je repasse par la !\n");
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

    public void set_tr(Double t){
        if((current == 1) || (current == 3)){
            tr = Double.POSITIVE_INFINITY;
        }
        else if(current == 2){
            tr = tn - t;
        }
    }
}
