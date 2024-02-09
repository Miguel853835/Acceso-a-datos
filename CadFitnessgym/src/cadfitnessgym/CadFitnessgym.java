/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package cadfitnessgym;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import pojosfitnessgym.Comida; 
import pojosfitnessgym.Dieta;
import pojosfitnessgym.Usuario;
import pojosfitnessgym.ExcepcionFitnessGym;
import pojosfitnessgym.Rutina;


/**
 * En esta clase es la que implementa el Componente de Acceso a Datos de la base de datos FitnessGym
 * @author DAM219
 * @version 1.0
 * @since AaD 1.0
*/
public class CadFitnessgym {
  private Connection conexion;
  
 /**
 * Constructor vacío de la clase CadFitnessGym 
 * @throws pojosfitnessgym.ExcepcionFitnessGym La excepción se produce cuando hay algún problemna en la carga del jdbc
*/ 
    public CadFitnessgym() throws ExcepcionFitnessGym {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch(ClassNotFoundException ex) {
            ExcepcionFitnessGym e = new ExcepcionFitnessGym();
            e.setMensajeErrorBd(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
        }   
    }
    
 /**
 *Método que establece la conexión con la base de datos FitnessGym
 * @throws java.lang.ExcepcionFitnessGym La excepción se produce cuando no es posible establecer la conexión con la base de datos FitnessGym
*/
    private void conectar() throws ExcepcionFitnessGym {
       try {
           conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "fitness_gym", "kk");
       } catch (SQLException ex){
           ExcepcionFitnessGym e = new ExcepcionFitnessGym();
           e.setCodigoErrorBd(ex.getErrorCode());
           e.setMensajeErrorBd(ex.getMessage());
           e.setMensajeUsuario("Error general del sistema. Consulte con el administrador.");
           throw e;
       }
   }

/**
 * Método que permite insertar un usuario de la base de datos FitnessGym
 * @param usuario Objeto usuario que almacena los datos a insertar en la base de datos
 * @return Integer con la cantidad de registros afectados al insertar el usuario
     * @throws pojosfitnessgym.ExcepcionFitnessGym La excepción se produce cuando no es posible establecer la conexión con la base de datos FitnessGym
*/
    public int insertarUsuario(Usuario usuario) throws ExcepcionFitnessGym{
        
        conectar();
        int registrosAfectados = 0;
        String llamada = "call insertar_usuario(?,?,?,?,?,?,?,?,?,?,?)";

        try {
           
            CallableStatement sentenciaLlamable = conexion.prepareCall(llamada);
            
            sentenciaLlamable.setString(1, usuario.getNombre());
            sentenciaLlamable.setString(2, usuario.getApellido1());
            sentenciaLlamable.setString(3, usuario.getApellido2());
            sentenciaLlamable.setObject(4, usuario.getTelefono(), Types.INTEGER);
            sentenciaLlamable.setString(5, usuario.getCorreo());
            sentenciaLlamable.setString(6, usuario.getContrasena());
            java.sql.Date sqlDate = new java.sql.Date(usuario.getFechaRegistro().getTime());
            sentenciaLlamable.setDate(7, sqlDate);
            sentenciaLlamable.setString(8, usuario.getEspecialidad());
            sentenciaLlamable.setObject(9, usuario.getRutina().getIdRutina(), Types.INTEGER);
            sentenciaLlamable.setObject(10, usuario.getDieta().getIdDieta(), Types.INTEGER);
            sentenciaLlamable.setObject(11, usuario.getEntrenador().getIdUsuario(), Types.INTEGER);
            sentenciaLlamable.executeUpdate();
            
            sentenciaLlamable.close();
            conexion.close();
           
        } catch (SQLException ex) {
           ExcepcionFitnessGym e = new ExcepcionFitnessGym();
           e.setCodigoErrorBd(ex.getErrorCode());
           e.setMensajeErrorBd(ex.getMessage());
           e.setSentenciaSql(llamada);
           switch(ex.getErrorCode()){
               case 1400:
                   e.setMensajeUsuario("Todos los campos menos el segundo apellido son obligatorios.");
                   break;
               case 1:
                   e.setMensajeUsuario("El número teléfono o la dirección de correo ya existen.");
                   break;
               case 2290:
                   e.setMensajeUsuario("El error se debe a una de estas tres razones:\n   El correo debe tener un @.\n   La fecha de registro debe ser el día actual.\n   La especialidad debe ser una entre 'M','Y','P','C' y 'F' si eres ENTRENADOR o 'U' si eres un USUARIO");
                   break;
               case 2291:
                   e.setMensajeUsuario("La rutina, dieta o entrenador no existe.");
                   break;
               default:
                   e.setMensajeUsuario("Error general del sistema consulte con el administrador");
           }
           throw e;
        }
        return registrosAfectados;
    }

