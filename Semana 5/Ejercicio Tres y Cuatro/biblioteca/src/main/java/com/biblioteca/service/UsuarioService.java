package com.biblioteca.service;

import com.biblioteca.model.Usuario;
import java.util.List;

public interface UsuarioService {
    Usuario registrarUsuario(Usuario usuario);
    Usuario obtenerUsuarioPorId(Long id);
    List<Usuario> obtenerTodosLosUsuarios();
    Usuario actualizarUsuario(Long id, Usuario usuario);
    void desactivarUsuario(Long id);
}
