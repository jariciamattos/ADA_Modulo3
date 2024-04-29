package com.example.banco.conta;


import com.example.banco.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {

   public Optional<Conta> findByNumero(Long numero);
   public Optional<List<Conta>> findByCliente(Cliente cliente);

}
