/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gestion.UsuarioGestion;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Usuario;

/**
 *
 * @author 111690771
 */
@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController extends Usuario implements Serializable {

    /**
     * Creates a new instance of UsuarioController
     */
    public UsuarioController() {
        super("","","");
    }
    
    public String valida(){
        
        Usuario usuario= UsuarioGestion.Valida(this.getIdUsuario(),this.getPwUsuario());
        
        if (usuario!=null){
            
            this.setIdUsuario(usuario.getIdUsuario());
            this.setNombreUsuario(usuario.getNombreUsuario());
            this.setIdRol(usuario.getIdRol());
            
            return "principal.xhtml";
        }else{
            
            FacesMessage msg = new FacesMessage (FacesMessage.SEVERITY_ERROR,"Error","Usuario o "
                    + "contraseña inválidos.");
            FacesContext.getCurrentInstance().addMessage("loginForm:clave", msg);
            return "index.xhtml";
        }
  
    }
    
}
