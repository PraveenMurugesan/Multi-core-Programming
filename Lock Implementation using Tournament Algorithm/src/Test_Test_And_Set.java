/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Praveen
 */
public class Test_Test_And_Set implements Mutex {

    AtomicInteger m_lock= new AtomicInteger(0);
    
    @Override
    public void lock(ThreadGenerator t) {
        while(true)
        {
            while(m_lock == new AtomicInteger(1));
            if(m_lock.getAndSet(1) == 0)
                break;
        }
    }

    @Override
    public void unlock(ThreadGenerator t) {
        m_lock = new AtomicInteger(0);
    }
    
}
