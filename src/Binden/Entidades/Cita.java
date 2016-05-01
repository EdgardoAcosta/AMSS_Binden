package Binden.Entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Cita {
   PreparedStatement stmt;
   String idCita, usuario1, usuario2, descripcion , fecha;
   SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

   public Cita(String user1 ,String user2,String id,String date,String desc){
     usuario1 = user1;
     usuario2 = user2;
     idCita = id;
     fecha = date;
     descripcion = desc;
   }

   private static java.sql.Date getCurrentDate() {
    java.util.Date today = new java.util.Date();
    return new java.sql.Date(today.getTime());
    }

    public Cita() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   public void agregarCita(String usuario1,String usuario2,String fecha,String descripcion, Connection con){
      try {
         String query = "INSERT INTO cita (idUsuario1, idUsuario2, fecha, descripcion, false) VALUES (?, ?, ?, ?, ?)";
         stmt = con.prepareStatement(query);
         stmt.setString(1, usuario1);
         stmt.setString(2, usuario2);
         stmt.setDate(3, getCurrentDate());
         stmt.setString(4, descripcion);
         stmt.setBoolean(5, false);

         stmt.execute();
      }catch (Exception e) { System.out.println ("No se pudo ejecutar agregarCita() a la tabla Cita" + e ); }
   }

   public ArrayList<Cita> obtenerCitas(String usuario, Connection con){
     ArrayList<Cita> lista = new ArrayList<Cita>();

     try {
        String query = "SELECT (SELECT u.nombre FROM usuario u WHERE u.idUsuario =c.idUsuario2 ) as nombre,fecha,descripcion,idCita FROM cita c WHERE idUsuario1 = ? ";
        stmt = con.prepareStatement(query);
        stmt.setString(1, usuario);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {

           lista.add(new Cita(rs.getString("idUsuario1"),rs.getString("nombre"),rs.getString("idCita"),rs.getString("fecha"),rs.getString("descripcion") )   );
        }
         rs.close();


     } catch (SQLException e) {}
       return( lista );

   }

   public void aceptarCita(String receiver, String cit, Connection con){
     try {
        //String query = "UPDATE cita c SET (aceptada) VALUES (?) WHERE (c.idUsuario2, c.idCita) VALUES (?, ?)";
        String query = "UPDATE cita c SET aceptada = ? WHERE c.idUsuario2 = ? AND  c.idCita = ?";
        stmt = con.prepareStatement(query);
        stmt.setBoolean(1, true);
        stmt.setString(2, cit);

        stmt.execute();
     }catch (Exception e) { System.out.println ("No se pudo ejecutar aceptarCita() a la tabla Cliente" + e ); }
   }

   public void cancelarCita(String cit, Connection con){
     try {
       String query = "DELETE FROM cita WHERE idCita = ?";
       stmt = con.prepareStatement(query);
       stmt.setString(1, cit);
       stmt.execute();
     }catch (Exception e) { System.out.println ("No se pudo ejecutar cancelarCita() a la tabla Cliente" + e ); }
   }
}
