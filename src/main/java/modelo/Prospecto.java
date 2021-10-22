


package modelo;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Prospecto {
    
    private String cedula;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private Date fechaNacimiento;
    private Date fechaGraduacionColegio;
    private Date fechaPosibleIngreso;
    private String correo;
    private String celular;

    public Prospecto() {
    }

    public Prospecto(String cedula, String nombre, String apellido1, String apellido2, Date fechaNacimiento, Date fechaGraduacionColegio, Date fechaPosibleIngreso, String correo, String celular) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaGraduacionColegio = fechaGraduacionColegio;
        this.fechaPosibleIngreso = fechaPosibleIngreso;
        this.correo = correo;
        this.celular = celular;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaGraduacionColegio() {
        return fechaGraduacionColegio;
    }

    public void setFechaGraduacionColegio(Date fechaGraduacionColegio) {
        this.fechaGraduacionColegio = fechaGraduacionColegio;
    }

    public Date getFechaPosibleIngreso() {
        return fechaPosibleIngreso;
    }

    public void setFechaPosibleIngreso(Date fechaPosibleIngreso) {
        this.fechaPosibleIngreso = fechaPosibleIngreso;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    //Sobreescribimos el método toString para mostrarlo como un JSON
    @Override
    public String toString() {
        
        //Convertimos los campos fecha al formato indicado
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
        String fecha1= format.format(this.fechaNacimiento);
        String fecha2= format.format(this.fechaGraduacionColegio);
        String fecha3= format.format(this.fechaPosibleIngreso);
        
        // Expresión JSON del objeto prospecto
        return "{\"Prospecto\":{"
              + "\"cedula\":\"" + cedula + "\",\n"
              + "\"nombre\":\"" + nombre + "\",\n"
              + "\"apellido1\":\"" + apellido1 + "\",\n"
              + "\"apellido2\":\"" + apellido2 + "\",\n"
              + "\"fechaNacimiento\":\"" + fecha1 + "\",\n"
              + "\"fechaGraduacionColegio\":\"" + fecha2 +"\",\n"
              + "\"fechaPosibleIngreso\":\"" + fecha3 + "\",\n"
              + "\"correo\":\"" + correo + "\",\n"
              + "\"celular\":\"" + celular + "\"\n}\n}";
    }
   
}
