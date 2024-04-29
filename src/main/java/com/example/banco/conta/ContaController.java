package com.example.banco.conta;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@RestController
@RequestMapping("/conta")
@RequiredArgsConstructor
public class ContaController {

    private final ContaServico servico;

    @GetMapping("{numero}")
    @PreAuthorize("hasRole(T(com.example.banco.usuario.Role).CLIENTE.name())")
    public ResponseEntity<ContaDto> saldo(@PathVariable("numero") Long numero, @RequestHeader(name="Authorization") String bearerToken) {
        return new ResponseEntity<>(this.servico.consultaSaldo(bearerToken, numero), HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("hasAnyRole(T(com.example.banco.usuario.Role).ADMIN.name(), T(com.example.banco.usuario.Role).FUNCIONARIO.name() )")
    public ResponseEntity<ContaDto> inserir (@Valid @RequestBody ContaDto dto, Authentication autenticacao ) {
    /*    if (! autenticacao.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_"+ Role.ADMIN.name()){
            throw new AccessDeniedException("Usuário não autorizado");
        }

     */
        return new ResponseEntity<>(this.servico.criar(dto), HttpStatus.CREATED);
    }

    @PutMapping("/sacar")
    @PreAuthorize("hasRole(T(com.example.banco.usuario.Role).CLIENTE.name())")
    public ResponseEntity<ContaDto> sacar (@Valid @RequestBody TransacaoDTO dto, @RequestHeader(name="Authorization") String bearerToken) {
        return new ResponseEntity<>(this.servico.efetuarSaque(bearerToken, dto), HttpStatus.OK);
    }

    @PutMapping("/depositar")
    @PreAuthorize("hasRole(T(com.example.banco.usuario.Role).CLIENTE.name())")
    public ResponseEntity<ContaDto> depositar (@Valid @RequestBody TransacaoDTO dto, @RequestHeader(name="Authorization") String bearerToken) {
        return new ResponseEntity<>(this.servico.efetuarDeposito(bearerToken, dto), HttpStatus.OK);
    }

    @PutMapping("/transferir")
    @PreAuthorize("hasRole(T(com.example.banco.usuario.Role).CLIENTE.name())")
    public ResponseEntity<TransferenciaDTO> transferir  (@Valid @RequestBody TransferenciaDTO dto, @RequestHeader(name="Authorization") String bearerToken) {
        return new ResponseEntity<>(this.servico.efeturarTransferencia(bearerToken, dto), HttpStatus.OK);
    }


}
