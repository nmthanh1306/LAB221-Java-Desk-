/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import javax.swing.JButton;
import view.GUI;

/**
 *
 * @author Minh Thanh
 */
public class Controller {

    private GUI frm = new GUI();
    private BigDecimal firstNum;
    private BigDecimal secondNum;
    private int operator = -1;
    private boolean OperatorProcess = false;  // user can input new number 
    // khi ma nhap vao 1 so sau do nhap toan tu + - * / thi process  = true de khi 
    // bam pressNumber()--> screen caclulate setText =0 --> to secondNumber getValue() on screen at now
    // khong bi chen so
    private boolean resetNumber = false; // lien quan den memory--> khi bam cac button MC MR M+ M-
    // press one into all button memory --> remove number da saved into momory
    private BigDecimal memory = new BigDecimal("0");

    public Controller() {
        frm.setResizable(false);
        frm.setLocationRelativeTo(null); //put GUI to the middle of the screen
        frm.setVisible(true);
        // call functions create event for every button.

        frm.getBtn0().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setColor(frm.getBtn0());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetColor(frm.getBtn0());
            }

        });
        frm.getBtn0().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pressNumber(0);
            }
        });
        frm.getBtn1().addActionListener((ActionEvent e) -> {
            pressNumber(1);
        });
        frm.getBtn2().addActionListener((ActionEvent e) -> {
            pressNumber(2);
        });
        frm.getBtn3().addActionListener((ActionEvent e) -> {
            pressNumber(3);
        });
        frm.getBtn4().addActionListener((ActionEvent e) -> {
            pressNumber(4);
        });
        frm.getBtn5().addActionListener((ActionEvent e) -> {
            pressNumber(5);
        });
        frm.getBtn6().addActionListener((ActionEvent e) -> {
            pressNumber(6);
        });
        frm.getBtn7().addActionListener((ActionEvent e) -> {
            pressNumber(7);
        });
        frm.getBtn8().addActionListener((ActionEvent e) -> {
            pressNumber(8);
        });
        frm.getBtn9().addActionListener((ActionEvent e) -> {
            pressNumber(9);
        });
        frm.getBtnDot().addActionListener((ActionEvent e) -> {
            pressDot();
        });
        frm.getBtnAdd().addActionListener((ActionEvent e) -> {
            calculate();
            setOperator(1);
        });
        frm.getBtnSub().addActionListener((ActionEvent e) -> {
            calculate();
            setOperator(2);
        });
        frm.getBtnMul().addActionListener((ActionEvent e) -> {
            calculate();
            setOperator(3);
        });
        frm.getBtnDiv().addActionListener((ActionEvent e) -> {
            calculate();
            setOperator(4);
        });
        frm.getBtnEqua().addActionListener((ActionEvent e) -> {
            pressResult();
        });
        frm.getBtnPercent().addActionListener((ActionEvent e) -> {
            pressPercent();
        });
        frm.getBtnSwap().addActionListener((ActionEvent e) -> {
            pressNegate();
        });
        frm.getBtnFlip().addActionListener((ActionEvent e) -> {
            pressInvert();
        });
        frm.getBtnSR().addActionListener((ActionEvent e) -> {
            pressSqrt();
        });
        frm.getBtnMAdd().addActionListener((ActionEvent e) -> {
            pressMAdd();
            frm.getBtnMr().setBackground(Color.red);
            frm.getBtnMr().setEnabled(true);
        });
        frm.getBtnMSub().addActionListener((ActionEvent e) -> {
            pressMSub();
            frm.getBtnMr().setBackground(Color.red);
            frm.getBtnMr().setEnabled(true);
        });
        frm.getBtnMr().addActionListener((ActionEvent e) -> {
            pressMR();
            frm.getBtnMr().setBackground(Color.red);
            frm.getBtnMr().setEnabled(true);
        });
        frm.getBtnMc().addActionListener((ActionEvent e) -> {
            pressMC();
            frm.getBtnMr().setBackground(new Color(255, 233, 148));
            frm.getBtnMr().setEnabled(false);
        });
        frm.getLblClear().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Clear();
            }
        });
        // M+ add to value stored in memory.
        // M- subtract from the value stored in memory.
        // MR display the value in memory.
        // MC clear the value in memory.
        // Create event for button M+, M-, MR, and MC.

    }

    public String getTxtResult() {
        return frm.getTxtResult().getText();
    }

    public void setTxtResult(String text) {
        frm.getTxtResult().setText(text);
    }

    // get value from text on screen.
    public BigDecimal getValue() {
        String value = getTxtResult();
        BigDecimal temp = new BigDecimal(value);
        return temp;
    }

    // press lbl All Clear.
    public void Clear() {
        setTxtResult("0");
        firstNum = new BigDecimal("0");
        secondNum = new BigDecimal("0");
        memory = new BigDecimal("0");
        operator = -1;
        OperatorProcess = false;
        resetNumber = false;
        frm.getBtnMr().setBackground(new Color(255, 233, 148));
        frm.getBtnMr().setEnabled(false);
    }

    // Create event for every button numer.
    // press button numbers.
    public void pressNumber(int number) {
        if (OperatorProcess == true || resetNumber == true) { // it's mean user has been pressed one of the 4 operators.
            setTxtResult(""); // to press a new number.
            OperatorProcess = false; // to do calculate().   
            resetNumber = false; // user can press a new number.
        }
        BigDecimal temp = new BigDecimal(getTxtResult() + number);
        setTxtResult(temp + "");
    }

    // press button operator.
    // set operators ("+ = 1", "- = 2", "* = 3", "/ = 4").
    public void setOperator(int operator) {
        this.operator = operator;
    }

    // calculate (+, -, *, /).
    public void calculate() {
        if (getTxtResult().equals("ERROR")) {
//            setTxtResult("0");
            firstNum = new BigDecimal("0");
            secondNum = new BigDecimal("0");
        }

        try {
            if (OperatorProcess == false) {
                if (operator == -1) { // operator is not press -> get first number
                    firstNum = getValue();
                } else { // operator has been pressed.
                    secondNum = getValue();
                    if (operator == 4 && secondNum.doubleValue() == 0) {
                        throw new Exception(); // error a number divide to 0.
                    } else {
                        MathContext mc = new MathContext(17);
                        switch (operator) {
                            case 1: // +     

                                firstNum = firstNum.add(secondNum, mc);
                                break;
                            case 2: // -
                                firstNum = firstNum.subtract(secondNum, mc);
                                break;
                            case 3: // *
                                firstNum = firstNum.multiply(secondNum, mc);
                                break;
                            case 4: // /          
                                firstNum = firstNum.divide(secondNum, mc); // get 17 number after dot.
                        }
                    }
                }
                // will set the result to screen when we click second operator or result button
                BigDecimal temp = firstNum;
                temp = temp.round(new MathContext(16, RoundingMode.HALF_UP));
                setTxtResult(temp.stripTrailingZeros().toPlainString() + "");
                OperatorProcess = true; // to press a new number
            }
        } catch (Exception e) {
            OperatorProcess = true; // to press a new number.
            setTxtResult("ERROR");
        }
    }

    // press button result.
    public void pressResult() {
        if (!getTxtResult().equals("ERROR")) {
            calculate(); // calculate() will call again.
            operator = -1; // resetNumber operator.
        } else {
            setTxtResult("0");
            firstNum = new BigDecimal("0");
            secondNum = new BigDecimal("0");
        }
    }

    // press button %
    public void pressPercent() {
        if (getTxtResult().equals("ERROR")) {
            Clear();
        }

        String value = getTxtResult();
        BigDecimal xValue = new BigDecimal(value);
        BigDecimal percent = new BigDecimal(100);
        try {
            setTxtResult(xValue.divide(percent, MathContext.DECIMAL64).stripTrailingZeros().toPlainString());

//            setTxtResult(getValue().doubleValue() / 100 + "");
//            pressResult();
        } catch (Exception e) {
            setTxtResult("ERROR");
        }
        OperatorProcess = false;
        resetNumber = true; // to press a new number.
    }

    // press button +/-
    public void pressNegate() {
        if (getTxtResult().equals("ERROR")) {
            Clear();
        }
        try {
            setTxtResult(getValue().negate() + "");
//            pressResult();
        } catch (Exception e) {
            setTxtResult("ERROR");
        }
        OperatorProcess = false;
        resetNumber = true; // to press a new number.
    }

    // press button 1/x
    public void pressInvert() {
        if (getTxtResult().equals("ERROR")) {
            Clear();
        }
        try {

            double result = getValue().doubleValue();
//            String xValue = getTxtResult();
            if (!getTxtResult().equals("0")) {
//                BigDecimal numberInvert = new BigDecimal(getTxtResult());
//                BigDecimal numberOne = new BigDecimal("1");
//                BigDecimal ans = numberOne.divide(numberInvert, new MathContext(17, RoundingMode.HALF_EVEN));
//                setTxtResult(ans.stripTrailingZeros().toPlainString());
                setTxtResult((1 / result) + "");
                if (getTxtResult().endsWith(".0")) {
                    String replace = getTxtResult().replace(".0", "");
                    setTxtResult(replace);
                }
            } else {
                setTxtResult("ERROR");
            }
//            pressResult();
        } catch (Exception e) {
            setTxtResult("ERROR");
        }
        OperatorProcess = false;
        resetNumber = true; // to press a new number
    }

    public void pressSqrt() {
        if (getTxtResult().equals("ERROR")) {
            Clear();
        }
        try {
            double result = getValue().doubleValue();
            // khi ma user input : 5 / 2 --> sqrt
            // thuc hien phep tinh cho ra ket qua roi thực hiện sqrt
            if (result >= 0) {
                double xResult = Math.sqrt(result);

                BigDecimal ans = new BigDecimal(xResult);
                setTxtResult(ans.add(new BigDecimal(0), new MathContext(17)).stripTrailingZeros().toPlainString() + "");
            } else {
                setTxtResult("ERROR");
            }
//             pressResult();
        } catch (Exception e) {
            setTxtResult("ERROR");
        }
        OperatorProcess = false;
        resetNumber = true; // to press a new number.
    }

    // press button dot.
    public void pressDot() {
        if (OperatorProcess == true) {
            setTxtResult("0");
            OperatorProcess = false;
        }

        if (!getTxtResult().contains(".")) {
            setTxtResult(getTxtResult() + ".");
        }
    }

    // press button MAdd.
    public void pressMAdd() {
        try {
            memory = memory.add(getValue()); // add value on screen with value in memory
        } catch (Exception e) {
            setTxtResult("ERROR");
        }
        resetNumber = true; // to press a new number.
    }

    // press button MSub
    public void pressMSub() {
        try {
            memory = memory.subtract(getValue());// subtract value on screen with value in memory
        } catch (Exception e) {
            setTxtResult("ERROR");
        }
        resetNumber = true; // to press a new number.
    }

    // press button MR.
    public void pressMR() {
        setTxtResult(memory + "");
        resetNumber = true; // to press a new number.
        OperatorProcess = false; // to calculate() can set first number = value of MR.
    }

    // press button MC.
    public void pressMC() {
        memory = new BigDecimal("0");
    }

    // hover
    public void setColor(JButton btn) {
        btn.setBackground(new Color(204, 255, 204));
    }

    public void resetColor(JButton btn) {
        btn.setBackground(new Color(153, 204, 255));
    }
}
