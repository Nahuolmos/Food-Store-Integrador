package DAO;

import entities.Usuario;

public class UsuarioDAO extends GenericMemoryDAO<Usuario> {
    public Usuario findByMail(String mail) {
        for (Usuario u : storage.values()) {
            if (!u.isEliminado() && u.getMail().equalsIgnoreCase(mail)) {
                return u;
            }
        }
        return null;
    }
}