package com.simu;

import chart.Chart;
import chart.ChartFrame;
import com.comp.*;
import com.evenement.Event;

import java.util.ArrayList;
import java.util.List;

public class Simulator {

    //Attributes
    private double t = 0;
    private double tr_min;
    List<Component> Comp = new ArrayList<>();

    //Functions
    public Simulator(){}

    public void ordonnanceur(){

        //Initialization
        Generator g;
        Buffer b;
        Processor p;
        Event ev = new Event();

        //Intialize Components
        b = new Buffer("B",1,Double.POSITIVE_INFINITY);
        g = new Generator("G",1,2);
        p = new Processor("P",1,Double.POSITIVE_INFINITY);

        Comp.add(0,b);Comp.add(1,g);Comp.add(2,p);

        //Intialize event
        ev.add("done",Boolean.FALSE);
        ev.add("job",Boolean.FALSE);
        ev.add("req",Boolean.FALSE);

        afficheur_ordo(b,g,p,ev);

        //Initialize view
        ChartFrame cf = new ChartFrame("Résultat","GBP");
        Chart Cq = new Chart("q");
        cf.addToLineChartPane(Cq);

        while(t<20){
            //Local variable
            List<Component> imms = new ArrayList<>();
            List<Component> ins = new ArrayList<>();

            //Stocking the minimum time response
            tr_min = Double.min(b.get_tr(),Double.min(g.get_tr(),p.get_tr()));

            for (Component component : Comp) {
                if (component.get_tr() == tr_min) {
                    imms.add(imms.size(), component);
                }
            }

            //Adding the minimum time response to the time
            t = t + tr_min;

            //Setting minimum time for all component
            for (Component component : Comp) {
                component.set_e(t - component.get_tl());
                component.set_tr(component.time() - component.get_e());
            }

            //Making the output function for imms (lambda)
            for (Component imm : imms) {
                imm.output(ev);
            }

            //Adding component who can change with output
            if(ev.get_str("done") || ev.get_str("job")){
                ins.add(0,b);
            }
            if(ev.get_str("req")){
                ins.add(ins.size(),p);
            }

            for (Component component : Comp) {
                boolean in_imms = Boolean.FALSE, in_ins = Boolean.FALSE;
                for (Component imm : imms) {
                    if (component.get_name().equals(imm.get_name())) {
                        in_imms = Boolean.TRUE;
                        break;
                    }
                }
                for (Component in : ins) {
                    if (component.get_name().equals(in.get_name())) {
                        in_ins = Boolean.TRUE;
                        break;
                    }
                }
                if (in_imms && !in_ins) {
                    component.intern(ev);
                    component.set_e(0);
                    component.set_tl(t);
                    component.set_tn(component.get_tl() + component.get_e());
                    component.set_tr(component.time() - component.get_e());
                } else if (!in_imms && in_ins) {
                    component.extern(ev);
                    component.set_e(0);
                    component.set_tl(t);
                    component.set_tn(component.get_tl() + component.get_e());
                    component.set_tr(component.time() - component.get_e());
                } else if (in_imms && in_ins) {
                    component.conflict(ev);
                    component.set_e(0);
                    component.set_tl(t);
                    component.set_tn(component.get_tl() + component.get_e());
                    component.set_tr(component.time() - component.get_e());
                }
            }
            //Load print and view
            afficheur_ordo(b,g,p,ev);
            Cq.addDataToSeries(t,b.get_q());

            //Reset all the event
            ev.reset();
        }
    }

    public void afficheur_ordo(Buffer b,Generator g,Processor p, Event ev){
        System.out.print("t = " + t);
        System.out.print("\nq = " + b.get_q());
        if(p.get_current() == 2){
            System.out.print("\ndone|" + p.get_tr() + " - " + ev.get_str("done"));
        }
        System.out.print("\njob|" + g.get_tr() + " - " + ev.get_str("job"));
        if(b.get_current() == 2){
            System.out.print("\nreq|" + b.get_tr() + " - " + ev.get_str("req"));
        }
        System.out.print("\nb : " + b.get_current());
        System.out.print("\ng : " + g.get_current());
        System.out.print("\np : " + p.get_current());
        System.out.print("\n\n");
    }

