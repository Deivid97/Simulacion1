/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Conexion;
import modelo.Materia;

/**
 *
 * @author jdavi
 */
public class MateriaGestion {
    
private static final String SQL_INSERTAR_MATERIA= "insert into materia (idMateria,nombre) values (?,?)";

public static boolean insertar (Materia materia){

    try {
        PreparedStatement sentencia=Conexion.getConexion().prepareCall(SQL_INSERTAR_MATERIA);
        sentencia.setString(1, materia.getIdMateria());
        sentencia.setString(2, materia.getNombre());
        
        return sentencia.executeUpdate()>0;
        
    } catch (SQLException ex) {
        
        Logger.getLogger(MateriaGestion.class.getName()).log(Level.SEVERE,null,ex);
        
    }

   return false;

}

private static final String SQL_SELECT_MATERIA = "select * from materia where idMateria=?";

public static Materia getMateria (String idMateria){

Materia materia=null;

    try {
        PreparedStatement consulta = Conexion.getConexion().prepareStatement(SQL_SELECT_MATERIA);
        consulta.setString(1, idMateria);
        ResultSet datos = consulta.executeQuery();
        
        if (datos.next()){
            materia=new Materia(
            datos.getString(2),
            datos.getString(3));
        
        }
        
        
    } catch (SQLException ex) {
        
        Logger.getLogger(MateriaGestion.class.getName()).log(Level.SEVERE,null,ex);
    }

    return materia;
}


private static final String SQL_UPDATE_MATERIA="update materia set nombre=? where idMateria=?";

public static boolean modificar(Materia materia){

    try {
        PreparedStatement sentencia=Conexion.getConexion().prepareCall(SQL_UPDATE_MATERIA);;
        sentencia.setString(1, materia.getNombre());
        
        return sentencia.executeUpdate()>0;
        
        
    } catch (SQLException ex) {
        
        Logger.getLogger(MateriaGestion.class.getName()).log(Level.SEVERE,null,ex);
    }

  return false;




}

private static final String SQL_SELECT_MATERIAS="select * from materia";

public static ArrayList<Materia> getMateria(){
    
 ArrayList<Materia> lista=new ArrayList<>();

    try {
        
        PreparedStatement consulta = Conexion.getConexion().prepareStatement(SQL_SELECT_MATERIAS);
        ResultSet rs = consulta.executeQuery();
        
        while(rs!=null && rs.next()){
         lista.add(new Materia(rs.getString(2),
         rs.getString(3)));
        
        }
        
    } catch (SQLException ex) {
        
        Logger.getLogger(MateriaGestion.class.getName()).log(Level.SEVERE,null,ex);
    }

 return lista;

}

public static final String SQL_DELETE_MATERIA="delete from materia where idMateria=?";


public static boolean eliminar(Materia materia){

    try {
        PreparedStatement consulta = Conexion.getConexion().prepareStatement(SQL_DELETE_MATERIA);
        consulta.setString(1, materia.getIdMateria());
        return consulta.executeUpdate()>0;
        
    } catch (SQLException ex) {
        
    Logger.getLogger(MateriaGestion.class.getName()).log(Level.SEVERE,null,ex);
    
    }
    
return false;

}
        


}
