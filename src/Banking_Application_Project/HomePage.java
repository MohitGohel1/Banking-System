package Banking_Application_Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

interface AdminDetails{
    String id="83299";
    String pass="66093";
}
class TradeMark extends JFrame{

    JLabel l1,l2;
    void setTradeMark(JFrame a){
        l1=new JLabel("Welcome To Kotak Bank");
        l2=new JLabel("By Mohit Gohel");
        l1.setBounds(200,0,300,25);
        l2.setBounds(225,15,150,25);
        l1.setForeground(Color.red);
        l2.setForeground(Color.BLUE);
        a.add(l1);
        a.add(l2);
    }
}
class DisplayAccNum extends JFrame{
    JLabel l1;
    void setComponents(JFrame a){
        l1=new JLabel("Account Number: "+OperationClass.acc.getAccNum());
        l1.setBounds(200,200,250,25);
        a.add(l1);
    }
}
public class HomePage extends JFrame {

    JButton b1,b2,adb;
    HomePage(){

    }
    HomePage(String s1){
        super(s1);
    }
    void setComponents(){
        new TradeMark().setTradeMark(this);
        b1=new JButton("Login");
        b2=new JButton("Open Account");
        adb=new JButton("Admin Login");
//        Font f=new Font("",Font.BOLD,20);
        b1.setBounds(200,200,200,50);
        b2.setBounds(200,260,200,50);
        adb.setBounds(200,320,200,50);
//        l1.setFont(f);
        b1.addActionListener(new LoginButton());
        b2.addActionListener(new OpenAccButton());
        adb.addActionListener(new AdminLoginButton());
        this.setLayout(null);
        add(b1);
        add(adb);
        add(b2);
        addWindowListener(new OnClose());
    }
    public static void main(String[] args){
        try{
            OperationClass.fileReadOperations();
        }catch (IOException a){
            a.printStackTrace();
        }catch (ClassNotFoundException b){
            b.printStackTrace();
        }
        HomePage.displayThisPage();
    }
    static void displayThisPage(){
        HomePage h=new HomePage("Banking Application");
        h.setVisible(true);
        h.setSize(600,600);
        h.setComponents();
        h.setDefaultCloseOperation(HomePage.DO_NOTHING_ON_CLOSE);
    }
    class AdminLoginButton implements ActionListener{
        public void actionPerformed(ActionEvent a){
            AdminLoginPage.displayThisPage();
        }
    }
    class OnClose extends WindowAdapter{
        public void windowClosing(WindowEvent e) {
            try{
                OperationClass.fileWriteOperation();
            }catch (IOException a){
                a.printStackTrace();
            }catch (ClassNotFoundException b){
                b.printStackTrace();
            }
            System.exit(0);
        }
    }
}
class AdminLoginPage extends JFrame{
    JLabel l3,l4,ll1,ll2;

    JTextField t1;
    JPasswordField t2;
    JButton b1,b2,b3;
    AdminLoginPage(){

    }
    AdminLoginPage(String s1){
        super(s1);
    }
    void setComponents(){
        new TradeMark().setTradeMark(this);
        l3=new JLabel("Enter Admin Id :");
        l4=new JLabel("Enter Password:");
        b1=new JButton("Login");
        b2=new JButton("clear");
        b3=new JButton("Back");
        ll1=new JLabel();
        ll2=new JLabel();
        setLayout(null);

        l3.setBounds(50,200,150,25);
        ll1.setBounds(400,200,200,25);
        ll2.setBounds(400,260,200,25);
        l4.setBounds(50,260,100,25);
        b1.setBounds(150,300,100,25);
        b2.setBounds(250,300,100,25);
        b3.setBounds(400,400,100,25);
        b1.addActionListener(new LoginAdminButton());
        b3.addActionListener(new Back());
        add(l3);
        add(ll1);
        add(l4);
        add(b1);
        add(b2);
        add(b3);
        add(ll2);
        textFieldGenerator();
    }
    void textFieldGenerator(){
        t1=new JTextField();
        t2=new JPasswordField();
        t1.setBounds(200,200,200,25);
        t2.setBounds(200,260,200,25);
        add(t1);
        add(t2);
    }
    boolean validateCode(){
        int a=2;
        if (t1.getText().length()==0){
            ll1.setText("Cannot be Empty");
        }
        else{
            a--;
            ll1.setText("");
        }
        if(String.valueOf(t2.getPassword()).length()==0){
            ll2.setText("Cannot be Empty");
        }
        else{
            a--;
            ll2.setText("");
        }
        if (a==0){
            return true;
        }
        return false;
    }
    static void displayThisPage(){
        AdminLoginPage a=new AdminLoginPage("Admin Login");
        a.setVisible(true);
        a.setSize(600,600);
        a.setComponents();
    }
    class LoginAdminButton implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(validateCode()==true){
                if(t1.getText().equals(AdminDetails.id) && String.valueOf(t2.getPassword()).equals(AdminDetails.pass)){
                    ApprovalPage.displayThisPage();
                }
            }
        }
    }
}
class ApprovalPage extends JFrame{
    JLabel[] l,l1;
    JButton[] b,b1;
    JButton back;
    ApprovalPage(){

    }
    ApprovalPage(String s1){
        super(s1);
    }
    void setComponents(){
        new TradeMark().setTradeMark(this);
        setLayout(null);
        back=new JButton("Back");
        back.setBounds(450,450,100,25);
        back.addActionListener(new AdminBack());
        add(back);
        CurrentAccount[] c=OperationClass.getCurrentAccount();
        LoanAccount[] loan=OperationClass.getLoanAccount();
        int x=50,y=100,width=300,height=25;
        if(c!=null){
            l=new JLabel[c.length];
            b=new JButton[c.length];
            for(int i=0;i<c.length;i++){
                l[i]=new JLabel(c[i].accNum+" Wants Rs"+c[i].overDraft+" as Overdraft");
                b[i]=new JButton("Approve");
                l[i].setBounds(x,y,width,height);
                b[i].setBounds(400,y,100,25);
                b[i].addActionListener(new ApproveOrNot(c[i]));
                y+=50;
                add(l[i]);
                add(b[i]);
            }
        }
        if(loan!=null){
            l1=new JLabel[loan.length];
            b1=new JButton[loan.length];
            for(int i=0;i<loan.length;i++){
                l1[i]=new JLabel(loan[i].accNum+" Wants Rs"+loan[i].loanAmount+" as Loan");
                b1[i]=new JButton("Sanction");
                l1[i].setBounds(x,y,width,height);
                b1[i].setBounds(400,y,100,25);
                b1[i].addActionListener(new LoanSanction(loan[i]));
                y+=50;
                add(l1[i]);
                add(b1[i]);
            }
        }
    }
    static void displayThisPage(){
        ApprovalPage a=new ApprovalPage("Approval Page");
        a.setVisible(true);
        a.setSize(600,600);
        a.setComponents();
    }
    class AdminBack implements ActionListener{
        public void actionPerformed(ActionEvent a){
            AdminLoginPage.displayThisPage();
        }
    }
}
class ApproveOrNot implements ActionListener{
    CurrentAccount c;
    ApproveOrNot(CurrentAccount c){
        this.c=c;
    }
    public void actionPerformed(ActionEvent a){
        c.approvedOrNot=true;
        ApprovalPage.displayThisPage();
    }
}
class LoanSanction implements ActionListener{
    LoanAccount l;
    LoanSanction(LoanAccount l){
        this.l=l;
    }
    public void actionPerformed(ActionEvent a){
        l.approveOrNot=true;
        l.balance=l.loanAmount*(-1);
        l.setTransaction(new Transaction(l.loanAmount,"Withdraw",LocalDate.now()));
        ApprovalPage.displayThisPage();
    }
}
class LoginButton implements ActionListener{
    public void actionPerformed(ActionEvent a){
        LoginPage l=new LoginPage("Login Page");
        l.setVisible(true);
        l.setSize(600,600);
        l.setComponents();
    }
}
class OpenAccButton implements ActionListener{
    public void actionPerformed(ActionEvent a){
        ChoicPage o=new ChoicPage("Choose Which Account To open");
        o.setVisible(true);
        o.setSize(600,600);
        o.setComponents();
    }
}
class LoginPage extends JFrame{
    JLabel l3,l4,ll1,ll2;
    JTextField t1;
    JPasswordField t2;
    JButton b1,b2,b3;
    LoginPage(){

    }
    LoginPage(String s1){
        super(s1);
    }
    void setComponents(){
        new TradeMark().setTradeMark(this);
        l3=new JLabel("Enter Account Number :");
        l4=new JLabel("Enter Password:");
        b1=new JButton("Login");
        b2=new JButton("clear");
        b3=new JButton("Back");
        ll1=new JLabel();
        ll2=new JLabel();
        setLayout(null);

        l3.setBounds(50,200,150,25);
        ll1.setBounds(400,200,200,25);
        ll2.setBounds(400,260,200,25);
        l4.setBounds(50,260,100,25);
        b1.setBounds(150,300,100,25);
        b2.setBounds(250,300,100,25);
        b3.setBounds(400,400,100,25);
        b1.addActionListener(new DiffLogin());
        b3.addActionListener(new Back());
        add(l3);
        add(ll1);
        add(l4);
        add(b1);
        add(b2);
        add(b3);
        add(ll2);
        textFieldGenerator();
    }
    void textFieldGenerator(){
        t1=new JTextField();
        t2=new JPasswordField();
        t1.setBounds(200,200,200,25);
        t2.setBounds(200,260,200,25);
        add(t1);
        add(t2);
    }
    boolean validateCode(){
        int a=2;
        if (t1.getText().length()==0){
            ll1.setText("Cannot be Empty");
        }
        else{
            a--;
            ll1.setText("");
        }
        if(String.valueOf(t2.getPassword()).length()==0){
            ll2.setText("Cannot be Empty");
        }
        else{
            a--;
            ll2.setText("");
        }
        if (a==0){
            return true;
        }
        return false;
    }
    class DiffLogin implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(validateCode()==true){
                OperationClass.giveAccount(t1.getText(),String.valueOf(t2.getPassword()));
            }
        }
    }
}
class Back implements ActionListener{
    public void actionPerformed(ActionEvent a){
        HomePage.displayThisPage();
    }
}
class ChoicPage extends JFrame{
    JButton b1,b2,b3,b4,b5;
    ChoicPage(){

    }
    ChoicPage(String s1){
        super(s1);
    }
    void setComponents(){
        new TradeMark().setTradeMark(this);
        b1=new JButton("Saving Account");
        b2=new JButton("Salary Account");
        b3=new JButton("Current Account");
        b4=new JButton("Loan Account");
        b5=new JButton("Back");
        setLayout(null);
        b1.setBounds(200,200,150,50);
        b2.setBounds(200,250,150,50);
        b3.setBounds(200,300,150,50);
        b4.setBounds(200,350,150,50);
        b5.setBounds(480,520,100,25);
        b1.addActionListener(new SavingAcc());
        b2.addActionListener(new SalaryAcc());
        b3.addActionListener(new CurrentAcc());
        b4.addActionListener(new LoanAcc());
        b5.addActionListener(new Back());
        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(b5);
    }
    class SavingAcc implements ActionListener{
        public void actionPerformed(ActionEvent a){
            SavingAccountPage s=new SavingAccountPage("SavingAccountPage");
            s.setVisible(true);
            s.setSize(600,600);
            s.setComponents();
        }
    }
    class SalaryAcc implements ActionListener{
        public void actionPerformed(ActionEvent a){
            SalaryAccountPage s=new SalaryAccountPage("Salary Account Page");
            s.setVisible(true);
            s.setSize(600,600);
            s.setComponents();
        }
    }
    class CurrentAcc implements ActionListener{
        public void actionPerformed(ActionEvent a){
            CurrentAccountPage.displayThisPage();
        }
    }
    class LoanAcc implements ActionListener{
        public void actionPerformed(ActionEvent a){
            LoanAccountPage.displayThisPage();
        }
    }
}
class OpenAccount extends JFrame{
    JLabel l3,l4,l5,l6,l7,l8,ll3,ll4,ll5,ll6,ll7;
    JButton b1,b2,b3;
    JLabel l9,ll8;
    JTextField t7;
    JTextField t1,t2,t3,t4,t5;
    JPasswordField t6;