    public void consigne(){

        //Initialisation
        double res;

        //Initialize step
        Step step1 = new Step("Step1",1.,-3.,0.65);
        Step step2 = new Step("Step2",0.,1.,0.35);
        Step step3 = new Step("Step3",0.,1.,1.);
        Step step4 = new Step("Step4",0.,4.,1.5);
        List<Step> stp = new ArrayList<>();
        stp.add(0,step1);stp.add(0,step2);stp.add(0,step3);stp.add(0,step4);

        //Initialize Adder
        Adder ad = new Adder("Ad",1,Double.POSITIVE_INFINITY);

        //Initalize event
        Event ev = new Event();
        ev.add("Step1",Boolean.FALSE);
        ev.add("Step2",Boolean.FALSE);
        ev.add("Step3",Boolean.FALSE);
        ev.add("Step4",Boolean.FALSE);
        ev.add("Adder",Boolean.FALSE);

        Comp.add(0,step1);Comp.add(1,step2);Comp.add(2,step3);Comp.add(3,step4);Comp.add(4,ad);

        //Initialize view
        ChartFrame cf = new ChartFrame("Résultat","Somme");
        Chart Sum = new Chart("Sum");
        cf.addToLineChartPane(Sum);

        //for t = 0 do
        ad.init(stp);
        res = ad.get_sum();
        Sum.addDataToSeries(t,res);

        while(t<1.5){
            //Local variable
            List<Component> imms = new ArrayList<>();
            List<Component> ins = new ArrayList<>();

                //Stocking the minimum time response
            tr_min = Double.min(ad.get_tr(),Double.min(step1.get_tr(),Double.min(step2.get_tr(),Double.min(step3.get_tr(),step4.get_tr()))));

            for (Component component : Comp) {
                if (component.get_tr() == tr_min) {
                    imms.add(imms.size(), component);
                }
            }

            //Adding the minimum time response to the time
            t = t + tr_min;

            //Setting minimum time for all component
            for (Component component : Comp) {
                component.set_e(t - component.get_tl());
                component.set_tr(component.time() - component.get_e());
            }

            //Making the output function for imms (lambda)
            for(Component imm : imms) {
                imm.output(ev);
            }

            //Adding component who can change with output
            if(ev.get_str("Step1")){
                ins.add(0,ad);
            }
            if(ev.get_str("Step2")){
                ins.add(ins.size(),ad);
            }
            if(ev.get_str("Step3")){
                ins.add(ins.size(),ad);
            }
            if(ev.get_str("Step4")){
                ins.add(ins.size(),ad);
            }

            for (Component component : Comp) {
                boolean in_imms = Boolean.FALSE, in_ins = Boolean.FALSE;
                for (Component imm : imms) {
                    if (component.get_name().equals(imm.get_name())) {
                        in_imms = Boolean.TRUE;
                        break;
                    }
                }
                for (Component in : ins) {
                    if (component.get_name().equals(in.get_name())) {
                        in_ins = Boolean.TRUE;
                        break;
                    }
                }
                if (in_imms && !in_ins) {
                    component.intern(ev);
                    component.set_e(0);
                    component.set_tl(t);
                    component.set_tn(component.get_tl() + component.get_e());
                    component.set_tr(component.time() - component.get_e());
                } else if (!in_imms && in_ins) {
                    component.extern(ev);
                    component.set_e(0);
                    component.set_tl(t);
                    component.set_tn(component.get_tl() + component.get_e());
                    component.set_tr(component.time() - component.get_e());
                } else if (in_imms && in_ins) {
                    component.conflict(ev);
                    component.set_e(0);
                    component.set_tl(t);
                    component.set_tn(component.get_tl() + component.get_e());
                    component.set_tr(component.time() - component.get_e());
                }
            }
            Sum.addDataToSeries(t,ad.get_sum());
        }
    }

