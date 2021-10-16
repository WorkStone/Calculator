package com.shiyanlou.calculator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;
import java.math.BigDecimal;

public class Calculator {
    //操作数1，为了程序的安全，初值一定要设置，次数设置为0
    String str1 = "0";
    //操作数2
    String str2 = "0";
    //运算符
    String signal = "+";
    //运算结果
    String result = "";

    //以下状态主要处理小数点和多符号运算的问题
    //以下k1至k2为状态开关.
    //开关1用于选择输入方向，将要写入str1或str2
    int k1 = 1;
    //开关2用于记录符号键的次数，如果k2>1说明进行的是多符号运算
    int k2 = 1;
    //开关3用于标识str1是否可以被清0，等于1时可以，不等于1时不能被清0
    int k3 = 1;
    //开关4用于标识str2是否可以被清0，等于1时可以，不等于1时不能被清0
    int k4 = 1;
    //开关5用于控制小数点可否被录入，等于1时可以，不为1时，输入的小数点被丢弃
    int k5 = 1;
    //store的作用类似于寄存器，用于记录是否连续按下符号键
    JButton store;

    @SuppressWarnings("rawtypes")     //忽略警告
    Vector vt = new Vector(20,10);

    //声明各个UI组件对象并初始化
    JFrame frame = new JFrame("小玉专属计算机");
    JTextField result_TextField = new JTextField(result,20);
    JButton clear_Button = new JButton("Clear");
    JButton button0 = new JButton("0");
    JButton button1 = new JButton("1");
    JButton button2 = new JButton("2");
    JButton button3 = new JButton("3");
    JButton button4 = new JButton("4");
    JButton button5 = new JButton("5");
    JButton button6 = new JButton("6");
    JButton button7 = new JButton("7");
    JButton button8 = new JButton("8");
    JButton button9 = new JButton("9");
    JButton button_Dian = new JButton(".");
    JButton button_Jia = new JButton("+");
    JButton button_Jian = new JButton("-");
    JButton button_Cheng = new JButton("*");
    JButton button_Chu = new JButton("/");
    JButton button_Dy = new JButton("=");

