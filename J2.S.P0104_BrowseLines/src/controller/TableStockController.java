/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import view.TableOfStock;

/**
 *
 * @author Minh Thanh
 */
public class TableStockController {

    private TableOfStock t;
    private Vector vctHeader = new Vector();
    private Vector vctData = new Vector();
    private int selectedRow;

    public TableStockController() {

        t = new TableOfStock();
        t.setVisible(true);
        t.setLocationRelativeTo(null);
        addDataToTable();

        t.getTblStock().setRowSelectionInterval(0, 0);
        enableBtn();

        // move first
        t.getBtnMoveFirst().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                t.getTblStock().setRowSelectionInterval(0, 0);
                enableBtn();

            }
        });

        // move last
        t.getBtnMoveLast().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                t.getTblStock().setRowSelectionInterval(vctData.size() - 1, vctData.size() - 1);
                enableBtn();

            }
        });

        //move next
        t.getBtnMoveNext().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//                selectedRow = t.getTblStock().getSelectedRow();
//                if (selectedRow == vctData.size() - 1) {
//                    t.getTblStock().setRowSelectionInterval(0, 0);
//                } else {
//                    t.getTblStock().setRowSelectionInterval(selectedRow + 1, selectedRow + 1);
//                }
                t.getTblStock().setRowSelectionInterval(selectedRow + 1, selectedRow + 1);
                enableBtn();
            }
        });

        //move pre
        t.getBtnMovePrevious().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                t.getTblStock().setRowSelectionInterval(selectedRow - 1, selectedRow - 1);
                enableBtn();
            }
        });

    }

    public void enableBtn() {
        // mouse event
        t.getTblStock().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRow = t.getTblStock().getSelectedRow();
                if (selectedRow == 0) { // first row
                    t.getBtnMoveFirst().setEnabled(false);
                    t.getBtnMovePrevious().setEnabled(false);
                } else {
                    t.getBtnMoveFirst().setEnabled(true);
                    t.getBtnMovePrevious().setEnabled(true);
                }

                if (selectedRow == vctData.size() - 1) { // last row
                    t.getBtnMoveNext().setEnabled(false);
                    t.getBtnMoveLast().setEnabled(false);
                } else {
                    t.getBtnMoveNext().setEnabled(true);
                    t.getBtnMoveLast().setEnabled(true);
                }
            }
        });
        //
        selectedRow = t.getTblStock().getSelectedRow();
        if (selectedRow == 0) { // first row
            t.getBtnMoveFirst().setEnabled(false);
            t.getBtnMovePrevious().setEnabled(false);
        }
    }

    public void addDataToTable() {

        // create name header of each coloum
        vctHeader.add("StockID");
        vctHeader.add("StockName");
        vctHeader.add("Address");
        vctHeader.add("DateAvailable");
        vctHeader.add("Note");

        // create rows of data
        Vector vctRow1 = new Vector();
        vctRow1.add("1");
        vctRow1.add("Stock one");
        vctRow1.add("No1 - Washington street");
        vctRow1.add("11/05/2010");
        vctRow1.add("");
        vctData.add(vctRow1);

        Vector vctRow2 = new Vector();
        vctRow2.add("2");
        vctRow2.add("Stock two");
        vctRow2.add("372 Cave town - 001 Banks");
        vctRow2.add("09/07/2011");
        vctRow2.add("");
        vctData.add(vctRow2);

        Vector vctRow3 = new Vector();
        vctRow3.add("3");
        vctRow3.add("Stock three");
        vctRow3.add("Nary angle -890 Number one");
        vctRow3.add("13/05/2010");
        vctRow3.add("");
        vctData.add(vctRow3);

        Vector vctRow4 = new Vector();
        vctRow4.add("4");
        vctRow4.add("Stock four");
        vctRow4.add("Twin tower - 01 Main street");
        vctRow4.add("04/07/2015");
        vctRow4.add("");
        vctData.add(vctRow4);

        Vector vctRow5 = new Vector();
        vctRow5.add("5");
        vctRow5.add("Stock five");
        vctRow5.add("Victory annivesary district");
        vctRow5.add("08/12/2014");
        vctRow5.add("");
        vctData.add(vctRow5);

        t.getTblStock().setModel(new DefaultTableModel(vctData, vctHeader));

    }
}