    OpenAccount(){

    }
    OpenAccount(String s1){
        super(s1);
    }
    void setComponents(){
        new TradeMark().setTradeMark(this);
        setLayout(null);
        l3=new JLabel("First Name:");
        l4=new JLabel("Last Name:");
        l5=new JLabel("Pan Card");
        l8=new JLabel("Mobile:");
        l6=new JLabel("new Pass:");
        l7=new JLabel("again Pass:");
        ll3=new JLabel();
        ll4=new JLabel();
        ll5=new JLabel();
        ll6=new JLabel();
        ll7=new JLabel();
        b1=new JButton("Open");
        b2=new JButton("Clear");
        b3=new JButton("Back");
        l3.setBounds(100,100,75,25);
        l4.setBounds(100,150,75,25);
        l5.setBounds(100,200,75,25);
        l8.setBounds(100,250,75,25);
        l6.setBounds(100,300,75,25);
        l7.setBounds(100,350,75,25);
        ll3.setBounds(400,100,200,25);
        ll4.setBounds(400,150,200,25);
        ll5.setBounds(400,200,200,25);
        ll6.setBounds(400,250,200,25);
        ll7.setBounds(400,300,200,25);
        b1.setBounds(200,500,100,25);
        b2.setBounds(300,500,100,25);
        b3.setBounds(480,520,100,25);
        b3.addActionListener(new OpenAccButton());
        add(l3);
        add(l4);
        add(l5);
        add(l6);
        add(l7);
        add(l8);
        add(b1);
        add(b2);
        add(b3);
        add(ll3);
        add(ll4);
        add(ll5);
        add(ll6);
        add(ll7);
    }
    void textFieldGenerator(){
        t1=new JTextField();
        t2=new JTextField();
        t3=new JTextField();
        t4=new JTextField();
        t5=new JTextField();
        t6=new JPasswordField();
        t1.setBounds(200,100,200,25);
        t2.setBounds(200,150,200,25);
        t3.setBounds(200,200,200,25);
        t4.setBounds(200,250,200,25);
        t5.setBounds(200,300,200,25);
        t6.setBounds(200,350,200,25);
        add(t1);
        add(t2);
        add(t3);
        add(t4);
        add(t5);
        add(t6);
    }
    boolean checkInput(){
        int a=6;
        if(t1.getText().length()==0){
            ll3.setText("Cannot be Empty");
        }
        else{
            a--;
            ll3.setText("");
        }
        if(t2.getText().length()==0){
            ll4.setText("Cannot be Empty");
        }
        else{
            a--;
            ll4.setText("");
        }
        if(t3.getText().length()==0){
            ll5.setText("Cannot be Empty");
        }
        else{
            a--;
            ll5.setText("");
        }
        if(t4.getText().length()==0){
            ll6.setText("Cannot be Empty");
        }
        else{
            try{
                Long.parseLong(t4.getText());
                if(t4.getText().length()<10 || t4.getText().length()>10){
                    ll6.setText("10 digits");
                }
                else{
                    a--;
                    ll6.setText("");
                }
            }catch (NumberFormatException a1){
                ll6.setText("Only Digits");
            }
        }
        if(t5.getText().length()==0){
            ll7.setText("Cannot be Empty");
        }
        else{
            a--;
            ll7.setText("");
        }
        if(t5.getText().equals(String.valueOf(t6.getPassword()))==false){
            JOptionPane.showMessageDialog(null,"Password Mismatch");
        }
        else{
            a--;
        }
        if(a==0){
            return true;
        }
        return false;
    }

}
class LoanAccountPage extends OpenAccount{
    LoanAccountPage(){

    }
    LoanAccountPage(String s1){
        super(s1);
    }
    void setComponents(){
        super.setComponents();
        l9=new JLabel("How Much Loan Do you want=");
        l9.setBounds(100,400,250,25);
        ll8=new JLabel();
        ll8.setBounds(450,400,200,25);
        add(l9);
        add(ll8);
        textFieldGenerator();
        b1.addActionListener(new LoanActionListner());
        super.textFieldGenerator();
    }
    void textFieldGenerator(){
        t7=new JTextField();
        t7.setBounds(350,400,100,25);
        add(t7);
    }
    static void displayThisPage(){
        LoanAccountPage c=new LoanAccountPage("Loan Account Page");
        c.setVisible(true);
        c.setSize(600,600);
        c.setComponents();
    }
    class LoanActionListner implements ActionListener{
        public void actionPerformed(ActionEvent a){
            boolean b=checkInput();
            if(t7.getText().length()==0){
                ll8.setText("Cannot be Empty");
            }
            else{
                try{
                    Double.parseDouble(t7.getText());
                    if(Double.parseDouble(t7.getText())<0){
                        ll8.setText("Cannot be Negative");
                    }
                    else{
                        if(b){
                            if(OperationClass.loanMobile(Long.parseLong(t4.getText()))==false){
                                if(OperationClass.loanPancard(t3.getText())==false){
                                    ll8.setText("");
                                    Account acc=new LoanAccount(t1.getText(),t2.getText(),t3.getText(),Long.parseLong(t4.getText()),t5.getText(),Double.parseDouble(t7.getText()));
                                    OperationClass.addAccount(acc);
                                    JOptionPane.showMessageDialog(null,"Account Opened SuccessFully\nwhen Approved by Admin\nYour Account Number="+acc.getAccNum());
                                    new LoginButton().actionPerformed(null);
                                    OperationClass.display();
                                }else{
                                    JOptionPane.showMessageDialog(null,"Pancard Already Exist");
                                }
                            }else{
                                JOptionPane.showMessageDialog(null,"Mobile Number Already Exist");
                            }

                        }
                    }
                }catch (NumberFormatException a2){
                    ll8.setText("Enter Digit");
                }
            }
        }

    }
}
class CurrentAccountPage extends OpenAccount{
    CurrentAccountPage(){

    }
    CurrentAccountPage(String s1){
        super(s1);
    }
    void setComponents(){
        super.setComponents();
        l9=new JLabel("OverDraft Limit You want=");
        l9.setBounds(100,400,250,25);
        ll8=new JLabel();
        ll8.setBounds(450,400,200,25);
        add(l9);
        add(ll8);
        textFieldGenerator();
        b1.addActionListener(new CurrentActionListner());
        super.textFieldGenerator();
    }
    void textFieldGenerator(){
        t7=new JTextField();
        t7.setBounds(350,400,100,25);
        add(t7);
    }
    static void displayThisPage(){
        CurrentAccountPage c=new CurrentAccountPage("Current Account Page");
        c.setVisible(true);
        c.setSize(600,600);
        c.setComponents();
    }
    class CurrentActionListner implements ActionListener{
        public void actionPerformed(ActionEvent a){
            boolean b=checkInput();
            double overDraft;
            try{
                if(t7.getText().length()==0 && b==true){
                    if(OperationClass.currentMobile(Long.parseLong(t4.getText()))==false){
                        if(OperationClass.currentPancard(t3.getText())==false){
                            Account acc=new CurrentAccount(t1.getText(),t2.getText(),t3.getText(),Long.parseLong(t4.getText()),t5.getText(),0);
                            OperationClass.addAccount(acc);
                            JOptionPane.showMessageDialog(null,"Account Opened SuccessFully\nYour Account Number="+acc.getAccNum());
                            new LoginButton().actionPerformed(null);
                            OperationClass.display();
                        }else{
                            JOptionPane.showMessageDialog(null,"Pancard Already Exist");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Mobile Number Already Exist");
                    }
                }
                else{
                    overDraft=Double.parseDouble(t7.getText());
                    if(overDraft<0){
                        ll8.setText("Enter Positive Number");
                    }else{
                        ll8.setText("");
                        if(b==true){
                            if(OperationClass.currentMobile(Long.parseLong(t4.getText()))==false){
                                if(OperationClass.currentPancard(t3.getText())==false){
                                    Account acc=new CurrentAccount(t1.getText(),t2.getText(),t3.getText(),Long.parseLong(t4.getText()),t5.getText(),overDraft);
                                    OperationClass.addAccount(acc);
                                    JOptionPane.showMessageDialog(null,"Account Opened SuccessFully\nYour Account Number="+acc.getAccNum());
                                    new LoginButton().actionPerformed(null);
                                    OperationClass.display();
                                }else{
                                    JOptionPane.showMessageDialog(null,"Pancard Already Exist");
                                }
                            }else{
                                JOptionPane.showMessageDialog(null,"Mobile Number Already Exist");
                            }
                        }
                    }
                }
            }catch (NumberFormatException a2){
                ll8.setText("Enter Digits");
            }
        }
    }
}
class CurrentAccount extends Account{
    static double currentInterest=2.5;
    double overDraft;
    boolean approvedOrNot;
    CurrentAccount(String firstName,String lastName,String panCard,long mobile,String pass,double overDraft){
        super(firstName,lastName,panCard,mobile,pass,null);
        this.overDraft=overDraft;
        approvedOrNot=false;
    }
    double calInterest(){
        double d=0;
        if(b!=null){
            for(int i=0;i<b.length-1;i++){
                if(b[i].balance<0){
                    long days= ChronoUnit.DAYS.between(b[i].date,b[i+1].date);
                    d+=((b[i].balance*currentInterest/365)/100)*days;
                }
            }
            if(b[b.length-1].balance<0){
                long days=ChronoUnit.DAYS.between(b[b.length-1].date,LocalDate.now());
                if(days==0){
                    d+=((b[b.length-1].balance*currentInterest/365)/100);
                }
                else{
                    if(days<0){
                        days=days*(-1);
                        d+=((b[b.length-1].balance*currentInterest/365)/100)*days;
                    }
                    else
                        d+=((b[b.length-1].balance*currentInterest/365)/100)*days;
                }
            }
        }
        if(d==0)
            return 0;
        return d*(-1);
    }
}
class SavingAccountPage extends OpenAccount{

