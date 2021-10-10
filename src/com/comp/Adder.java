package com.comp;

import com.evenement.Event;

import java.util.List;

public class Adder extends Component{

    //Attributes
    private Double sum = 0.;

    //Functions
    public Adder(String n, int c, double t){
        name = n;
        current = c;
        tr = t;
        ta = t;
        tn = t;
        tl = 0.;
        e = 0.;
    }

    public void init(List<Step> s){
        for (Step step : s) {
            sum += step.get_xi();
        }
    }

    public void intern(Event ev) {
        if(current == 2){
            current = 1;
        }
    }

    public void extern(Event ev) {
        if((current == 1)&&(ev.get_str("Step1"))){
            sum += ev.val("Step1");
            ev.set("Step1",Boolean.FALSE);
            current = 2;
        }
        else if((current == 1)&&(ev.get_str("Step2"))){
            sum += ev.val("Step2");
            ev.set("Step2",Boolean.FALSE);
            current = 2;
        }
        else if((current == 1)&&(ev.get_str("Step3"))){
            sum += ev.val("Step3");
            ev.set("Step3",Boolean.FALSE);
            current = 2;
        }
        else if((current == 1)&&(ev.get_str("Step4"))){
            sum += ev.val("Step4");
            ev.set("Step4",Boolean.FALSE);
            current = 2;
        }
    }

    public void output(Event ev) {
        if(current == 2){
            ev.transmit("Adder",sum);
        }
    }

    public double time() {
        if(current == 1){
            return Double.POSITIVE_INFINITY;
        }
        return 0.;
    }

    public void conflict(Event ev) {}

    public double get_sum(){
        return sum;
    }
}
