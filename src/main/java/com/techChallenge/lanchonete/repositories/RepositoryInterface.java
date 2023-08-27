package com.techChallenge.lanchonete.repositories;

import java.util.List;

public interface RepositoryInterface <Obj,Long,ObjEntity>  {

    ObjEntity save(Obj obj);
    Obj findById(Long id);
    List<Obj> findAll();
    boolean existsById(Long id);
    void deleteById(Long id);

}
