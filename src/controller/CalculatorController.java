/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.math.BigDecimal;
import java.math.MathContext;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author Admin
 */
public class CalculatorController {

    private BigDecimal firstNumber = null;
    private BigDecimal secondNumber = null;
    private BigDecimal memory = BigDecimal.ZERO;
    private boolean process;
    private boolean existNumber = false;
    private boolean reset;
    private boolean isMR;
    private JTextField textField;
    private int operator = -1;

    public boolean isIsMR() {
        return isMR;
    }

    public void setIsMR(boolean isMR) {
        this.isMR = isMR;
    }

    public CalculatorController(JTextField textField) {
        this.textField = textField;
        operator = -1;
        existNumber = false;
        process = true;
    }

    public BigDecimal getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(BigDecimal firstNumber) {
        this.firstNumber = firstNumber;
    }

    public BigDecimal getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(BigDecimal secondNumber) {
        this.secondNumber = secondNumber;
    }

    public boolean isProcess() {
        return process;
    }

    public void setProcess(boolean process) {
        this.process = process;
    }

    public boolean isExistNumber() {
        if (textField.getText().isEmpty()) {
            existNumber = false;
        } else {
            existNumber = true;
        }
        return existNumber;

    }

    public void setExistNumber(boolean existNumber) {
        this.existNumber = existNumber;
    }

    public void pressNumber(JButton button) {
        BigDecimal temp;
        String value = button.getText();

        if (process || reset) {
            textField.setText("0");
            setProcess(false);
            reset = false;

        }

        temp = new BigDecimal(textField.getText() + value);
        textField.setText(temp + "");
        System.out.println("f " + firstNumber);
        System.out.println("s " + secondNumber);
        System.out.println("enter number ");

    }

    public void pressDot() {
        if (process || reset) {
            textField.setText("0");
            process = false;
            reset = false;
        }
        if (!textField.getText().contains(".")) {
            textField.setText(textField.getText() + ".");
        }
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public BigDecimal getNumber() {
        String value = textField.getText();
        BigDecimal temp;
        temp = new BigDecimal(value);
        return temp;

    }

    public void calculate() {
        System.out.println(isExistNumber());
        if (isExistNumber()) {
            System.out.println("in calculate:");
            if (!process) {
                if (firstNumber == null || operator == -1) {
                    firstNumber = getNumber();
                    System.out.println("fi " + firstNumber);
                    setProcess(true);
                } else {
                    secondNumber = getNumber();
                    System.out.println("sec " + secondNumber);
                    switch (operator) {
                        case 1:
                            firstNumber = firstNumber.add(secondNumber);
                            break;
                        case 2:
                            firstNumber = firstNumber.subtract(secondNumber);
                            break;
                        case 3:
                            firstNumber = firstNumber.multiply(secondNumber);
                            break;
                        case 4:
                            if (secondNumber.toString().equals("0") || firstNumber.toString().equals("0")) {
                                textField.setText("ERROR");
                                clearAll();
                                setExistNumber(false);
                                break;
                            } else {
                                firstNumber = firstNumber.divide(secondNumber);
                                break;
                            }
                    }
                }
                try {
                    if (!firstNumber.equals(null)) {
                        textField.setText(String.valueOf(firstNumber));
                        System.out.println(firstNumber);
                    }
                } catch (Exception e) {
                    System.out.println("null");
                }
            }
            operator = -1;
        } else {
            operator = -1;
        }
        setProcess(true);
    }

    public void pressResult() {
        if (!textField.getText().equals("ERROR")) {
            calculate();
            operator = -1;
        } else {
            textField.setText(firstNumber + "");
        }
    }

    public void pressNegate() {
        pressResult();
        textField.setText(getNumber().negate() + "");
        process = false;
        reset = true;
    }

    public void pressPercent() {
        pressResult();
        try {
            textField.setText((getNumber().doubleValue()) / 100 + "");
            process = false;
            reset = true;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void clearAll() {
        setFirstNumber(null);
        setSecondNumber(null);
        operator = -1;
    }

    public void pressSqrt() {
        pressResult();
        try {
            BigDecimal result = getNumber();
            if (result.doubleValue() >= 0) {
                String display = Math.sqrt(result.doubleValue()) + "";
                if (display.endsWith(".0")) {
                    display = display.replace(".0", "");
                }
                textField.setText(display);
                process = false;
            } else {
                textField.setText("ERROR");
                firstNumber = null;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        reset = true;
    }

    public void pressInvert() {
        pressResult();
        try {
            double result = getNumber().doubleValue();
            if (result != 0) {
                textField.setText((1 / result) + "");
                process = false;
            } else {
                textField.setText("ERROR");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        reset = true;

    }

    public void pressMPlus() {
        memory = memory.add(getNumber());
        process = false;
        reset = true;
        System.out.println("memory+" + memory);
    }

    public void pressMMMin() {
        memory = memory.add(getNumber().negate());
        process = false;
        reset = true;
        System.out.println("memory-" + memory);
    }

    public void pressMC() {
        memory = BigDecimal.ZERO;
        isMR = false;
       
    }

    public void pressMR() {
        textField.setText(memory + "");
        process = false;
    }

}
