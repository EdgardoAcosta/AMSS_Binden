package Binden.Interfaces;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
/*
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
*/
import javax.servlet.http.*;

import Binden.Controles.ControlLogin;

@WebServlet(urlPatterns = "/Login",
  initParams = {
     @WebInitParam(name = "class", value = "interfaces.LoginServlet")
  }
)
public class LoginServlet extends HttpServlet {

   ///Redirige cualquier GET recibido a POST
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
     doPost(request,response);
   }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException {

		String user = request.getParameter("correo");
		String pwd = request.getParameter("contra");

    ///La conexion se establecio en ContextListener
    Connection conn = (Connection) getServletContext().getAttribute("DBConnection");

		ControlLogin cLogin = new ControlLogin();
		int ncuenta = cLogin.validarUsuario(user, pwd, conn);

		if( ncuenta == 0 ) { ///El usuario o clave son incorrectos
			PrintWriter out = response.getWriter();
		  out.println("<h3><font color=red>El usuario o la clave son incorrectos.</font></h3>");
      RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
			rd.include(request, response); ///include() permite que el mensaje anterior se incluya en la pagina Web
		}else{
		  ///Crea una sesion que expirara en 30 minutos
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(30*60);

			String sCuenta = Integer.toString(ncuenta);
			session.setAttribute("cuenta", sCuenta);
			session.setAttribute("usuario", user);
			session.setAttribute("password", pwd);
      RequestDispatcher rd=request.getRequestDispatcher("Menu");
      rd.forward(request, response);
		}

	}

}
