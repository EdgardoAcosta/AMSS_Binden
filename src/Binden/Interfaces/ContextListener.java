package Binden.Interfaces;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
       ServletContext context = servletContextEvent.getServletContext();
       DBConnectionManager connectionManager = new DBConnectionManager(
               "jdbc:mysql://localhost/binden", "root", "admin"
       );
       context.setAttribute("DBConnection", connectionManager.getConnection());
    }

   @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
   	   Connection conn = (Connection) servletContextEvent.getServletContext().getAttribute("DBConnection");
    	 try {
			   conn.close();
		   } catch (SQLException e) {
		   }
    }
}
