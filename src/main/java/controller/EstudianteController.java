
package controller;

import gestion.EstudianteGestion;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.Estudiante;


@Named(value = "estudianteController")
@SessionScoped
public class EstudianteController extends Estudiante implements Serializable {

    /**
     * Creates a new instance of EstudianteController
     */
    public EstudianteController() {
    }
    
    //Método insertar del controlador
    public String inserta(){
        
        if (EstudianteGestion.insertar(this)){
            return "list.xhtml";
            
        }else{
            
            FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
            "Error", "Posible identificación duplicada");
            FacesContext.getCurrentInstance().addMessage("editaEstudianteForm:identificacion", mensaje);
            return "edita.xhtml";
        }
        
    }
    
    public String modifica(){
        
        if (EstudianteGestion.actualiza(this)){
            return "list.xhtml";
        }else{
             FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
            "Error", "Posible identificación duplicada");
            FacesContext.getCurrentInstance().addMessage("editaEstudianteForm:identificacion", mensaje);
            return "edita.xhtml";   
        }
    }
    
    public String elimina(){
        
        if (EstudianteGestion.eliminar(this)){
            return "list.xhtml";
        }else{
             FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
            "Error", "Posible que el id no exista");
            FacesContext.getCurrentInstance().addMessage("editaEstudianteForm:identificacion", mensaje);
            return "edita.xhtml";
            
        }
   
    }
    
    public String edita(String id){
        
        Estudiante estudiante= EstudianteGestion.getEstudiante(id);
        if (estudiante!=null){
            
            this.setId(estudiante.getId());
            this.setNombre(estudiante.getNombre());
            this.setApellido1(estudiante.getApellido1());
            this.setApellido2(estudiante.getApellido2());
            this.setFechaNaci(estudiante.getFechaNaci());
            this.setFechaIngr(estudiante.getFechaIngr());
            this.setGenero(estudiante.getGenero());
            return "edita.xhtml";
        }else{
             FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
            "Error", "Posible que el id no exista");
            FacesContext.getCurrentInstance().addMessage("listForm", mensaje);
            return "edita.xhtml";
            
        }

    }

    public List<Estudiante> getEstudiantes(){
        
        return EstudianteGestion.getEstudiantes();
        
    }
    
    private boolean noImprimir= true;  //Para habilitar o deshabilitar el botón que imprime la certificación

    public boolean isNoImprimir() {
        return noImprimir;
    }

    public void setNoImprimir(boolean noImprimir) {
        this.noImprimir = noImprimir;
    }
    
    public void buscaEstudiante (String id){
        
        Estudiante estudiante = EstudianteGestion.getEstudiante(id);
        
        if (estudiante!= null){
            this.setId(estudiante.getId());
            this.setNombre(estudiante.getNombre());
            this.setApellido1(estudiante.getApellido1());
            this.setApellido2(estudiante.getApellido2());
            this.setFechaNaci(estudiante.getFechaNaci());
            this.setFechaIngr(estudiante.getFechaIngr());
            noImprimir=false;
            
        }else {
            
            this.setId("");
            this.setNombre("");
            this.setApellido1("");
            this.setApellido2("");
            this.setFechaNaci(null);
            this.setFechaIngr(null);
            
            FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_WARN, "No Encontrado",
            "Estudiante No Encontrado");
            FacesContext.getCurrentInstance().addMessage("certificacionEstudianteForm:identificacion", mensaje);
            noImprimir=true;
            
        }
        
        //return "certificacion.xhtml";
        
    }
    
    public void respaldo(){
        
        String json  = EstudianteGestion.GenerarJson();
        try{
            File f = new File (FacesContext.getCurrentInstance().getExternalContext().getRealPath("/respaldo")+"estudiantes.zip");
            ZipOutputStream out = new ZipOutputStream (new FileOutputStream(f));
            ZipEntry e = new ZipEntry ("estudiantes.json");
            try{     
                out.putNextEntry(e);
                byte[] data =json.getBytes();
                out.write(data,0,data.length);
                out.closeEntry();
                out.close();

                File zipPath = new File (FacesContext.getCurrentInstance().getExternalContext()
                .getRealPath("/respaldo")+"estudiantes.zip");
                
                byte[] zip = Files.readAllBytes(zipPath.toPath());
                
                HttpServletResponse respuesta = (HttpServletResponse)
                    FacesContext.getCurrentInstance().getExternalContext().getResponse();
                
                ServletOutputStream sos = respuesta.getOutputStream();
            
                respuesta.setContentType("application/zip");
                respuesta.addHeader("Content-Disposition","attachment; filename=estudiantes.zip");
                
                sos.write(zip);
                sos.flush();
                FacesContext.getCurrentInstance().responseComplete();
                
            }catch (IOException ex){

            }
        }
        catch(FileNotFoundException ex){
            
        }
        
        
    }
    
    
    
}
