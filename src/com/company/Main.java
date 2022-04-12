package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static ArrayList<Integer> generator(int pagesNum, int amount, int maxSize, double probability, int maxAmount) {
        Random rand = new Random();
        ArrayList<Integer> req = new ArrayList<>();
        int n = 0;
        int randSize;

        while(n < amount) {
            if(rand.nextDouble(0,1) < probability) {
                ArrayList<Integer> temp = new ArrayList<>();
                randSize = rand.nextInt(1, maxSize);

                for(int i = 0; i < randSize; i++) {
                    temp.add(rand.nextInt(1, pagesNum));
                }
                for(int i = 0; i < rand.nextInt(0, maxAmount); i++) {
                    if(n >= amount) {
                        break;
                    }
                    n++;
                    req.add(temp.get(rand.nextInt(0, randSize)));
                }
            } else {
                req.add(rand.nextInt(0, pagesNum));
                n++;
            }
        }
        return req;
    }


    public static void main(String[] args) {
        int framesNum = 5;
        int pagesNum = 30;
	    ArrayList<Integer> req =  generator(pagesNum, 10000, 3, 0.2, 10);


        for(int i = framesNum; i<=pagesNum; i+=5) {
            Algorithms algo = new Algorithms(pagesNum, i, req);
            System.out.println("Ilosc stron: " + pagesNum + "\tIlosc ramek:" + i);
            System.out.println("FIFO errors:\s" + algo.runFIFO());
            System.out.println("OPT errors:\s" + algo.runOPT());
            System.out.println("LRU errors:\s" + algo.runLRU());
            System.out.println("Aproksymacja LRU errors:\s" + algo.runSCA());
            System.out.println("RAND errors:\s" + algo.runRAND());
            System.out.println("\n");
        }
    }
}
