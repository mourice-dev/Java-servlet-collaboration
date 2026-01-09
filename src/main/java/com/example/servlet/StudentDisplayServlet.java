package com.example.servlet;

import com.example.model.Student;
import com.example.util.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/list")
public class StudentDisplayServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Student> students = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {
            
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setEmail(rs.getString("email"));
                student.setCourse(rs.getString("course"));
                students.add(student);
            }
        } catch (SQLException ex) {
            throw new ServletException("Database access error", ex);
        }

        // Simple HTML output
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><title>Student List</title>");
        out.println("<style>table {width: 100%; border-collapse: collapse;} th, td {border: 1px solid #ddd; padding: 8px;} th {background-color: #3498db; color: white;} a {text-decoration: none; padding: 5px 10px; background: #2ecc71; color: white; border-radius: 3px;}</style>");
        out.println("</head><body>");
        out.println("<h2>Registered Students</h2>");
        out.println("<p><a href='register'>Add New Student</a></p>");
        out.println("<table>");
        out.println("<tr><th>ID</th><th>First Name</th><th>Last Name</th><th>Email</th><th>Course</th></tr>");
        
        for (Student s : students) {
            out.println("<tr>");
            out.println("<td>" + s.getId() + "</td>");
            out.println("<td>" + s.getFirstName() + "</td>");
            out.println("<td>" + s.getLastName() + "</td>");
            out.println("<td>" + s.getEmail() + "</td>");
            out.println("<td>" + s.getCourse() + "</td>");
            out.println("</tr>");
        }
        
        out.println("</table>");
        out.println("</body></html>");
    }
}
