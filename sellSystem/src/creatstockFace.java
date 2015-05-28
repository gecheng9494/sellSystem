import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Vector;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.*;
import javax.swing.table.*;
public class creatstockFace extends JPanel{
	private final static long serialVersionUID = 2300323156L;
	private javax.swing.table.DefaultTableModel m = null;
	private Object[][]  info=null;
	private String title[]  = {"��ƷID","��Ʒ����","����","��������","������","��������","��Ʒ���"};//�����ͷ;
	private String goodID,name,price,date,quality,factory,stock;  // ����ѯ��õļ�¼���ݣ�ת�����ʺ�����JTable��������ʽ
	private int count=0;
	     	
	public creatstockFace(){
		try{System.out.println("ok1");
			// �������
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection conn = DriverManager.getConnection("jdbc:odbc:mysource","","");// ������ѯ����
			String sql = "Select * FROM goods"; 
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();// ִ�в�ѯ			
			while(rs.next()){
				count++;
			}
			rs = pstm.executeQuery();			
			info = new Object[count][7];	
			count=0;
			while(rs.next()){
				goodID=rs.getString("��ƷID");
				name=rs.getString("��Ʒ����");
				price=rs.getString("����");
				date=rs.getString("��������");
				quality=rs.getString("������");
				factory=rs.getString("��������");
				stock=rs.getString("��Ʒ���");
			    	info[count][0] = goodID;
			    	info[count][1] = name;
			    	info[count][2]=price;
			    	info[count][3]=date;
			    	info[count][4]=quality;
			    	info[count][5]=factory;
			    	info[count][6]=stock;
			    	count++;
			   }
	     	}catch(ClassNotFoundException cnfe){
			JOptionPane.showMessageDialog(null,"����Դ����","����",JOptionPane.ERROR_MESSAGE);
		    }
	     	catch(SQLException sqle){
			JOptionPane.showMessageDialog(null,"���ݲ�������","����",JOptionPane.ERROR_MESSAGE);
		    }
		
		java.awt.Toolkit tools = this.getToolkit();
		java.awt.Dimension dimension = tools.getScreenSize();
		//data = info;
		//st = title;
		 TableModel dataModel = new AbstractTableModel() { 
             public int getColumnCount() { return title.length; } 
             public int getRowCount() { return info.length;} 
             public Object getValueAt(int row, int col) {return info[row][col];} 
             public String getColumnName(int column) {return title[column];} 
             public Class getColumnClass(int c) {return getColumnName(c).getClass();} 
 	    public boolean isCellEditable(int row, int col) {return col > 1 && row > 1;} 
             public void setValueAt(Object aValue, int row, int column) { info[row][column] = aValue; } 
          }; 
         TableRowSorter trs = new TableRowSorter(dataModel);
		JTable table = new JTable(dataModel);
		table.setRowSorter(trs);
		table.setOpaque(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(30);
		table.setRowMargin(5);
		table.setShowGrid(true);
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		table.doLayout();
		table.setSize(dimension);
		table.setVisible(true);
		javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(table);
		this.setLayout(new java.awt.BorderLayout());
		this.add(scrollPane,"Center");
		this.setSize(480,320);
		this.validate();
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(!isOpaque()){return;}
		Graphics2D g2d = (Graphics2D) g;
		//int rule = AlphaComposite.SRC_OVER;
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
