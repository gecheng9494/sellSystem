import java.awt.Dimension;
import java.awt.Graphics;   
import java.awt.GridBagConstraints;   
import java.awt.GridBagLayout;   
import java.awt.Insets;   
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
  
import javax.swing.ImageIcon;   
import javax.swing.JButton;   
import javax.swing.JFrame;   
import javax.swing.JLabel;   
import javax.swing.JOptionPane;
import javax.swing.JPanel;   
import javax.swing.JPasswordField;   
import javax.swing.JTextField;   
  

public class LoginJFrame implements ActionListener 
{   
    public JFrame jframe;   
    public JLabel jlabel, jlabel1;   
    public GridBagLayout gridbag;   
    public GridBagConstraints constraints;   
    public JTextField jtfield1;   
    public  JPasswordField jpfield1;   
    public JButton jbutton1, jbutton2, jbutton3;   
    public JPanel jpanel;
    public Connection con;
    public Statement sql;                     //声明Statement对象
    public ResultSet rs; 
    
    public static void main(String[] args) {
    	 new LoginJFrame(); 

    }

  
    public LoginJFrame(){   
        jframe = new JFrame();   
        jframe.setTitle("登陆");   
        jlabel = new JLabel();   
        jlabel.setText("用户名：");   
        jlabel1 = new JLabel();   
        jlabel1.setText("密    码：");   
        jtfield1 = new JTextField(7);   
        jpfield1 = new JPasswordField(7);   
        gridbag = new GridBagLayout();   
        jbutton1 = new JButton();   
        jbutton1.setText("登    录");   
        jbutton2 = new JButton();   
        jbutton2.setText("退    出");   
        jbutton3 = new JButton();   
        jbutton3.setText("帮    助"); 
        jpanel = new JPanel()
        {
        	  public void paintComponent(Graphics g) {
        	  super.paintComponent(g);
        	  ImageIcon img = new ImageIcon("src/img/j01.jpg");
        	  g.drawImage(img.getImage(), 0, 0, null);
        	 }
        	};
  
        //设置窗体属性   
        Dimension sr=Toolkit.getDefaultToolkit().getScreenSize();
        jframe.setBounds((sr.width-460)/2, (sr.height-340)/2, 460, 340);   
        jframe.setDefaultCloseOperation(3);   
  
        // 设置JPanel为透明，且使用GridBagLayout布局管理器   
       // jpanel.setOpaque(true);   
        jpanel.setLayout(gridbag);   
  
        // 用户名文本框显示   
        constraints = getGridBagConstraints(0, 0, 1, 1, 0, 0,   
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(   
                        10, 0, 10, 0), 0, 0);   
  
        gridbag.setConstraints(jlabel, constraints);   
        jpanel.add(jlabel);   
  
        // 用户名输入框显示   
        constraints = getGridBagConstraints(1, 0, 1, 1, 0, 0,   
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(   
                        10, 0, 10, 0), 100, 0);   
  
        gridbag.setConstraints(jtfield1, constraints);   
        jpanel.add(jtfield1);   
  
        // 密码文本框显示   
        constraints = getGridBagConstraints(0, 1, 1, 1, 0, 0,   
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(   
                        10, 0, 10, 0), 0, 0);   
        gridbag.setConstraints(jlabel1, constraints);   
        jpanel.add(jlabel1);   
  
        // 密码输入框显示   
        constraints = getGridBagConstraints(1, 1, 1, 1, 0, 0,   
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(   
                        10, 0, 10, 0), 100, 0);   
  
        gridbag.setConstraints(jpfield1, constraints);   
        jpanel.add(jpfield1);   
  
        // 帮助按钮显示   
        constraints = getGridBagConstraints(0, 2, 1, 1, 0, 0,   
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(   
                        10, 0, 10, 0), 0, 0);   
  
        gridbag.setConstraints(jbutton3, constraints);   
        jpanel.add(jbutton3);   
  
        // 登录按钮显示   
        constraints = getGridBagConstraints(1, 2, 1, 1, 0, 0,   
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(   
                        10, 0, 10, 0), 0, 0);   
  
        gridbag.setConstraints(jbutton1, constraints);   
        jpanel.add(jbutton1);   
  
        // 退出按钮显示   
        constraints = getGridBagConstraints(2, 2, 1, 1, 0, 0,   
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(   
                        10, 0, 10, 0), 0, 0);   
  
        gridbag.setConstraints(jbutton2, constraints);   
        jpanel.add(jbutton2);   
        
        //获得ActionEvent的监听器
        jbutton1.addActionListener(this);
        jbutton2.addActionListener(this);
        jbutton3.addActionListener(this);
        jframe.add(jpanel);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setVisible(true); 
    }   
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==jbutton1){
            if(jtfield1.getText().equals("")||jpfield1.getPassword().equals("")){
                JOptionPane.showMessageDialog(null,"输入不能为空！");
            } 
            else{
                try{
                    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                } catch(ClassNotFoundException a){
                    System.out.println(""+a);
                }
                try{ 
                    con=DriverManager.getConnection("jdbc:odbc:mysource","","");
                    sql=con.createStatement();
                    rs=sql.executeQuery("Select * FROM login_1");    //查询数据库
                    boolean check = false;
                    while(rs.next()){
                        String name=rs.getString(2);         //获得数据库第二列
                        String password=rs.getString(3);     //获得数据库第三列
                        char[] s=jpfield1.getPassword();
                        String psw=new String(s);
                        if(jtfield1.getText().equals(name) && psw.equals(password))
                        {    //判断语句
                        	check = true;
                        	new MainFrame(jtfield1.getText());
                        	jframe.dispose();                       	
                            break;
                        }
                    }
                    if(check == false){
                        JOptionPane.showMessageDialog(null,"登陆失败，请重新输入！");
                    }               
                con.close();
            } catch(SQLException el){}
          }        
       }
        else if(e.getSource()==jbutton2)
       {
    	  System.exit(0);
        }
        if(e.getSource()==jbutton3)
        {
        	JOptionPane.showMessageDialog(null, "您需要注册一个用户名和密码才可以进入系统！");
        }
    }
  
    private static GridBagConstraints getGridBagConstraints(int gridx,   
            int gridy, int gridwidth, int gridheight, double weightx,   
            double weighty, int anchor, int fill, Insets insets, int ipadx,   
            int ipady) {   
  
        return new GridBagConstraints(gridx, gridy, gridwidth, gridheight,   
                weightx, weighty, anchor, fill, insets, ipadx, ipady);   
    }          
 
}
