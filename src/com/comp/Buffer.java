package com.comp;

import com.evenement.Event;

import java.util.List;

public class Buffer extends Component {

    //Attributes
    private static int q = 0;

    //Functions
    public Buffer(){}

    public Buffer(String n, int[] s, int c, double t){
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
            tr = 0;
        }
        else if (current == 3){
            tr = Double.POSITIVE_INFINITY;
        }
        return tr;
    }

    public static void intern(){
        if(current == 2){
            q--;
            current = 3;
            Buffer.get_tr();
        }
    }

    public static void extern(Event ev){
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

    public static double time(){
        if ((current == 1) || (current == 3)){
            return Double.POSITIVE_INFINITY;
        }
        return 0.;
    }

    public static void output(Event ev){
        if(current == 2){
            ev.set("get",Boolean.TRUE);
        }
    }

    public static int get_q(){
        return q;
    }
}