   /**
 * Método que permite modificar un usuario de la base de datos FitnessGym
 * @param idUsuario identificador del Usuario a modificar
 * @param usuario Objeto usuario que almacena los datos a modificar en la base de datos
 * @return Array de objetos Usuario que contiene los datos de los usuarios leídos
 * @throws pojosfitnessgym.ExcepcionFitnessGym La excepción se produce cuando no es posible establecer la conexión con la base de datos FitnessGym
     * @throws java.sql.SQLException
*/
    public int modificarUsuario(Integer idUsuario, Usuario usuario) throws ExcepcionFitnessGym, SQLException{

        conectar();
        int registrosAfectados = 0;
        String dml = "update USUARIO set NOMBRE=?, APELLIDO1=?, APELLIDO2=?, TELEFONO=?, CORREO=?, CONTRASENA=?, FECHA_REGISTRO=?, ESPECIALIDAD=?, ID_ENTRENADOR=? where ID_USUARIO=?";
        PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml); 

        try {

            sentenciaPreparada.setString(1, usuario.getNombre() );
            sentenciaPreparada.setString(2, usuario.getApellido1() );
            sentenciaPreparada.setString(3, usuario.getApellido2());
            sentenciaPreparada.setString(4, usuario.getTelefono());
            sentenciaPreparada.setString(5, usuario.getCorreo());
            sentenciaPreparada.setString(6, usuario.getContrasena());
            java.sql.Date sqlDate = new java.sql.Date(usuario.getFechaRegistro().getTime());
            sentenciaPreparada.setDate(7, sqlDate);
            sentenciaPreparada.setString(8, usuario.getEspecialidad());
            sentenciaPreparada.setObject(9, usuario.getEntrenador().getIdUsuario(), Types.INTEGER);
            sentenciaPreparada.setObject(10,idUsuario, Types.INTEGER);

            registrosAfectados = sentenciaPreparada.executeUpdate();

            sentenciaPreparada.close();
            conexion.close();

        } catch (SQLException ex) {
           ExcepcionFitnessGym e = new ExcepcionFitnessGym();
           e.setCodigoErrorBd(ex.getErrorCode());
           e.setMensajeErrorBd(ex.getMessage());
           e.setSentenciaSql(dml);
           switch(ex.getErrorCode()){
               case 1407:
                   e.setMensajeUsuario("Todos los campos menos el segundo apellido son obligatorios.");
                   break;
               case 1:
                   e.setMensajeUsuario("El número teléfono o la dirección de correo ya existen.");
                   break;
               case 2290:
                   e.setMensajeUsuario("El error se debe a una de estas tres razones:\nEl correo debe tener un @.\nLa fecha de registro debe ser el día actual.\nLa especialidad debe ser una entre 'M','Y','P','C' y 'F' si eres ENTRENADOR o 'U' si eres un USUARIO");
                   break;
               case 2291:
                   e.setMensajeUsuario("La rutina, dieta o entrenador no existe.");
                   break;
               default:
                   e.setMensajeUsuario("Error general del sistema consulte con el administrador");
           }
           throw e;
        }

