<%-- 
    Document   : UsersPics
    Created on : Sep 24, 2014, 2:52:48 PM
    Author     : Administrator
--%>

<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="/Instagrim/Styles.css" />
    </head>
    <body>
        <header>
        
        <h1>InstaGrim ! </h1>
        <h2>Your world in Black and White</h2>
        </header>
        <h1>My Profile Page</h1>
            <%         
                LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                String username = lg.getUsername();
                String ppid = (String) request.getAttribute("PPID");
                if (ppid == null) {
             %>
                 <p>No Profile Pic</p>
             <%} else { 
             %>
                <img class="profilepic" src="/Instagrim/Thumb/<%=ppid%>">
             <%    
                }
             %>
        <nav>
            <ul> 
                <li class="nav"><a href="/Instagrim">Home</a></li>
                <li class="nav"><a href="/Instagrim/Images/<%=username%>">Your Pics</a></li>
                <li class="nav"><a href="/Instagrim/upload.jsp">Upload</a></li>
                <form action="${pageContext.request.contextPath}/Logout" method="POST">
                    <li><input type="submit" value="Logout"</li>
                </form>
            </ul>
        </nav>
 
        <article>
            <h1><%=username.toUpperCase()%></h1>
            <%
                String fname = (String) request.getAttribute("fname");
                String lname = (String) request.getAttribute("lname");
                Set<String> email = (Set) request.getAttribute("emails");
            %>
            <p>
                First Name: <%=fname.toUpperCase()%></br>
                Last Name: <%=lname.toUpperCase()%></br>
                <% 
                for (String e : email) {
              %>Email: <%=e%></br><%
                }
                %>
            </p>
        </article>
        <footer>
            <ul>
                
            </ul>
        </footer>
    </body>
</html>