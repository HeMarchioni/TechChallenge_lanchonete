package com.techChallenge.lanchonete.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ControllerInterface <in,out> {

    ResponseEntity<List<out>> getAll();
    ResponseEntity<out> get(Long id);
    ResponseEntity<?> post(in obj);
    ResponseEntity<?> put(in obj);
    ResponseEntity<?> delete(Long id);

}
