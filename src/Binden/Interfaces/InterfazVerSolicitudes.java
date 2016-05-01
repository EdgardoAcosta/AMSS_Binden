package Binden.Interfaces;
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
import Binden.Controles.ControlSolicitud;
import Binden.Entidades.Solicitud;
import java.sql.Connection;
import java.util.ArrayList;


//Utilizando urlPatterns en lugar de urlPattern permite agregar mas parametros a la anotacion
@WebServlet(urlPatterns = "/Solicitudes",
  initParams = {
     @WebInitParam(name = "class", value = "interfaces.InterfazVerSolicitudes")
  }
)
public class InterfazVerSolicitudes extends HttpServlet {

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

        ControlSolicitud cSolicitud = new ControlSolicitud();

        ArrayList<Solicitud> listSolicitudes = cSolicitud.obtenerSolicitudes(idUsuario, conn);

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
            "<h2>Solicitudes pendientes:</h2> \n");

            for(Solicitud sol : listSolicitudes){
              out.println("Nombre: " + sol.usuario1 + "\n"
                          );
              //Este boton va a agregar la solicitud
              out.println("<input type= 'submit' name='aceptar' value='" + sol.idSolicitud + "'/>\n");
              out.println("<input type= 'submit' name='rechazar' value='" + sol.idSolicitud + "'/>\n");
            }

            String aceptar = request.getParameter("aceptar");
            String rechazar = request.getParameter("rechazar");


            if (aceptar != null) {
              cSolicitud.aceptarSolicitud(idUsuario, sol.usuario2, conn);
            } else if (rechazar != null){
              cSolicitud.borrarSolicitud(idUsuario, sol.usuario2, conn);
            }

         out.println(
            "<a href=Menu>Regresar</a> </p>" +
            "</body>" +
            "</html>"
         );
      }
   }
}
