/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Praveen
 */
public class Critical_Section {
    
    Mutex mutex;
    
    int sharedVarible=0;
    
    Critical_Section(Mutex m)
    {
        mutex = m; 
    }
    
    void executeCS()
    {
        sharedVarible++;
    }
    
    void enterCriticalSection(ThreadGenerator t)
    {
        mutex.lock(t);
        executeCS();
        mutex.unlock(t);
    }
}
