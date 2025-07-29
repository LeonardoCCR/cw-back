package com.example.concesswebapi.controller;

import com.example.concesswebapi.api.dto.UsuarioDTO;
import com.example.concesswebapi.exception.SenhaInvalidaException;
import com.example.concesswebapi.Model.Entity.Usuario;
//import com.example.concesswebapi.security.JwtService;
import com.example.concesswebapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final  UsuarioService usuarioService;
    //private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody Usuario usuario ){
        PasswordEncoder passwordEncoder;
        passwordEncoder = new BCryptPasswordEncoder();
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioService.salvar(usuario);
    }

//    @PostMapping("/auth")
//    public TokenDTO autenticar(@RequestBody UsuarioDTO credenciais){
//        try{
//            Usuario usuario = Usuario.builder()
//                    .login(credenciais.getLogin())
//                    .senha(credenciais.getSenha()).build();
//            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
//            String token = jwtService.gerarToken(usuario);
//            return new TokenDTO(usuario., token);
//        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
//        }
//    }
}
