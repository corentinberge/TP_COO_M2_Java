package com.comp;

import com.evenement.Event;

import java.util.List;

public class Generator extends Component {

    //Functions
    public Generator(){}

    public Generator(String n, int[] s, int c, double t){
        name = n;
        S = s;
        current = c;
        ta = t;
    }

    public static double get_tr(){
        if (current == 1){
            tr = 2;
        }
        return tr;
    }

    public static void intern(){
        return ;
    }

    public static void extern(Event ev){
        return ;
    }

    public static double time(){
        return ta;
    }

    public static void output(Event ev){
        ev.set("job",Boolean.TRUE);
    }
}
