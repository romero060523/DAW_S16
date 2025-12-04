package com.tecsup.evaluacion03.security;

import com.tecsup.evaluacion03.model.Usuario;
import com.tecsup.evaluacion03.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + nombreUsuario));

        if (!usuario.getActivo()) {
            throw new UsernameNotFoundException("Usuario inactivo");
        }

    return User.builder()
        .username(usuario.getNombreUsuario())
        .password(usuario.getPassword())
        // mapear roles a GrantedAuthority y asegurar prefijo ROLE_ para compatibilidad con hasRole()
        .authorities(mapRolesToAuthorities(usuario))
        .build();
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Usuario usuario) {
    return usuario.getRoles().stream()
        .map(rol -> {
            String roleName = rol.getNombre();
            // si el rol ya tiene prefijo ROLE_ lo dejamos, si no, lo añadimos
            if (!roleName.startsWith("ROLE_")) {
            roleName = "ROLE_" + roleName;
            }
            return new SimpleGrantedAuthority(roleName);
        })
        .collect(Collectors.toList());
    }
}