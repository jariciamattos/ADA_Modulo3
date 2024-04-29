package com.example.banco.conta;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
public class TransferenciaDTO  {
    private Long contaOrigem;
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal valor;
    private Long contaDestino;
}
