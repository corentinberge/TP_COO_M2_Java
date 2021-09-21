package com.comp;

import com.evenement.Event;

import java.util.List;

public class Adder extends Component{

    //Attributes
    private Double sum = 0.;

    //Functions
    public Adder(){}

    public Adder(List<Step> s){
        for(int i = 0;i<s.size();i++){
            sum+=s.get(i).sw(0.);
        }
    }

    public double add(Double d){
        sum += d;
        return sum;
    }

    public double get_sum(){
        return sum;
    }

    public void set_tr(Double t) {

    }

    public void intern(Event ev) {

    }

    public void extern(Event ev) {

    }

    public void output(Event ev) {

    }

    public double time() {
        return 0;
    }

    public void conflict(Event ev) {

    }
}
