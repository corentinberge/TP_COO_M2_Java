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
    }

    public void intern(Event ev){
        //System.out.print("Je suis passé ici !\n");
        if(current == 2){
            //System.out.print("Je repasse par la !\n");
            q--;
            current = 3;
            tn = tl + Double.POSITIVE_INFINITY;
        }
    }

    public void extern(Event ev){
        //System.out.print("Je suis la ma couille \n");
        if ((current == 1) && (ev.get("job"))){
            q++;
            current = 2;
            tn = tl + 0;
            ev.set("job",Boolean.FALSE);
        }
        else if ((current == 3) && (ev.get("job"))){
            q++;
            ev.set("job",Boolean.FALSE);
        }
        else if ((current == 3) && (q>0) && (ev.get("done"))){
            current = 2;
            tn = tl + 0;
        }
        else if ((current == 3) && (q == 0) && (ev.get("done"))){
            current = 1;
            tn = tl + Double.POSITIVE_INFINITY;
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
        if (ev.get("done")){
            extern(ev);
        }
        else {
            intern(ev);

        }
    }

    public int get_q(){
        return q;
    }

    public String get_name(){
        return name;
    }

    public void set_tr(Double t){
        if((current == 1) || (current == 3)){
            tr = Double.POSITIVE_INFINITY;
        }
        else if(current == 2){
            tr = 0;
        }
    }
}
