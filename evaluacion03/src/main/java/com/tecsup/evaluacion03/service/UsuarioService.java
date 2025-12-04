package com.tecsup.evaluacion03.service;

import com.tecsup.evaluacion03.model.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario save(Usuario usuario);
    Usuario update(Long id, Usuario usuario);
    void delete(Long id);
    List<Usuario> findAll();
    Usuario findById(Long id);
    Usuario findByNombreUsuario(String nombreUsuario);
    List<Usuario> findActivos();
}
