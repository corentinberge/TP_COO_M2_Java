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
    private double t_fin;
    private double tr_min;
    private double ts_min;
    private double old_ts;

    //Functions
    public Simulator() {}

    public Simulator(double f){
        t_fin = 20;
    }

    public void ordonnanceur(){
        List<Component> Comp = new ArrayList<Component>();
        Generator g;
        Buffer b;
        Processor p;
        Event ev = new Event();
        int[] list_B,list_G,list_P;

        list_B = new int[1];list_B[0] = 1;
        list_G = new int[3];list_G[0] = 1;list_G[1] = 2;list_G[2] = 3;
        list_P = new int[2];list_P[0] = 1;list_P[1] = 2;
        b = new Buffer("B",list_B,1,Double.POSITIVE_INFINITY);
        g = new Generator("G",list_G,1,2);
        p = new Processor("P",list_P,1,Double.POSITIVE_INFINITY);

        Comp.add(0,b);Comp.add(1,g);Comp.add(2,p);

        //Intialize event
        ev.add("done",Boolean.FALSE);
        ev.add("job",Boolean.FALSE);
        ev.add("req",Boolean.FALSE);

        afficheur_ordo(b,g,p,ev);

        /*ChartFrame cf = new ChartFrame("Résultat","GBP");
        Chart Cq = new Chart("q");
        cf.addToLineChartPane(Cq);*/

        while(t<t_fin){
            //Local variable
            List<Component> imms = new ArrayList<Component>();
            List<Component> ins = new ArrayList<Component>();

            //Stocking the minimum time response
            tr_min = Double.min(b.get_tr(),Double.min(g.get_tr(),p.get_tr()));

            for(int i = 0;i<Comp.size();i++){
                if(Comp.get(i).get_tr() == tr_min){
                    imms.add(imms.size(),Comp.get(i));
                }
            }

            //Adding the minimum time response to the time
            t = t + tr_min;

            //Setting minimum time for all component
            for(int i = 0;i<Comp.size();i++){
                Comp.get(i).set_e(t);
                Comp.get(i).set_tr(t);
            }

            //Making the output function for imms (lambda)
            for(int i = 0;i<imms.size();i++){
                imms.get(i).output(ev);
            }

            //Adding component who can change with output
            if(ev.get_str("done")){
                ins.add(ins.size(),b);
            }
            if(ev.get_str("job")){
                ins.add(ins.size(),b);
            }
            if(ev.get_str("req")){
                ins.add(ins.size(),p);
            }

            for(int i = 0;i<Comp.size();i++){
                Boolean in_imms=Boolean.FALSE,in_ins=Boolean.FALSE;
                for(int j = 0;j<imms.size();j++){
                    if(Comp.get(i).get_name().equals(imms.get(j).get_name())){
                        in_imms = Boolean.TRUE;
                    }
                }
                for(int j = 0;j<ins.size();j++){
                    if(Comp.get(i).get_name().equals(ins.get(j).get_name())){
                        in_ins = Boolean.TRUE;
                    }
                }
                if(in_imms && !in_ins){
                    Comp.get(i).intern(ev);
                    Comp.get(i).set_tl();
                    Comp.get(i).set_tn();
                    Comp.get(i).set_tr(t);
                    Comp.get(i).set_e(t);
                }
                else if(!in_imms && in_ins){
                    Comp.get(i).extern(ev);
                    Comp.get(i).set_tl();
                    Comp.get(i).set_tn();
                    Comp.get(i).set_tr(t);
                    Comp.get(i).set_e(t);
                }
                else if(in_imms && in_ins){
                    Comp.get(i).conflict(ev);
                    System.out.print(Comp.get(i).get_name() + "\n");
                    Comp.get(i).set_tl();
                    Comp.get(i).set_tn();
                    Comp.get(i).set_tr(t);
                    Comp.get(i).set_e(t);
                }
            }
            /*afficheur_ordo(b,g,p,ev);
            Cq.addDataToSeries(t,b.get_q());
            ev.reset();*/
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
        List<Component> Comp = new ArrayList<Component>();
        Double res;
        Step step1 = new Step("step1",1.,-3.,0.65);
        Step step2 = new Step("step2",0.,1.,0.35);
        Step step3 = new Step("step3",0.,1.,1.);
        Step step4 = new Step("step4",0.,4.,1.5);

        int[] list_A;
        list_A = new int[2];list_A[0] = 1;list_A[1] = 2;

        List<Step> stp = new ArrayList<Step>();
        stp.add(0,step1);stp.add(0,step2);stp.add(0,step3);stp.add(0,step4);

        Adder ad = new Adder("Ad",list_A,1,Double.POSITIVE_INFINITY);

        Event ev = new Event();
        ev.add("Adder",Boolean.FALSE);
        ev.add("step1",Boolean.FALSE);
        ev.add("step2",Boolean.FALSE);
        ev.add("step3",Boolean.FALSE);
        ev.add("step4",Boolean.FALSE);

        ChartFrame cf = new ChartFrame("Résultat","Somme");
        Chart Sum = new Chart("Sum");
        cf.addToLineChartPane(Sum);

        Comp.add(0,step1);Comp.add(1,step2);Comp.add(2,step3);Comp.add(3,step4);Comp.add(4,ad);

        ad.init(stp);
        res = ad.get_sum();
        Sum.addDataToSeries(t,res);

        //System.out.print("Sum : " + ad.get_sum() + "\n\n");

        while(t<1.5){
            //Local variable
            List<Component> imms = new ArrayList<Component>();
            List<Component> ins = new ArrayList<Component>();

                //Stocking the minimum time response
            tr_min = Double.min(ad.get_tr(),Double.min(step1.get_tr(),Double.min(step2.get_tr(),Double.min(step3.get_tr(),step4.get_tr()))));

            for(int i = 0;i<Comp.size();i++){
                if(Comp.get(i).get_tr() == tr_min){
                    imms.add(imms.size(),Comp.get(i));
                }
            }

            //Adding the minimum time response to the time
            t = t + tr_min;

            //Setting minimum time for all component
            for(int i = 0;i<Comp.size();i++){
                Comp.get(i).set_e(t);
                Comp.get(i).set_tr(tr_min);
            }

            //Making the output function for imms (lambda)
            for(int i = 0;i<imms.size();i++){
                imms.get(i).output(ev);
            }

            //Adding component who can change with output
            if(ev.get_str("step1")){
                ins.add(ins.size(),ad);
            }
            if(ev.get_str("step2")){
                ins.add(ins.size(),ad);
            }
            if(ev.get_str("step3")){
                ins.add(ins.size(),ad);
            }
            if(ev.get_str("step4")){
                ins.add(ins.size(),ad);
            }

            for(int i = 0;i<Comp.size();i++){
                Boolean in_imms=Boolean.FALSE,in_ins=Boolean.FALSE;
                for(int j = 0;j<imms.size();j++){
                    if(Comp.get(i).get_name().equals(imms.get(j).get_name())){
                        in_imms = Boolean.TRUE;
                    }
                }
                for(int j = 0;j<ins.size();j++){
                    if(Comp.get(i).get_name().equals(ins.get(j).get_name())){
                        in_ins = Boolean.TRUE;
                    }
                }
                if(in_imms && !in_ins){
                    Comp.get(i).intern(ev);
                    Comp.get(i).set_tl();
                    Comp.get(i).set_tn();
                    Comp.get(i).set_tr(t);
                    Comp.get(i).set_e(t);
                }
                else if(!in_imms && in_ins){
                    Comp.get(i).extern(ev);
                    Comp.get(i).set_tl();
                    Comp.get(i).set_tn();
                    Comp.get(i).set_tr(t);
                    Comp.get(i).set_e(t);
                }
                else if(in_imms && in_ins){
                    Comp.get(i).conflict(ev);
                    Comp.get(i).set_tl();
                    Comp.get(i).set_tn();
                    Comp.get(i).set_tr(t);
                    Comp.get(i).set_e(t);
                }
            }
            System.out.print("T : "+ t +"\t Sum : " + ad.get_sum() + "\n\n");
            Sum.addDataToSeries(t,ad.get_sum());
            ev.reset();


        }
    }

    public void integrate() {

        //Initialisation
        List<Component> Comp = new ArrayList<Component>();
        Double res;
        Step step1 = new Step("step1", 1., -3., 0.65);
        Step step2 = new Step("step2", 0., 1., 0.35);
        Step step3 = new Step("step3", 0., 1., 1.);
        Step step4 = new Step("step4", 0., 4., 1.5);

        int[] list_A;
        list_A = new int[2];
        list_A[0] = 1;
        list_A[1] = 2;

        List<Step> stp = new ArrayList<Step>();
        stp.add(0, step1);
        stp.add(0, step2);
        stp.add(0, step3);
        stp.add(0, step4);

        Adder ad = new Adder("Ad", list_A, 1, Double.POSITIVE_INFINITY);

        Event ev = new Event();
        ev.add("Adder", Boolean.FALSE);
        ev.add("step1", Boolean.FALSE);
        ev.add("step2", Boolean.FALSE);
        ev.add("step3", Boolean.FALSE);
        ev.add("step4", Boolean.FALSE);
        ev.add("Derivative",Boolean.FALSE);

        ChartFrame cf = new ChartFrame("Résultat", "Somme");
        Chart X = new Chart("X");
        cf.addToLineChartPane(X);

        ad.init(stp);
        ad.output(ev);

        Integrator I = new Integrator("Integrator",list_A,1,0.05,ad.get_sum());

        Comp.add(0, step1);
        Comp.add(1, step2);
        Comp.add(2, step3);
        Comp.add(3, step4);
        Comp.add(4, ad);
        Comp.add(5,I);

        //System.out.print("Sum : " + ad.get_sum() + "\n\n");

        while (t < 2) {
            //Local variable
            List<Component> imms = new ArrayList<Component>();
            List<Component> ins = new ArrayList<Component>();

            //Stocking the minimum time response
            tr_min = Double.min(I.get_tr(),Double.min(ad.get_tr(), Double.min(step1.get_tr(), Double.min(step2.get_tr(), Double.min(step3.get_tr(), step4.get_tr())))));

            for (int i = 0; i < Comp.size(); i++) {
                if (Comp.get(i).get_tr() == tr_min) {
                    imms.add(imms.size(), Comp.get(i));
                }
            }

            //Adding the minimum time response to the time
            t = t + tr_min;

            //Setting minimum time for all component
            for (int i = 0; i < Comp.size(); i++) {
                Comp.get(i).set_e(t);
                Comp.get(i).set_tr(t);
            }

            //Making the output function for imms (lambda)
            for (int i = 0; i < imms.size(); i++) {
                imms.get(i).output(ev);
            }

            //Adding component who can change with output
            if (ev.get_str("step1")) {
                ins.add(ins.size(), ad);
            }
            if (ev.get_str("step2")) {
                ins.add(ins.size(), ad);
            }
            if (ev.get_str("step3")) {
                ins.add(ins.size(), ad);
            }
            if (ev.get_str("step4")) {
                ins.add(ins.size(), ad);
            }
            if (ev.get_str("Derivative")){
                ins.add(ins.size(),I);
            }

            for (int i = 0; i < Comp.size(); i++) {
                Boolean in_imms = Boolean.FALSE, in_ins = Boolean.FALSE;
                for (int j = 0; j < imms.size(); j++) {
                    if (Comp.get(i).get_name().equals(imms.get(j).get_name())) {
                        in_imms = Boolean.TRUE;
                    }
                }
                for (int j = 0; j < ins.size(); j++) {
                    if (Comp.get(i).get_name().equals(ins.get(j).get_name())) {
                        in_ins = Boolean.TRUE;
                    }
                }
                if (in_imms && !in_ins) {
                    Comp.get(i).intern(ev);
                    Comp.get(i).set_tl();
                    Comp.get(i).set_tn();
                    Comp.get(i).set_tr(t);
                    Comp.get(i).set_e(t);
                } else if (!in_imms && in_ins) {
                    Comp.get(i).extern(ev);
                    Comp.get(i).set_tl();
                    Comp.get(i).set_tn();
                    Comp.get(i).set_tr(t);
                    Comp.get(i).set_e(t);
                } else if (in_imms && in_ins) {
                    Comp.get(i).conflict(ev);
                    Comp.get(i).set_tl();
                    Comp.get(i).set_tn();
                    Comp.get(i).set_tr(t);
                    Comp.get(i).set_e(t);
                }
            }
            System.out.print("T : " + t + "\t Sum : " + ad.get_sum() + "\tStep : " + I.get_step() + "\n\n");
            X.addDataToSeries(t,I.get_X());
            ev.reset();


        }
    }
}