        return registrosAfectados;
    }

    /**
 * Método que permite eliminar un usuario de la base de datos FitnessGym
 * @param idUsuario identificador del Usuario a modificar
 * @return Integer con la cantidad de registros afectados al insertar el usuario
     * @throws pojosfitnessgym.ExcepcionFitnessGym
     * @throws java.sql.SQLException
*/
    public int eliminarUsuario(Integer idUsuario) throws ExcepcionFitnessGym, SQLException{
    conectar();
    int registrosAfectados = 0; 
    String dml = "DELETE FROM USUARIO WHERE ID_USUARIO = ?";
    PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);
    try {

        sentenciaPreparada.setInt(1, idUsuario);
 
        registrosAfectados = sentenciaPreparada.executeUpdate();
 
        sentenciaPreparada.close();
        conexion.close();
    
    } catch (SQLException ex) {
        ExcepcionFitnessGym e = new ExcepcionFitnessGym();
        e.setCodigoErrorBd(ex.getErrorCode());
        e.setMensajeErrorBd(ex.getMessage());
        e.setSentenciaSql(dml);
 
        switch (ex.getErrorCode()) {
            case 2292:
                e.setMensajeUsuario("No se puede eliminar el usuario porque hay registros asociados a el.");
                break;
            default:
                e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
        }
 
        throw e;
    }
 
    return registrosAfectados;
    }

     /**
 * Método que permite leer un usuario de la base de datos FitnessGym
     * @param idUsuario
 * @return Objeto Usuario que contiene los datos del usuario leído
     * @throws pojosfitnessgym.ExcepcionFitnessGym
*/
       public Usuario leerUsuario(Integer idUsuario) throws ExcepcionFitnessGym{
    conectar();
    Usuario u = new Usuario();
 
    String query = "SELECT * FROM USUARIO WHERE ID_USUARIO = " + idUsuario;
 
    try {
        Statement statement = conexion.createStatement();
        ResultSet resultado = statement.executeQuery(query);
 
        if (resultado.next()) {
 
            u.setIdUsuario(idUsuario);
            u.setNombre(resultado.getString("NOMBRE"));
            u.setApellido1(resultado.getString("APELLIDO1"));
            u.setApellido2(resultado.getString("APELLIDO2"));
            u.setTelefono(resultado.getString("TELEFONO"));
            u.setCorreo(resultado.getString("CORREO"));
            u.setContrasena(resultado.getString("CONTRASENA"));
            u.setFechaRegistro(resultado.getDate("FECHA_REGISTRO"));
            u.setEspecialidad(resultado.getString("ESPECIALIDAD"));
        }
 
        resultado.close();
        statement.close();
        conexion.close();
    } catch (SQLException ex) {
        ExcepcionFitnessGym e = new ExcepcionFitnessGym();
        e.setCodigoErrorBd(ex.getErrorCode());
        e.setMensajeErrorBd(ex.getMessage());
        e.setSentenciaSql(query);
        switch (ex.getErrorCode()) {
            case 1064:
                e.setMensajeUsuario("Error en la consulta SQL. Consulte con el administrador");
                break;
            case 1146:
                e.setMensajeUsuario("La tabla Usuario no existe en la base de datos");
                break;
            case 1403:
                e.setMensajeUsuario("No hay datos para el usuario con ID indicado");
                break;
            default:
                e.setMensajeUsuario("Error al leer el usuario. Consulte con el administrador");
        }
        throw e;
    }
 
    return u;
    }

  /**
 * Método que permite leer los usuarios de la base de datos FitnessGym
 * @return Array de objetos Usuario que contiene los datos de los usuarios leídos
     * @throws pojosfitnessgym.ExcepcionFitnessGym
     * @throws java.sql.SQLException
*/
 public ArrayList<Usuario> leerUsuarios() throws ExcepcionFitnessGym, SQLException {
    conectar();
    ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    String dql = "select * from usuario u join dieta d on u.id_usuario=d.id_usuario join rutina r on u.id_usuario=r.id_usuario";
    Statement sentencia = conexion.createStatement();
    ResultSet resultado = sentencia.executeQuery(dql);

    try {
        while (resultado.next()) {
            Usuario u = new Usuario();
            u.setIdUsuario((Integer) resultado.getInt("ID_USUARIO"));
            u.setNombre(resultado.getString("NOMBRE"));
            u.setApellido1(resultado.getString("APELLIDO1"));
            u.setApellido2(resultado.getString("APELLIDO2"));
            u.setTelefono(resultado.getString("TELEFONO"));
            u.setCorreo(resultado.getString("CORREO"));
            u.setContrasena(resultado.getString("CONTRASENA"));
            u.setEspecialidad(resultado.getString("ESPECIALIDAD"));

            Rutina r = new Rutina();
            r.setIdRutina((Integer) resultado.getInt("ID_RUTINA"));
            r.setNombre(resultado.getString("NOMBRE"));
            r.setDescripcion(resultado.getString("DESCRIPCION"));
            r.setFechaInicio((Date) resultado.getObject("FECHA_INICIO"));
            r.setObjetivo(resultado.getString("OBJETIVO"));
            // No se establece el usuario en la rutina aquí para evitar la recursión
            // r.setUsuario(u);

            Dieta d = new Dieta();
            d.setIdDieta((Integer) resultado.getInt("ID_DIETA"));
            d.setNombre(resultado.getString("NOMBRE"));
            d.setDescripcion(resultado.getString("DESCRIPCION"));
            d.setDuracionDias(resultado.getInt("DURACION_DIAS"));
            d.setFechaInicio((Date) resultado.getObject("FECHA_INICIO"));
            d.setFechaFin((Date) resultado.getObject("FECHA_FIN"));
            // No se establece el usuario en la dieta aquí para evitar la recursión
            // d.setUsuario(u);

            r.setUsuario(u); // Establecer el usuario en la rutina después de crear la dieta y viceversa
            d.setUsuario(u);

            u.setRutina(r);
            u.setDieta(d);

            listaUsuarios.add(u);
        }

        resultado.close();
        sentencia.close();
        conexion.close();

    } catch (SQLException ex) {
        System.out.println("Error SQL: " + ex.getMessage());
    }

    return listaUsuarios;
}



   /**
 * Método que permite insertar una comida de la base de datos FitnessGym
 * @param comida Objeto comida que almacena los datos a insertar en la base de datos
 * @return Integer con la cantidad de registros afectados al insertar la comida
     * @throws pojosfitnessgym.ExcepcionFitnessGym
*/
    public int insertarComida(Comida comida) throws ExcepcionFitnessGym{
        int registrosAfectados = 0;
        conectar();
            String llamada = "call insertar_comida(?,?,?,?)";
       try {
            
            CallableStatement sentenciaLlamable = conexion.prepareCall(llamada);
            
            sentenciaLlamable.setString(1, comida.getNombre());
            sentenciaLlamable.setString(2, comida.getDescripcion());
            sentenciaLlamable.setObject(3, comida.getCalorias(), Types.INTEGER);
            sentenciaLlamable.setObject(4, comida.getUsuario().getIdUsuario(), Types.INTEGER);
            sentenciaLlamable.executeUpdate();
            
            sentenciaLlamable.close();
            conexion.close();
           
        } catch (SQLException ex) {
           ExcepcionFitnessGym e = new ExcepcionFitnessGym();
           e.setCodigoErrorBd(ex.getErrorCode());
           e.setMensajeErrorBd(ex.getMessage());
           e.setSentenciaSql(llamada);
           switch(ex.getErrorCode()){
               case 1407:
                   e.setMensajeUsuario("Todos los campos menos la descripcion y el usuario son obligatorios.");
                   break;
               case 1:
                   e.setMensajeUsuario("El nombre ya existe.");
                   break;
               case 2290:
                   e.setMensajeUsuario("El error se debe a que la descripcion es extensa");
                   break;
               case 2291:
                   e.setMensajeUsuario("La comida o usuario ya existe.");
                   break;
               default:
                   e.setMensajeUsuario("Error general del sistema consulte con el administrador");
           }
           throw e;
        }
       return registrosAfectados;
    }    

    /**
 * Método que permite modificar una comida de la base de datos FitnessGym
 * @param idComida identificador de la comida a modificar
 * @param comida Objeto comida que almacena los datos a modificar en la base de datos
 * @return Integer de número de comidas afectadas que contiene los datos de las comidas afectadas
     * @throws pojosfitnessgym.ExcepcionFitnessGym
     * @throws java.sql.SQLException
*/
    public int modificarComida(Integer idComida, Comida comida) throws ExcepcionFitnessGym, SQLException{
conectar();
        int registrosAfectados = 0;
        String dml = "update COMIDA set NOMBRE=?, DESCRIPCION=?, CALORIAS=?, ID_USUARIO=? where ID_COMIDA=?";
        PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml); 

        try {

            sentenciaPreparada.setString(1, comida.getNombre() );
            sentenciaPreparada.setString(2, comida.getDescripcion() );
            sentenciaPreparada.setObject(3, comida.getCalorias(), Types.INTEGER);
            sentenciaPreparada.setObject(4, comida.getUsuario().getIdUsuario(), Types.INTEGER);
            sentenciaPreparada.setObject(5, idComida, Types.INTEGER);
            

            registrosAfectados = sentenciaPreparada.executeUpdate();

            sentenciaPreparada.close();
            conexion.close();

        } catch (SQLException ex) {
           ExcepcionFitnessGym e = new ExcepcionFitnessGym();
           e.setCodigoErrorBd(ex.getErrorCode());
           e.setMensajeErrorBd(ex.getMessage());
           e.setSentenciaSql(dml);
           switch(ex.getErrorCode()){
               case 1407:
                   e.setMensajeUsuario("Todos los campos menos la descripcion y el usuario son obligatorios.");
                   break;
               case 1:
                   e.setMensajeUsuario("El nombre ya existe.");
                   break;
               case 2290:
                   e.setMensajeUsuario("El error se debe a que la descripcion es extensa");
                   break;
               case 2291:
                   e.setMensajeUsuario("El usuario no existe.");
                   break;
               default:
                   e.setMensajeUsuario("Error general del sistema consulte con el administrador");
           }
           throw e;
        }

        return registrosAfectados;
    }

     /**
 * Método que permite eliminar una comida de la base de datos FitnessGym
 * @param idComida identificador de la Comida a modificar
 * @return Integer con la cantidad de registros afectados al eliminar la comida
     * @throws pojosfitnessgym.ExcepcionFitnessGym
     * @throws java.sql.SQLException
*/
    public int eliminarComida(Integer idComida) throws ExcepcionFitnessGym, SQLException{
       conectar();
    int registrosAfectados = 0; 
    String dml = "DELETE FROM COMIDA WHERE ID_COMIDA = ?";
    PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);
    try {

        sentenciaPreparada.setInt(1, idComida);
 
        registrosAfectados = sentenciaPreparada.executeUpdate();
 
        sentenciaPreparada.close();
        conexion.close();
    
    } catch (SQLException ex) {
        ExcepcionFitnessGym e = new ExcepcionFitnessGym();
        e.setCodigoErrorBd(ex.getErrorCode());
        e.setMensajeErrorBd(ex.getMessage());
        e.setSentenciaSql(dml);
 
        switch (ex.getErrorCode()) {
            case 2292:
                e.setMensajeUsuario("No se puede eliminar la comida porque hay registros asociados a el.");
                break;
            default:
                e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
        }
        throw e;
    }
    return registrosAfectados;
    }

