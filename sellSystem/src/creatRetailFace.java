import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.AlphaComposite;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.text.DecimalFormat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class creatRetailFace extends javax.swing.JPanel{
	private final static long serialVersionUID = 230002328992L;
	private JTextField good_id = null;
	private JTextField payText = null;
	private JLabel  changeTextLabel=null;
	private JLabel sumTextLabel=null;
	private JButton addButton = null;
	private JButton enterButton = null;
	private JPanel p1=new JPanel();
	private JPanel p2=new JPanel();
	private JPanel p3=new JPanel();
	private JTable tabDemo=null;
	private int i=1;   //计数满足查询商品ID号的数据库表中的一整行记录
	private Object[][] info = new Object[10][6];  //购物清单表中的总行数	
	private double sum=0, pay;	

	public creatRetailFace(){
		this.setLayout(null);		
		JLabel good_id_label = new JLabel("请输入将要购买商品的ID号:");
		good_id_label.setBounds(10, 20, 200, 30);
		good_id = new javax.swing.JTextField(10);
		good_id.requestFocus();
		good_id.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar() == KeyEvent.VK_ENTER) && ((good_id.getText().trim()) != "")){
					addButton.requestFocus();
				}
			}
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
		good_id.setBounds(180, 20, 200, 30);
		addButton=new JButton("添    加");
		addButton.setBounds(420, 20, 100, 30);
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				add_ActionPerformed(e);
			}
		});
		JLabel sumLabel=new JLabel("总计：");
		sumLabel.setBounds(10, 20, 80, 30);
		sumTextLabel=new JLabel();
		sumTextLabel.setBounds(50, 20, 80, 30);
		JLabel payLabel=new JLabel("实付：");
		payLabel.setBounds(120,20,80,30);
		payText=new JTextField(10);
		payText.setBounds(160, 20, 80, 30);
		payText.requestFocus();
		payText.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar() == KeyEvent.VK_ENTER) && ((payText.getText().trim()) != "")){
					pay=Double.parseDouble( payText.getText());
					if(sum>pay)
					{JOptionPane.showMessageDialog(null,"对不起！您的支付款额不够");}
					else
					{
						double temp;
						temp=pay-sum;
						DecimalFormat  decimal=new DecimalFormat("#.##");
						String result=decimal.format(temp);
						changeTextLabel.setText(result);						
					}
					enterButton.requestFocus();
				}
			}
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
		JLabel changeLabel=new JLabel("找零：");
		changeLabel.setBounds(280, 20, 80, 30);
		changeTextLabel=new JLabel();
		changeTextLabel.setBounds(320, 20, 80, 30);
		enterButton=new JButton("确    定");
		enterButton.setBounds(250, 70, 80, 30);
		enterButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
		   {
			   pay=Double.parseDouble( payText.getText());
			   if(sum>pay)
				{JOptionPane.showMessageDialog(null,"对不起！您的支付款额不够");}
			   else
				{
					double temp;
					temp=pay-sum;
					DecimalFormat  decimal=new DecimalFormat("#.##");
					String result=decimal.format(temp);
					changeTextLabel.setText(result);						
				}
			}
		 });
		
		p1.setBounds(0, 0, 600, 80);
		p1.setLayout(null);
		p1.add(good_id_label);
		p1.add(good_id);
		p1.add(addButton);
		p2.setBounds(0, 85, 600, 310);
		p3.setBounds(0, 400, 600, 200);
		p3.setLayout(null);
		p3.add(sumLabel);p3.add(sumTextLabel);
		p3.add(payLabel);p3.add(payText);
		p3.add(changeLabel);p3.add(changeTextLabel);
		p3.add(enterButton);	
		
		this.add(p1);
		this.add(p2);
		this.add(p3);
		this.setVisible(true);
		this.validate();
	}
	public void add_ActionPerformed(ActionEvent e){
		// 以下是连接数据源和显示数据的具体处理方法，请注意下
		try{
			// 获得连接
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection conn = DriverManager.getConnection("jdbc:odbc:mysource","","");// 建立查询条件
			String sql = "Select * FROM goods"; 
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();// 执行查询
			rs = pstm.executeQuery();
			info[0][0] = "商 品 ID";
	    	info[0][1] = "商 品 名 称";
	    	info[0][2]="单 价( 元  )";
	    	info[0][3]="生 产 日 期";
	    	info[0][4]="保   质  期";
	    	info[0][5]="生 产 厂 家";
			String goodID,name,price,date,quality,factory;  // 将查询获得的记录数据，转换成适合生成JTable的数据形式
			while(rs.next()){
				goodID=rs.getString("商品ID");
				name=rs.getString("商品名称");
				price=rs.getString("单价");
				date=rs.getString("生产日期");
				quality=rs.getString("保质期");
				factory=rs.getString("生产厂家");
				if(goodID.equals(good_id.getText().toString())){
			    	info[i][0] = goodID;
			    	info[i][1] = name;
			    	info[i][2]=price;
			    	info[i][3]=date;
			    	info[i][4]=quality;
			    	info[i][5]=factory;
			    	sum+=Double.parseDouble(price);
			    	i++;			    
				}				
			} 
			DecimalFormat decimal=new DecimalFormat("#.##");
			String result=decimal.format(sum);
			sumTextLabel.setText(result);
			final Object[] title  = {"商品ID","商品名称","单价","生产日期","保质期","生产厂家"};// 定义表头			
			tabDemo = new JTable(info,title);// 创建JTable
			tabDemo.setRowHeight(30); //设置每一行的高
			tabDemo.setLocation(20, 40);			
		    p2.removeAll();
			p2.add(tabDemo);  
		    p2.setVisible(true);
		    p2.validate();
		    p2.repaint();
	     	}catch(ClassNotFoundException cnfe){
			JOptionPane.showMessageDialog(null,"数据源错误","错误",JOptionPane.ERROR_MESSAGE);
		    }
	     	catch(SQLException sqle){
			JOptionPane.showMessageDialog(null,"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(!isOpaque()){return;}
		Graphics2D g2d = (Graphics2D) g;
		int rule = AlphaComposite.SRC_OVER;
		AlphaComposite opaque = AlphaComposite.SrcOver;
		//AlphaComposite blend = AlphaComposite.getInstance(rule, 0.6f);
		//AlphaComposite set = AlphaComposite.Src;
		int width = getWidth();
		int height = getHeight();
		GradientPaint gradientPaint = new GradientPaint(0,0,java.awt.Color.green,width/2,height/2,java.awt.Color.yellow,false);
		g2d.setComposite(opaque);
		g2d.setPaint(gradientPaint);
		g2d.fillRect(0, 0, width,height);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
	}



}
