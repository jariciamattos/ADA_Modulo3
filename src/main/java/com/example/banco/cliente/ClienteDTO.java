package com.example.banco.cliente;

import com.example.banco.constraints.BirthDate;
import com.example.banco.constraints.ValueOfEnum;
import com.example.banco.conta.Conta;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ClienteDTO implements Serializable {

    @NotBlank(message = "Username obrigatório")
    private String username;
    @NotBlank(message = "Password obrigatória")
    private String password;
    @NotBlank(message = "Nome obrigatorio")
    private String nome;
    @CPF(message = "CPF Inválido")
    @NotBlank(message = "CPF obrigatorio")
    private String cpf;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @BirthDate(message = "Idade minima 18 anos")
    private LocalDate dataNascimento;
    @ValueOfEnum(enumClass = StatusEnum.class, message = "Status Inválido, informe ATIVO ou INATIVO")
    private String status;
    @JsonIgnore
    private List<Conta> contas;

}
