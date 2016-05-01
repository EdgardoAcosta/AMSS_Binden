package Binden.Interfaces;
import Binden.Controles.ControlSolicitud;
import Binden.Entidades.Usuario;
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Binden.Controles.ControlCita;
import Binden.Entidades.Cita;
import java.sql.Connection;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;


//Utilizando urlPatterns en lugar de urlPattern permite agregar mas parametros a la anotacion
@WebServlet(urlPatterns = "/Agenda",
  initParams = {
     @WebInitParam(name = "class", value = "interfaces.InterfazCita")
  }
)
public class InterfazVerCitas extends HttpServlet {

   //Redirige cualquier GET recibido a POST
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      doPost(request,response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      PrintWriter out = response.getWriter();

        HttpSession sesion = request.getSession(false);

        String idUsuario = (String) sesion.getAttribute("cuenta");

        Connection conn = (Connection) getServletContext().getAttribute("DBConnection");

        ControlCita cCita = new ControlCita();

        ArrayList<Cita> listCitas = cCita.obtenerCitas(idUsuario, conn);

      if (sesion == null) { ///El usuario no esta logeado
		     out.println("<font color=red>Favor de proporcionar primero usuario y clave.</font>");
         RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
			   rd.include(request, response); ///include() permite que el mensaje anterior se incluya en la pagina Web
      } else {
         //Mostrar el menu de opciones
         response.setContentType("text/html");
         out.println(
            "<!DOCTYPE html> \n" +
            "<html> \n" +
            "<head> \n" +
            "<meta charset=utf-8> \n" +
            "</head> \n" +
            "<body> \n" +
            "<title>Binden</title> \n" +
            "<h2>Conectate con quien desees!</h2> \n");

            for(Cita cita : listCitas){
              out.println("Usuario 1: " + cita.usuario1 + "\n" +
                          "Usuario 2: " + cita.usuario2 + "\n" +
                          "Lugar: " + cita.descripcion + "\n" +
                          "Fecha: " + cita.fecha + "\n"
                          );
              //Este boton va a agregar la solicitud
              out.println("<input type= 'submit' name='aceptar' value='" + cita.idCita + "'/>\n");
              out.println("<input type= 'submit' name='rechazar' value='" + cita.idCita + "'/>\n");

            }

            String aceptar = request.getParameter("aceptar");
            String rechazar = request.getParameter("rechazar");


            if (aceptar != null) {
              cCita.aceptarCita(idUsuario, aceptar, conn);
            } else if (rechazar != null){
              cCita.cancelarCita(rechazar, conn);
            }


         out.println(
            "<a href=Menu>Regresar</a> </p>" +
            "</body>" +
            "</html>"
         );
      }
   }
}
