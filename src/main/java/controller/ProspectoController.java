
package controller;

import gestion.ProspectoGestion;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import modelo.Prospecto;


@Named(value = "prospectoController")
@SessionScoped
public class ProspectoController extends Prospecto implements Serializable {

    
    public ProspectoController() {
    }
    
    public String inserta (){
        
        if (ProspectoGestion.insertar(this)){
            
            return "confirmacion.xhtml";
            
        }else {
            
            FacesMessage mensaje = new FacesMessage (FacesMessage.SEVERITY_ERROR, "Error",
                    "Posible cédula duplicada...");
            FacesContext.getCurrentInstance().addMessage("registroProspectoForm:cedula", mensaje);
            return "registro.xhtml";
            
        }
    }
    
    //Nuevo atributo 
    
    private String tiraJson;

    public String getTiraJson() {
        return tiraJson;
    }

    public void setTiraJson(String tiraJson) {
        this.tiraJson = tiraJson;
    }
    
    public void buscaProspecto(String id){
        
        Prospecto prospecto = ProspectoGestion.getProspecto(id);
        
        if (prospecto!=null){
            
            this.setCedula(prospecto.getCedula());
            this.setNombre(prospecto.getNombre());
            this.setApellido1(prospecto.getApellido1());
            this.setApellido2(prospecto.getApellido2());
            this.setFechaNacimiento(prospecto.getFechaNacimiento());
            this.setFechaGraduacionColegio(prospecto.getFechaGraduacionColegio());
            this.setFechaPosibleIngreso(prospecto.getFechaPosibleIngreso());
            this.setCorreo(prospecto.getCorreo());
            this.setCelular(prospecto.getCelular());
            
        }else{
            
            this.setCedula("");
            this.setNombre("");
            this.setApellido1("");
            this.setApellido2("");
            this.setFechaNacimiento(null);
            this.setFechaGraduacionColegio(null);
            this.setFechaPosibleIngreso(null);
            this.setCorreo("");
            this.setCelular("");
            
            FacesMessage mensaje = new FacesMessage (FacesMessage.SEVERITY_WARN, "No Encontrado",
                "Prospecto No Encontrado");
            FacesContext.getCurrentInstance().addMessage("prospectoJsonForm:identificacion", mensaje);
  
        }
   
    }
    
    
    //Crea un objeto tipo JSON a partir de los datos traídos de BD del prospecto que se buscó
    public void creaJson (){
        
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        
        JsonObjectBuilder jsonObjectBuilder= Json.createObjectBuilder();
        
        JsonObject jsonObject = jsonObjectBuilder.add("idProspecto",this.getCedula()).
                                add("nombre",this.getNombre()).
                                add("apellido1",this.getApellido1()).
                                add("apellido2",this.getApellido2()).
                                add("fechaNacimiento",dateFormat.format(this.getFechaNacimiento())).
                                add("fechaGraduacionColegio",dateFormat.format(this.getFechaGraduacionColegio())).
                                add("fechaPosibleIngreso",dateFormat.format(this.getFechaPosibleIngreso())).
                                add("correo",this.getCorreo()).
                                add("celular",this.getCelular()).build();
        
        StringWriter tira= new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(tira);
        jsonWriter.writeObject(jsonObject);
        
        //Seteamos el valor del objeto JSON en formato cadena para poder mostrarlo en pantalla
        this.setTiraJson(tira.toString());
        
    }
    
    //Crea un objeto de tipo prospecto a partir de la información de un formato JSON
    public void creaObjectProspecto (){
        
        try{
            
            DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
            JsonReader lectorJson = Json.createReader(new StringReader(tiraJson));
            JsonObject objetoJson = lectorJson.readObject();
            
            this.setCedula(objetoJson.getString("idProspecto"));
            this.setNombre(objetoJson.getString("nombre"));
            this.setApellido1(objetoJson.getString("apellido1"));
            this.setApellido2(objetoJson.getString("apellido2"));
            this.setFechaNacimiento(dateFormat.parse(objetoJson.getString("fechaNacimiento")));
            this.setFechaGraduacionColegio(dateFormat.parse(objetoJson.getString("fechaGraduacionColegio")));
            this.setFechaPosibleIngreso(dateFormat.parse(objetoJson.getString("fechaPosibleIngreso")));
            this.setCorreo(objetoJson.getString("correo"));
            this.setCelular(objetoJson.getString("celular"));
            
            
        }catch (ParseException ex){
            
            Logger.getLogger(ProspectoController.class.getName()).log(Level.SEVERE,null, ex);
        }
            
        
        
        
    }
    
}