    //以上均是各种组件对象，或者控制对象等；接下来是各种方法
    //计算器类的构造方法
    public Calculator(){
        //为按钮设置等效键，既可以通过对应的键盘按键来代替点击它
        button0.setMnemonic(KeyEvent.VK_0);
        button1.setMnemonic(KeyEvent.VK_1);
        button2.setMnemonic(KeyEvent.VK_2);
        button3.setMnemonic(KeyEvent.VK_3);
        button4.setMnemonic(KeyEvent.VK_4);
        button5.setMnemonic(KeyEvent.VK_5);
        button6.setMnemonic(KeyEvent.VK_6);
        button7.setMnemonic(KeyEvent.VK_7);
        button8.setMnemonic(KeyEvent.VK_8);
        button9.setMnemonic(KeyEvent.VK_9);
        button_Dian.setMnemonic(KeyEvent.VK_PERIOD);
        button_Jia.setMnemonic(KeyEvent.VK_ADD);
        button_Jian.setMnemonic(KeyEvent.VK_SUBTRACT);
        button_Cheng.setMnemonic(KeyEvent.VK_MULTIPLY);
        button_Chu.setMnemonic(KeyEvent.VK_SLASH);
        button_Dy.setMnemonic(KeyEvent.VK_EQUALS);

        //设置文本框为右对齐，使输入和结果都靠右显示
        result_TextField.setHorizontalAlignment(JTextField.RIGHT);
        //将UI组件添加进容器内
        JPanel pan = new JPanel();
        pan.setLayout(new GridLayout(4,4,5,5));
        pan.add(button7);
        pan.add(button8);
        pan.add(button9);
        pan.add(button_Chu);
        pan.add(button4);
        pan.add(button5);
        pan.add(button6);
        pan.add(button_Cheng);
        pan.add(button1);
        pan.add(button2);
        pan.add(button3);
        pan.add(button_Jian);
        pan.add(button0);
        pan.add(button_Dian);
        pan.add(button_Dy);
        pan.add(button_Jia);
        pan.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        JPanel pan2 = new JPanel();
        pan2.setLayout(new BorderLayout());
        pan2.add(result_TextField,BorderLayout.WEST);
        pan2.add(clear_Button,BorderLayout.EAST);

        //设置主窗口出现在屏幕上的位置
        frame.setLocation(300,200);
        //设置窗体不能调大小
        frame.setResizable(false);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(pan2,BorderLayout.NORTH);
        frame.getContentPane().add(pan,BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);

        //事件处理程序
        //数字键
        class Listener implements ActionListener{

            @SuppressWarnings("unchecked")
            @Override
            public void actionPerformed(ActionEvent e) {
                String ss = ((JButton) e.getSource()).getText();
                store = (JButton) e.getSource();
                vt.add(store);
                if (k1 == 1){
                    if (k3 == 1){
                        str1 = "";
                        //还原开关k5的状态
                        k5 = 1;
                    }
                    str1 = str1 + ss;
                    k3 = k3 + 1;

                    result_TextField.setText(str1);
                }else if (k1 == 2){
                    if (k4 == 1){
                        str2 = "";
                        k5 = 1;
                    }
                    str2 = str2 + ss;
                    k4 = k4 + 1;
                    result_TextField.setText(str2);
                }

            }
        }

        //输入的运算符号的处理
        class Listener_signal implements ActionListener{

            @SuppressWarnings("unchecked")
            @Override
            public void actionPerformed(ActionEvent e) {
                String ss2 = ((JButton) e.getSource()).getText();
                store = (JButton) e.getSource();
                vt.add(store);
                if (k2 == 1){
                    //开关k1为1时向数1写输入值，为2时向数2写输入值
                    k1 = 2;
                    k5 = 1;
                    signal = ss2;
                    k2 = k2 + 1;
                }else {
                    int a = vt.size();
                    JButton c = (JButton) vt.get(a-2);

                    if (!(c.getText().equals("+")) && !(c.getText().equals("-")) && !(c.getText().equals("*")) && !(c.getText().equals("/"))){
                        cal();
                        str1 = result;
                        //开关k1为1时向数1写值，为2向数2写
                        k1 = 2;
                        k5 = 1;
                        k4 = 1;
                        signal = ss2;
                    }
                    k2 = k2 +1;
                }

            }
        }

        //清除键的逻辑(clear)
        class Listener_clear implements ActionListener{
            @SuppressWarnings("unchecked")
            @Override
            public void actionPerformed(ActionEvent e) {
                store = (JButton) e.getSource();
                vt.add(store);
                k5 = 1;
                k2 = 1;
                k1 = 1;
                k3 = 1;
                k4 = 1;
                str1 = "0";
                str2 = "0";
                signal = "";
                result = "";
                result_TextField.setText(result);
                vt.clear();
            }
        }

        //等于键的逻辑
        class Listener_Dy implements ActionListener{
            @SuppressWarnings("unchecked")
            @Override
            public void actionPerformed(ActionEvent e) {
                store = (JButton) e.getSource();
                vt.add(store);
                cal();

                //还原各个开关的状态
                k1 = 1;
                k2 = 1;
                k3 = 1;
                k4 = 1;

                str1 = result;
            }
        }

        //小数点逻辑
        class Listener_Xsd implements ActionListener{
            @SuppressWarnings("unchecked")
            @Override
            public void actionPerformed(ActionEvent e) {
                store = (JButton) e.getSource();
                vt.add(store);
                if (k5 == 1){
                    String ss2 = ((JButton) e.getSource()).getText();
                    if (k1 == 1){
                        if (k3 == 1){
                            str1 = "";
                            //还原开关5的状态
                            k5 = 1;
                        }
                        str1 = str1 + ss2;
                        k3 = k3 + 1;
                        //显示结果
                        result_TextField.setText(str1);
                    }else if (k1 == 2){
                        if (k4 == 1){
                            str2 = "";
                            //还原开关5的状态
                            k5 = 1;
                        }
                        str2 = str2 + ss2;
                        k4 = k4 + 1;
                        result_TextField.setText(str2);
                    }
                }
                k5 = k5 + 1;
            }
        }

        //注册各个监听器，即绑定事件响应逻辑在各个UI组件上
        Listener_Dy jt_dy = new Listener_Dy();
        Listener jt = new Listener();
        Listener_signal jt_signal = new Listener_signal();
        Listener_clear jt_clear = new Listener_clear();
        Listener_Xsd jt_xsd = new Listener_Xsd();

        button7.addActionListener(jt);
        button8.addActionListener(jt);
        button9.addActionListener(jt);
        button_Chu.addActionListener(jt_signal);
        button4.addActionListener(jt);
        button5.addActionListener(jt);
        button6.addActionListener(jt);
        button_Cheng.addActionListener(jt_signal);
        button1.addActionListener(jt);
        button2.addActionListener(jt);
        button3.addActionListener(jt);
        button_Jian.addActionListener(jt_signal);
        button0.addActionListener(jt);
        button_Dian.addActionListener(jt_xsd);
        button_Dy.addActionListener(jt_dy);
        button_Jia.addActionListener(jt_signal);
        clear_Button.addActionListener(jt_clear);

        //窗体关闭事件的响应程序
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //super.windowClosing(e);
                System.exit(0);
            }
        });

    }

    //计算逻辑
    public void cal(){
        //操作数1
        double a2;
        //操作数2
        double b2;
        //运算符
        String c = signal;
        //运算结果
        double result2 = 0;

        if (c.equals("")) {
            result_TextField.setText("Please input operator");

        } else {
            //手动处理小数点的问题
            if (str1.equals("."))
                str1 = "0.0";
            if (str2.equals("."))
                str2 = "0.0";
            a2 = Double.valueOf(str1).doubleValue();
            b2 = Double.valueOf(str2).doubleValue();

            if (c.equals("+")) {
                result2 = a2 + b2;
            }
            if (c.equals("-")) {
                result2 = a2 - b2;
            }
            if (c.equals("*")) {
                BigDecimal m1 = new BigDecimal(Double.toString(a2));
                BigDecimal m2 = new BigDecimal(Double.toString(b2));
                result2 = m1.multiply(m2).doubleValue();
            }
            if (c.equals("/")) {
                if (b2 == 0) {
                    result2 = 0;
                } else {
                    result2 = a2 / b2;
                }

            }

            result = ((new Double(result2)).toString());

            result_TextField.setText(result);
        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        //设置程序显示的界面风格，可以去除
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calculator cal = new Calculator();
    }

}
