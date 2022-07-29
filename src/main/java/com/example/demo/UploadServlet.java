package com.example.demo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "UploadServlet", value = "/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50)
public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
        ArrayList<String> fileName = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from upload;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                fileName.add("image\\" + resultSet.getString("path_file"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("upload.jsp");
        request.setAttribute("filename", fileName);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        for (Part part : request.getParts()) {
            System.out.println(getFileName(part));
            String fileName = getFileName(part);
            String upload = "C:\\Users\\ADMIN\\Desktop\\demo\\src\\main\\webapp\\image\\" + fileName;
            part.write(upload);
            uploadFileInDatabase(fileName, request, response);
        }
    }

    private String getFileName(Part part) {
        String content = part.getHeader("content-disposition");
        String[] contents = content.split(";");
        for (String s : contents) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() -1);
            }
        }
        return "";
    }

    private void uploadFileInDatabase(String pathFile, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into upload(path_file) value (?);");
            preparedStatement.setString(1, pathFile);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        response.sendRedirect("/upload");
    }
}
