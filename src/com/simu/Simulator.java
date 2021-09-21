package com.simu;

import com.comp.Buffer;
import com.comp.Component;
import com.comp.Generator;
import com.comp.Processor;
import com.evenement.Event;

import java.util.ArrayList;
import java.util.List;

public class Simulator {

    //Attributes
    private double t = 0;
    private double t_fin;
    private double tr_min;

    //Functions
    public Simulator() {}

    public Simulator(double f){
        t_fin = f;
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
            if(ev.get("done")){
                ins.add(ins.size(),b);
            }
            if(ev.get("job")){
                ins.add(ins.size(),b);
            }
            if(ev.get("req")){
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
            afficheur_ordo(b,g,p,ev);
            ev.reset();
        }
    }

    public void afficheur_ordo(Buffer b,Generator g,Processor p, Event ev){
        System.out.print("t = " + t);
        System.out.print("\nq = " + b.get_q());
        if(p.get_current() == 2){
            System.out.print("\ndone|" + p.get_tr() + " - " + ev.get("done"));
        }
        System.out.print("\njob|" + g.get_tr() + " - " + ev.get("job"));
        if(b.get_current() == 2){
            System.out.print("\nreq|" + b.get_tr() + " - " + ev.get("req"));
        }
        System.out.print("\nb : " + b.get_current());
        System.out.print("\ng : " + g.get_current());
        System.out.print("\np : " + p.get_current());
        System.out.print("\n\n");
    }
}


