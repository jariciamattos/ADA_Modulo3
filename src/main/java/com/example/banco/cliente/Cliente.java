package com.example.banco.cliente;

import com.example.banco.conta.Conta;
import com.example.banco.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name="id")
public class Cliente extends Usuario {

    @NotBlank(message = "Nome obrigatorio")
    private String nome;
    @Column(unique = true)
    private String cpf;
    private LocalDate dataNascimento;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private List<Conta> contas;

}