    SavingAccountPage(){

    }
    SavingAccountPage(String s1){
        super(s1);
    }
    void setComponents(){
        super.setComponents();
        l9=new JLabel("Deposite Minimum Rs "+Saving.minBalance+":");
        l9.setBounds(100,400,250,25);
        ll8=new JLabel();
        ll8.setBounds(450,400,200,25);
        add(l9);
        add(ll8);
        textFieldGenerator();
        b1.addActionListener(new OpenSavingAcc());
        super.textFieldGenerator();
    }
    void textFieldGenerator(){
        t7=new JTextField();
        t7.setBounds(350,400,100,25);
        add(t7);
    }
    class OpenSavingAcc implements ActionListener{

        public void actionPerformed(ActionEvent a){
            boolean b=checkInput();
            try{
                Double.parseDouble(t7.getText());
                if(Double.parseDouble(t7.getText())<10000){
                    ll8.setText("More than Rs10000");
                }else{
                    ll8.setText("");
                    if(b==true){
                        if(OperationClass.savingMobile(Long.parseLong(t4.getText()))==false){
                            if(OperationClass.savingPancard(t3.getText())==false){
                                Account acc=new Saving(t1.getText(),t2.getText(),t3.getText(),Long.parseLong(t4.getText()),t5.getText(),new Transaction(Double.parseDouble(t7.getText()),"Deposite",LocalDate.now()));
                                OperationClass.addAccount(acc);
                                JOptionPane.showMessageDialog(null,"Account Opened SuccessFully\nYour Account Number="+acc.getAccNum());
                                new LoginButton().actionPerformed(null);
                                OperationClass.display();
                            }
                            else{
                                JOptionPane.showMessageDialog(null,"Pancard number Already Exist");
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Mobile number Already Exist");
                        }
                    }
                }
            }catch (NumberFormatException a2){
                ll8.setText("Enter Digits");
            }
        }
    }
}

class SalaryAccountPage extends OpenAccount{
    JLabel l9,l10,ll8,ll9;
    JTextField t8,t7;

    SalaryAccountPage(){

    }
    SalaryAccountPage(String s1){
        super(s1);
    }
    void setComponents(){
        super.setComponents();
        ll9=new JLabel();
        l9=new JLabel("Deposite Minimum Rs "+Saving.minBalance+":");
        l9.setBounds(100,400,250,25);
        ll8=new JLabel();
        ll8.setBounds(450,400,200,25);
        add(l9);
        add(ll8);
        l10=new JLabel("Enter the Date of Transaction(YYYY/MM/dd):");
        l10.setBounds(25,450,300,25);
        ll9.setBounds(450,450,150,25);
        add(l10);
        add(ll9);
        b1.addActionListener(new OpenSalaryAcc());
        super.textFieldGenerator();
        textFieldGenerator();
    }
    void textFieldGenerator(){
        t7=new JTextField();
        t7.setBounds(350,400,100,25);
        add(t7);
        t8=new JTextField();
        t8.setBounds(350,450,100,25);
        add(t8);
    }
    static LocalDate checkDate(String tDate){
        String dateString = tDate; // Example date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate d=null;
        try {
            d=LocalDate.parse(dateString, formatter);
            int year=Integer.parseInt(String.valueOf(tDate.charAt(0))+String.valueOf(tDate.charAt(1))+String.valueOf(tDate.charAt(2))+String.valueOf(tDate.charAt(3)));
            int month=Integer.parseInt(String.valueOf(tDate.charAt(5))+String.valueOf(tDate.charAt(6)));
            int date=Integer.parseInt(String.valueOf(tDate.charAt(8))+String.valueOf(tDate.charAt(9)));
            System.out.println(year+" "+month+" "+date);
            if(month==2){
                if(year%100==0){
                    if(year%400==0){
                        if(date>29){
                            return null;
                        }
                        else{
                            return d;
                        }
                    }
                    else{
                        if(date>28){
                            return null;
                        }
                        else{
                            return d;
                        }
                    }
                }
                else{
                    if(year%4==0){
                        if(date>29){
                            return null;
                        }
                        else{
                            return d;
                        }
                    }
                    else{
                        if(date>28){
                            return null;
                        }
                        else{
                            return d;
                        }
                    }
                }
            }else if(month==4||month==6||month==9||month==11){
                if(date>30){
                    return null;
                }
            }
        } catch (DateTimeParseException e) {
            return null;
        }
        return d;
    }
    boolean checkInput(){
        boolean b=super.checkInput();
        int a=2;
        try{
            Double.parseDouble(t7.getText());
            if(Double.parseDouble(t7.getText())<10000){
                ll8.setText("More than Rs10000");
            }else{
                a--;
                ll8.setText("");
            }
        }catch (NumberFormatException a2){
            ll8.setText("Enter Digits");
        }
        if(t8.getText().length()==0){
            ll9.setText("Cannot be Empty");
        }
        else{
            if(SalaryAccountPage.checkDate(t8.getText())!=null){
                a--;
                ll9.setText("");
            }
            else{
                ll9.setText("Enter valid Date");
            }
        }
        if(a==0){
            return b && true;
        }
        return false;
    }
    class OpenSalaryAcc implements ActionListener{
        public void actionPerformed(ActionEvent a){
            if(checkInput()==true) {
                if(OperationClass.salaryMobile(Long.parseLong(t4.getText()))==false){
                    if(OperationClass.salaryPancard(t3.getText())==false){
                        Account acc = new Salary(t1.getText(), t2.getText(), t3.getText(), Long.parseLong(t4.getText()), t5.getText(), new Transaction(Double.parseDouble(t7.getText()), "Deposite", checkDate(t8.getText())));
                        JOptionPane.showMessageDialog(null, "Account Opened SuccessFully\n Your Account Number:"+acc.getAccNum());
                        OperationClass.addAccount(acc);
                        OperationClass.display();
                        new LoginButton().actionPerformed(a);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Account with this pancard Already Exist");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Mobile Number Already Exist");
                }
            }
        }
    }
}
class SalaryLoginPage extends JFrame{
    JButton b1,b2,b3,b4,b5,b6;

