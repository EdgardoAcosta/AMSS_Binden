package Binden.Controles;
import java.sql.Connection;
import Binden.Entidades.Usuario;

public class ControlLogin {
   Usuario usuario;

   public ControlLogin(){
     usuario = new Usuario();
   }

   //agregar va en control registro
  //  public void agregarUsuario (String usuarioo, String paswd, int cuenta, String nombre, Connection con){
  //     usuario.agregar(usuarioo, paswd, cuenta, nombre, con);
  //  }

   ///Valida al cliente en la base de datos
   public int validarUsuario(String usuarioo, String password, Connection con){
      int ncuenta = usuario.validar(usuarioo, password, con);
      return( ncuenta );
   }

}
