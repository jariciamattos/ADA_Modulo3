package com.example.banco.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    public Optional<Cliente> findByCpf(String cpf);
    public Optional<Cliente> findByUsername(String username);

}
