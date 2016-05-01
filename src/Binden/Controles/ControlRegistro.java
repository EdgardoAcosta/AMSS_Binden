package Binden.Controles;
import java.sql.Connection;
import Binden.Entidades.Usuario;

public class ControlRegistro {
   Usuario usuario;

   public ControlRegistro(){
     usuario = new Usuario();
   }

   public void agregarUsuario (String correo, String paswd, String tipoUsuario, String nombre,String ubicacion, String descripcion, Connection con){
      usuario.agregar(correo, paswd, tipoUsuario, nombre,ubicacion ,descripcion, con);
   }

   ///Valida al cliente en la base de datos
   public int validarUsuario(String correo, Connection con){
      int ncuenta = usuario.validarCorreo(correo, con);
      return( ncuenta );
   }

}
