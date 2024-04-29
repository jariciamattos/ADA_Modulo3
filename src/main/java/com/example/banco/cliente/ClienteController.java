package com.example.banco.cliente;

import com.example.banco.conta.ContaDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;
    private final ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole(T(com.example.banco.usuario.Role).ADMIN.name(), T(com.example.banco.usuario.Role).FUNCIONARIO.name() )")
    public List<ClienteDTO> listarTodos() {
        return this.service.listarClientes().stream()
                .collect(Collectors.toList());
    }

    @GetMapping("/{cpf}/conta")
    @PreAuthorize("hasAnyRole(T(com.example.banco.usuario.Role).ADMIN.name(), T(com.example.banco.usuario.Role).FUNCIONARIO.name() )")
    public ResponseEntity<List<ContaDto>> listarContas(@PathVariable("cpf") String cpf) {
        return new ResponseEntity<>(this.service.listarContas(cpf), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole(T(com.example.banco.usuario.Role).ADMIN.name(), T(com.example.banco.usuario.Role).FUNCIONARIO.name() )")
    public ResponseEntity<ClienteDTO> inserir (@Valid @RequestBody ClienteDTO dto) {
        return new ResponseEntity<>(this.service.salvar(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{cpf}")
    @PreAuthorize("hasAnyRole(T(com.example.banco.usuario.Role).ADMIN.name(), T(com.example.banco.usuario.Role).FUNCIONARIO.name() )")
    public ResponseEntity<ClienteDTO> buscarPorCpf (@PathVariable("cpf") String cpf) {
        var cliente = this.service.buscarPorCpf(cpf);
        return cliente.map(clienteDTO -> ResponseEntity.ok(this.modelMapper.map(clienteDTO, ClienteDTO.class)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{cpf}")
    @PreAuthorize("hasAnyRole(T(com.example.banco.usuario.Role).ADMIN.name(), T(com.example.banco.usuario.Role).FUNCIONARIO.name() )")
    public ResponseEntity<ClienteDTO> atualizar (@PathVariable("cpf") String cpf, @Valid @RequestBody ClienteDTO dto) {
        dto.setCpf(cpf);
        return new ResponseEntity<>(this.service.atualizar(dto), HttpStatus.OK);
    }

    @DeleteMapping("/{cpf}")
    @PreAuthorize("hasAnyRole(T(com.example.banco.usuario.Role).ADMIN.name(), T(com.example.banco.usuario.Role).FUNCIONARIO.name() )")
    public ResponseEntity excluir (@PathVariable("cpf") String cpf) {
        service.excluir(cpf);
        return ResponseEntity.noContent().build();
    }
}
