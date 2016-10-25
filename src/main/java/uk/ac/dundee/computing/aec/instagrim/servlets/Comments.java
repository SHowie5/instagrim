/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

/**
 *
 * @author Scott
 */
@WebServlet(name = "Comments", urlPatterns = {
    "/Comments",
    "/Comments/*",
})
public class Comments extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Cluster cluster;
    private HashMap CommandsMap = new HashMap();
    
    public Comments() {
        super();
        // TODO Auto-generated constructor stub
        CommandsMap.put("Comments", 1);
    }
    
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String args[] = Convertors.SplitRequestPath(request);
        int command;
        try {
            command = (Integer) CommandsMap.get(args[1]);
        } catch (Exception et) {
            error("Bad Operator", response);
            return;
        }
        switch (command) {
            case 1:
                DisplayComments(args[2], request, response);
                break;
            default:
                error("Bad Operator", response);      
        }
    }
    
    private void DisplayComments (String uuid, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
        Set<String> comments = tm.getPicComments(uuid);
        request.setAttribute("Comms", comments);
        RequestDispatcher rd = request.getRequestDispatcher("/comments.jsp");
        rd.forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String comment = request.getParameter("comment");
        String picid = request.getParameter("picid");
        LoggedIn lg = (LoggedIn) request.getSession().getAttribute("LoggedIn");
        String user = lg.getUsername();
        PicModel pm = new PicModel();
        pm.setCluster(CassandraHosts.getCluster());
        pm.setComments(picid, comment);
        
        //RequestDispatcher rd = request.getRequestDispatcher("/comments.jsp");
        //rd.forward(request, response);
        response.sendRedirect("/Instagrim/Images/"+user);
    }
    
    
    


    @Override
    public String getServletInfo() {
        return "Short description";
    }
  
    private void error(String mess, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = null;
        out = new PrintWriter(response.getOutputStream());
        out.println("<h1>You have a na error in your input</h1>");
        out.println("<h2>" + mess + "</h2>");
        out.close();
        return;
    }
    
}
