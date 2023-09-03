package com.techChallenge.lanchonete.usecases.cliente;

import com.techChallenge.lanchonete.usecases.UseCaseInterface;

public interface ClienteUseCasePort extends UseCaseInterface<ClienteDTO, ClienteDTO> {

    ClienteDTO findByCpfEmail(BuscaCpfEmailDTO buscaCpfEmailDTO);

}