    SalaryLoginPage(){

    }
    SalaryLoginPage(String s1){
        super(s1);
    }
    void setComponents(){
        new TradeMark().setTradeMark(this);
        new DisplayAccNum().setComponents(this);
        b1=new JButton("Deposit");
        b2=new JButton("Withdraw");
        b3=new JButton("Balance");
        b4=new JButton("Mini Statement");
        b5=new JButton("back");
        b6=new JButton("Interest");

        setLayout(null);
        b1.setBounds(200,250,100,50);
        b2.setBounds(200,300,100,50);
        b3.setBounds(200,350,100,50);
        b4.setBounds(200,400,150,50);
        b6.setBounds(200,450,150,50);
        b5.setBounds(450,500,100,25);
        b1.addActionListener(new DepoiteButtonOne());
        b2.addActionListener(new SalaryWithDrawButton());
        b5.addActionListener(new LoginButton());
        b3.addActionListener(new CheckBalance(this));
        b4.addActionListener(new MiniStatementbutton(this));
        b6.addActionListener(new CalInterest());
        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(b5);
        add(b6);
    }
    static void displaythisPage(){
        SalaryLoginPage s=new SalaryLoginPage("Salary Account Page");
        s.setVisible(true);
        s.setSize(600,600);
        s.setComponents();
    }
    class DepoiteButtonOne implements ActionListener{
        public void actionPerformed(ActionEvent a){
            Salary s=(Salary)OperationClass.acc;
            if(s.freeze==true){
                JOptionPane.showMessageDialog(null,"Your Account is Freezed\nDue to No Transaction in 60 days");
            }
            else
                SalaryDepositePage.CreatePage();
        }
    }
    class SalaryWithDrawButton implements ActionListener{
        public void actionPerformed(ActionEvent a){
            SalaryWithDrawPage.CreatePage();
        }
    }
}
class SalaryDepositePage extends JFrame{
    SalaryDepositePage(){

    }
    SalaryDepositePage(String s1){
        super(s1);
    }
    JLabel l1,l2,ll1,ll2;
    JTextField t1,t2;
    JButton b1,b2;

    void setComponents(){
        new TradeMark().setTradeMark(this);
        new DisplayAccNum().setComponents(this);
        ll2=new JLabel();
        l1=new JLabel("Enter the amount you want to deposit:");
        l2=new JLabel("Enter the Date of Transaction (YYYY/MM/yy):");
        ll1=new JLabel();
        b1=new JButton("Deposit");
        b2=new JButton("Back");
        l1.setBounds(25,300,250,25);
        l2.setBounds(25,350,300,25);
        b1.setBounds(400,400,100,25);
        ll2.setBounds(450,350,100,25);
        b2.setBounds(450,500,100,25);
        ll1.setBounds(400,300,125,25);
        b1.addActionListener(new DepositeButton());
        b2.addActionListener(new SalaryBack());
        setLayout(null);
        add(l1);
        add(l2);
        add(b1);
        add(ll2);
        add(b2);
        add(ll1);
        textFieldGenerator();
    }
    void textFieldGenerator(){
        t1=new JTextField();
        t2=new JTextField();
        t1.setBounds(300,300,100,25);
        t2.setBounds(350,350,100,25);
        add(t1);
        add(t2);
    }
    static void CreatePage(){
        SalaryDepositePage p=new SalaryDepositePage("Salary Deposit Page");
        p.setVisible(true);
        p.setSize(600,600);
        p.setComponents();
    }
    boolean checkValidation(){
        if(t1.getText().length()==0){
            ll1.setText("Cannot be Empty");
        }
        else{
            try{
                Double.parseDouble(t1.getText());
                if(Double.parseDouble(t1.getText())<=0){
                    ll1.setText("Cannot be Negative");
                }
                else
                    return true;
            }catch (NumberFormatException a){
                ll1.setText("Only Digits");
            }
        }
        return false;
    }
    LocalDate checkDate(){
        LocalDate d=SalaryAccountPage.checkDate(t2.getText());
        if(d==null){
            ll2.setText("Enter valid text");
        }
        return d;
    }
    class DepositeButton implements ActionListener{
        public void actionPerformed(ActionEvent a){
            LocalDate d=checkDate();
            if(checkValidation()==true && d!=null){
                System.out.println(OperationClass.acc.getLastTransaction().getDate());
                System.out.println("Date="+OperationClass.acc.getLastTransaction().getDate());
                if(d.isAfter(OperationClass.acc.getLastTransaction().getDate().plusDays(60))){
                    Salary s=(Salary)OperationClass.acc;
                    s.freeze=true;
                    JOptionPane.showMessageDialog(null,"Your Account is Freezed");
                    new SalaryBack().actionPerformed(a);
                }
                else{
                    if(d.equals(OperationClass.acc.a[OperationClass.acc.a.length-1].date) || d.isAfter(OperationClass.acc.a[OperationClass.acc.a.length-1].date)){
                        OperationClass.acc.balance+=Double.parseDouble(t1.getText());
                        OperationClass.acc.setTransaction(new Transaction(Double.parseDouble(t1.getText()),"Deposit",d));
                        JOptionPane.showMessageDialog(null,"Transaction SuccessFull");
                        t1.setText("");
                        t2.setText("");
                        ll1.setText("");
                        ll2.setText("");
                    }else{
                        JOptionPane.showMessageDialog(null,"Enter "+OperationClass.acc.a[OperationClass.acc.a.length-1].date+" or After");
                    }
                }
            }
        }
    }
}
class SalaryWithDrawPage extends JFrame{
    SalaryWithDrawPage(){

    }
    SalaryWithDrawPage(String s1){
        super(s1);
    }
    JLabel l1,l2,ll1,ll2;
    JTextField t1,t2;
    JButton b1,b2;

