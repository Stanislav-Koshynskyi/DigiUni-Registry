package repository;

import entity.Entity;
import util.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepositoryByLong<T extends Entity> implements Repository<T, Long>{
    private final IdGenerator idGenerator = new IdGenerator();
    private final HashMap<Long, T> data = new HashMap<>();
    public AbstractRepositoryByLong(){}

    public AbstractRepositoryByLong(long id){
        idGenerator.setCurrentId(id);
    }
    @Override
    public Optional<T> findById(Long id){
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<T> findAll(){
        return new ArrayList<>(data.values());
    }

    @Override
    public synchronized T save(T entity){
        if (entity.getId() == null) {
            entity.setId(idGenerator.nextId());
        }
        data.put(entity.getId(), entity);
        return entity;
    }
    public long getCurrentId(){
        return idGenerator.getId();
    }
    public long setCurrentId(long id){
        idGenerator.setCurrentId(id);
        return idGenerator.getId();
    }
    @Override
    public void delete(T entity){
        data.remove(entity.getId());
    }
    @Override
    public void deleteById(Long id){
        data.remove(id);
    }
    @Override
    public boolean existsById(Long id){
        return data.containsKey(id);
    }

    protected HashMap<Long, T> getData(){
        return data;
    }

}