    public void integrate() {

        //Initialisation
        double res;
        Step step1 = new Step("Step1",1.,-3.,0.65);
        Step step2 = new Step("Step2",0.,1.,0.35);
        Step step3 = new Step("Step3",0.,1.,1.);
        Step step4 = new Step("Step4",0.,4.,1.5);

        //Initialize step
        List<Step> stp = new ArrayList<>();
        stp.add(0,step1);stp.add(0,step2);stp.add(0,step3);stp.add(0,step4);

        //Initialize Adder
        Adder ad = new Adder("Ad",1,Double.POSITIVE_INFINITY);

        //Initialize Event
        Event ev = new Event();
        ev.add("Adder",Boolean.FALSE);
        ev.add("Step1",Boolean.FALSE);
        ev.add("Step2",Boolean.FALSE);
        ev.add("Step3",Boolean.FALSE);
        ev.add("Step4",Boolean.FALSE);
        ev.add("Derivative",Boolean.FALSE);

        //Initialize View
        ChartFrame cf = new ChartFrame("Résultat","Integrator");
        Chart Sum = new Chart("Sum");
        cf.addToLineChartPane(Sum);
        Chart X = new Chart("X");
        cf.addToLineChartPane(X);

        //For t = 0 do
        ad.init(stp);
        res = ad.get_sum();
        Sum.addDataToSeries(t,res);

        //Initialize Integrator
        Integrator I = new Integrator("I",1,0.01,ad.get_sum());
        X.addDataToSeries(t,I.get_X());

        Comp.add(0,step1);Comp.add(1,step2);Comp.add(2,step3);Comp.add(3,step4);Comp.add(4,ad);Comp.add(5,I);

        while(t<2){
            //Local variable
            List<Component> imms = new ArrayList<>();
            List<Component> ins = new ArrayList<>();

            //Stocking the minimum time response
            tr_min = Double.min(ad.get_tr(),Double.min(step1.get_tr(),Double.min(step2.get_tr(),Double.min(step3.get_tr(),Double.min(I.get_tr(),step4.get_tr())))));

            for (Component component : Comp) {
                if (component.get_tr() == tr_min) {
                    imms.add(imms.size(), component);
                }
            }

            //Adding the minimum time response to the time
            t = t + tr_min;

            //Setting minimum time for all component
            for (Component component : Comp) {
                component.set_e(t - component.get_tl());
                component.set_tr(component.time() - component.get_e());
            }

            //Making the output function for imms (lambda)
            for (Component imm : imms) {
                imm.output(ev);
            }

            //Adding component who can change with output
            if(ev.get_str("Step1")){
                ins.add(0,ad);
            }
            if(ev.get_str("Step2")){
                ins.add(ins.size(),ad);
            }
            if(ev.get_str("Step3")){
                ins.add(ins.size(),ad);
            }
            if(ev.get_str("Step4")){
                ins.add(ins.size(),ad);
            }
            if(ev.get_str("Adder")){
                ins.add(ins.size(),I);
            }

            for (Component component : Comp) {
                boolean in_imms = Boolean.FALSE, in_ins = Boolean.FALSE;
                for (Component imm : imms) {
                    if (component.get_name().equals(imm.get_name())) {
                        in_imms = Boolean.TRUE;
                        break;
                    }
                }
                for (Component in : ins) {
                    if (component.get_name().equals(in.get_name())) {
                        in_ins = Boolean.TRUE;
                        break;
                    }
                }
                if (in_imms && !in_ins) {
                    component.intern(ev);
                    component.set_e(0);
                    component.set_tl(t);
                    component.set_tn(component.get_tl() + component.get_e());
                    component.set_tr(component.time() - component.get_e());
                } else if (!in_imms && in_ins) {
                    component.extern(ev);
                    component.set_e(0);
                    component.set_tl(t);
                    component.set_tn(component.get_tl() + component.get_e());
                    component.set_tr(component.time() - component.get_e());
                } else if (in_imms && in_ins) {
                    component.conflict(ev);
                    component.set_e(0);
                    component.set_tl(t);
                    component.set_tn(component.get_tl() + component.get_e());
                    component.set_tr(component.time() - component.get_e());
                }
            }

            //Load data to view
            Sum.addDataToSeries(t,ad.get_sum());
            X.addDataToSeries(t,I.get_X());
        }
    }

