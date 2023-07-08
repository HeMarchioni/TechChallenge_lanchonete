package com.techChallenge.lanchonete.core.applications.ports.interfaces;

import java.util.List;

public interface ServiceInterface<in,out> {

    out create(in obj);
    out findById(Long id);
    List<out> findAll();
    boolean update(in obj);
    boolean delete(Long id);
}
