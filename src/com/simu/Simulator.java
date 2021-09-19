package com.simu;

import com.comp.Buffer;
import com.comp.Generator;
import com.comp.Processor;
import com.evenement.Event;

import java.util.ArrayList;
import java.util.List;

public class Simulator {

    //Attributes
    private static double t = 0;
    private static double t_fin;
    private static double tr_min;

    //Functions
    public Simulator() {}

    public Simulator(double f){
        t_fin = f;
    }

    public static void ordonnanceur(){
        List Comp = new ArrayList();
        Generator g;
        Buffer b;
        Processor p;
        Event ev = new Event();
        int[] list_B,list_G,list_P;

        //Initialisation of variables
        ev.add("done",Boolean.FALSE);
        ev.add("job",Boolean.FALSE);
        ev.add("req",Boolean.FALSE);

        list_B = new int[1];list_B[0] = 1;
        list_G = new int[3];list_G[0] = 1;list_G[1] = 2;list_G[2] = 3;
        list_P = new int[2];list_P[0] = 1;list_P[1] = 2;
        b = new Buffer("B",list_B,1,2);
        g = new Generator("G",list_G,1,Double.POSITIVE_INFINITY);
        p = new Processor("P",list_P,1,Double.POSITIVE_INFINITY);

        Comp.add(0,b);Comp.add(1,g);Comp.add(2,p);

        while(t<t_fin){
            //Local variable
            List imms = new ArrayList();
            List ins = new ArrayList();

            //Stocking the minimum time response
            tr_min = Double.min(b.get_tr(),Double.min(g.get_tr(),p.get_tr()));

            /*for(int i = 0;i<Comp.size();i++){
                if(Comp.get(i).get_tr() == tr_min){

                }
            }*/
            if (b.get_tr() == tr_min){
                imms.add(imms.size(),b);
            }
            if (g.get_tr() == tr_min){
                imms.add(imms.size(),g);
            }
            if (p.get_tr() == tr_min){
                imms.add(imms.size(),p);
            }

            //Adding the minimum time response to the time
            t = t + tr_min;

            //Setting minimum time for all component
            b.set_e(t);
            g.set_e(t);
            p.set_e(t);

            b.set_tr();
            g.set_tr();
            p.set_tr();

            //Making the output function for imms (lambda)
            for(int i = 0;i<imms.size();i++){
                if(imms.get(i).get_name().equals("B")){

                }
            }

            //Output of all the component if there is an output
            b.output(ev);
            g.output(ev);
            p.output(ev);


            //Adding component who can change with output
            if(ev.get("done")){
                ins.add(ins.size(),b);
            }
            if(ev.get("job")){
                ins.add(ins.size(),b);
            }
            if(ev.get("req")){
                ins.add(ins.size(),p);
            }

            System.out.print(b.get_q());

            //Making extern function
        }
    }
}
