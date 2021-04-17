/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ffcrapssimulator;

import java.util.Random;

/**
 *
 * @author Ryan
 */

public class FFCrapsSimulator {
    
    public final String CARDS[] = {"BALANCE", "EWER", "BOLE", "SPEAR", "ARROW", "SPIRE"};
    public double houseMoney = 1000000;
    public boolean noPassPush = false;
    
    public class Astro {
        String thisCard; 
        public Astro(){ 
            thisCard = "";
        }
        public String draw() {
            thisCard = CARDS[(int)(Math.round(Math.random()*100)%6)];                
            //System.out.println("Draw: " + thisCard);
            return thisCard;
        }
        public String reDraw() {
            String drawnCard = "NULL";
            do {
                drawnCard = CARDS[(int)(Math.round(Math.random()*100)%6)];
                //if(drawnCard == thisCard)
                    //System.out.println("Drew same card " + drawnCard + ": Drawing again");
            } while(drawnCard == thisCard);
            
            thisCard = drawnCard;
            //System.out.println("Redraw: " + thisCard);
            return thisCard;
        }
    }
    public FFCrapsSimulator() {
        
        double edge = 7;
        do{
            double houseWinCounter = 0;
            houseMoney = 1000000;
            double houseInitialTotal = houseMoney;
            double startingTotal;
            double profit = 0;
            
            for(int i=0; i< 100000; i++) {
                startingTotal = houseMoney;
                gamePlay(edge);
                if(startingTotal < houseMoney) houseWinCounter++;
                //System.out.println("House Total: " + houseMoney);
            }
//            System.out.println("House Initial was: " + houseInitialTotal);
//            System.out.println("House Finishing Total was: " + houseMoney);
            profit = houseMoney - houseInitialTotal;
            System.out.println("House profit: " + profit);
            System.out.println("House wins " + houseWinCounter + " times");
//            if(profit > 0) edge += 0.01;
//            else edge -= 0.01;
//            System.out.println("Edge is " + edge);
        }while(true);
    }
    public void gamePlay(double edge) {
        Astro astro = new Astro();
        double passLineBet = 0;
        double noPassBet = 1000; //only on comeout roll.
        String pointOne, pointTwo, pointThree;
        
        if(!noPassPush) {
            houseMoney += noPassBet; //the comeout roll bet is made.
        }
        noPassPush = false;
        //Comeout roll
        String point = astro.draw();
        if(point == "EWER") {
            houseMoney -= passLineBet*2; //1:1 payout. initial bet returned plus initial bet.
            return;
        }
        else if(point == "SPIRE") {
            noPassPush = true;
            return;
        }
        
        //Point has been set;
        pointOne = astro.draw();
        if(pointOne == point) {
            houseMoney -= passLineBet*3; //2:1 payout first roll. initial bet plus 2*bet returned.
            return;
        }
        else if(pointOne == "SPIRE") return;

        //redraw 1
        pointTwo = astro.reDraw();
        if(pointTwo == point) {
            houseMoney -= passLineBet*2; //1:1 payout. initial bet returned plus initial bet.
            return;
        }
        else if(pointTwo == "SPIRE") return;
        
        //redraw 2
        pointThree = astro.reDraw();
        if(pointThree == point) {
            houseMoney -= passLineBet*2; //1:1 payout. initial bet returned plus initial bet.
            return;
        }
        else if(pointThree == "SPIRE") return;

        houseMoney -= noPassBet*5; //4:1 payout. initial bet returned plus initial bet.
    }
    public static void main(String... args) throws Exception {   
        FFCrapsSimulator ewer = new FFCrapsSimulator();
    }
}