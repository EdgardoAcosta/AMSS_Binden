package Binden.Controles;
import Binden.Entidades.Usuario;
import Binden.Entidades.Solicitud;
import java.sql.Connection;
import java.util.ArrayList;

public class ControlSolicitud {
  Usuario usuario1, usuario2;
  Solicitud solicitud;

   public ControlSolicitud(){
      usuario1 = new Usuario();
      usuario2 = new Usuario();
      solicitud = new Solicitud();
   }

   public ArrayList<Usuario> obtenerUsuarios(String tipo, Connection con){
     return (usuario1.obtenerUsuarios(tipo, con));
   }

   public ArrayList<Solicitud> obtenerSolicitudes(String idUsuario, Connection con){
      return (solicitud.obtenerSolicitudes(idUsuario, con));
   }

   public void crearSolicitud(String sender, String receiver, Connection con){
      solicitud.mandarSolicitud(sender, receiver, con);
   }

   public void aceptarSolicitud(String usuario1,String usuario2, Connection con){
     solicitud.aceptarSolicitud(usuario1,usuario2, con);
   }

   public void ignorarSolicitud(String solic, Connection con){
     solicitud.borrarSolicitud(solic, con);
   }

   public String obtenerTipoUsuario(String usuario, Connection con){
     return usuario1.obtenerTipoUsuario(usuario, con);
   }

}
