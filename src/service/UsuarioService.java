package service;

import DAO.UsuarioDAO;
import entities.Usuario;
import enums.Rol;
import exceptions.DuplicateEntityException;
import exceptions.EntityNotFoundException;
import exceptions.ValidationException;
import java.util.List;

public class UsuarioService {
    private UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public void crearUsuario(String nombre, String apellido, String mail, String celular, String contraseña, Rol rol) 
            throws ValidationException, DuplicateEntityException {
        if (nombre == null || nombre.trim().isEmpty()) throw new ValidationException("Nombre obligatorio.");
        if (mail == null || mail.trim().isEmpty()) throw new ValidationException("El email no puede estar vacío.");
        if (usuarioDAO.findByMail(mail) != null) throw new DuplicateEntityException("El mail ya se encuentra registrado.");

        Usuario u = new Usuario(nombre, apellido, mail, celular, contraseña, rol);
        usuarioDAO.save(u);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.findAll();
    }

    public Usuario obtenerPorId(Long id) throws EntityNotFoundException {
        Usuario u = usuarioDAO.findById(id);
        if (u == null || u.isEliminado()) throw new EntityNotFoundException("El usuario con ID " + id + " no existe.");
        return u;
    }

    public void editarUsuario(Long id, String nombre, String apellido, String mail, String celular, String contraseña, Rol rol) 
            throws EntityNotFoundException, ValidationException, DuplicateEntityException {
        Usuario u = obtenerPorId(id);
        if (mail == null || mail.trim().isEmpty()) throw new ValidationException("Email vacío.");

        Usuario existente = usuarioDAO.findByMail(mail);
        if (existente != null && !existente.getId().equals(id)) {
            throw new DuplicateEntityException("El email ya pertenece a otro usuario.");
        }

        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setMail(mail);
        u.setCelular(celular);
        u.setContrasenia(contraseña);
        u.setRol(rol);
        usuarioDAO.update(u);
    }

    public void eliminarUsuario(Long id) throws EntityNotFoundException {
        obtenerPorId(id);
        usuarioDAO.delete(id);
    }
}

