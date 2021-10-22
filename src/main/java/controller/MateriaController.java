/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author jdavi
 */
@Named(value = "materiaController")
@RequestScoped
public class MateriaController {

    private String idMateria;
    private String tiraJson="xxxx";
    private String salida;
    private final String URI="http://localhost/Lec03-1/resources/materia";
    
    
    public MateriaController() {
    }

    public String getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(String idMateria) {
        this.idMateria = idMateria;
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

   
   
    
    
     public void hacerGetAll(){
               
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI);
        JsonArray response = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class);
        salida= response.toString();
        
    }
    
    
    public void hacerGet(){
               
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI+"/"+idMateria);
        JsonObject response = target.request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        salida = response.asJsonObject().toString();
        
    }
    
    
     public void hacerPut(){
               
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI);
        JsonReader lectorJson = Json.createReader(new StringReader(tiraJson));
        JsonObject jsonObject= lectorJson.readObject();
        Response response = target.request(MediaType.APPLICATION_JSON).put(Entity.json(jsonObject));
        salida= response.readEntity(String.class);
        
    }
    
    
    public void hacerPost(){
               
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI);
        JsonReader lectorJson = Json.createReader(new StringReader(tiraJson));
        JsonObject jsonObject= lectorJson.readObject();
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(jsonObject));
        salida= response.readEntity(String.class);
        
    }
    
    public void hacerDelete(){
    
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI+"/"+idMateria);
        JsonObject response = target.request(MediaType.APPLICATION_JSON).delete(JsonObject.class);
        salida= response.asJsonObject().toString();
    
    
    }
    
}
