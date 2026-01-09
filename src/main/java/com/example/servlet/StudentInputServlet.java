package com.example.servlet;

import com.example.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class StudentInputServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String course = request.getParameter("course");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO students (first_name, last_name, email, course) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, course);
            
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                // Redirect to the list page after successful registration
                response.sendRedirect("list");
            } else {
                response.getWriter().println("Failed to register student.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServletException("Database access error", ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Show the registration form
        response.setContentType("text/html");
        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html><head><title>Register Student</title>");
        response.getWriter().println("<style>body{font-family:sans-serif; padding:20px;} form{max-width:400px; margin:auto;} input{width:100%; margin-bottom:10px; padding:8px;} button{padding:10px; width:100%; background:#3498db; color:white; border:none;}</style>");
        response.getWriter().println("</head><body>");
        response.getWriter().println("<div class='container'><h2>Register New Student</h2>");
        response.getWriter().println("<form action='register' method='post'>");
        response.getWriter().println("First Name: <input type='text' name='firstName' required>");
        response.getWriter().println("Last Name: <input type='text' name='lastName' required>");
        response.getWriter().println("Email: <input type='text' name='email' required>");
        response.getWriter().println("Course: <input type='text' name='course' required>");
        response.getWriter().println("<button type='submit'>Register</button>");
        response.getWriter().println("</form></div></body></html>");
    }
}
