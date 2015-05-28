import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
public class MainFrame {
	private final static javax.swing.ImageIcon icon = new javax.swing.ImageIcon("src/img/logo1.jpg");
	private JFrame frame = new javax.swing.JFrame();
	private JMenuBar menuBar = null;
	private JToolBar toolBar = null;
	private JSplitPane splitPane = null;
	private JTabbedPane tabbedPane = new javax.swing.JTabbedPane();
    private JButton Retail = null;//商品零售
    private JButton stock = null;//库存管理
	private JButton help = null;//帮助
	private JButton about = null;//关于
	private JButton exit_system = null;//退出系统
	private JLabel leftLabel=null;//左部标签
	private String sql = null;
	
	public MainFrame(String name){
		try
		{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}
		catch(Exception exe)
		{System.err.print(exe.getMessage());}
		java.awt.Toolkit tool = frame.getToolkit();
		Image image = tool.getImage("res/logo.jpg");
		Dimension dimn = tool.getScreenSize();
		String title = "超市管理系统--"+name;
		frame.setTitle(title);
		frame.setIconImage(image);
		frame.setFocusable(true);
		frame.setLayout(new BorderLayout());
		frame.setJMenuBar(createJMenuBar());
		frame.add(createJToolBar(),"North");
		frame.add(createSplitPane(),"Center");
		frame.addWindowListener(new WindowListener(){
			public void windowActivated(WindowEvent e){}
			//因对窗口调用dispose()而将其关闭时调用
			public void windowClosed(WindowEvent e){}
			//用户试图从窗口的系统菜单中关闭窗口时调用
			public void windowClosing(WindowEvent e){
				frame.dispose();
				System.exit(0);
			}
			//当窗口不再是活动窗口时调用
			public void windowDeactivated(WindowEvent e){}
			//窗口从最小化状态变为正常状态时调用
			public void windowDeiconified(WindowEvent e){}
			//窗口从正常状态变为最小状态时调用
			public void windowIconified(WindowEvent e){}
			//窗口首次变为可见时调用
			public void windowOpened(WindowEvent e){}
		});
		Dimension sr=new Dimension(750,640);
		frame.setPreferredSize(sr);
		frame.setLocation((dimn.width-750)/2, (dimn.height-640)/2);
		frame.setResizable(false);
		frame.setBackground(java.awt.Color.pink);
		frame.pack();
		frame.validate();
		frame.setVisible(true);
	}
	private JMenuBar createJMenuBar()
	{
		    menuBar = new JMenuBar()
		    {
			    public void paintComponent(Graphics g)
			    {
			    	super.paintComponent(g);
			    	if(!isOpaque()){return;}
			    	Graphics2D g2d = (Graphics2D) g;
		 	     	AlphaComposite opaque = AlphaComposite.SrcOver;
			    	int width = getWidth();
			    	int height = getHeight();
			    	GradientPaint gradientPaint = new GradientPaint(0,0,java.awt.Color.green,width/2,height/2,java.awt.Color.yellow,false);
		     		g2d.setComposite(opaque);
			    	g2d.setPaint(gradientPaint);
			    	g2d.fillRect(0, 0, width,height);
			    	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		    	}
	    	};
	     	menuBar.add(createSystem_Manage_Menu());
	     	menuBar.add(createRetail_Manage_Menu());
	     	menuBar.add(createStock_Manage_Menu());
	     	menuBar.add(createHelp_Menu());
	    	menuBar.setBackground(java.awt.Color.pink);
	     	return menuBar;
     }
    private JToolBar createJToolBar(){

		toolBar = new javax.swing.JToolBar(){
		public void paintComponent(Graphics g){
				super.paintComponent(g);
				if(!isOpaque()){return;}
				Graphics2D g2d = (Graphics2D) g;
				AlphaComposite opaque = AlphaComposite.SrcOver;
				int width = getWidth();
				int height = getHeight();
				GradientPaint gradientPaint = new GradientPaint(0,0,java.awt.Color.green,width/2,height/2,java.awt.Color.yellow,false);
				g2d.setComposite(opaque);
				g2d.setPaint(gradientPaint);
				g2d.fillRect(0, 0, width,height);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
			}
		};		
		Retail = new javax.swing.JButton("商品零售");
		Retail.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				retailManage("零      售");
			}
		});		
		stock = new javax.swing.JButton("库存管理");
		stock.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){			
				stockManage("库存信息");
			}
		});
		help = new javax.swing.JButton("帮     助");
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,"帮助");
				//AddHelp();
			}
		});
		about = new javax.swing.JButton("关     于");
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,"关于");
				//new UserAbout();
			}
		});
		exit_system = new javax.swing.JButton("退出系统");
		exit_system.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){				
				frame.dispose();
				System.exit(0);
			}
		});
		toolBar.add(Retail);
		toolBar.addSeparator();
		toolBar.add(stock);
		toolBar.addSeparator();
		toolBar.add(help);
		toolBar.addSeparator();
		toolBar.add(about);
		toolBar.addSeparator();
		toolBar.add(exit_system);
		toolBar.setBackground(java.awt.Color.pink);
		return toolBar;
	}
	private JSplitPane createSplitPane(){
		leftLabel=new JLabel();
		leftLabel.setBackground( Color.green);
		leftLabel.setBounds(0, 0, 400, 600);
		leftLabel.setOpaque(true);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,leftLabel,createHandlePane()){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				if(!isOpaque()){return;}
				Graphics2D g2d = (Graphics2D) g;
				AlphaComposite opaque = AlphaComposite.SrcOver;
				int width = getWidth();
				int height = getHeight();
				GradientPaint gradientPaint = new GradientPaint(0,0,java.awt.Color.green,width/2,height/2,java.awt.Color.yellow,false);
				g2d.setComposite(opaque);
				g2d.setPaint(gradientPaint);
				g2d.fillRect(0, 0, width,height);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
			}
		};
		splitPane.setDividerLocation(138);
		splitPane.setDividerSize(5);
		splitPane.setOneTouchExpandable(true);
		splitPane.setBackground(java.awt.Color.pink);
		return splitPane;
	}
	private JTabbedPane createHandlePane(){
		javax.swing.ImageIcon image = new javax.swing.ImageIcon("src/img/welcome1.jpg");
		javax.swing.JLabel component = new javax.swing.JLabel(image);
		tabbedPane.addTab("欢迎",icon ,component);
		tabbedPane.setBackgroundAt(0, java.awt.Color.pink);
		return tabbedPane;
	}
	private void retailManage(String s){
		int i = tabbedPane.getTabCount();
		if(i>0)tabbedPane.removeAll();
		tabbedPane.addTab(s, icon,new creatRetailFace());
		int b = tabbedPane.getTabCount();
		tabbedPane.setSelectedIndex(b-1);
	}
	private void stockManage(String s){
		int i = tabbedPane.getTabCount();
		if(i>0)tabbedPane.removeAll();
		tabbedPane.addTab(s, icon,new creatstockFace());
		int b = tabbedPane.getTabCount();
		tabbedPane.setSelectedIndex(b-1);
	}
	
	private JMenu createSystem_Manage_Menu(){
		javax.swing.JMenu systemMenu = new javax.swing.JMenu("系统管理");
		javax.swing.JMenuItem login = new javax.swing.JMenuItem("注          册");
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,"注册");
				//new AddUser();
			}
		});
		javax.swing.JMenuItem logout = new javax.swing.JMenuItem("删除用户");
		logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String jp = new JOptionPane().showInputDialog(frame,"请输入要删除的用户名！","输入用户名",JOptionPane.YES_NO_CANCEL_OPTION).trim();
			}
		});
		javax.swing.JMenuItem exit = new javax.swing.JMenuItem("退出系统");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		systemMenu.add(login);
		systemMenu.add(logout);
		systemMenu.add(exit);
		return systemMenu;
	}
	private JMenu createRetail_Manage_Menu(){
		javax.swing.JMenu retailMenu = new javax.swing.JMenu("零售管理");
		javax.swing.JMenuItem retail = new javax.swing.JMenuItem("零       售");
		retail.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				retailManage("零      售");
			}
		});
		retailMenu.add(retail);
		return retailMenu;
	}
	private JMenu createStock_Manage_Menu(){
		javax.swing.JMenu stockMenu = new javax.swing.JMenu("库存管理");
		javax.swing.JMenuItem purcharse = new javax.swing.JMenuItem("库存信息");
		purcharse.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				stockManage("库存信息");
			}
		});
		javax.swing.JMenuItem updata = new javax.swing.JMenuItem("库存更新");
		updata.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		stockMenu.add(purcharse); 
		stockMenu.add(updata); 
		return stockMenu;
	}
	private JMenu createHelp_Menu(){
		javax.swing.JMenu helpMenu = new javax.swing.JMenu("帮助");
		javax.swing.JMenuItem help = new javax.swing.JMenuItem("帮      助");
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,"帮助");
			}
		});
		javax.swing.JMenuItem about = new javax.swing.JMenuItem("关      于");
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,"关于");
			}
		});
		helpMenu.add(help);
		helpMenu.add(about);
		return helpMenu;
	}	
}
