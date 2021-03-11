/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dal.CourseDAO;
import gui.AddCourse;
import gui.CourseManagement;
import gui.ListCourse;
import gui.SearchCourse;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.Course;

/**
 *
 * @author Minh Thanh
 */
public class CourseController {

    private CourseManagement manage; // JFrame
    private AddCourse add; // Jdialog
    private ListCourse list; // Jdialog
    private SearchCourse search; // Jdialog
    private CourseDAO dao;

    public CourseController() throws Exception {

        dao = new CourseDAO();
        manage = new CourseManagement();
        manage.setVisible(true);
        manage.setLocationRelativeTo(null);

        manage.getBtnDisplay().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                list = new ListCourse(manage, true);
                list.setModal(true);

                //display all Course
                List<Course> listC = dao.getAllCourses();
                String c = "";

                for (Course course : listC) {
                    c += course;
                }
                list.getTxtList().setText(c);
//                list.getTxtList().setFont(new Font(c, 0, 15));
                list.setVisible(true);
            }
        });

        manage.getBtnSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                search = new SearchCourse(manage, true);
                search.setModal(true);

                search.setVisible(true);

                search.getBtnSearch().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        String code = search.getTxtcode().getText();
                        Course c = dao.getCourseByCode(code);
                        search.getTxtName().setText(c.getName());
                        search.getTxtCredit().setText("" + c.getCredit());
                    }
                });

            }
        });

    }

}
