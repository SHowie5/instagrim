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
        <% String id = request.getParameter("pictureID"); %>
        <img src="/Instagrim/Thumb/<%=id%>" height="150" width="150">
        <h1>Comments Page</h1>
            <%
                LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                String username = lg.getUsername();
            %>
            <form method="POST" action="/Instagrim/Comments/">
                <input type="text" placeholder="Enter comment here" maxlength="100" name="comment">
                <input type="hidden" value="<%=id%>" name="picid">
                <input type="submit" value="Post Comment">
            </form>
        <nav>
            <ul> 
                <li class="nav"><a href="/Instagrim">Home</a></li>
                <li class="nav"><a href="/Instagrim/Profile/<%=username%>">My Profile</a></li>
                <li class="nav"><a href="/Instagrim/Images/<%=username%>">Your Pics</a></li>
                <li class="nav"><a href="/Instagrim/upload.jsp">Upload</a></li>
                <form action="${pageContext.request.contextPath}/Logout" method="POST">
                <li><input type="submit" value="Logout"</li>
                 </form>
            </ul>
        </nav>
 
        <article>
            <h1><%=username.toUpperCase()%></h1>
        </article>
        <%
            Set<String> comments = (Set) request.getAttribute("Comms");
            if (comments == null) {
        %>
        <p>No comments found</p>
        <%
            } else {
            for (String c : comments) {
            %><p><%=c%></p></br><%
                }
            }   
        %>

    </body>
</html>
