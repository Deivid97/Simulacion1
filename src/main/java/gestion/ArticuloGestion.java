
package gestion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Articulo;
import modelo.Conexion;


public class ArticuloGestion {
    
    
    // Operación de inserción en base de datos
    
    private static final String SQL_INSERTAR_ARTICULO= "insert into articulo (id,descripcion,"
            + "precio) values (?,?,?)";
    
    
    public static boolean insertar (Articulo articulo){
        
        System.out.println("insertar called");
        
        try{
            
            PreparedStatement sentencia = Conexion.getConexion().prepareCall(SQL_INSERTAR_ARTICULO);
            sentencia.setInt(1, articulo.getId());
            sentencia.setString(2, articulo.getDescripcion());
            sentencia.setInt(3, articulo.getPrecio());
            
            return sentencia.executeUpdate()>0;
            
        }catch (SQLException ex){
            
            Logger.getLogger(ArticuloGestion.class.getName()).log(Level.SEVERE,null,ex);
            
        }
        
        return false;
 
    }
   
    //SELECCIONA UN ARTICULO
    private static final String SQL_SELECT_ARTICULO = "select * from articulo where id=?";
    
    public static Articulo getArticulo (int id){
        
        System.out.println("getArticulo called");
        Articulo articulo=null;
        
        try{
            
            PreparedStatement consulta = Conexion.getConexion().prepareStatement(SQL_SELECT_ARTICULO);
            consulta.setInt(1, id);
            ResultSet datos = consulta.executeQuery();
            
            if (datos.next()){
                articulo=new Articulo(
                datos.getInt(1),
                datos.getString(2),
                datos.getInt(3));
                
            }

        }catch (SQLException ex){
            
            Logger.getLogger(ArticuloGestion.class.getName()).log(Level.SEVERE,null,ex);
            
        }
        return articulo;

    }
    
    //ACTUALIZA EL ARTICULO
    
    private static final String SQL_UPDATE_ARTICULO="update articulo set descripcion=?,"
            + "precio=? where id=?";
    
    public static boolean modificar (Articulo articulo){
        
        System.out.println("modificar called");
      
        try{
            
           PreparedStatement sentencia= Conexion.getConexion().prepareCall(SQL_UPDATE_ARTICULO);
           sentencia.setString(1, articulo.getDescripcion());
           sentencia.setInt(2, articulo.getPrecio());
           sentencia.setInt(3, articulo.getId());
           
           return sentencia.executeUpdate()>0;

        }catch (SQLException ex){
            
            Logger.getLogger(ArticuloGestion.class.getName()).log(Level.SEVERE,null,ex);
            
        }
        return false;
        
    }
    
    
    //ELIMINA EL ARTICULO
    
    private static final String SQL_DELETE_ARTICULO="delete from articulo where id=?";
    
    public static boolean eliminar (int id){
        
        System.out.println("eliminar called");
      
        try{
            
           PreparedStatement consulta= Conexion.getConexion().prepareStatement(SQL_DELETE_ARTICULO);
           consulta.setInt(1, id);
           return consulta.executeUpdate()>0;

        }catch (SQLException ex){
            
            Logger.getLogger(ArticuloGestion.class.getName()).log(Level.SEVERE,null,ex);
            
        }
        return false;
        
    }
    
    
     //SELECCIONA UN ARTICULO
    private static final String SQL_SELECT_ARTICULOS = "select * from articulo";
    
    public static ArrayList<Articulo> getArticulos (){
        
        System.out.println("getArticulos called");
        ArrayList<Articulo> lista= new ArrayList<>();
        
        try{
            
            PreparedStatement consulta = Conexion.getConexion().prepareStatement(SQL_SELECT_ARTICULOS);
            ResultSet rs = consulta.executeQuery();
            
            while (rs!=null && rs.next()){
                lista.add(new Articulo (rs.getInt(1),
                rs.getString(2),
                rs.getInt(3)));                
            }

        }catch (SQLException ex){
            
            Logger.getLogger(ArticuloGestion.class.getName()).log(Level.SEVERE,null,ex);
            
        }
        return lista;

    }
    
    
    
    
}