    public void integrate_discrete() {

        //Initialisation
        double res;

        //Initialize Step
        Step step1 = new Step("Step1",1.,-3.,0.65);
        Step step2 = new Step("Step2",0.,1.,0.35);
        Step step3 = new Step("Step3",0.,1.,1.);
        Step step4 = new Step("Step4",0.,4.,1.5);
        List<Step> stp = new ArrayList<>();
        stp.add(0,step1);stp.add(0,step2);stp.add(0,step3);stp.add(0,step4);

        //Initialize Adder
        Adder ad = new Adder("Ad",1,Double.POSITIVE_INFINITY);

        //Initialize Events
        Event ev = new Event();
        ev.add("Adder",Boolean.FALSE);
        ev.add("Step1",Boolean.FALSE);
        ev.add("Step2",Boolean.FALSE);
        ev.add("Step3",Boolean.FALSE);
        ev.add("Step4",Boolean.FALSE);
        ev.add("Derivative_d",Boolean.FALSE);

        //Initialize View
        ChartFrame cf = new ChartFrame("Résultat","Discrete intégrator");
        Chart Sum = new Chart("Sum");
        cf.addToLineChartPane(Sum);
        Chart X = new Chart("X");
        cf.addToLineChartPane(X);

        //For t = 0 do
        ad.init(stp);
        res = ad.get_sum();
        Sum.addDataToSeries(t,res);

        //Initialize Integrator
        Integrator_discrete I = new Integrator_discrete("I",1,0,ad.get_sum(),0.01);
        X.addDataToSeries(t,I.get_X());

        Comp.add(0,step1);Comp.add(1,step2);Comp.add(2,step3);Comp.add(3,step4);Comp.add(4,ad);Comp.add(5,I);

        while(t<2){
            //Local variable
            List<Component> imms = new ArrayList<>();
            List<Component> ins = new ArrayList<>();

            //Stocking the minimum time response
            tr_min = Double.min(ad.get_tr(),Double.min(step1.get_tr(),Double.min(step2.get_tr(),Double.min(step3.get_tr(),Double.min(I.get_tr(),step4.get_tr())))));

            for (Component component : Comp) {
                if (component.get_tr() == tr_min) {
                    imms.add(imms.size(), component);
                }
            }

            //Adding the minimum time response to the time
            t = t + tr_min;

            //Setting minimum time for all component
            for (Component component : Comp) {
                component.set_e(t - component.get_tl());
                component.set_tr(component.time() - component.get_e());
            }

            //Making the output function for imms (lambda)
            for (Component imm : imms) {
                imm.output(ev);
            }

            //Adding component who can change with output
            if(ev.get_str("Step1")){
                ins.add(0,ad);
            }
            if(ev.get_str("Step2")){
                ins.add(ins.size(),ad);
            }
            if(ev.get_str("Step3")){
                ins.add(ins.size(),ad);
            }
            if(ev.get_str("Step4")){
                ins.add(ins.size(),ad);
            }
            if(ev.get_str("Adder")){
                ins.add(ins.size(),I);
            }

            for (Component component : Comp) {
                boolean in_imms = Boolean.FALSE, in_ins = Boolean.FALSE;
                for (Component imm : imms) {
                    if (component.get_name().equals(imm.get_name())) {
                        in_imms = Boolean.TRUE;
                        break;
                    }
                }
                for (Component in : ins) {
                    if (component.get_name().equals(in.get_name())) {
                        in_ins = Boolean.TRUE;
                        break;
                    }
                }
                if (in_imms && !in_ins) {
                    component.intern(ev);
                    component.set_e(0);
                    component.set_tl(t);
                    component.set_tn(component.get_tl() + component.get_e());
                    component.set_tr(component.time() - component.get_e());
                } else if (!in_imms && in_ins) {
                    component.extern(ev);
                    component.set_e(0);
                    component.set_tl(t);
                    component.set_tn(component.get_tl() + component.get_e());
                    component.set_tr(component.time() - component.get_e());
                } else if (in_imms && in_ins) {
                    component.conflict(ev);
                    component.set_e(0);
                    component.set_tl(t);
                    component.set_tn(component.get_tl() + component.get_e());
                    component.set_tr(component.time() - component.get_e());
                }
            }

            //Load data to view
            Sum.addDataToSeries(t,ad.get_sum());
            if (t != Double.POSITIVE_INFINITY){
                X.addDataToSeries(t,I.get_X());
            }
        }
    }

