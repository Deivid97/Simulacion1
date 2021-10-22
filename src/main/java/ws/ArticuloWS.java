
package ws;

import gestion.ArticuloGestion;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import modelo.Articulo;


@Path("articulo")
@RequestScoped
public class ArticuloWS {

    @Context
    private UriInfo context;

   
    public ArticuloWS() {
    }
    
    /*
    Método GET
    <server>:<port>/Lec03-1/resources/articulo
    @return un listado de articulos en JSON
    
    */
    
    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public List<Articulo> getArticulos(){
        return ArticuloGestion.getArticulos();
    }
    
    
     /*
    Método GET
    <server>:<port>/Lec03-1/resources/articulo/id
    @params id el id del articulo a recuperar 
    @return un articulo con id= (id)
    
    */
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Articulo getArticulo(@PathParam("id") int id){
        return ArticuloGestion.getArticulo(id);
    }
    
      /*
    Método DELETE
    <server>:<port>/Lec03-1/resources/articulo/id
    @params id el id del articulo a eliminar
        
    */
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Articulo deleteArticulo(@PathParam("id") int id){
        Articulo articulo = ArticuloGestion.getArticulo(id);
        
        if (articulo!=null){
            if (ArticuloGestion.eliminar(id)){
                return articulo;
            }
        }       
        return null;
    }
    
      /*
    Método PUT
    <server>:<port>/Lec03-1/resources/articulo/ consume un objeto en formato JSON
    @paraam articulo
    @return el articulo insertado o actualizado
        
    */
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Articulo putArticulo(Articulo articulo){
        Articulo articulo2 = ArticuloGestion.getArticulo(articulo.getId());
        
        if (articulo2!=null){
            ArticuloGestion.modificar(articulo);
        }else{
            ArticuloGestion.insertar(articulo);
        }
        return ArticuloGestion.getArticulo(articulo.getId());
    }
    
      /*
    Método POST
    <server>:<port>/Lec03-1/resources/articulo/ consume un objeto en formato JSON
    @paraam articulo
    @return el articulo insertado o actualizado
        
    */
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Articulo postArticulo(Articulo articulo){
        Articulo articulo2 = ArticuloGestion.getArticulo(articulo.getId());
        
        if (articulo2!=null){
            ArticuloGestion.modificar(articulo);
        }else{
            ArticuloGestion.insertar(articulo);
        }
        return ArticuloGestion.getArticulo(articulo.getId());
    }
    
    
    
    
    
    
    

    
}
