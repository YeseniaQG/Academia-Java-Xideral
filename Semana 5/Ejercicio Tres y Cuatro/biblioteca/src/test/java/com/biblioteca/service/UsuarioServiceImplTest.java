package com.biblioteca.service;

import com.biblioteca.exception.EmailDuplicadoException;
import com.biblioteca.exception.UsuarioNotFoundException;
import com.biblioteca.model.Usuario;
import com.biblioteca.model.enums.Rol;
import com.biblioteca.repository.UsuarioRepository;
import com.biblioteca.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Pérez");
        usuario.setTelefono("1234567890");
        usuario.setEmail("juan@test.com");
        usuario.setPassword("password123");
        usuario.setActivo(true);
        usuario.setRol(Rol.ROLE_USER);
    }

    @Test
    void registrarUsuario_exitoso() {
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.registrarUsuario(usuario);

        assertNotNull(resultado);
        assertEquals("juan@test.com", resultado.getEmail());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void registrarUsuario_emailDuplicado_lanzaEmailDuplicadoException() {
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(true);

        assertThrows(EmailDuplicadoException.class, () -> usuarioService.registrarUsuario(usuario));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void obtenerUsuarioPorId_exitoso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.obtenerUsuarioPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void obtenerUsuarioPorId_noExiste_lanzaUsuarioNotFoundException() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNotFoundException.class, () -> usuarioService.obtenerUsuarioPorId(99L));
    }

    @Test
    void obtenerTodosLosUsuarios_exitoso() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioService.obtenerTodosLosUsuarios();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void actualizarUsuario_exitoso() {
        Usuario actualizado = new Usuario();
        actualizado.setNombre("Juan Actualizado");
        actualizado.setTelefono("9876543210");
        actualizado.setEmail("juan@test.com");
        actualizado.setPassword("newpass");
        actualizado.setRol(Rol.ROLE_USER);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(actualizado);

        Usuario resultado = usuarioService.actualizarUsuario(1L, actualizado);

        assertNotNull(resultado);
        assertEquals("Juan Actualizado", resultado.getNombre());
    }

    @Test
    void actualizarUsuario_noExiste_lanzaUsuarioNotFoundException() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNotFoundException.class, () -> usuarioService.actualizarUsuario(99L, usuario));
    }

    @Test
    void desactivarUsuario_exitoso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        usuarioService.desactivarUsuario(1L);

        assertFalse(usuario.isActivo());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void desactivarUsuario_noExiste_lanzaUsuarioNotFoundException() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNotFoundException.class, () -> usuarioService.desactivarUsuario(99L));
    }
}
