package co.edu.elpoli.ces3.momentovalorativo.servlet;

import co.edu.elpoli.ces3.momentovalorativo.controller.CtrLibro;
import co.edu.elpoli.ces3.momentovalorativo.dto.DtoLibro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "LibroServlet", value = "/Libro")
public class LibroServlet extends MyServlet {
    private String message;

    private GsonBuilder gsonBuilder;

    private Gson gson;

    private ArrayList<DtoLibro> libros;

    CtrLibro ctr = new CtrLibro();

    public void init() {
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        libros = new ArrayList<>();

        DtoLibro libro1 = new DtoLibro();
        //libro1.settitulo("Hamlet");
        //libro1.setautor("William Shakespeare");

        libros.add(libro1);

        for (int i = 0; i < libros.size(); i++)
        {
            System.out.println(libros.get(i));
        }
        message = "Hi Lector";
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");
        JsonObject body = this.getParamsFromPost(req);
        DtoLibro std = new DtoLibro(
                body.get("titulo").getAsString(),
                body.get("autor").getAsString()
        );

        DtoLibro newLibro = ctr.addLibro(std);

        out.print(gson.toJson(newLibro));
        out.flush();


    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");
        String libroIdParam = req.getParameter("id");

        if (libroIdParam != null && !libroIdParam.isEmpty()) {
            int libroId = Integer.parseInt(libroIdParam);
            DtoLibro libro = ctr.getLibroById(libroId);
            out.print(gson.toJson(libro));
        } else {
            ArrayList<DtoLibro> libros = ctr.getAllLibros();
            out.print(gson.toJson(libros));
        }

        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");
        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        JsonObject body = gson.fromJson(stringBuilder.toString(), JsonObject.class);
        int libroId = body.get("id").getAsInt();

        DtoLibro updatedLibro = new DtoLibro(
                body.get("titulo").getAsString(),
                body.get("autor").getAsString()
        );

        DtoLibro result = ctr.updateLibro(libroId, updatedLibro);

        out.print(gson.toJson(result));
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");

        int libroId = Integer.parseInt(req.getParameter("id"));

        ctr.deleteLibro(libroId);

        out.print(gson.toJson("Libro eliminado"));
        out.flush();
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        switch (method){
            case "PATCH":
                this.doPatch(req, resp);
                break;
            default:
                super.service(req, resp);
        }

    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("************");
        System.out.println("Entro al metodo patch!!!");
        System.out.println("************");
    }

}