package com.example.banco.usuario;

import com.example.banco.exception.NaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    private final  ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    private UsuarioDTO convertDto(Usuario usuario) {
        return this.modelMapper.map(usuario, UsuarioDTO.class);
    }

    private Usuario convertFromDto(UsuarioDTO usuarioDto) {
        return this.modelMapper.map(usuarioDto, Usuario.class);
    }

    public List<UsuarioDTO> listarUsuarios() {
        return this.usuarioRepository.findAll().stream()
                .map(this::convertDto)
                .collect(Collectors.toList());
    }

    public UsuarioDTO salvar(UsuarioDTO usuarioDto) {
        var usuario = this.convertFromDto(usuarioDto);
        if (! usuarioRepository.findByUsername(usuarioDto.getUsername()).isEmpty()){
            throw new IllegalArgumentException("Usermane já cadastrado, escolha outro.");

        }
        usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
        usuario.setActive(true);
        var savedUsuario = this.usuarioRepository.save(usuario);
        return this.convertDto(savedUsuario);
    }

    public void excluir(String username) {
        var usuario = this.usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado") );
        System.out.println(usuario);
        this.usuarioRepository.delete(usuario);
    }

    public UsuarioDTO atualizar(UsuarioDTO usuarioDto) {
        var usuario = this.usuarioRepository.findByUsername(usuarioDto.getUsername())
                .orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado") );
        usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
        usuario.setRoles(usuarioDto.getRoles());
        return this.convertDto(usuarioRepository.save(usuario));
    }
    public Usuario getByUsernameEntity(String username) {
        return this.usuarioRepository.findByUsername(username)
                .orElseThrow();
    }

}
