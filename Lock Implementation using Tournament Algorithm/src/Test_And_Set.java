

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.atomic.AtomicInteger;

public class Test_And_Set implements Mutex {
    
    AtomicInteger m_lock= new AtomicInteger(0);

    @Override
    public void lock(ThreadGenerator t)
    {
        //System.out.println("I, "+t.getName()+" is trying to acquire lock");
        while(m_lock.getAndSet(1)==1);
    }
    
    @Override
    public void unlock(ThreadGenerator t)
    {
        //System.out.println("I, "+t.getName()+" is leaving  critical section");
        m_lock  = new AtomicInteger(0);
    }
}
