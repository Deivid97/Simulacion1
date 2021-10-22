
package controller;

import java.io.StringReader;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Named(value = "articuloController")
@RequestScoped
public class ArticuloController {

    private int id; // El id para bucar artículos
    private String tiraJson="xxxx";
    private String salida;
    private final String URI="http://localhost/Lec03-1/resources/articulo";
    
    
    public ArticuloController() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTiraJson() {
        return tiraJson;
    }

    public void setTiraJson(String tiraJson) {
        this.tiraJson = tiraJson;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }
    
    
    //El método hacerGetAll llama al método GET (todos) y 
    // coloca el resultado en la salida
    
    public void hacerGetAll(){
               
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI);
        JsonArray response = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class);
        salida= response.toString();
        
    }
    
    
     //El método hacerGet llama al método GET ojo como se pasa el parámetro
    // agregando el id como parte del target, se coloca el resulta en la salida
    
    
    public void hacerGet(){
               
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI+"/"+id);
        JsonObject response = target.request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        salida= response.asJsonObject().toString();
        
    }
    
    
      //El método hacer delete llama al método DELETE ojo como se pasa el parámetro
    //agregando el id como parte del target, se coloca el resultado en la salida
    
    
    
    public void hacerDelete(){
               
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI+"/"+id);
        JsonObject response = target.request(MediaType.APPLICATION_JSON).delete(JsonObject.class);
        salida= response.asJsonObject().toString();
        
    }
    
    
   // El método hacerPUT llama al método PUT ojo como se la pasa la información
    //Se coloca el resultado el salida
    
    public void hacerPut(){
               
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI);
        JsonReader lectorJson = Json.createReader(new StringReader(tiraJson));
        JsonObject jsonObject= lectorJson.readObject();
        Response response = target.request(MediaType.APPLICATION_JSON).put(Entity.json(jsonObject));
        salida= response.readEntity(String.class);
        
    }
    
    
    // El método hacerPUT llama al método PUT ojo como se la pasa la información
    //Se coloca el resultado el salida
    
    public void hacerPost(){
               
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI);
        JsonReader lectorJson = Json.createReader(new StringReader(tiraJson));
        JsonObject jsonObject= lectorJson.readObject();
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(jsonObject));
        salida= response.readEntity(String.class);
        
    }
    
    
}