/**
 * Método que permite leer una comida de la base de datos FitnessGym
     * @param idComida
 * @return Objeto Comida que contiene los datos de la comida leída
     * @throws pojosfitnessgym.ExcepcionFitnessGym
*/
    public Comida leerComida(Integer idComida) throws ExcepcionFitnessGym{
        conectar();
        Comida c = new Comida();

        String query = "SELECT * FROM COMIDA WHERE ID_COMIDA = " + idComida;

        try {
            Statement statement = conexion.createStatement();
            ResultSet resultado = statement.executeQuery(query);

            if (resultado.next()) {

                c.setIdComida((Integer) resultado.getInt("ID_COMIDA"));
                c.setNombre(resultado.getString("NOMBRE"));
                c.setDescripcion(resultado.getString("DESCRIPCION"));
                c.setCalorias((Integer) resultado.getInt("CALORIAS"));
            }

            resultado.close();
            statement.close();
            conexion.close();
        } catch (SQLException ex) {
            ExcepcionFitnessGym e = new ExcepcionFitnessGym();
            e.setCodigoErrorBd(ex.getErrorCode());
            e.setMensajeErrorBd(ex.getMessage());
            e.setSentenciaSql(query);
            switch (ex.getErrorCode()) {
                case 1064:
                    e.setMensajeUsuario("Error en la consulta SQL. Consulte con el administrador");
                    break;
                case 1146:
                    e.setMensajeUsuario("La tabla Comida no existe en la base de datos");
                    break;
                case 1403:
                    e.setMensajeUsuario("No hay datos para la Comida con ID indicado");
                    break;
                default:
                    e.setMensajeUsuario("Error al leer la Comida. Consulte con el administrador");
            }
            throw e;
        }

        return c;
    }

