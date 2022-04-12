package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Algorithms {

    private int pagesNum;
    private int framesNum;
    private ArrayList<Integer> req;

    public Algorithms(int pagesNum, int framesNum, ArrayList<Integer> req) {
        this.pagesNum = pagesNum;
        this.framesNum = framesNum;
        this.req = req;
    }

    private ArrayList<Integer> createFrames() {
        ArrayList<Integer> frames = new ArrayList<>();
        for(int i = 0; i<framesNum; i++) {
            frames.add(-1);
        }
        return frames;
    }

    private ArrayList<Page> createPages() {
        ArrayList<Page> pages = new ArrayList<>();
        for(int i = 0; i<pagesNum; i++) {
            pages.add(new Page(i));
        }
        return pages;
    }

    public int runFIFO() {
        int errors = 0;
        int time = 0;
        ArrayList<Page> pages = createPages();
        ArrayList<Integer> frames = createFrames();
        boolean done;
        int max;
        int toDelete;

        for(Integer r : req) {
            done = false;
            for(int i = 0; i<framesNum; i++) {
                if(frames.get(i) == r) {
                    done = true;
                    break;
                }
            }

            if(!done) {
                for(int i = 0; i<framesNum; i++) {
                    if(frames.get(i) == -1) {
                        frames.set(i, r);
                        pages.get(r).setArrivalTime(time);
                        errors++;
                        done = true;
                        break;
                    }
                }
            }

            if(!done) {
                max = time - pages.get(frames.get(0)).getArrivalTime();
                toDelete = 0;

                for(int i = 0; i<framesNum; i++) {
                    if(time - pages.get(frames.get(i)).getArrivalTime() > max) {
                        max = time - pages.get(frames.get(i)).getArrivalTime();
                        toDelete = i;
                    }
                }
                frames.set(toDelete, r);
                pages.get(r).setArrivalTime(time);
                errors++;
            }
            time++;
        }
        return errors;
    }

    public int runOPT() {
        int errors = 0;
        int time = 0;
        ArrayList<Page> pages = createPages();
        ArrayList<Integer> frames = createFrames();
        boolean done;
        int max;
        int toDelete;

        for(Integer r : req) {
            done = false;
            for(Integer frame : frames) {
                if(frame == r) {
                    done = true;
                    break;
                }
            }

            if(!done) {
                for(int i = 0; i<framesNum; i++) {
                    if(frames.get(i) == -1) {
                        frames.set(i, r);
                        pages.get(r).setArrivalTime(time);
                        errors++;
                        done = true;
                        break;
                    }
                }
            }

            if(!done) {
                max = 0;
                toDelete = 0;

                for(int i = 0; i<framesNum; i++) {
                    for(int j = time; j<req.size(); j++) {
                        if(frames.get(i) == req.get(j)) {
                            if(max < j - time) {
                                max = j - time;
                                toDelete = i;
                            }
                            break;
                        }
                    }
                }
                frames.set(toDelete, r);
                pages.get(r).setArrivalTime(time);
                errors++;
            }
            time++;
        }

        return errors;
    }

    public int runLRU() {
        int errors = 0;
        int time = 0;
        ArrayList<Page> pages = createPages();
        ArrayList<Integer> frames = createFrames();
        boolean done;
        int max;
        int toDelete;

        for(Integer r : req) {
            done = false;
            for (int i = 0; i < framesNum; i++) {
                if (frames.get(i) == r) {
                    pages.get(r).setLastUsedTime(time);
                    done = true;
                    break;
                }
            }
            if(!done) {
                for(int i = 0; i<framesNum; i++) {
                    if(frames.get(i) == -1) {
                        frames.set(i, r);
                        pages.get(r).setArrivalTime(time);
                        pages.get(r).setLastUsedTime(time);
                        errors++;
                        done = true;
                        break;
                    }
                }
            }
            if(!done) {
                max = 0;
                toDelete = -1;

                for(int i = 0; i<framesNum; i++) {
                    if(time - pages.get(frames.get(i)).getLastUsedTime() > max) {
                        toDelete = i;
                        max = time - pages.get(frames.get(i)).getLastUsedTime();
                    }
                }
                frames.set(toDelete, r);
                pages.get(r).setArrivalTime(time);
                pages.get(r).setLastUsedTime(time);
                errors++;
            }
            time++;
        }
        return errors;
    }

    public int runSCA() {
        int errors = 0;
        int time = 0;
        ArrayList<Page> pages = createPages();
        ArrayList<Integer> frames = createFrames();
        boolean done;
        Page first;
        Page toDelete;

        Queue<Page> queue = new LinkedList<>();

        for(Integer r : req) {
            done = false;

            for(Integer frame : frames) {
                if(frame == r) {
                    done = true;
                    pages.get(r).setBit(1);
                    break;
                }
            }

            if(!done) {
                for(int i = 0; i<framesNum; i++) {
                    if(frames.get(i) == -1) {
                        frames.set(i, r);
                        pages.get(r).setBit(1);
                        queue.add(pages.get(r));
                        errors++;
                        done = true;
                        break;
                    }
                }
            }

            if(!done) {
                first = queue.poll();
                if(first.getBit() == 0) {
                    for(int i = 0; i<framesNum; i++) {
                        if(frames.get(i) == first.getId()) {
                            frames.set(i, r);
                            pages.get(r).setBit(1);
                            break;
                        }
                    }
                }
                else {
                    while (true) {
                        toDelete = first;

                        if(toDelete.getBit() == 0) {
                            break;
                        }
                        else {
                            toDelete.setBit(0);
                            queue.add(toDelete);
                            first = queue.poll();
                        }
                    }
                    for(int i = 0; i<framesNum; i++) {
                        if(frames.get(i) == toDelete.getId()) {
                            frames.set(i, r);
                            pages.get(r).setBit(1);
                            queue.add(pages.get(r));
                            break;
                        }
                    }
                }
                errors++;
            }
            time++;
        }
        return errors;
    }

    public int runRAND() {
        int errors = 0;
        int time = 0;
        ArrayList<Page> pages = createPages();
        ArrayList<Integer> frames = createFrames();
        boolean done;
        int toDelete;

        for(Integer r : req) {
            done = false;

            for(Integer frame : frames) {
                if(frame == r) {
                    done = true;
                    break;
                }
            }

            if(!done) {
                for(int i = 0; i<framesNum; i++) {
                    if(frames.get(i) == -1) {
                        frames.set(i, r);
                        errors++;
                        done = true;
                        break;
                    }
                }
            }

            if(!done) {
                Random rand = new Random();
                toDelete = rand.nextInt(0, framesNum);
                frames.set(toDelete, r);
                errors++;
            }
            time++;
        }
        return errors;
    }
}