    public void ODE(){

        //Initialisation

        //Intialize Constante
        Constante Cons = new Constante("Cons",-9.81);

        //Initialize Events
        Event ev = new Event();
        ev.add("Cons",Boolean.FALSE);
        ev.add("Derivative_1",Boolean.FALSE);
        ev.add("Derivative_2",Boolean.FALSE);
        ev.add("Adder", Boolean.FALSE);
        ev.add("change",Boolean.FALSE);
        ev.add("Neg",Boolean.FALSE);

        //Initialize View
        ChartFrame cf = new ChartFrame("Résultat","ODE");
        Chart Sum = new Chart("I1");
        cf.addToLineChartPane(Sum);
        Chart X = new Chart("I2");
        cf.addToLineChartPane(X);

        //Initialize Integrators
        Integrator_discrete2 I1 = new Integrator_discrete2("Derivative_1",1,0,0,Double.POSITIVE_INFINITY,0.01);
        Integrator_discrete I2 = new Integrator_discrete("Derivative_2",1,10,10,Double.POSITIVE_INFINITY,0.01);

        Comp.add(0,Cons);Comp.add(1,I1);Comp.add(2,I2);

        while(t<5){
            //Local variable
            List<Component> imms = new ArrayList<>();
            List<Component> ins = new ArrayList<>();

            //Stocking the minimum time response
            tr_min = Double.min(I1.get_tr(),Double.min(I2.get_tr(),Cons.get_tr()));

            for (Component component : Comp) {
                if (component.get_tr() == tr_min) {
                    imms.add(imms.size(), component);
                }
            }

            //Adding the minimum time response to the time
            t = t + tr_min;

            //Setting minimum time for all component
            for (Component component : Comp) {
                component.set_e(t - component.get_tl());
                component.set_tr(component.time() - component.get_e());
            }

            //Making the output function for imms (lambda)
            for (Component imm : imms) {
                imm.output(ev);
            }

            //Adding component who can change with output
            if(ev.get_str("Cons")){
                ins.add(0,I1);
            }
            if(ev.get_str("Derivative_1")){
                ins.add(ins.size(),I2);
            }


            for (Component component : Comp) {
                boolean in_imms = Boolean.FALSE, in_ins = Boolean.FALSE;
                for (Component imm : imms) {
                    if (component.get_name().equals(imm.get_name())) {
                        in_imms = Boolean.TRUE;
                        break;
                    }
                }
                for (Component in : ins) {
                    if (component.get_name().equals(in.get_name())) {
                        in_ins = Boolean.TRUE;
                        break;
                    }
                }
                if (in_imms && !in_ins) {
                    component.intern(ev);
                    component.set_e(0);
                    component.set_tl(t);
                    component.set_tn(component.get_tl() + component.get_e());
                    component.set_tr(component.time() - component.get_e());
                } else if (!in_imms && in_ins) {
                    component.extern(ev);
                    component.set_e(0);
                    component.set_tl(t);
                    component.set_tn(component.get_tl() + component.get_e());
                    component.set_tr(component.time() - component.get_e());
                } else if (in_imms && in_ins) {
                    component.conflict(ev);
                    component.set_e(0);
                    component.set_tl(t);
                    component.set_tn(component.get_tl() + component.get_e());
                    component.set_tr(component.time() - component.get_e());
                }
            }

            //Load data to View
            if (t != Double.POSITIVE_INFINITY){
                Sum.addDataToSeries(t,I1.get_X());
                X.addDataToSeries(t,I2.get_X());
            }
        }
    }

