package Binden.Interfaces;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import Binden.Interfaces.DBConnectionManager;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
       ServletContext context = servletContextEvent.getServletContext();
       DBConnectionManager connectionManager = null;
        try {
            connectionManager = new DBConnectionManager(
                    "jdbc:mysql://localhost/binden", "root", "admin"
            );
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
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
