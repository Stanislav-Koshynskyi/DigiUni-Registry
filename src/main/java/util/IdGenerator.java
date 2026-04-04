package util;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private final AtomicLong idGenerator;

    public IdGenerator(){
        idGenerator = new AtomicLong(0);
    }
    public Long nextId(){
        return idGenerator.getAndIncrement();
    }
    public Long getId(){
        return idGenerator.get();
    }
    public void setCurrentId(Long id){
        idGenerator.set(id);
    }
}
