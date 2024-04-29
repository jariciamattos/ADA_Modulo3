package com.example.banco.login;


import com.example.banco.usuario.Usuario;
import com.example.banco.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.usuarioService.getByUsernameEntity(username);
    }

    public Usuario getUsuarioByToken(String bearerToken){
        String token = bearerToken.substring(7);
        return (Usuario) jwtService.getUserDetails(token);
    }
}
