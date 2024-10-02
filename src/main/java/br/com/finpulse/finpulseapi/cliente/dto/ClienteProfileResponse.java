package br.com.finpulse.finpulseapi.cliente.dto;

import br.com.finpulse.finpulseapi.cliente.Cliente;

public record ClienteProfileResponse(
        String name,
        String email
) {
    public ClienteProfileResponse(Cliente cliente){
        this(cliente.getNome(), cliente.getEmail());
    }
}
