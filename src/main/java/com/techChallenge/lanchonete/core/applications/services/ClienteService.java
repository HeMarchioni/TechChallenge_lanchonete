package com.techChallenge.lanchonete.core.applications.services;

import com.techChallenge.lanchonete.core.applications.dtos.in.BuscaCpfEmailDTO;
import com.techChallenge.lanchonete.core.applications.dtos.in.ClienteDTO;
import com.techChallenge.lanchonete.core.applications.mapper.ClienteMapper;
import com.techChallenge.lanchonete.core.applications.ports.interfaces.ClienteServicePort;
import com.techChallenge.lanchonete.core.applications.ports.repositories.ClienteRepositoryPort;
import com.techChallenge.lanchonete.core.domain.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService implements ClienteServicePort {


    private final ClienteRepositoryPort clienteRepositoryPort;
    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteRepositoryPort clienteRepositoryPort) {
        this.clienteRepositoryPort = clienteRepositoryPort;
        this.clienteMapper = ClienteMapper.INSTANCE;
    }


    @Override
    public ClienteDTO create(ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.toModel(clienteDTO);
        clienteRepositoryPort.save(cliente);
        return clienteDTO;
    }

    @Override
    public ClienteDTO findById(Long id) {
        Cliente cliente = clienteRepositoryPort.findById(id);
        return clienteMapper.toDTO(cliente);
    }

    @Override
    public List<ClienteDTO> findAll() {
        List<Cliente> clientes = clienteRepositoryPort.findAll();
        return clientes.stream().map(clienteMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public boolean update(ClienteDTO clienteDTO) {

        if (clienteRepositoryPort.existsById(clienteDTO.getId())) {
            Cliente cliente = clienteMapper.toModel(clienteDTO);
            clienteRepositoryPort.save(cliente);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (clienteRepositoryPort.existsById(id)) {
            clienteRepositoryPort.deleteById(id);
            return true;
        }
        return false;
    }


    public ClienteDTO findByCpfEmail(BuscaCpfEmailDTO buscaCpfEmailDTO) {

        Optional<Cliente>  cliente;

        if (buscaCpfEmailDTO.getCpf()!= null) {

            cliente = Optional.ofNullable(clienteRepositoryPort.findClienteByCd_Cpf(buscaCpfEmailDTO.getCpf()));

        } else if(buscaCpfEmailDTO.getEmail()!= null) {

            cliente = Optional.ofNullable(clienteRepositoryPort.findClienteByDs_Email(buscaCpfEmailDTO.getEmail()));
        }else {
            throw new IllegalArgumentException("Não é possivel Realizar a Busca passe um CPF ou Email");
        }
        return clienteMapper.toDTO(cliente.orElse(null));
    }

}
