/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Praveen
 */
public interface Mutex {
    
    public void lock(ThreadGenerator t);
    
    public void unlock(ThreadGenerator t);
}
