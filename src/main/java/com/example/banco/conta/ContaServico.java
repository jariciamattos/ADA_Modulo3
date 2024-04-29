package com.example.banco.conta;

import com.example.banco.cliente.ClienteRepository;
import com.example.banco.exception.NaoEncontradoException;
import com.example.banco.login.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class ContaServico  {

    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    private final AuthService loginService;

    private ContaDto convertDto(Conta conta) {
        return this.modelMapper.map(conta, ContaDto.class);
    }

    private Conta convertFromDto(ContaDto conta) {
        return this.modelMapper.map(conta, Conta.class);
    }

    public Optional<ContaDto> buscarPorNumero(Long numero) {
        return this.contaRepository.findByNumero(numero).map(this::convertDto);
    }

    public ContaDto criar(ContaDto contaDto) {
        var cliente = this.clienteRepository.findByCpf(contaDto.getClienteCpf())
                .orElseThrow(() -> new NaoEncontradoException("Cliente "+contaDto.getClienteCpf()+" não encontrado"));
        var conta = this.convertFromDto(contaDto);
        conta.setCliente(cliente);
        return this.convertDto(contaRepository.save(conta));
    }

    public ContaDto consultaSaldo(String token, Long numeroConta) {
        return convertDto(validaPermissao(token, numeroConta));
    }

    public ContaDto efetuarDeposito(String token, TransacaoDTO dto) {
         Conta conta = validaPermissao(token, dto.getContaOrigem());
         conta = depositar(conta, dto.getValor());
        return convertDto(conta);
    }

    private Conta depositar(Conta conta, BigDecimal valor){
        conta.setSaldo(conta.getSaldo().add(valor));
        contaRepository.save(conta);
        return conta;
    }

    public ContaDto efetuarSaque(String token, TransacaoDTO dto) {
        Conta conta = validaPermissao(token, dto.getContaOrigem());
        conta = sacar(conta, dto.getValor());
        return convertDto(conta);
    }

    private Conta sacar(Conta conta, BigDecimal valor){
        if ( conta.getSaldo().compareTo(valor) < 0 ){
            throw new RuntimeException("Saldo Insuficiente !!") ;
        }
        conta.setSaldo(conta.getSaldo().subtract(valor));
        contaRepository.save(conta);
        return conta;
    }
    public TransferenciaDTO efeturarTransferencia (String token, TransferenciaDTO dto){
        Conta contaOrigem = validaPermissao(token, dto.getContaOrigem());
        Conta contaDestino = this.contaRepository.findByNumero(dto.getContaDestino())
                .orElseThrow(() -> new NaoEncontradoException("Conta "+dto.getContaDestino()+" não encontrada"));
        sacar(contaOrigem,dto.getValor());
        depositar(contaDestino,dto.getValor());
        return dto;
    }

    private Conta validaPermissao(String token, Long numeroConta){
        var usuario = loginService.getUsuarioByToken(token);
        Conta conta = this.contaRepository.findByNumero(numeroConta)
                .orElseThrow(() -> new NaoEncontradoException("Conta "+numeroConta+" não encontrada"));
        if(!usuario.getUsername().equals(conta.getCliente().getUsername())){
            throw new AccessDeniedException("Acesso Negado: A conta "+numeroConta+" não pertence ao usuário logado");
        }
        return conta;
    }
}
