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
                Course c = new Course(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3));
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
                course.setName(rs.getString(2));
                course.setCredit(rs.getInt(3));
            }
            return null;
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return course;
    }

    public static void main(String[] args){
        try {
            CourseDAO dao = new CourseDAO();
            List<Course> listC = dao.getAllCourses();
            String c = "";
            for (Course course : listC) {
                c += course;
            }
            System.out.println(c);
        } catch (Exception ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