/**
 * Método que permite leer las comidas de la base de datos FitnessGym
 * @return Array de objetos Comida que contiene los datos de las comidas leídas
     * @throws pojosfitnessgym.ExcepcionFitnessGym
     * @throws java.sql.SQLException
*/
    public ArrayList<Comida> leerComidas() throws ExcepcionFitnessGym, SQLException{
       conectar();
       ArrayList<Comida> listaComida = new ArrayList();
       String dql = "select * from Usuario u, Comida c where u.ID_USUARIO = c.ID_USUARIO";
       Statement sentencia = conexion.createStatement();
       ResultSet resultado = sentencia.executeQuery(dql);
        try {
            
            while (resultado.next()) {
 
                Usuario u = new Usuario();
                Dieta d = new Dieta();
 
                u.setIdUsuario(((BigDecimal) resultado.getObject("ID_USUARIO")).intValue());
                u.setNombre(resultado.getString("NOMBRE"));
                u.setApellido1(resultado.getString("APELLIDO1"));
                u.setApellido2(resultado.getString("APELLIDO2"));
                u.setTelefono(resultado.getString("TELEFONO"));
                u.setCorreo(resultado.getString("CORREO"));
                u.setContrasena(resultado.getString("CONTRASENA"));
                u.setFechaRegistro((Date)resultado.getObject("FECHA_REGISTRO"));
                u.setEspecialidad(resultado.getString("ESPECIALIDAD"));
 
                Comida c = new Comida();
                c.setIdComida((Integer) resultado.getInt("ID_COMIDA"));
                c.setNombre(resultado.getString("NOMBRE"));
                c.setDescripcion(resultado.getString("DESCRIPCION"));
                c.setCalorias((Integer) resultado.getInt("CALORIAS"));
                c.setUsuario(u);
                listaComida.add(c);
            }
            resultado.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException ex) {
            ExcepcionFitnessGym e = new ExcepcionFitnessGym();
            e.setCodigoErrorBd(ex.getErrorCode());
            e.setMensajeErrorBd(ex.getMessage());
            e.setSentenciaSql(dql);
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        return listaComida;
    }

}