    public void BB(){

        //Initialization

        //Initialize Constante and Comparator
        Constante Cons = new Constante("Cons",-9.81);
        Connector Com = new Connector("C1",1,Double.POSITIVE_INFINITY);

        //Initialize Events
        Event ev = new Event();
        ev.add("Cons",Boolean.FALSE);
        ev.add("Derivative_1",Boolean.FALSE);
        ev.add("Derivative_2",Boolean.FALSE);
        ev.add("Adder",Boolean.FALSE);
        ev.add("Neg",Boolean.FALSE);
        ev.add("change",Boolean.FALSE);

        //Initialize View
        ChartFrame cf = new ChartFrame("Résultat","Bouncing Ball");
        Chart Sum = new Chart("I1");
        cf.addToLineChartPane(Sum);
        Chart X = new Chart("I2");
        cf.addToLineChartPane(X);

        //Initialize Integrators
        Integrator_discrete2 I1 = new Integrator_discrete2("Derivative_1",1,0,0,Double.POSITIVE_INFINITY,0.01);
        Integrator_discrete I2 = new Integrator_discrete("Derivative_2",1,10,0,Double.POSITIVE_INFINITY,0.001);

        Comp.add(0,Cons);Comp.add(1,I1);Comp.add(2,I2);Comp.add(3,Com);

        while(t<5){
            //Local variable
            List<Component> imms = new ArrayList<>();
            List<Component> ins = new ArrayList<>();

            //Stocking the minimum time response
            tr_min = Double.min(I1.get_tr(),Double.min(I2.get_tr(),Cons.get_tr()));

            for (Component component : Comp) {
                if (component.get_tr() == tr_min) {
                    imms.add(imms.size(), component);
                }
            }

            //Adding the minimum time response to the time
            t = t + tr_min;

            //Setting minimum time for all component
            for (Component component : Comp) {
                component.set_e(t - component.get_tl());
                component.set_tr(component.time() - component.get_e());
            }

            //Making the output function for imms (lambda)
            for (Component imm : imms) {
                imm.output(ev);
            }

            //Adding component who can change with output
            if(ev.get_str("Cons") || (ev.get_str("change"))){
                ins.add(0,I1);
            }
            if(ev.get_str("Derivative_1")){
                ins.add(ins.size(),I2);
            }
            if(ev.get_str("Neg")){
                ins.add(ins.size(),Com);
            }


            for (Component component : Comp) {
                boolean in_imms = Boolean.FALSE, in_ins = Boolean.FALSE;
                for (Component imm : imms) {
                    if (component.get_name().equals(imm.get_name())) {
                        in_imms = Boolean.TRUE;
                        break;
                    }
                }
                for (Component in : ins) {
                    if (component.get_name().equals(in.get_name())) {
                        in_ins = Boolean.TRUE;
                        break;
                    }
                }
                if (in_imms && !in_ins) {
                    component.intern(ev);
                    component.set_e(0);
                    component.set_tl(t);
                    component.set_tn(component.get_tl() + component.get_e());
                    component.set_tr(component.time() - component.get_e());
                } else if (!in_imms && in_ins) {
                    component.extern(ev);
                    component.set_e(0);
                    component.set_tl(t);
                    component.set_tn(component.get_tl() + component.get_e());
                    component.set_tr(component.time() - component.get_e());
                } else if (in_imms && in_ins) {
                    component.conflict(ev);
                    component.set_e(0);
                    component.set_tl(t);
                    component.set_tn(component.get_tl() + component.get_e());
                    component.set_tr(component.time() - component.get_e());
                }
            }

            //Load data to View
            if (t != Double.POSITIVE_INFINITY){
                Sum.addDataToSeries(t,I1.get_X());
                X.addDataToSeries(t,I2.get_X());
            }
        }
    }
}


