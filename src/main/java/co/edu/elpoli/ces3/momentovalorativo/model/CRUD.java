package co.edu.elpoli.ces3.momentovalorativo.model;

import co.edu.elpoli.ces3.momentovalorativo.dto.DtoLibro;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CRUD {
    Libro create(DtoLibro libro) throws SQLException; //Se cambia student por libro

    public ArrayList<Libro> all();

    public Libro findById(int id) throws SQLException;

    Libro update(Libro libro) throws SQLException;

    void delete(int librotId) throws SQLException;
}