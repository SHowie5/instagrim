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
            <%
                        
                LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                String username = lg.getUsername();
            %>
        <nav>
            <ul>
                <li class="nav"><a href="/Instagrim">Home</a></li>
                <li class="nav"><a href="/Instagrim/Profile/<%=username%>">My Profile</a></li>
                <li class="nav"><a href="/Instagrim/upload.jsp">Upload</a></li>
                <form action="${pageContext.request.contextPath}/Logout" method="POST">
                <li><input type="submit" value="Logout"</li>
                </form>
            </ul>
        </nav>
 
        <article>
            <h1>Your Pics</h1>
        <%
            java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
            if (lsPics == null) {
        %>
        <p>No Pictures found</p>
        <%
        } else {
            Iterator<Pic> iterator;
            iterator = lsPics.iterator();
            while (iterator.hasNext()) {
                Pic p = (Pic) iterator.next();
        %>
        <form action="/Instagrim/Comments/<%=p.getSUUID()%>">
           <input type="hidden" value="<%=p.getSUUID()%>" name="pictureID">
           <input type="image" src="/Instagrim/Thumb/<%=p.getSUUID()%>" alt="Submit">
        </form>
        <form method="POST" action="/Instagrim/ProfilePic/<%=lg.getUsername()%>">
            <input type="submit" value="Set as Profile Picture">
            <input type="hidden" value="<%=p.getSUUID()%>" name="picid">
        </form>
        <%
            }
            }
        %>
        </article>
    </body>
</html>
