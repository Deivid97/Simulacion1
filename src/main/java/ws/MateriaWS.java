/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import gestion.MateriaGestion;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import modelo.Materia;

/**
 * REST Web Service
 *
 * @author jdavi
 */
@Path("materia")
@RequestScoped
public class MateriaWS {

    @Context
    private UriInfo context;

    
    public MateriaWS() {
    }

  @GET
  @Produces (MediaType.APPLICATION_JSON)
  public List<Materia> getMateria(){
      return MateriaGestion.getMateria();
  }
    
    
  @GET
  @Path("/{idMateria}")
  @Produces(MediaType.APPLICATION_JSON)
  public Materia getMateria(@PathParam("idMateria")String idMateria){
      return MateriaGestion.getMateria(idMateria);
  }
  
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public Materia putMateria(Materia materia){
   Materia materia2=MateriaGestion.getMateria(materia.getIdMateria());
   
      if (materia2!=null) {
          MateriaGestion.modificar(materia);
      }else{
          MateriaGestion.insertar(materia);   
      }      
      return MateriaGestion.getMateria(materia.getIdMateria());
  }
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Materia postMateria(Materia materia){
   Materia materia2 = MateriaGestion.getMateria(materia.getIdMateria());
   
      if (materia2!=null) {
          MateriaGestion.modificar(materia);
      }else{
          MateriaGestion.insertar(materia);
      }
        return MateriaGestion.getMateria(materia.getIdMateria());
  
  
  }
  
  
  @DELETE
  @Path("/{idMateria}")
  @Produces(MediaType.APPLICATION_JSON)
  public Materia deleteMateria(@PathParam("idMateria") String idMateria){
  Materia materia = MateriaGestion.getMateria(idMateria);
  
      if (materia!=null) {
          if (MateriaGestion.eliminar(materia)) {
              return materia;
          }
      }
  return null;
  }
  
}