    void setComponents(){
        new TradeMark().setTradeMark(this);
        new DisplayAccNum().setComponents(this);
        ll2=new JLabel();
        l1=new JLabel("Enter the amount you want to withdraw:");
        l2=new JLabel("Enter the Date of Transaction (YYYY/MM/yy):");
        ll1=new JLabel();
        b1=new JButton("Withdraw");
        b2=new JButton("Back");
        l1.setBounds(25,300,250,25);
        l2.setBounds(25,350,300,25);
        b1.setBounds(400,400,100,25);
        ll2.setBounds(450,350,100,25);
        b2.setBounds(450,500,100,25);
        ll1.setBounds(400,300,125,25);
        b1.addActionListener(new SalaryWithDrawButton());
        b2.addActionListener(new SalaryBack());
        setLayout(null);
        add(l1);
        add(l2);
        add(b1);
        add(ll2);
        add(b2);
        add(ll1);
        textFieldGenerator();
    }
    void textFieldGenerator(){
        t1=new JTextField();
        t2=new JTextField();
        t1.setBounds(300,300,100,25);
        t2.setBounds(350,350,100,25);
        add(t1);
        add(t2);
    }
    static void CreatePage(){
        SalaryWithDrawPage p=new SalaryWithDrawPage("Salary Withdraw Page");
        p.setVisible(true);
        p.setSize(600,600);
        p.setComponents();
    }
    boolean checkValidation(){
        if(t1.getText().length()==0){
            ll1.setText("Cannot be Empty");
        }
        else{
            try{
                Double.parseDouble(t1.getText());
                if(Double.parseDouble(t1.getText())<=0){
                    ll1.setText("Cannot be Negative");
                }
                else
                    return true;
            }catch (NumberFormatException a){
                ll1.setText("Only Digits");
            }
        }
        return false;
    }
    LocalDate checkDate(){
        LocalDate d=SalaryAccountPage.checkDate(t2.getText());
        if(d==null){
            ll2.setText("Enter valid text");
        }
        return d;
    }
    class SalaryWithDrawButton implements ActionListener{
        public void actionPerformed(ActionEvent a){
            LocalDate d=checkDate();
            if(checkValidation()==true && d!=null){
                if(Double.parseDouble(t1.getText())<OperationClass.acc.getBalance()){
                    if((OperationClass.acc.getBalance()-Double.parseDouble(t1.getText()))>=Saving.getMinBalance()){
                        if(d.equals(OperationClass.acc.a[OperationClass.acc.a.length-1].date) || d.isAfter(OperationClass.acc.a[OperationClass.acc.a.length-1].date)){
                            OperationClass.acc.setBalance(OperationClass.acc.getBalance()-Double.parseDouble(t1.getText()));
                            OperationClass.acc.setTransaction(new Transaction(Double.parseDouble(t1.getText()),"Withdraw",d));
                            t1.setText("");
                            ll1.setText("");
                            JOptionPane.showMessageDialog(null,"Withdraw Successfully\nRemaining Balance: "+OperationClass.acc.getBalance());
                        }else{
                            JOptionPane.showMessageDialog(null,"Enter "+OperationClass.acc.a[OperationClass.acc.a.length-1].date+" or After");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Minimum Rs"+Saving.getMinBalance()+" Must be there in Account");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Insufficient Balance");
                }
            }
        }
    }
}
class SalaryBack implements ActionListener{
    public void actionPerformed(ActionEvent a){
        SalaryLoginPage.displaythisPage();
    }
}
abstract class Account implements Serializable{
    static long num;
    static int chances=2;
    String firstName;
    String lastName;
    String panCard;
    String pass;
    long mobile;
    Transaction[] a;
    LastbalanceForInterest[] b;
    double balance;
    long accNum;
    int fail;
    Account(String firstName,String lastName,String panCard,long mobile,String pass,Transaction t){
        this.firstName=firstName;
        this.lastName=lastName;
        this.panCard=panCard;
        this.pass=pass;
        this.mobile=mobile;
        if (t==null)
            this.balance=0;
        else
            this.balance=t.amount;
        if(t!=null){
            a=new Transaction[1];
            a[0]=t;
            b=new LastbalanceForInterest[1];
            b[0]=new LastbalanceForInterest();
            b[0].balance=this.balance;
            b[0].date=a[a.length-1].date;
        }
        if(OperationClass.accounts==null){
            num=123456;
        }else{
            num=OperationClass.accounts[OperationClass.accounts.length-1].accNum+100;
        }
        accNum=num;
        num+=100;
        fail=chances;
    }
    void addLastbalanceForInterest(){
        if(this.b==null){
            b=new LastbalanceForInterest[1];
            b[0]=new LastbalanceForInterest();
            b[0].balance=this.balance;
            b[0].date=a[a.length-1].date;
        }
        else{
            if(a[a.length-1].date.isAfter(b[b.length-1].date)){
                LastbalanceForInterest[] b1=new LastbalanceForInterest[b.length+1];
                for(int i=0;i<b.length;i++){
                    b1[i]=b[i];
                }
                b1[b.length]=new LastbalanceForInterest();
                b1[b.length].balance=this.balance;
                b1[b.length].date=a[a.length-1].date;
                b=b1;
            }
            else{
                b[b.length-1].balance=this.balance;
            }
        }
    }
    String getPass(){
        return this.pass;
    }
    void setFail(int fail){
        this.fail=fail;
    }
    Transaction[] getTransaction(){
        return a;
    }
    int getFail(){
        return fail;
    }
    void setBalance(double balance){
        this.balance=balance;
    }
    double getBalance(){
        return this.balance;
    }
    static int getChances(){
        return chances;
    }
    void setTransaction(Transaction t){
        if (a==null){
            a=new Transaction[1];
            a[0]=t;
            addLastbalanceForInterest();
            return;
        }
        Transaction[] a1=new Transaction[a.length+1];
        for(int i=0;i<a.length;i++){
            a1[i]=a[i];
        }
        a1[a.length]=t;
        a=a1;
        addLastbalanceForInterest();
    }
    void display(){
        System.out.println(firstName);
        System.out.println(lastName);
        System.out.println(panCard);
        System.out.println(mobile);
        if (a!=null){
            for (int i=0;i<a.length;i++){
                System.out.println(a[i]);
            }
        }
    }
    long getAccNum(){
        return this.accNum;
    }
    Transaction getLastTransaction(){
        if(a==null){
            return null;
        }
        return a[a.length-1];
    }
    abstract double calInterest();
}
class Saving extends Account{
    static double minBalance=10000;
    static double interest=4.5;
    Saving(String firstName,String lastName,String panCard,long mobile,String pass,Transaction t){
        super(firstName,lastName,panCard,mobile,pass,t);
    }
    static double getMinBalance(){
        return minBalance;
    }
    double calInterest(){
        double d=0;
        for(int i=0;i<b.length-1;i++){
            long days= ChronoUnit.DAYS.between(b[i].date,b[i+1].date);
            d+=((b[i].balance*interest/365)/100)*days;
        }
        long days=ChronoUnit.DAYS.between(b[b.length-1].date,LocalDate.now());
        if(days==0){
            d+=((b[b.length-1].balance*interest/365)/100);
        }
        else{
            if(days<0){
                days=days*(-1);
                d+=((b[b.length-1].balance*interest/365)/100)*days;
            }
            else
                d+=((b[b.length-1].balance*interest/365)/100)*days;
        }
        return d;
    }
}
class Salary extends Saving{
    boolean freeze;
    Salary(String firstName,String lastName,String panCard,long mobile,String pass,Transaction t){
        super(firstName,lastName,panCard,mobile,pass,t);
        freeze=false;
    }
}
class LoanAccount extends Account{
    static double loanInterest=12.5;
    boolean approveOrNot;
    double loanAmount;
    LoanAccount(String firstName,String lastName,String panCard,long mobile,String pass,double loanAmount){
        super(firstName,lastName,panCard,mobile,pass,null);
        this.loanAmount=loanAmount;
        approveOrNot=false;
    }
    double calInterest(){
        double d=0;
        for(int i=0;i<b.length-1;i++){
            long days= ChronoUnit.DAYS.between(b[i].date,b[i+1].date);
            d+=((b[i].balance*loanInterest/365)/100)*days;
        }
        long days=ChronoUnit.DAYS.between(b[b.length-1].date,LocalDate.now());
        if(days==0){
            d+=((b[b.length-1].balance*loanInterest/365)/100);
        }
        else{
            if(days<0){
                days=days*(-1);
                d+=((b[b.length-1].balance*loanInterest/365)/100)*days;
            }
            else
                d+=((b[b.length-1].balance*loanInterest/365)/100)*days;
        }
        return d*(-1);
    }
}
class Transaction implements Serializable{
    double amount;
    String type;
    LocalDate date;
    Transaction(double amount,String type,LocalDate date){
        this.amount=amount;
        this.type=type;
        this.date=date;
    }
    public String toString(){
        return amount+" "+type+" "+date;
    }
    LocalDate getDate(){
        return date;
    }
}
class LastbalanceForInterest implements Serializable{
    double balance;
    LocalDate date;
    LastbalanceForInterest(){

    }
    LastbalanceForInterest(double balance,LocalDate date){
        this.balance=balance;
        this.date=date;
    }
}
class OperationClass{
    static Account[] accounts;
    static Account acc;
    static void addAccount(Account a){
        if(accounts==null){
            accounts=new Account[1];
            accounts[0]=a;
        }
        else{
            Account[] a1=new Account[accounts.length+1];
            for(int i=0;i<accounts.length;i++){
                a1[i]=accounts[i];
            }
            a1[accounts.length]=a;
            accounts=a1;
        }
    }
    static void display(){
        for(int i=0;i<accounts.length;i++){
            accounts[i].display();
        }
    }
    static void giveAccount(String accNum,String pass){
        if(accounts==null){
            JOptionPane.showMessageDialog(null,"No Accounts Added");
            return;
        }
        boolean found=false;
        for(int i=0;i<accounts.length;i++){
            if(accounts[i].getAccNum()==Long.parseLong(accNum)){
                found=true;
                if(accounts[i].getPass().equals(pass)){
                    if(accounts[i].getFail()==0){
                        JOptionPane.showMessageDialog(null,"Your Account is Freeze for 24Hrs\nDue to Previous Fail Login Attempts");
                    }
                    else{
                        accounts[i].setFail(accounts[i].getChances());
                        OperationClass.acc=accounts[i];
                        if(accounts[i] instanceof Saving){
                            Saving s=(Saving)accounts[i];
                            if(s instanceof Salary){
                                loginSuccessFullMessage();
                                SalaryLoginPage.displaythisPage();
                            }
                            else{
                                loginSuccessFullMessage();
                                SavingLogin.displaythisPage();
                            }
                        }
                        if(accounts[i] instanceof CurrentAccount){
                            loginSuccessFullMessage();
                            CurrentLogin.displaythisPage();
                        }
                        if(accounts[i] instanceof LoanAccount){
                            LoanAccount l=(LoanAccount) accounts[i];
                            if(l.approveOrNot==false){
                                JOptionPane.showMessageDialog(null,"Your Loan has Not Been Approved yet");
                            }
                            else{
                                loginSuccessFullMessage();
                                LoanLoginPage.displaythisPage();
                            }
                        }
                    }
                }
                else{
                    if (accounts[i].getFail()==0){
                        JOptionPane.showMessageDialog(null,"Your Account is Freezed for 24hrs");
                        return;
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"You Have Left With "+(accounts[i].getFail())+" Chances\n After that Account  Will Freeze for 24 hrs");
                        accounts[i].setFail(accounts[i].getFail()-1);
                    }

                }
            }
        }
        if(found==false){
            JOptionPane.showMessageDialog(null,"Enter Valid Account Number");
        }
    }
    static void loginSuccessFullMessage(){
        JOptionPane.showMessageDialog(null,"Login Successfull");
    }
    static String[] getMiniStatement(){
        if (acc.getLastTransaction()==null){
            return null;
        }
        String[] s=new String[acc.getTransaction().length];
        for(int i=0;i<acc.getTransaction().length;i++){
            s[i]=acc.getTransaction()[i].toString();
        }
        return s;
    }
    static CurrentAccount[] getCurrentAccount(){
        CurrentAccount c[]=null;
        int size=0;
        if(accounts==null){
            return null;
        }
        for(int i=0;i<accounts.length;i++){
            if(accounts[i] instanceof CurrentAccount){
                CurrentAccount c1=(CurrentAccount)accounts[i];
                if(c1.overDraft>0 && c1.approvedOrNot==false){
                    size++;
                }
            }
        }
        if(size==0)
            return null;
        c=new CurrentAccount[size];
        int j=0;
        for(int i=0;i<accounts.length;i++){
            if(accounts[i] instanceof CurrentAccount){
                CurrentAccount c1=(CurrentAccount)accounts[i];
                if(c1.overDraft>0 && c1.approvedOrNot==false){
                    c[j++]=c1;
                }
            }
        }
        return c;
    }
    static LoanAccount[] getLoanAccount(){
        LoanAccount c[]=null;
        int size=0;
        if(accounts==null){
            return null;
        }
        for(int i=0;i<accounts.length;i++){
            if(accounts[i] instanceof LoanAccount){
                LoanAccount c1=(LoanAccount)accounts[i];
                if(c1.approveOrNot==false){
                    size++;
                }
            }
        }
        if(size==0)
            return null;
        c=new LoanAccount[size];
        int j=0;
        for(int i=0;i<accounts.length;i++){
            if(accounts[i] instanceof LoanAccount){
                LoanAccount c1=(LoanAccount)accounts[i];
                if(c1.approveOrNot==false){
                    c[j++]=c1;
                }
            }
        }
        return c;
    }
    static boolean savingMobile(long mobile){
        if(OperationClass.accounts!=null){
            for(int i=0;i<OperationClass.accounts.length;i++){
                if(OperationClass.accounts[i] instanceof Saving){
                    Saving s=(Saving) OperationClass.accounts[i];
                    if((s instanceof Salary)==false){
                        if(OperationClass.accounts[i].mobile==mobile){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    static boolean savingPancard(String pancard){
        if(OperationClass.accounts!=null){
            for(int i=0;i<OperationClass.accounts.length;i++){
                if(OperationClass.accounts[i] instanceof Saving){
                    Saving s=(Saving) OperationClass.accounts[i];
                    if((s instanceof Salary)==false){
                        if(OperationClass.accounts[i].panCard.equals(pancard)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    static boolean salaryPancard(String pancard){
        if(OperationClass.accounts!=null){
            for(int i=0;i<OperationClass.accounts.length;i++){
                if(OperationClass.accounts[i] instanceof Saving){
                    Saving s=(Saving) OperationClass.accounts[i];
                    if((s instanceof Salary)){
                        if(OperationClass.accounts[i].panCard.equals(pancard)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    static boolean salaryMobile(long mobile){
        if(OperationClass.accounts!=null){
            for(int i=0;i<OperationClass.accounts.length;i++){
                if(OperationClass.accounts[i] instanceof Saving){
                    Saving s=(Saving) OperationClass.accounts[i];
                    if((s instanceof Salary)){
                        if(OperationClass.accounts[i].mobile==mobile){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    static boolean currentMobile(long mobile){
        if(OperationClass.accounts!=null){
            for(int i=0;i<OperationClass.accounts.length;i++){
                if(OperationClass.accounts[i] instanceof CurrentAccount){
                    if(OperationClass.accounts[i].mobile==mobile){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    static boolean currentPancard(String pancard){
        if(OperationClass.accounts!=null){
            for(int i=0;i<OperationClass.accounts.length;i++){
                if(OperationClass.accounts[i] instanceof CurrentAccount){
                    if(OperationClass.accounts[i].panCard.equals(pancard)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    static boolean loanPancard(String pancard){
        if(OperationClass.accounts!=null){
            for(int i=0;i<OperationClass.accounts.length;i++){
                if(OperationClass.accounts[i] instanceof LoanAccount){
                    if(OperationClass.accounts[i].panCard.equals(pancard)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    static boolean loanMobile(long mobile){
        if(OperationClass.accounts!=null){
            for(int i=0;i<OperationClass.accounts.length;i++){
                if(OperationClass.accounts[i] instanceof LoanAccount){
                    if(OperationClass.accounts[i].mobile==mobile){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    static void fileReadOperations() throws IOException,ClassNotFoundException{
        ObjectInputStream in=new ObjectInputStream(new FileInputStream("BankingData1.dat"));
        while(true){
            try{
                Account a=(Account) in.readObject();
                OperationClass.addAccount(a);
            }catch (EOFException a){
                break;
            }
        }
        if(OperationClass.accounts!=null)
            System.out.print(OperationClass.accounts.length);
        in.close();
    }
    static void fileWriteOperation()throws IOException,ClassNotFoundException{
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("BankingData1.dat"));
        if(accounts!=null){
            System.out.print(OperationClass.accounts.length);
            for(int i=0;i<OperationClass.accounts.length;i++){
                out.writeObject(OperationClass.accounts[i]);
            }
        }
        out.close();
    }

}
class CurrentLogin extends JFrame{
    JButton b1,b2,b3,b4,b5,b6;

    CurrentLogin(){

    }
    CurrentLogin(String s1){
        super(s1);
    }
    void setComponents(){
        new TradeMark().setTradeMark(this);
        new DisplayAccNum().setComponents(this);
        b1=new JButton("Deposit");
        b2=new JButton("Withdraw");
        b3=new JButton("Balance");
        b4=new JButton("Mini Statement");
        b5=new JButton("back");
        b6=new JButton("Interest");

        setLayout(null);
        b1.setBounds(200,250,100,50);
        b2.setBounds(200,300,100,50);
        b3.setBounds(200,350,100,50);
        b4.setBounds(200,400,150,50);
        b6.setBounds(200,450,150,50);
        b5.setBounds(450,500,100,25);
        b1.addActionListener(new CurrentAccountDepositButton());
        b2.addActionListener(new CurrentAccountWithDrawButton());
        b5.addActionListener(new LoginButton());
        b3.addActionListener(new CheckBalance(this));
        b4.addActionListener(new MiniStatementbutton(this));
        b6.addActionListener(new CalInterest());
        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(b5);
        add(b6);
    }
    static void displaythisPage(){
        CurrentLogin s=new CurrentLogin("Current Account Page");
        s.setVisible(true);
        s.setSize(600,600);
        s.setComponents();
    }
    class CurrentAccountWithDrawButton implements ActionListener{
        public void actionPerformed(ActionEvent a){
            CurrentWithDrawPage.displayThisPage();
        }
    }
    class CurrentAccountDepositButton implements ActionListener{
        public void actionPerformed(ActionEvent a){
            CurrentDepositPage.CreatePage();
        }
    }
}
class LoanLoginPage extends JFrame{
    JButton b1,b3,b4,b5,b2;

    LoanLoginPage(){

    }
    LoanLoginPage(String s1){
        super(s1);
    }
    void setComponents(){
        new TradeMark().setTradeMark(this);
        new DisplayAccNum().setComponents(this);
        b1=new JButton("Deposit");
        b3=new JButton("Balance");
        b4=new JButton("Mini Statement");
        b2=new JButton("Interest");
        b5=new JButton("back");

        setLayout(null);
        b1.setBounds(200,250,100,50);
        b3.setBounds(200,300,100,50);
        b4.setBounds(200,350,150,50);
        b2.setBounds(200,400,150,50);
        b5.setBounds(450,400,100,25);
        b1.addActionListener(new LoanDepositButton());
        b5.addActionListener(new LoginButton());
        b3.addActionListener(new CheckBalance(this));
        b4.addActionListener(new MiniStatementbutton(this));
        b2.addActionListener(new CalInterest());
        add(b1);
        add(b3);
        add(b4);
        add(b5);
        add(b2);
    }
    static void displaythisPage(){
        LoanLoginPage s=new LoanLoginPage("Loan Account Page");
        s.setVisible(true);
        s.setSize(600,600);
        s.setComponents();
    }
    class LoanDepositButton implements ActionListener{
        public void actionPerformed(ActionEvent a){
            LoanDepositPage.CreatePage();
        }
    }
}
class LoanDepositPage extends JFrame{
    JLabel l1,ll1;
    JTextField t1;
    JButton b1,b2;
    LoanDepositPage(String s1){
        super(s1);
    }
    void setComponents(){
        new TradeMark().setTradeMark(this);
        new DisplayAccNum().setComponents(this);
        l1=new JLabel("Enter the amount you want to deposit:");
        ll1=new JLabel();
        b1=new JButton("Deposit");
        b2=new JButton("Back");
        l1.setBounds(25,300,250,25);
        b1.setBounds(400,350,100,25);
        b2.setBounds(450,500,100,25);
        ll1.setBounds(400,300,125,25);
        b1.addActionListener(new LoanDepositButton());
        b2.addActionListener(new LoginBackButton());
        setLayout(null);
        add(l1);
        add(b1);
        add(b2);
        add(ll1);
        textFieldGenerator();
    }
    void textFieldGenerator(){
        t1=new JTextField();
        t1.setBounds(300,300,100,25);
        add(t1);
    }
    static void CreatePage(){
        LoanDepositPage p=new LoanDepositPage("Loan Deposit Page");
        p.setVisible(true);
        p.setSize(600,600);
        p.setComponents();
    }
    boolean checkValidation(){
        if(t1.getText().length()==0){
            ll1.setText("Cannot be Empty");
        }
        else{
            try{
                Double.parseDouble(t1.getText());
                if(Double.parseDouble(t1.getText())<=0){
                    ll1.setText("Only Postive Numbers");
                    return false;
                }
                return true;
            }catch (NumberFormatException a){
                ll1.setText("Only Digits");
            }
        }
        return false;
    }
    class LoanDepositButton implements ActionListener{
        public void actionPerformed(ActionEvent a){
            boolean b=checkValidation();
            if(b){
                double balance=OperationClass.acc.getBalance()+Double.parseDouble(t1.getText());
                if(balance<=0){
                    OperationClass.acc.setBalance(OperationClass.acc.getBalance()+Double.parseDouble(t1.getText()));
                    OperationClass.acc.setTransaction(new Transaction(Double.parseDouble(t1.getText()),"Deposit",LocalDate.now()));
                    JOptionPane.showMessageDialog(null,"Amount Deposited SuccessFully\nUpdated Balance :"+OperationClass.acc.getBalance());
                    t1.setText("");
                    ll1.setText("");
                }
                else{
                    JOptionPane.showMessageDialog(null,"You Have to pay Only Rs"+((-1)*OperationClass.acc.balance));
                }
            }
        }
    }
}
class LoginBackButton implements ActionListener{
    public void actionPerformed(ActionEvent a){
        LoanLoginPage.displaythisPage();
    }
}
class SavingLogin extends JFrame{
    JButton b1,b2,b3,b4,b5,b6;

    SavingLogin(){

    }
    SavingLogin(String s1){
        super(s1);
    }
    void setComponents(){
        new TradeMark().setTradeMark(this);
        new DisplayAccNum().setComponents(this);
        b1=new JButton("Deposit");
        b2=new JButton("Withdraw");
        b3=new JButton("Balance");
        b4=new JButton("Mini Statement");
        b6=new JButton("Interest");
        b5=new JButton("back");

        setLayout(null);
        b1.setBounds(200,250,100,50);
        b2.setBounds(200,300,100,50);
        b3.setBounds(200,350,100,50);
        b4.setBounds(200,400,150,50);
        b6.setBounds(200,450,150,50);
        b5.setBounds(450,500,100,25);
        b1.addActionListener(new DepositeButton());
        b2.addActionListener(new WithdrawButton());
        b5.addActionListener(new LoginButton());
        b3.addActionListener(new CheckBalance(this));
        b4.addActionListener(new MiniStatementbutton(this));
        b6.addActionListener(new CalInterest());
        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(b5);
        add(b6);
    }
    static void displaythisPage(){
        SavingLogin s=new SavingLogin("Saving Account Page");
        s.setVisible(true);
        s.setSize(600,600);
        s.setComponents();
    }
}
class CalInterest implements ActionListener{
    public void actionPerformed(ActionEvent a){
        if(OperationClass.acc instanceof Saving){
            JOptionPane.showMessageDialog(null,"Rs"+OperationClass.acc.calInterest()+" Will be Paid by Bank");
        } else {
            JOptionPane.showMessageDialog(null,"Rs"+OperationClass.acc.calInterest()+" you have to pay to Bank");
        }
    }
}
class WithdrawButton implements ActionListener{
    public void actionPerformed(ActionEvent a){
        WithDrawPage.displayThisPage();
    }
}
class DepositeButton implements ActionListener{
    public void actionPerformed(ActionEvent a){
        DepositePage.CreatePage();
    }
}
class CheckBalance implements ActionListener {
    JFrame f;
    CheckBalance(JFrame f){
        this.f=f;
    }
    public void actionPerformed(ActionEvent a){
        ShowBalancePage.createThisPage(this.f);
    }
}
class CurrentDepositPage extends JFrame{
    JLabel l1,ll1;
    JTextField t1;
    JButton b1,b2;
    CurrentDepositPage(String s1){
        super(s1);
    }
    void setComponents(){
        new TradeMark().setTradeMark(this);
        new DisplayAccNum().setComponents(this);
        l1=new JLabel("Enter the amount you want to deposit:");
        ll1=new JLabel();
        b1=new JButton("Deposit");
        b2=new JButton("Back");
        l1.setBounds(25,300,250,25);
        b1.setBounds(400,350,100,25);
        b2.setBounds(450,500,100,25);
        ll1.setBounds(400,300,125,25);
        b1.addActionListener(new CurrentDepositButton());
        b2.addActionListener(new CurrentBackButton());
        setLayout(null);
        add(l1);
        add(b1);
        add(b2);
        add(ll1);
        textFieldGenerator();
    }
    void textFieldGenerator(){
        t1=new JTextField();
        t1.setBounds(300,300,100,25);
        add(t1);
    }
    static void CreatePage(){
        CurrentDepositPage p=new CurrentDepositPage("Current Deposit Page");
        p.setVisible(true);
        p.setSize(600,600);
        p.setComponents();
    }
    boolean checkValidation(){
        if(t1.getText().length()==0){
            ll1.setText("Cannot be Empty");
        }
        else{
            try{
                Double.parseDouble(t1.getText());
                if(Double.parseDouble(t1.getText())<=0){
                    ll1.setText("Only Postive Numbers");
                    return false;
                }
                return true;
            }catch (NumberFormatException a){
                ll1.setText("Only Digits");
            }
        }
        return false;
    }
    class CurrentDepositButton implements ActionListener{
        public void actionPerformed(ActionEvent a){
            if(checkValidation()){
                OperationClass.acc.balance+=Double.parseDouble(t1.getText());
                OperationClass.acc.setTransaction(new Transaction(Double.parseDouble(t1.getText()),"Deposit",LocalDate.now()));
                JOptionPane.showMessageDialog(null,"Transaction SuccessFull\nUpdated Balance:Rs"+OperationClass.acc.balance);
                ll1.setText("");
                t1.setText("");
            }
        }
    }
}
class CurrentWithDrawPage extends JFrame{
    JLabel l1,ll1;
    JTextField t1;
    JButton b1,b2;
    CurrentWithDrawPage(){

    }
    CurrentWithDrawPage(String s1){
        super(s1);
    }
    void setComponents(){
        new TradeMark().setTradeMark(this);
        new DisplayAccNum().setComponents(this);
        l1=new JLabel("Enter the amount you want to Withdraw:");
        ll1=new JLabel();
        b1=new JButton("Withdraw");
        b2=new JButton("Back");
        l1.setBounds(25,300,250,25);
        b1.setBounds(400,350,100,25);
        b2.setBounds(450,500,100,25);
        ll1.setBounds(400,300,125,25);
        b1.addActionListener(new CurrentWithdrawButton());
        b2.addActionListener(new CurrentBackButton());
        setLayout(null);
        add(l1);
        add(b1);
        add(b2);
        add(ll1);
        textFieldGenerator();
    }
    void textFieldGenerator(){
        t1=new JTextField();
        t1.setBounds(300,300,100,25);
        add(t1);
    }
    boolean checkValidation(){
        if(t1.getText().length()==0){
            ll1.setText("Cannot be Empty");
        }
        else{
            try{
                Double.parseDouble(t1.getText());
                if(Double.parseDouble(t1.getText())<=0){
                    ll1.setText("Only Postive Numbers");
                    return false;
                }else{
                    return true;
                }
            }catch (NumberFormatException a){
                ll1.setText("Only Digits");
            }
        }
        return false;
    }
    static void displayThisPage(){
        CurrentWithDrawPage w=new CurrentWithDrawPage("Current Withdraw Page");
        w.setVisible(true);
        w.setSize(600,600);
        w.setComponents();
    }
    void reset(){
        t1.setText("");
        ll1.setText("");
    }
    class CurrentWithdrawButton implements ActionListener{
        public void actionPerformed(ActionEvent a){
            if(checkValidation()){
                double balance=OperationClass.acc.getBalance();
                CurrentAccount c=(CurrentAccount) OperationClass.acc;
                double overDraft=c.overDraft;
                if(overDraft>0){
                    if(c.approvedOrNot){
                        if(Double.parseDouble(t1.getText())>(balance+overDraft)){
                            JOptionPane.showMessageDialog(null,"OverDraft Limit Exceeded");
                        }
                        else{
                            c.balance=c.balance-Double.parseDouble(t1.getText());
                            OperationClass.acc.setTransaction(new Transaction(Double.parseDouble(t1.getText()),"withdraw",LocalDate.now()));
                            JOptionPane.showMessageDialog(null,"Transaction SuccessFull\n Your Updated Balance: Rs"+c.balance);
                        }
                    }
                    else{
                        if(Double.parseDouble(t1.getText())>balance){
                            JOptionPane.showMessageDialog(null,"Insufficient Balance\nYour OverDraft limit is not Approved Yet");
                        }
                        else{
                            c.balance=c.balance-Double.parseDouble(t1.getText());
                            OperationClass.acc.setTransaction(new Transaction(Double.parseDouble(t1.getText()),"withdraw",LocalDate.now()));
                            JOptionPane.showMessageDialog(null,"Transaction SuccessFul\n Your Updated Balance: Rs"+c.balance);
                        }
                    }
                    reset();
                }
                else{

                    if(Double.parseDouble(t1.getText())>balance){
                        JOptionPane.showMessageDialog(null,"Insufficient Balance");
                    }
                    else{
                        c.balance=c.balance-Double.parseDouble(t1.getText());
                        OperationClass.acc.setTransaction(new Transaction(Double.parseDouble(t1.getText()),"withdraw",LocalDate.now()));
                        JOptionPane.showMessageDialog(null,"Transaction SuccessFul\n Your Updated Balance: Rs"+c.balance);
                    }
                    reset();
                }
            }
        }
    }

}
class CurrentBackButton implements ActionListener{
    public void actionPerformed(ActionEvent a){
        CurrentLogin.displaythisPage();
    }
}
class DepositePage extends JFrame{
    JLabel l1,ll1;
    JTextField t1;
    JButton b1,b2;
    DepositePage(String s1){
        super(s1);
    }
    void setComponents(){
        new TradeMark().setTradeMark(this);
        new DisplayAccNum().setComponents(this);
        l1=new JLabel("Enter the amount you want to deposit:");
        ll1=new JLabel();
        b1=new JButton("Deposit");
        b2=new JButton("Back");
        l1.setBounds(25,300,250,25);
        b1.setBounds(400,350,100,25);
        b2.setBounds(450,500,100,25);
        ll1.setBounds(400,300,125,25);
        b1.addActionListener(new DepositeAmount());
        b2.addActionListener(new BackToSavingLogin());
        setLayout(null);
        add(l1);
        add(b1);
        add(b2);
        add(ll1);
        textFieldGenerator();
    }
    void textFieldGenerator(){
        t1=new JTextField();
        t1.setBounds(300,300,100,25);
        add(t1);
    }
    static void CreatePage(){
        DepositePage p=new DepositePage("Deposit Page");
        p.setVisible(true);
        p.setSize(600,600);
        p.setComponents();
    }
    boolean checkValidation(){
        if(t1.getText().length()==0){
            ll1.setText("Cannot be Empty");
        }
        else{
            try{
                Double.parseDouble(t1.getText());
                if(Double.parseDouble(t1.getText())<=0){
                    ll1.setText("Only Postive Numbers");
                    return false;
                }
                return true;
            }catch (NumberFormatException a){
                ll1.setText("Only Digits");
            }
        }
        return false;
    }
    class DepositeAmount implements ActionListener{
        public void actionPerformed(ActionEvent a){
            if(checkValidation()==true){
                OperationClass.acc.setBalance(OperationClass.acc.getBalance()+Double.parseDouble(t1.getText()));
                OperationClass.acc.setTransaction(new Transaction(Double.parseDouble(t1.getText()),"Deposit",LocalDate.now()));
                JOptionPane.showMessageDialog(null,"Amount Deposited SuccessFully\nUpdated Balance :"+OperationClass.acc.getBalance());
                t1.setText("");
                ll1.setText("");
            }
        }
    }
}
class BackToSavingLogin implements ActionListener{
    public void actionPerformed(ActionEvent a){
        SavingLogin.displaythisPage();
    }
}
class MiniStatementbutton implements ActionListener{
    JFrame f;
    MiniStatementbutton(JFrame f){
        this.f=f;
    }
    public void actionPerformed(ActionEvent a){
        MiniStatement.displayThisPage(f);
    }
}
class ShowBalancePage extends JFrame{
    static JFrame page;
    JLabel l1;
    JButton b1;
    ShowBalancePage(){

    }
    ShowBalancePage(String s1){
        super(s1);
    }
    void setComponents(){
        new TradeMark().setTradeMark(this);
        new DisplayAccNum().setComponents(this);
        l1=new JLabel("Balance :Rs"+OperationClass.acc.getBalance());
        b1=new JButton("Back");
        if(page instanceof SavingLogin){
            b1.addActionListener(new BackToSavingLogin());
        }else if (page instanceof CurrentLogin){
            b1.addActionListener(new CurrentBackButton());
        } else if (page instanceof SalaryLoginPage) {
            b1.addActionListener(new SalaryBack());
        } else if (page instanceof LoanLoginPage) {
            b1.addActionListener(new LoginBackButton());
        }
        l1.setBounds(150,300,250,25);
        b1.setBounds(450,500,100,25);
        setLayout(null);
        add(l1);
        add(b1);
    }
    static void createThisPage(JFrame page1){
        page=page1;
        ShowBalancePage b=new ShowBalancePage("Balance Page");
        b.setVisible(true);
        b.setSize(600,600);
        b.setComponents();
    }
}
class MiniStatement extends JFrame{
    static JFrame page1;
    JLabel[] l1;
    JButton b1;
    MiniStatement(String s1){
        super(s1);
    }
    void setComponents(){
        new TradeMark().setTradeMark(this);
        new DisplayAccNum().setComponents(this);
        setLayout(null);
        b1=new JButton("Back");
        if(page1 instanceof SavingLogin){
            b1.addActionListener(new BackToSavingLogin());
        }else if (page1 instanceof CurrentLogin){
            b1.addActionListener(new CurrentBackButton());
        } else if (page1 instanceof SalaryLoginPage) {
            b1.addActionListener(new SalaryBack());
        } else if (page1 instanceof LoanLoginPage) {
            b1.addActionListener(new LoginBackButton());
        }
        b1.setBounds(450,500,100,25);
        String[] s=OperationClass.getMiniStatement();
        if(s!=null){
            l1=new JLabel[s.length];
            int x=100;
            int y=250;
            int width=200;
            int hight=25;
            for (int i=0;i<s.length;i++){
                l1[i]=new JLabel();
                l1[i].setText(s[i]);
                l1[i].setBounds(x,y,width,hight);
                add(l1[i]);
                y+=25;
            }
        }
        add(b1);
    }
    static void displayThisPage(JFrame page2){
        page1=page2;
        MiniStatement m=new MiniStatement("Mini Statement");
        m.setVisible(true);
        m.setSize(600,600);
        m.setComponents();
    }
}
class WithDrawPage extends JFrame{
    JLabel l1,ll1;
    JTextField t1;
    JButton b1,b2;
    WithDrawPage(){

    }
    WithDrawPage(String s1){
        super(s1);
    }
    void setComponents(){
        new TradeMark().setTradeMark(this);
        new DisplayAccNum().setComponents(this);
        l1=new JLabel("Enter the amount you want to Withdraw:");
        ll1=new JLabel();
        b1=new JButton("Withdraw");
        b2=new JButton("Back");
        l1.setBounds(25,300,250,25);
        b1.setBounds(400,350,100,25);
        b2.setBounds(450,500,100,25);
        ll1.setBounds(400,300,125,25);
        b1.addActionListener(new WithDrawMoney());
        b2.addActionListener(new BackToSavingLogin());
        setLayout(null);
        add(l1);
        add(b1);
        add(b2);
        add(ll1);
        textFieldGenerator();
    }
    void textFieldGenerator(){
        t1=new JTextField();
        t1.setBounds(300,300,100,25);
        add(t1);
    }
    boolean checkValidation(){
        if(t1.getText().length()==0){
            ll1.setText("Cannot be Empty");
        }
        else{
            try{
                Double.parseDouble(t1.getText());
                if(Double.parseDouble(t1.getText())<=0){
                    ll1.setText("Only Postive Numbers");
                    return false;
                }
                return true;
            }catch (NumberFormatException a){
                ll1.setText("Only Digits");
            }
        }
        return false;
    }
    static void displayThisPage(){
        WithDrawPage w=new WithDrawPage("Withdraw Page");
        w.setVisible(true);
        w.setSize(600,600);
        w.setComponents();
    }
    class WithDrawMoney implements ActionListener{
        public void actionPerformed(ActionEvent a){
            if(checkValidation()==true){
                if(Double.parseDouble(t1.getText())<OperationClass.acc.getBalance()){
                    if((OperationClass.acc.getBalance()-Double.parseDouble(t1.getText()))>=Saving.getMinBalance()){
                        OperationClass.acc.setBalance(OperationClass.acc.getBalance()-Double.parseDouble(t1.getText()));
                        OperationClass.acc.setTransaction(new Transaction(Double.parseDouble(t1.getText()),"Withdraw",LocalDate.now()));
                        t1.setText("");
                        ll1.setText("");
                        JOptionPane.showMessageDialog(null,"Withdraw Successfully\nRemaining Balance: "+OperationClass.acc.getBalance());
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Minimum Rs"+Saving.getMinBalance()+" Must be there in Account");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Insufficient Balance");
                }
            }
        }
    }
}