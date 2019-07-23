package main.java.com.m1gl.config;

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

//    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
//
//    }
//
//    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
//
//    }
}
