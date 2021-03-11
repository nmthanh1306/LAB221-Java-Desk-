/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;

/**
 *
 * @author Minh Thanh
 */
public class CourseDAO {

    private PreparedStatement st;
    private ResultSet rs;
    private final DBContext context = new DBContext();
    private final Connection con;

    public CourseDAO() throws Exception {
        this.con = context.getConnection();
    }

    public List<Course> getAllCourses() {

        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM Course";
        try {
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                String code = rs.getString(1);
                String s = rs.getString(2);
                String name = normalizeNameCourse(s);
                int credit = rs.getInt(3);
                Course c = new Course(code, name, credit);
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return list;
    }

    public Course getCourseByCode(String code) {

        Course course = new Course();
        String sql = "SELECT * FROM Course WHERE code = ?";
        try {
            st = con.prepareStatement(sql);
            st.setString(1, code);
            rs = st.executeQuery();

            if (rs.next()) {             
                course.setCode(rs.getString(1));
                
                String s = rs.getString(2);
                String name = normalizeNameCourse(s);
                
                course.setName(name);
                course.setCredit(rs.getInt(3));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return course;
    }

    public boolean addNewCourse(Course c) throws Exception {

        String sql = "INSERT INTO Course VALUES(?,?,?)";
        if (isExistCode(c.getCode())) {
            return false;
        }
        try {

            st = con.prepareStatement(sql);
            st.setString(1, c.getCode());
            st.setString(2, c.getName());
            st.setInt(3, c.getCredit());
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return true;
    }

    public boolean isExistCode(String code) throws Exception {

        CourseDAO dao = new CourseDAO();
        List<Course> list = dao.getAllCourses();

        for (Course course : list) {
            if (code.equalsIgnoreCase(course.getCode())) {
                return true;
            }
        }

        return false;
    }

    public static String normalizeNameCourse(String str) {

        String name = str.replaceAll("\\s{2,}", " "); // replace all 2 or more space to 1
        String[] arr = name.split(" ");
        String result = "";

        for (String x : arr) {
            result = result + (x.substring(0, 1).toUpperCase() + x.substring(1).toLowerCase());
            result = result + " ";
        }
        return result;
    }

    public static void main(String[] args) {
//        try {
//            CourseDAO dao = new CourseDAO();
//            List<Course> listC = dao.getAllCourses();
//            String c = "";
//            for (Course course : listC) {
//                c += course;
//            }
//            System.out.println(c);
//        } catch (Exception ex) {
//            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
        String a = "asdasd  Lol    BBB    WASD  23  ER#";
        String b = normalizeNameCourse(a);
        System.out.println(b);
    }
}
