package co.edu.elpoli.ces3.momentovalorativo.model;

import co.edu.elpoli.ces3.momentovalorativo.dto.DtoLibro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Libro extends Conexion implements CRUD{
    public int id;

    protected String titulo;

    private String autor;

    public Libro(int id, String titulo, String autor){
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
    }

    public Libro(String titulo){
        this.titulo = titulo;
    }

    public Libro() {
    }

    public int getId(){
        return this.id;
    }


    private void setId(int id){
        this.id = id;
    }

    public String gettitulo() {
        return titulo;
    }

    public void settitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getautor() {
        return autor;
    }

    public void setautor(String autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "El estudiante se llama: " + this.autor +
                " Su tituloo es: " + this.titulo;
    }

    @Override
    public Libro create(DtoLibro libro) throws SQLException {
        Connection cnn = this.getConexion();
        if(cnn != null) {
            String sql = "INSERT INTO LIBROS(titulo, autor) VALUES('"+libro.gettitulo()+"', '"+libro.getautor()+"')";
            this.titulo = libro.gettitulo();
            this.autor = libro.getautor();
            try {
                PreparedStatement stmt = cnn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                this.id = rs.getInt(1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                cnn.close();
            }
            return this;
        }
        return null;
    }

    @Override
    public ArrayList<Libro> all() {
        Connection cnn = this.getConexion();
        ArrayList<Libro> libros = new ArrayList<>();

        if (cnn != null) {
            String sql = "SELECT id,titulo,autor FROM LIBROS";
            try {
                PreparedStatement stmt = cnn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String autor = rs.getString("autor");
                    String titulo = rs.getString("titulo");
                    Libro libro = new Libro(id, titulo, autor);
                    libros.add(libro);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (cnn != null) {
                        cnn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return libros;
        }
        return null;
    }



@Override
    public Libro findById(int libroId) throws SQLException {
        Connection cnn = getConexion();

        if (cnn != null) {
            String sql = "SELECT id,titulo,autor FROM LIBROS WHERE id = ?";
            try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setInt(1, libroId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("id");
                        String titulo = rs.getString("titulo");
                        String autor = rs.getString("autor");
                        return new Libro(id, titulo, autor);
                    } else {
                        return null;
                    }
                }
            } finally {
                if (cnn != null) {
                    cnn.close();
                }
            }
        }
        return null;
    }

    @Override
    public Libro update(Libro libro) throws SQLException {
        Connection cnn = getConexion();

        if (cnn != null) {
            String sql = "UPDATE LIBROS SET titulo = ?, autor = ? WHERE id = ?";
            try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setString(2, libro.gettitulo());
                stmt.setString(3, libro.getautor());
                stmt.setInt(1, libro.getId());
                stmt.executeUpdate();
            } finally {
                if (cnn != null) {
                    cnn.close();
                }
            }
        }
        return libro;
    }

    @Override
    public void delete(int libroId) throws SQLException {
        Connection cnn = getConexion();

        if (cnn != null) {
            String sql = "DELETE FROM LIBROS WHERE id = ?";
            try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setInt(1, libroId);
                stmt.executeUpdate();
            } finally {
                if (cnn != null) {
                    cnn.close();
                }
            }
        }
    }


}
