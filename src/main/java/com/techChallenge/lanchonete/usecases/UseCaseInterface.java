package com.techChallenge.lanchonete.usecases;

import java.util.List;

public interface UseCaseInterface<in,out> {

    out create(in obj);
    out findById(Long id);
    List<out> findAll();
    boolean update(in obj);
    boolean delete(Long id);
}
