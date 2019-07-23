package main.java.com.immovable.config;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "InitializeDatabaseServlet", urlPatterns = "/", loadOnStartup = 1)
public class InitializeDatabaseServlet extends javax.servlet.http.HttpServlet {

    @Override
    public void init() throws ServletException {
        HibernateConfiguration configuration = new HibernateConfiguration();
        try {
            configuration.initDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
