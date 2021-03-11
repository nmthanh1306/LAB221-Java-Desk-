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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
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

    public static String toUpperCase(String str) {

        String name = str.replaceAll("\\s{2,}", " "); // replace all 2 or more space to 1
        String[] arr = name.split(" ");
        String result = "";

        for (String x : arr) {
            result = result + (x.substring(0, 1).toUpperCase() + x.substring(1).toLowerCase());
            result = result + " ";
        }
        return result;
    }

    public CourseController() throws Exception {

        dao = new CourseDAO();
        manage = new CourseManagement();
        manage.setVisible(true);
        manage.setLocationRelativeTo(null);

        // display all list 
        manage.getBtnDisplay().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                list = new ListCourse(manage, true);

                //display all Course
                List<Course> listC = dao.getAllCourses();
                String c = "";

                for (Course course : listC) {
                    c += course;
                }
                list.getTxtList().setText(c);
//                list.getTxtList().setFont(new Font(c, 0, 15));
                list.setModal(true);
                list.setVisible(true);
            }
        });

        // search course by code
        manage.getBtnSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                search = new SearchCourse(manage, true);
                search.getTxtName().setEditable(false);
                search.getTxtCredit().setEditable(false);

                search.getBtnSearch().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String code = search.getTxtcode().getText();
                        Course c = dao.getCourseByCode(code);
                        search.getTxtName().setText(c.getName());
                        search.getTxtCredit().setText("" + c.getCredit());

                    }
                });
                search.setModal(true);
                search.setVisible(true);
            }
        });

        // add new course
        manage.getBtnAdd().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                add = new AddCourse(manage, true);

                // button add
                add.getBtnAdd().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String code = add.getTxtCode().getText();
                        String name = add.getTxtName().getText();
                        String sCredit = add.getTxtCredit().getText();
                        int iCredit = 0;

                        if (code.isEmpty() || name.isEmpty() || sCredit.isEmpty()) {
                            JOptionPane.showMessageDialog(add, "Please fill all field");
                            return;
                        }

                        try {
                            iCredit = Integer.parseInt(sCredit);
                            if (iCredit <= 0 || iCredit > 33) {
                                throw new NumberFormatException();
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(add, "Credit must be number and less than 34");
                            return;
                        }

                        Course c = new Course(code, name, iCredit);
                        try {
                            if (!dao.addNewCourse(c)) {
                                JOptionPane.showMessageDialog(add, "This code is existed in database, please re-enter!");
                            } else {
                                JOptionPane.showMessageDialog(add, "Add a new course successfully!");
                            }
                        } catch (Exception ex) {

                        }

                    }
                });

                //  button clear field
                add.getBtnClear().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        add.getTxtCode().setText("");
                        add.getTxtName().setText("");
                        add.getTxtCredit().setText("");
                    }
                });

                add.setModal(true);
                add.setVisible(true);
            }
        });

        // exit app
        manage.getBtnExit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

}
