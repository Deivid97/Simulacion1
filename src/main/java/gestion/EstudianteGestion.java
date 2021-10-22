
package gestion;

import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import modelo.Conexion;
import modelo.Estudiante;

/*
Operaciones básicas de gestión de la información en base de datos

CRUD
Create
Read
Update
Delete

*/

public class EstudianteGestion {
    
    // Declaración de Query y método para insertar un nuevo estudiante 
    private static final String SQL_INSERT_ESTUDIANTE= "insert into estudiante (idEstudiante,nombre,"
            + "apellido1,apellido2,fechaNaci,fechaIngr,genero) values (?,?,?,?,?,?,?)";
    
    public static boolean insertar(Estudiante estudiante){

        try {
            PreparedStatement sentencia= Conexion.getConexion().prepareCall(SQL_INSERT_ESTUDIANTE);
            sentencia.setString(1, estudiante.getId());
            sentencia.setString(2, estudiante.getNombre());
            sentencia.setString(3, estudiante.getApellido1());
            sentencia.setString(4, estudiante.getApellido2());
            sentencia.setObject(5, estudiante.getFechaNaci());
            sentencia.setObject(6, estudiante.getFechaIngr());
            sentencia.setString(7, ""+estudiante.getGenero());
            return sentencia.executeUpdate()>0; // Retorna true si lo logra insertar, false si no
            
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteGestion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false; // No se logró insertar.
        
    }
    
    // Declaración de Query y método para consultar un estudiante en específico
    
    private static final String SQL_SELECT_ESTUDIANTE= "select * from estudiante where idEstudiante=?";
    
    
    public static Estudiante getEstudiante(String id){
        
        Estudiante estudiante=null;
        
        try {
            
            
            PreparedStatement consulta = Conexion.getConexion().prepareStatement(SQL_SELECT_ESTUDIANTE);
            consulta.setString(1, id);
            ResultSet datos= consulta.executeQuery();
            //Si encontró alguna coincidencia
            if (datos.next()){
                estudiante= new Estudiante(
                datos.getString(2),//cédula o idEstudiante
                datos.getString(3),
                datos.getString(4),
                datos.getString(5),
                datos.getDate(6),
                datos.getDate(7),
                datos.getString(8).charAt(0)
                );    
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteGestion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return estudiante;
        
    }
    
    // Declaración de Query y método para actualizar un estudiante en base de datos
    
    private static final String SQL_UPDATE_ESTUDIANTE= "update estudiante set nombre=?,apellido1=?,"
            + "apellido2=?,fechaNaci=?,fechaIngr=?,genero=? where idEstudiante=?";
    
    public static boolean actualiza (Estudiante estudiante){
        
        
        try {
            PreparedStatement sentencia= Conexion.getConexion().prepareCall(SQL_UPDATE_ESTUDIANTE);
            sentencia.setString(1, estudiante.getNombre());
            sentencia.setString(2, estudiante.getApellido1());
            sentencia.setString(3, estudiante.getApellido2());
            sentencia.setObject(4, estudiante.getFechaNaci());
            sentencia.setObject(5, estudiante.getFechaIngr());
            sentencia.setString(6, ""+estudiante.getGenero());
            sentencia.setString(7, estudiante.getId());
            return sentencia.executeUpdate()>0; // Retorna true en caso de poder actualizar, false caso contrario
            
            
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteGestion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
     // Declaración de Query y método para eliminar un estudiante en base de datos
    private static final String SQL_DELETE_ESTUDIANTE= "delete from estudiante where idEstudiante=?";
    
    public static boolean eliminar (Estudiante estudiante){
        
        try {
            PreparedStatement consulta= Conexion.getConexion().prepareStatement(SQL_DELETE_ESTUDIANTE);
            consulta.setString(1, estudiante.getId());
            return consulta.executeUpdate()>0; // Si es mayor quiere decir que lo borró y retorna true, de lo contrario
            //false
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteGestion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
        
    }

    // Declaración de Query y método para consultar la lista de estudiantes en base de datos
    
    private static final String SQL_SELECT_ESTUDIANTES="Select * from estudiante";
    //private static final String SQL_SELECT_ESTUDIANTES="Select * from estudiante Limit 0,200";
    
    public static ArrayList<Estudiante> getEstudiantes(){
        
        ArrayList<Estudiante> lista= new ArrayList<>();
        
        try {
            
            PreparedStatement consulta= Conexion.getConexion().prepareStatement(SQL_SELECT_ESTUDIANTES);
            ResultSet rs= consulta.executeQuery();
            while (rs!=null && rs.next()){
                lista.add(new Estudiante(rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getDate(6),
                rs.getDate(7),
                rs.getString(8).charAt(0)));           
            }

        } catch (SQLException ex) {
            Logger.getLogger(EstudianteGestion.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return lista;     
    }

    public static String GenerarJson(){
        
        Estudiante estudiante= new Estudiante ();
        String tiraJson="",fechaNaci,fechaIngr;
        DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        
        try {
            
            PreparedStatement consulta= Conexion.getConexion().prepareStatement(SQL_SELECT_ESTUDIANTES);
            ResultSet rs= consulta.executeQuery();
            while (rs!=null && rs.next()){
               estudiante.setId(rs.getString(2));
               estudiante.setNombre(rs.getString(3));
               estudiante.setApellido1(rs.getString(4));
               estudiante.setApellido2(rs.getString(5));
               estudiante.setFechaNaci(rs.getDate(6));
               estudiante.setFechaIngr(rs.getDate(7));
               
                JsonObjectBuilder jsonObjectBuilder= Json.createObjectBuilder();
                JsonObject jsonObject = jsonObjectBuilder.
                add("idEstudiante", estudiante.getId()).
                add("nombre", estudiante.getNombre()).
                add("apellido1", estudiante.getApellido1()).
                add("apellido2", estudiante.getApellido2()).
                add("fechaNacimiento", dateFormat.format(estudiante.getFechaNaci())).
                add("fechaGraduacionColegio", dateFormat.format(estudiante.getFechaIngr())).build();
                
                
                StringWriter tira= new StringWriter();
                JsonWriter jsonWriter = Json.createWriter(tira);
                jsonWriter.writeObject(jsonObject);
                
                if (tiraJson==null){
                    tiraJson= tira.toString() + "\n";
                }else{
                    tiraJson= tiraJson + tira.toString() + "\n";
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(EstudianteGestion.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        return tiraJson;
    }
    
    
    
}


