package com.example.banco.conta;

import lombok.*;

import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContaDto implements Serializable {

    private Long numero;
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal saldo;
    private String clienteCpf;

}
