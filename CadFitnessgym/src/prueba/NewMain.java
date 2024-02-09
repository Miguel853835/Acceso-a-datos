/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import cadfitnessgym.CadFitnessgym;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import pojosfitnessgym.Comida;
import pojosfitnessgym.Dieta;
import pojosfitnessgym.ExcepcionFitnessGym;
import pojosfitnessgym.Rutina;
import pojosfitnessgym.Usuario;

/**
 *
 * @author DAM219
 */
public class NewMain {
    public static void main(String[]args) throws ParseException, ExcepcionFitnessGym, SQLException{
        
CadFitnessgym cfg = new CadFitnessgym();


///////////////INSERTAR USUARIO///////////////
//
//int ra = 0;
//        try{
//            String fechaStr = "23/12/2023";
//
//        Date fechaDate = null;
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        Rutina r = new Rutina();
//        r.setIdRutina(1);
//        Dieta d = new Dieta();
//        d.setIdDieta(1);
//        Usuario e = new Usuario();
//        e.setIdUsuario(1);
//        try {
//            fechaDate = dateFormat.parse(fechaStr);
//        } catch (ParseException ex) {
//            ex.printStackTrace();
//        }
//            Usuario u = new Usuario("Carlos","Sedano","Izurieta","666777888","carlos@sedano","cs",fechaDate,"U",r,d,e);
//            ra = cfg.insertarUsuario(u);
//        }catch (ExcepcionFitnessGym ex){
//            System.out.println(ex.getMensajeUsuario());
//            System.out.println("Log: " + ex.getCodigoErrorBd() + " - " + ex.getMensajeErrorBd() + " - " + ex.getSentenciaSql());
//        }
//        System.out.println("Registros afectados " + ra);

///////////////ELIMINAR USUARIO///////////////
//int ra = 0;
//        try{
//            ra = cfg.eliminarUsuario(1);
//        }catch (ExcepcionFitnessGym ex){
//            System.out.println(ex.getMensajeUsuario());
//            System.out.println("Log: " + ex.getCodigoErrorBd() + " - " + ex.getMensajeErrorBd() + " - " + ex.getSentenciaSql());
//        }
//        System.out.println("Registros afectados " + ra);


///////////////INSERTAR COMIDA  ///////////////
//
//int ra = 0;
//        try{
//            Usuario e = new Usuario();
//            e.setIdUsuario(1);
//    Comida c = new Comida("Pasta","Bolognesa",12,e);
//            ra = cfg.insertarComida(c);
//        }catch (ExcepcionFitnessGym ex){
//            System.out.println(ex.getMensajeUsuario());
//            System.out.println("Log: " + ex.getCodigoErrorBd() + " - " + ex.getMensajeErrorBd() + " - " + ex.getSentenciaSql());
//        }
//        System.out.println("Registros afectados " + ra);

//        
///////////////MODIFICAR USUARIO///////////////

//        Usuario u = new Usuario();
//        Rutina r = new Rutina();
//        
//        r.setIdRutina(1);
//        
//        Usuario e = new Usuario();
//        
//        e.setIdUsuario(2);
//        
//        Dieta d = new Dieta();
//        d.setIdDieta(1);
//        u.setNombre("sd");
//        u.setApellido1("prueba");
//        u.setApellido2("prueba");
//        u.setTelefono("132423");
//        u.setCorreo("prueba@prueba");
//        u.setContrasena("jj");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date fechaRegistro = sdf.parse("2023-11-19");
//        u.setFechaRegistro(fechaRegistro);
//        u.setEspecialidad("U");
//        u.setRutina(r);
//        u.setDieta(d);
//        u.setEntrenador(e);
//        
//        int ra = 0;
//        try{
//            ra = cfg.modificarUsuario(1, u);
//        }catch (ExcepcionFitnessGym ex){
//            System.out.println(ex.getMensajeUsuario());
//            System.out.println("Log: " + ex.getCodigoErrorBd() + " - " + ex.getMensajeErrorBd() + " - " + ex.getSentenciaSql());
//        }
//        System.out.println("Registros afectados " + ra);
        
   
///////////////MODIFICAR COMIDA///////////////
//    Usuario g = new Usuario();
//    g.setIdUsuario(1);
//    Comida c = new Comida();
//    c.setIdComida(2);
//    c.setNombre("queso");
//    c.setDescripcion("queso");
//    c.setCalorias(3);
//    c.setUsuario(g);
//    
//    int ra = 0;
//        try{
//            ra = cfg.modificarComida(2, c);
//        }catch (ExcepcionFitnessGym ex){
//            System.out.println(ex.getMensajeUsuario());
//            System.out.println("Log: " + ex.getCodigoErrorBd() + " - " + ex.getMensajeErrorBd() + " - " + ex.getSentenciaSql());
//        }
//        System.out.println("Registros afectados " + ra);

///////////////ELIMINAR COMIDA///////////////
//int ra = 0;
//        try{
//            ra = cfg.eliminarComida(1);
//        }catch (ExcepcionFitnessGym ex){
//            System.out.println(ex.getMensajeUsuario());
//            System.out.println("Log: " + ex.getCodigoErrorBd() + " - " + ex.getMensajeErrorBd() + " - " + ex.getSentenciaSql());
//        }
//        System.out.println("Registros afectados " + ra);

///////////////LEER UN USUARIO///////////////
//    Usuario lu = cfg.leerUsuario(1);
//        System.out.println(lu);

///////////////LEER UNA COMIDA///////////////
//    Comida lc = cfg.leerComida(2);
//        System.out.println(lc);

///////////////LEER COMIDAS///////////////
//
//        ArrayList lc = cfg.leerComidas();
//        System.out.println(lc);
        
///////////////LEER USUARIOS///////////////
//
//    ArrayList lu = cfg.leerUsuarios();           ERROR STACKOVERFLOW
//    System.out.println(lu);
    }
}
