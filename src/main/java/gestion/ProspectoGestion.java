
package gestion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Conexion;
import modelo.Prospecto;


public class ProspectoGestion {
    
    private static final String SQL_INSERT_PROSPECTO= 
            "insert into prospecto (idProspecto,nombre,Apellido1,Apellido2,"
            + "fechaNacimiento,fechaGraduacionColegio,fechaPosibleIngreso,correo,celular) "
            + "values (?,?,?,?,?,?,?,?,?)";
    
    
    //Retorna verdadero si se logra insertar, falso si no lo logra
    public static boolean insertar(Prospecto prospecto){
        
        try {
            PreparedStatement sentencia = Conexion.getConexion().prepareStatement(SQL_INSERT_PROSPECTO);
            
            sentencia.setString(1, prospecto.getCedula());
            sentencia.setString(2, prospecto.getNombre());
            sentencia.setString(3, prospecto.getApellido1());
            sentencia.setString(4, prospecto.getApellido2());
            sentencia.setObject(5, prospecto.getFechaNacimiento());
            sentencia.setObject(6, prospecto.getFechaGraduacionColegio());
            sentencia.setObject(7, prospecto.getFechaPosibleIngreso());
            sentencia.setString(8, prospecto.getCorreo());
            sentencia.setString(9, prospecto.getCelular());
            
            int fila = sentencia.executeUpdate();
            
            return fila>0; // True en caso de que si se pueda insertar el valor en base de datos 
            
        } catch (SQLException ex) {
            Logger.getLogger(ProspectoGestion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    private static final String SQL_SELECT_PROSPECTO = "select * from prospecto where idProspecto=?";
    
    
    public static Prospecto getProspecto (String id){
        
        Prospecto prospecto = null;
        
        try{
            
            PreparedStatement consulta=  Conexion.getConexion().prepareStatement(SQL_SELECT_PROSPECTO);
            consulta.setString(1, id);
            ResultSet datos = consulta.executeQuery();
            
            if (datos.next()){
                
                prospecto = new Prospecto(
                        datos.getString(2),
                        datos.getString(3),
                        datos.getString(4),
                        datos.getString(5),
                        datos.getDate(6),
                        datos.getDate(7),
                        datos.getDate(8),
                        datos.getString(9),
                        datos.getString(10)
                );
            }
            
            
        }catch (SQLException ex) {
            Logger.getLogger(ProspectoGestion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prospecto;
        
    }
    
    
}
