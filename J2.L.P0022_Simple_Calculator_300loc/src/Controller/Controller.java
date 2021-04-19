package Controller;

import GUI.GUI;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import javax.swing.JButton;

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

//        btnDot();
//        btnAdd();
//        btnSubtract();
//        btnMutiply();
//        btnDivide();
//        btnResult();
//        btnPercent();
//        btnNegate();
//        btnInvert();
//        btnSqrt();
//        btnMAdd();
//        btnMSub();
//        btnMR();
//        btnMC();
//        lblClear();
        frm.getTxtResult().setText("0");
    }

    // press lbl All Clear.
    public void Clear() {
        frm.getTxtResult().setText("0");
        firstNum = new BigDecimal("0");
        secondNum = new BigDecimal("0");
        memory = new BigDecimal("0");
        operator = -1;
        OperatorProcess = false;
        resetNumber = false;
        frm.getBtnMr().setBackground(new Color(255, 233, 148));
        frm.getBtnMr().setEnabled(false);
    }

    // get value from text on screen.
    public BigDecimal getValue() {
        String value = frm.getTxtResult().getText();
        BigDecimal temp = new BigDecimal(value);
        return temp;
    }

    // Create event for every button numer.
    // press button numbers.
    public void pressNumber(int number) {
        if (OperatorProcess == true || resetNumber == true) { // it's mean user has been pressed one of the 4 operators.
            frm.getTxtResult().setText(""); // to press a new number.
            OperatorProcess = false; // to do calculate().   
            resetNumber = false; // user can press a new number.
        }
        BigDecimal temp = new BigDecimal(frm.getTxtResult().getText() + number);
        frm.getTxtResult().setText(temp + "");
    }

    // press button operator.
    // set operators ("+ = 1", "- = 2", "* = 3", "/ = 4").
    public void setOperator(int operator) {
        this.operator = operator;
    }

    // calculate (+, -, *, /).
    public void calculate() {
        if (frm.getTxtResult().getText().equals("ERROR")) {
            frm.getTxtResult().setText("0");
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
                                firstNum = firstNum.divide(secondNum, mc); // get 20 number after dot.
                        }
                    }
                }
                // will set the result to screen when we click second operator or result button
                BigDecimal temp = firstNum;
                temp = temp.round(new MathContext(16, RoundingMode.HALF_UP));
                frm.getTxtResult().setText(temp.stripTrailingZeros().toPlainString() + "");
                OperatorProcess = true; // to press a new number
            }
        } catch (Exception e) {
            OperatorProcess = true; // to press a new number.
            frm.getTxtResult().setText("ERROR");
        }
    }

    // press button result.
    public void pressResult() {
        if (!frm.getTxtResult().getText().equals("ERROR")) {
            calculate(); // calculate() will call again.
            operator = -1; // resetNumber operator.
        } else {
            frm.getTxtResult().setText("0");
            firstNum = new BigDecimal("0");
            secondNum = new BigDecimal("0");
        }
    }

    // press button %
    public void pressPercent() {
        if (frm.getTxtResult().getText().equals("ERROR")) {
            Clear();
        }

        String value = frm.getTxtResult().getText();
        BigDecimal xValue = new BigDecimal(value);
        BigDecimal percent = new BigDecimal(100);
        try {
            frm.getTxtResult().setText(xValue.divide(percent, MathContext.DECIMAL64).stripTrailingZeros().toPlainString());

//            frm.getTxtResult().setText(getValue().doubleValue() / 100 + "");
//            pressResult();
        } catch (Exception e) {
            frm.getTxtResult().setText("ERROR");
        }
        OperatorProcess = false;
        resetNumber = true; // to press a new number.
    }

    // press button +/-
    public void pressNegate() {
        if (frm.getTxtResult().getText().equals("ERROR")) {
            Clear();
        }
        try {

            frm.getTxtResult().setText(getValue().negate() + "");
//            pressResult();
        } catch (Exception e) {
            frm.getTxtResult().setText("ERROR");
        }
        OperatorProcess = false;
        resetNumber = true; // to press a new number.
    }

    // press button 1/x
    public void pressInvert() {
        if (frm.getTxtResult().getText().equals("ERROR")) {
            Clear();
        }
        try {

            double result = getValue().doubleValue();
            if (result != 0) {
                BigDecimal numberInVert = new BigDecimal(frm.getTxtResult().getText());
                BigDecimal numberOne = new BigDecimal(1);
                BigDecimal ans = numberOne.divide(numberInVert, new MathContext(17));
                frm.getTxtResult().setText(ans.stripTrailingZeros().toPlainString());
                frm.getTxtResult().setText((1 / result) + "");
            } else {
                frm.getTxtResult().setText("ERROR");
            }
//            pressResult();
        } catch (Exception e) {
            frm.getTxtResult().setText("ERROR");
        }
        OperatorProcess = false;
        resetNumber = true; // to press a new number
    }

    public void pressSqrt() {
        if (frm.getTxtResult().getText().equals("ERROR")) {
            Clear();
        }
        try {
            double result = getValue().doubleValue();
            // khi ma user input : 5 / 2 --> sqrt
            // thuc hien phep tinh cho ra ket qua roi thực hiện sqrt
            if (result >= 0) {
                double xResult = Math.sqrt(result);

                BigDecimal ans = new BigDecimal(xResult);

                frm.getTxtResult().setText(ans.add(new BigDecimal(0), new MathContext(17)).stripTrailingZeros().toPlainString() + "");
            } else {
                frm.getTxtResult().setText("ERROR");
            }
//             pressResult();
        } catch (Exception e) {
            frm.getTxtResult().setText("ERROR");
        }
        OperatorProcess = false;
        resetNumber = true; // to press a new number.
    }

    // press button dot.
    public void pressDot() {
        if (OperatorProcess == true) {
            frm.getTxtResult().setText("0");
            OperatorProcess = false;
        }

        if (!frm.getTxtResult().getText().contains(".")) {
            frm.getTxtResult().setText(frm.getTxtResult().getText() + ".");
        }
    }

    // press button MAdd.
    public void pressMAdd() {
        try {
            memory = memory.add(getValue()); // add value on screen with value in memory
        } catch (Exception e) {
            frm.getTxtResult().setText("ERROR");
        }
        resetNumber = true; // to press a new number.
    }

    // press button MSub
    public void pressMSub() {
        try {
            memory = memory.subtract(getValue());// subtract value on screen with value in memory
        } catch (Exception e) {
            frm.getTxtResult().setText("ERROR");
        }
        resetNumber = true; // to press a new number.
    }

    // press button MR.
    public void pressMR() {
        frm.getTxtResult().setText(memory + "");
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
