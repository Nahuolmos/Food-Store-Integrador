package DAO;

import entities.Producto;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO extends GenericMemoryDAO<Producto> {
    
    public List<Producto> findByCategoria(Long idCategoria) {
        List<Producto> res = new ArrayList<>();
        for (Producto p : storage.values()) {
            if (!p.isEliminado() && p.getCategoria() != null && p.getCategoria().getId().equals(idCategoria)) {
                res.add(p);
            }
        }
        return res;
    }

    public boolean tieneProductosAsociados(Long idCategoria) {
        for (Producto p : storage.values()) {
            if (!p.isEliminado() && p.getCategoria() != null && p.getCategoria().getId().equals(idCategoria)) {
                return true;
            }
        }
        return false;
    }
}
