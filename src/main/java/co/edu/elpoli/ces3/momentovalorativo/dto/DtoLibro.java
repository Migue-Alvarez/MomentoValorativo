package co.edu.elpoli.ces3.momentovalorativo.dto;

public class DtoLibro {
    public int id;

    protected String titulo;

    private String autor;

    public DtoLibro(int id, String titulo, String autor){
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
    }

    public DtoLibro(String titulo, String autor){
        this.autor = autor;
        this.titulo = titulo;
    }

    public DtoLibro() {

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
        return "El autor se llama: " + this.autor +
                " Su tituloo es: " + this.titulo;
    }
}
