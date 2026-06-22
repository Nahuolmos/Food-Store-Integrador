package DAO;
 
import entities.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
 
public class UsuarioDAO {
 
    // Simulación en memoria hasta conectar la DB
    private final List<Usuario> usuarios = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);
 
    public List<Usuario> findAllActive() {
        return usuarios.stream()
                .filter(u -> !u.isEliminado())
                .toList();
    }
 
    public Usuario findById(Long id) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
 
    public Usuario save(Usuario usuario) {
        usuario.setId(contadorId.getAndIncrement());
        usuarios.add(usuario);
        return usuario;
    }
 
    public void update(Usuario usuario) {
        Optional<Usuario> existente = usuarios.stream()
                .filter(u -> u.getId().equals(usuario.getId()) && !u.isEliminado())
                .findFirst();
 
        existente.ifPresent(u -> {
            u.setNombre(usuario.getNombre());
            u.setApellido(usuario.getApellido());
            u.setMail(usuario.getMail());
            u.setCelular(usuario.getCelular());
            u.setContrasenia(usuario.getContrasenia());
            u.setRol(usuario.getRol());
        });
    }
 
    public void softDelete(Long id) {
        usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .ifPresent(u -> u.setEliminado(true));
    }
 
    public boolean existeMail(String mail) {
        return usuarios.stream()
                .anyMatch(u -> u.getMail().equalsIgnoreCase(mail) && !u.isEliminado());
    }
}
