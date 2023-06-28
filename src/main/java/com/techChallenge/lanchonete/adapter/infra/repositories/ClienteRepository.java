package com.techChallenge.lanchonete.adapter.infra.repositories;


import com.techChallenge.lanchonete.adapter.infra.entity.ClienteEntity;
import com.techChallenge.lanchonete.core.applications.mapper.ClienteMapper;
import com.techChallenge.lanchonete.core.applications.ports.repositories.ClienteRepositoryPort;
import com.techChallenge.lanchonete.core.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ClienteRepository implements ClienteRepositoryPort {


    private final ClienteMapper clienteMapper = ClienteMapper.INSTANCE;
    private final ClienteRepositoryJpa clienteRepositoryJpa;

    @Autowired
    public ClienteRepository(ClienteRepositoryJpa clienteRepositoryJpa) {
        this.clienteRepositoryJpa = clienteRepositoryJpa;
    }

    @Override
    public void save(Cliente cliente) {
        ClienteEntity clienteEntity = clienteMapper.toEntity(cliente);
        clienteRepositoryJpa.save(clienteEntity);
    }

    @Override
    public Cliente findById(Long id) {
        Optional<ClienteEntity> clienteEntity = clienteRepositoryJpa.findById(id);
        return clienteMapper.toModel(clienteEntity.orElse(null));
    }

    @Override
    public List<Cliente> findAll() {
        List<ClienteEntity> clienteEntityList = clienteRepositoryJpa.findAll();
        return clienteEntityList.stream().map(clienteMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return clienteRepositoryJpa.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        clienteRepositoryJpa.deleteById(id);
    }
}
