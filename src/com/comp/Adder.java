package com.comp;

import com.evenement.Event;

import java.util.List;

public class Adder extends Component{

    //Attributes
    private Double sum = 0.;

    //Functions
    public Adder(){}

    public Adder(String n, int[] s, int c, double t){
        name = n;
        S = s;
        current = c;
        tr = t;
        ta = t;
        tn = t;
        tl = 0.;
        e = 0.;
    }

    public void init(List<Step> s){
        for(int i = 0;i<s.size();i++){
            sum += s.get(i).get_xi();
        }
    }

    public void set_tr(Double t) {
        if(current == 1){
            tr = Double.POSITIVE_INFINITY;
        }
        else if(current == 2){
            tr = 0.;
        }
    }

    public void intern(Event ev) {
        if((current == 1)&&(ev.get_str("Adder"))){
            sum += ev.val("Adder");
            ev.transmit("Derivative",sum);
            //current = 2;
        }
    }

    public void extern(Event ev) {
        if((current == 1)&&(ev.get_str("Adder"))){
            sum += ev.val("Adder");
            ev.transmit("Derivative",sum);
            //current = 2;
        }
    }

    public void output(Event ev) {
        ev.transmit("Sum",sum);
    }

    public double time() {
        if(current == 1){
            return Double.POSITIVE_INFINITY;
        }
        return 0.;
    }

    public void conflict(Event ev) {
        return ;
    }

    public double get_sum(){
        return sum;
    }
}
