package com.techChallenge.lanchonete.core.applications.ports.interfaces;

import com.techChallenge.lanchonete.core.applications.dtos.in.BuscaCpfEmailDTO;
import com.techChallenge.lanchonete.core.applications.dtos.in.ClienteDTO;

public interface ClienteServicePort extends ServiceInterface<ClienteDTO, ClienteDTO>{

    ClienteDTO findByCpfEmail(BuscaCpfEmailDTO buscaCpfEmailDTO);

}
