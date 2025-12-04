package com.tecsup.evaluacion03.service.impl;

import com.tecsup.evaluacion03.model.Usuario;
import com.tecsup.evaluacion03.repository.UsuarioRepository;
import com.tecsup.evaluacion03.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Long id, Usuario usuario) {
        Usuario exist = usuarioRepository.findById(id).orElseThrow();
        exist.setNombreUsuario(usuario.getNombreUsuario());
        exist.setActivo(usuario.getActivo());
        exist.setPassword(usuario.getPassword());

        return usuarioRepository.save(exist);
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario findByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario).orElse(null);
    }

    @Override
    public List<Usuario> findActivos() {
        return usuarioRepository.findByActivoTrue();
    }
}
