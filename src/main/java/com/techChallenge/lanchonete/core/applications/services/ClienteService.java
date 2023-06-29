package com.techChallenge.lanchonete.core.applications.services;

import com.techChallenge.lanchonete.core.applications.dtos.in.ClienteDTO;
import com.techChallenge.lanchonete.core.applications.mapper.ClienteMapper;
import com.techChallenge.lanchonete.core.applications.ports.interfaces.ClienteServicePort;
import com.techChallenge.lanchonete.core.applications.ports.repositories.ClienteRepositoryPort;
import com.techChallenge.lanchonete.core.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService implements ClienteServicePort {

    private final ClienteMapper clienteMapper = ClienteMapper.INSTANCE;
    private final ClienteRepositoryPort clienteRepository;

    @Autowired
    public ClienteService(ClienteRepositoryPort clienteRepository) {

        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteDTO create(ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.toModel(clienteDTO);
        clienteRepository.save(cliente);
        return clienteDTO;
    }

    @Override
    public ClienteDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        return clienteMapper.toDTO(cliente);
    }

    @Override
    public List<ClienteDTO> findAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream().map(clienteMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public boolean update(ClienteDTO clienteDTO) {

        if (clienteRepository.existsById(clienteDTO.getId())) {
            Cliente cliente = clienteMapper.toModel(clienteDTO);
            clienteRepository.save(cliente);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
