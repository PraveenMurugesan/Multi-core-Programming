/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Praveen
 */
class PietersenLock {

    private volatile boolean flag[] = {false, false};
    private volatile int victim;

    void lock(int i) {
        flag[i] = true;
        victim = i;
        while (flag[1 - i] && victim == i);
    }

    void unclock(int i) {
        flag[i] = false;
    }
}

public class Tournament implements Mutex {

    PietersenLock pLocks[];

    Tournament() {
        int noOfPLocks = (int) Math.pow(2, Main.levels) - 1;
        pLocks = new PietersenLock[noOfPLocks+1];
        for(int i =0 ; i < pLocks.length;i++)
        {
            pLocks[i] = new PietersenLock();
        }
    }

    @Override
    public void lock(ThreadGenerator t) {
        int nodeID = t.threadID + pLocks.length;
        for (int i = 1; i <= Main.levels; i++) {
            t.pID[i] = nodeID % 2;
            nodeID = nodeID / 2;
            pLocks[nodeID].lock(t.pID[i]);
        }
    }

    @Override
    public void unlock(ThreadGenerator t) {
        int nodeID = 1;
        for (int i = Main.levels; i >= 1; i--) {
            pLocks[nodeID].unclock(t.pID[i]);
            nodeID = 2 * nodeID + t.pID[i];
        }
    }
}
