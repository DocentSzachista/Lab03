package model;

import java.util.Map;

public interface DAO<T> 
{
    <T> T get(int id);
    
    Map<Integer,T> getAll();
    
    void save(T t);
    
    void update(T t, String[] params);
    
    void delete(T t);

}
