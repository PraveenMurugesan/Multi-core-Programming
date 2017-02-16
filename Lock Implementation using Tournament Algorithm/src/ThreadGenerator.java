/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Praveen
 */
public class ThreadGenerator extends Thread {

    Critical_Section cs;
    int nodeID;
    int threadID;
    int pID[]; 
    public ThreadGenerator(Critical_Section cs, int threadId) {
        this.cs = cs;
        nodeID = 0;
        threadID = threadId;
        pID = new int[Main.levels+1];
    }
    
    public void run()
    {
        for (int i=0; i<Main.noOfExecutions;i++)
        {
            cs.enterCriticalSection(this);
        }
    }
}
