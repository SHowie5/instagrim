/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;
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
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

/**
 *
 * @author Scott
 */
@WebServlet(name = "ProfilePic", urlPatterns = {
    "/ProfilePic",
    "/ProfilePic/*",
})
public class ProfilePic extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Cluster cluster;
    private HashMap CommandsMap = new HashMap();
    
    public ProfilePic() {
        super();
        // TODO Auto-generated constructor stub
        CommandsMap.put("ProfilePic", 1);
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
                DisplayProfilePic(args[2], request, response);
                break;
            default:
                error("Bad Operator", response);
        }
    }
    
    private void DisplayProfilePic(String User, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        PicModel pm = new PicModel();
        pm.setCluster(cluster);
        String profilePic = pm.getProfilePic(User);
        User user = new User();
        RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");
        request.setAttribute("PPID", profilePic);
        rd.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String picid = request.getParameter("picid");
	LoggedIn loggedIn = (LoggedIn) request.getSession().getAttribute("LoggedIn");
        Pic pic = new Pic();
		
        PicModel pm = new PicModel();
        pm.setCluster(CassandraHosts.getCluster());
        pic.setProfileID(picid);
        //loggedIn.setProfileID(UUID.fromString(picid));
        pm.setProfilePic(loggedIn.getUsername(), picid);
        //RequestDispatcher rd=request.getRequestDispatcher("/UsersPics.jsp");
        // rd.forward(request,response);
        response.sendRedirect("/Instagrim/Images/"+ loggedIn.getUsername());
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
