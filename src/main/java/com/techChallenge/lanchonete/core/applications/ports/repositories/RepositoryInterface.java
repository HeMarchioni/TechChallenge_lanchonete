package com.techChallenge.lanchonete.core.applications.ports.repositories;

import java.util.List;

public interface RepositoryInterface <Obj,Long>  {

    void save(Obj obj);
    Obj findById(Long id);
    List<Obj> findAll();
    boolean existsById(Long id);
    void deleteById(Long id);

}
