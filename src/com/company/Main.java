package com.company;

import com.simu.Simulator;

public class Main {



    public static void main(String[] args) {

        //System.out.print("--- PROGRAMME DE AGATHE ET CORENTIN ---\n\n\n");

        //System.out.print("Menu principal, rentrez un chiffre pour simuler :\n1- RGB\n2- consigne\n");


        Simulator s = new Simulator(10);

        //s.ordonnanceur();

        //s.consigne();

        s.integrate();
    }
}
