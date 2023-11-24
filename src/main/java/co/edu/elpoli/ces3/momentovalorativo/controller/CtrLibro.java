package co.edu.elpoli.ces3.momentovalorativo.controller;

import co.edu.elpoli.ces3.momentovalorativo.dto.DtoLibro;
import co.edu.elpoli.ces3.momentovalorativo.model.Libro;

import java.sql.SQLException;
import java.util.ArrayList;

public class CtrLibro {

    private Libro modelLibro;

    public CtrLibro(){
        modelLibro = new Libro();
    }

    public DtoLibro addLibro(DtoLibro Libro){
        try {
            Libro newLibro = modelLibro.create(Libro);
            return new DtoLibro(newLibro.getId(), newLibro.gettitulo(), newLibro.getautor());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<DtoLibro> getAllLibros() {
        try {
            ArrayList<Libro> Libros = modelLibro.all(); // Llama al método 'all' del modelo
            ArrayList<DtoLibro> dtoLibros = new ArrayList<>();

            for (Libro Libro : Libros) {
                DtoLibro dtoLibro = new DtoLibro(
                        Libro.getId(),
                        Libro.gettitulo(), //Se cambia por gettitulo Document
                        Libro.getautor() //Se cambia por getautor Name
                );
                dtoLibros.add(dtoLibro);
            }

            return dtoLibros;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DtoLibro getLibroById(int LibroId) {
        try {
            Libro Libro = modelLibro.findById(LibroId);
            if (Libro != null) {
                return new DtoLibro(Libro.getId(), Libro.gettitulo(), Libro.getautor());
            } else {
                throw new RuntimeException("No se encuentra el libro");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DtoLibro updateLibro(int LibroId, DtoLibro updatedLibro) {
        try {
            Libro Libro = new Libro(
                    LibroId,
                    updatedLibro.gettitulo(),
                    updatedLibro.getautor()

            );

            Libro updated = modelLibro.update(Libro);
            return new DtoLibro(updated.getId(), updated.gettitulo(), updated.getautor());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteLibro(int LibroId) {
        try {
            modelLibro.delete(LibroId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
