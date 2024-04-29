package com.example.banco.cliente;

import com.example.banco.conta.Conta;
import com.example.banco.conta.ContaDto;
import com.example.banco.exception.NaoEncontradoException;
import com.example.banco.usuario.Role;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    private ClienteDTO convertDto(Cliente cliente) {
        return this.modelMapper.map(cliente, ClienteDTO.class);
    }
    private ContaDto contaConvertDto(Conta conta) {
        return this.modelMapper.map(conta, ContaDto.class);
    }

    private Cliente convertFromDto(ClienteDTO clienteDto) {
        return this.modelMapper.map(clienteDto, Cliente.class);
    }

    public List<ClienteDTO> listarClientes() {
        return this.clienteRepository.findAll().stream()
                .map(this::convertDto)
                .collect(Collectors.toList());
    }

    public List<ContaDto> listarContas(String cpf) {
        var cliente = this.clienteRepository.findByCpf(cpf).orElseThrow(
                () -> new NaoEncontradoException("Cliente não encontrado"));
        return cliente.getContas().stream().map(this::contaConvertDto)
                .collect(Collectors.toList());
    }

    public ClienteDTO salvar(ClienteDTO clienteDto) {
        var cliente = this.convertFromDto(clienteDto);
        if ( ! clienteRepository.findByCpf(cliente.getCpf()).isEmpty()){
            throw new RuntimeException("Cliente já cadastrado.");
        }
        if (! clienteRepository.findByUsername(cliente.getUsername()).isEmpty()){
            throw new RuntimeException("Usermane já cadastrado, escolha outro.");
        }

       // cliente.setStatus(StatusEnum.ATIVO);
        cliente.setActive(true);
        cliente.setRoles(Role.CLIENTE.name());
        cliente.setPassword(passwordEncoder.encode(clienteDto.getPassword()));
        return this.convertDto(this.clienteRepository.save(cliente));
    }

    public Optional<ClienteDTO> buscarPorCpf(String cpf) {
        return this.clienteRepository.findByCpf(cpf).map(this::convertDto);
    }

    public void excluir(String cpf) {
        var cliente = this.clienteRepository.findByCpf(cpf).orElseThrow(
                () -> new NaoEncontradoException("Cliente não encontrado"));
        if (! cliente.getContas().isEmpty()){
            throw new RuntimeException("Não é possível excluir clientes com contas");
        }
        this.clienteRepository.delete(cliente);
    }

    public ClienteDTO atualizar(ClienteDTO clienteDto) {
        var clienteCadastrado= this.clienteRepository.findByCpf(clienteDto.getCpf())
                .orElseThrow(() -> new NaoEncontradoException("Cliente não encontrado"));

        clienteCadastrado.setNome(clienteDto.getNome());
        clienteCadastrado.setStatus(StatusEnum.valueOf(clienteDto.getStatus()));
        clienteCadastrado.setDataNascimento(clienteDto.getDataNascimento());
        clienteCadastrado.setPassword(passwordEncoder.encode(clienteDto.getPassword()));

        return this.convertDto(clienteRepository.save(clienteCadastrado));
    }

}
