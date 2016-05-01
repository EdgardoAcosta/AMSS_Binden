package Binden.Entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Solicitud {
  PreparedStatement stmt;
  String idSolicitud, usuario1, usuario2;

  public Solicitud(String sender, String receiver, String id){
    idSolicitud = id;
    usuario1 = sender;
    usuario2 = receiver;
  }
  public Solicitud(){
      
  }

  public void mandarSolicitud(String usuario11,String usuario22, Connection con){
    try {
      String query = "INSERT INTO Solicitud (idUsuarioS1, idUsuarioS2) VALUES (?, ?)";
      stmt = con.prepareStatement(query);
      stmt.setString(1, usuario11);
      stmt.setString(2, usuario22);
      stmt.execute();
    }catch (Exception e) { System.out.println ("No se pudo ejecutar agregar() a la tabla Cliente" + e ); }
/*
    try {
      String query = "SELECT idSolicitud FROM Solicitud WHERE idUsuario1 = ? AND idUsuario2 = ?";
      stmt = con.prepareStatement(query);
      stmt.setString(1, usuario11);
      stmt.setString(2, usuario22);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) { ///Va al primer registro si lo hay
        int idSol = rs.getInt ("idSolicitud");
        rs.close();
        return Integer.toString(idSol);
      }
    }catch (Exception e) { System.out.println ("No se pudo ejecutar agregar() a la tabla Cliente" + e ); }
*/

  }

  public void borrarSolicitud(String idSolicitud, Connection con){
    try {
      String query = "DELETE FROM solicitud WHERE idSolicitud = ?";
      stmt = con.prepareStatement(query);
      stmt.setString(1, idSolicitud);
      stmt.execute();
    }catch (Exception e) { System.out.println ("No se pudo ejecutar agregar() a la tabla Cliente" + e ); }
  }

  public void aceptarSolicitud(String idUsuario1,String idusuario2 , Connection con){
    try {
      String query = "INSERT INTO usuario_conexion (idUsuarioS1, idUsuarioS2) VALUES (?, ?)";
      stmt = con.prepareStatement(query);
      stmt.setString(1, idUsuario1);
      stmt.setString(2, idusuario2);
      stmt.execute();
    }catch (Exception e) { System.out.println ("No se pudo ejecutar agregar() a la tabla Cliente" + e ); }
  }

  public ArrayList<Solicitud> obtenerSolicitudes(String idUsuario2, Connection con){
    ArrayList<Solicitud> lista = new ArrayList<Solicitud>();

    try {
      String query = "SELECT idSolicitud,idUsuario1,idUsuario2 FROM solicitud WHERE idUsuario2 = ? ";
      stmt = con.prepareStatement(query);
      stmt.setString(1, idUsuario2);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {

        lista.add(new Solicitud(rs.getString("idSolicitud"),rs.getString("idUsuario1"),rs.getString("idUsuario2")  ));
      }
      rs.close();
    }
    catch (SQLException e) { }
    return lista;


  }

}
