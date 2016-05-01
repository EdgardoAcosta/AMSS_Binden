package Binden.Controles;
import java.sql.Connection;
import java.util.Calendar;
import Binden.Entidades.Usuario;
import Binden.Entidades.Cita;
import java.util.ArrayList;

public class ControlCita {
   Cita cita;
   Usuario usuario1;
   Usuario usuario2;

   public ControlCita(){

   }

   public ArrayList<Usuario> obtenerUsuarios (String tipo, Connection con){
     return usuario1.obtenerUsuarios(tipo, con);
   }

   public ArrayList<Usuario> obtenerAmigos(String idUsuario1, Connection con){
     return usuario1.obtenerAmigos(idUsuario1, con);
   }

   public ArrayList<Cita> obtenerCitas(String idUsuario1, Connection con){
     return cita.obtenerCitas(idUsuario1, con);
   }

   public void agendarCita(String receiver, String sender, String fecha,
        String descripcion, Connection con){
     cita.agregarCita(sender, receiver, fecha, descripcion, con);
   }

   public void aceptarCita(String receiver, String cit, Connection con){
     cita.aceptarCita(receiver, cit, con);
   }

   public void cancelarCita(String cit, Connection con){
     cita.cancelarCita(cit, con);
   }

}
