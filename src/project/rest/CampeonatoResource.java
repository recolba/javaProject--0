package project.rest;

import project.entidade.Campeonato;
import project.dao.CampeonatoDao;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import io.dropwizard.jersey.*;
import io.dropwizard.jersey.params.*;
import java.util.*;
import project.dao.DaoException;

@Path("/campeonato")
@Produces(MediaType.APPLICATION_JSON)
public class CampeonatoResource {    

    private CampeonatoDao dao;
    private long proximoId;
    
    public CampeonatoResource(CampeonatoDao dao) {
        this.dao = dao;
    }

    @POST
    public Campeonato create(Campeonato c) {
        Campeonato resp;
        try {
            long id = dao.create(c);
            c.setId(id);
            resp = c;
        } catch(DaoException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return resp;
    }
    
    @GET
    public List<Campeonato> read() {
        List<Campeonato> campeonato;
        try { campeonato = dao.read(); }
        catch(DaoException ex) { 
            ex.printStackTrace();
            campeonato = null; 
        }
        return campeonato;
    }
    
    @PUT
    @Path("{id}")
    public Campeonato update(@PathParam("id") LongParam id, Campeonato c) {
        Campeonato resp;
        try {
            c.setId(id.get());
            dao.update(c);
            resp = c;
        } catch(DaoException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return c;
    }    

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") LongParam id) {
        Campeonato c;
        try {
            c = dao.readById(id.get());
        } catch(DaoException ex) {
            ex.printStackTrace();
            throw new WebApplicationException("Erro ao buscar campeonato com id="
                                                + id.get(), 500);  
        }
        if (c != null) { 
            try{ 
                dao.delete(id.get()); 
            } catch(DaoException ex) {
                ex.printStackTrace();
                throw new WebApplicationException("Erro ao tentar apagar campeonato com id="
                                                  + id.get(), 500);                
            }
        }
        else {
            throw new WebApplicationException("Campeonato com id=" + id.get() 
                                              + " não encontrado!", 404);
        }
        return Response.ok().build();
    }
}


    