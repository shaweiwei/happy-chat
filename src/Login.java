import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

/**
 * <p>
 * Title: HappyChat聊天系统登录程序
 * </p>
 * <p>
 * Description: 根据指定的服务器地址、用户名和密码登录聊天服务器 友情下载：http://www.codefans.net
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Filename: Login.java
 * </p>
 * 
 * @author 刘志成
 * @version 1.0
 */
public class Login extends JFrame implements ActionListener {

	private static final long serialVersionUID = -8965773902056088264L;

	private JPanel pnlLogin;

	private JButton btnLogin, btnRegister, btnExit;

	private JLabel lblServer, lblUserName, lblPassword, lblLogo;

	private JTextField txtUserName, txtServer;

	private JPasswordField pwdPassword;

	private String strServerIp;

	// 用于将窗口定位
	private Dimension scrnsize;

	private Toolkit toolkit = Toolkit.getDefaultToolkit();

	/**
	 * 构造登陆窗体
	 */
	public Login() {
		super("登录[HappyChat]聊天室");
		pnlLogin = new JPanel();
		this.getContentPane().add(pnlLogin);

		lblServer = new JLabel("服务器(S):");
		lblUserName = new JLabel("用户名(U):");
		lblPassword = new JLabel("口  令(P):");
		txtServer = new JTextField(20);
		txtServer.setText("127.0.0.1");
		txtUserName = new JTextField(20);
		pwdPassword = new JPasswordField(20);
		btnLogin = new JButton("登录(L)");
		btnLogin.setToolTipText("登录到服务器");
		btnLogin.setMnemonic('L');
		btnRegister = new JButton("注册(R)");
		btnRegister.setToolTipText("注册新用户");
		btnRegister.setMnemonic('R');
		btnExit = new JButton("退出(X)");
		btnExit.setToolTipText("退出系统");
		btnExit.setMnemonic('X');
		/***********************************************************************
		 * 该布局采用手动布局 setBounds设置组件位置 * setFont设置字体、字型、字号 * setForeground设置文字的颜色
		 * * setBackground设置背景色 * setOpaque将背景设置为透明
		 */
		pnlLogin.setLayout(null); // 组件用手动布局
		pnlLogin.setBackground(new Color(52, 130, 203));

		lblServer.setBounds(50, 100, 100, 30);
		txtServer.setBounds(150, 100, 120, 25);
		lblUserName.setBounds(50, 130, 100, 30);
		txtUserName.setBounds(150, 130, 120, 25);
		lblPassword.setBounds(50, 160, 100, 30);
		pwdPassword.setBounds(150, 160, 120, 25);
		btnLogin.setBounds(50, 200, 80, 25);
		btnRegister.setBounds(130, 200, 80, 25);
		btnExit.setBounds(210, 200, 80, 25);

		Font fontstr = new Font("宋体", Font.PLAIN, 12);
		lblServer.setFont(fontstr);
		txtServer.setFont(fontstr);
		lblUserName.setFont(fontstr);
		txtUserName.setFont(fontstr);
		lblPassword.setFont(fontstr);
		pwdPassword.setFont(fontstr);
		btnLogin.setFont(fontstr);
		btnRegister.setFont(fontstr);
		btnExit.setFont(fontstr);

		lblUserName.setForeground(Color.BLACK);
		lblPassword.setForeground(Color.BLACK);
		btnLogin.setBackground(Color.ORANGE);
		btnRegister.setBackground(Color.ORANGE);
		btnExit.setBackground(Color.ORANGE);

		pnlLogin.add(lblServer);
		pnlLogin.add(txtServer);
		pnlLogin.add(lblUserName);
		pnlLogin.add(txtUserName);
		pnlLogin.add(lblPassword);
		pnlLogin.add(pwdPassword);
		pnlLogin.add(btnLogin);
		pnlLogin.add(btnRegister);
		pnlLogin.add(btnExit);

		// 设置背景图片
		Icon logo1 = new ImageIcon("images\\loginlogo.jpg");
		lblLogo = new JLabel(logo1);
		lblLogo.setBounds(0, 0, 340, 66);
		pnlLogin.add(lblLogo);
		// 设置登录窗口
		setResizable(false);
		setSize(340, 260);
		setVisible(true);
		scrnsize = toolkit.getScreenSize();
		setLocation(scrnsize.width / 2 - this.getWidth() / 2, scrnsize.height / 2 - this.getHeight() / 2);
		Image img = toolkit.getImage("images\\appico.jpg");
		setIconImage(img);

		// 三个按钮注册监听
		btnLogin.addActionListener(this);
		btnRegister.addActionListener(this);
		btnExit.addActionListener(this);

	} // 构造方法结束

	/**
	 * 按钮监听响应
	 */
	@SuppressWarnings({ "deprecation", "static-access" })
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if (source.equals(btnLogin)) {
			// 判断用户名和密码是否为空
			if (txtUserName.getText().equals("") || pwdPassword.getText().equals("")) {
				JOptionPane op1 = new JOptionPane();
				op1.showMessageDialog(null, "用户名或密码不能为空");
			} else {
				strServerIp = txtServer.getText();
				login();
			}
		}
		if (source.equals(btnRegister)) {
			strServerIp = txtServer.getText();
			this.dispose();
			new Register(strServerIp);
		}
		if (source == btnExit) {
			System.exit(0);
		}
	} // actionPerformed()结束

	/**
	 * 登录事件响应方法
	 */
	@SuppressWarnings("deprecation")
	public void login() {
		// 接受客户的详细资料
		Customer data = new Customer();
		data.custName = txtUserName.getText();
		data.custPassword = pwdPassword.getText();
		try {
			// 连接到服务器
			Socket toServer;
			toServer = new Socket(strServerIp, 1001);
			ObjectOutputStream streamToServer = new ObjectOutputStream(toServer.getOutputStream());
			// 写客户详细资料到服务器socket
			streamToServer.writeObject((Customer) data);
			// 读来自服务器socket的登录状态
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(toServer.getInputStream()));
			String status = fromServer.readLine();
			if (status.equals("登录成功")) {
				new ChatRoom((String) data.custName, strServerIp);
				this.dispose();
				// 关闭流对象
				streamToServer.close();
				fromServer.close();
				toServer.close();
			} else {
				JOptionPane.showMessageDialog(null, status);
				// 关闭流对象
				streamToServer.close();
				fromServer.close();
				toServer.close();
			}
		} catch (ConnectException e1) {
			JOptionPane.showMessageDialog(null, "未能建立到指定服务器的连接!");
		} catch (InvalidClassException e2) {
			JOptionPane.showMessageDialog(null, "类错误!");
		} catch (NotSerializableException e3) {
			JOptionPane.showMessageDialog(null, "对象未序列化!");
		} catch (IOException e4) {
			JOptionPane.showMessageDialog(null, "不能写入到指定服务器!");
		}
	} // login()结束

	/**
	 * 启动登陆窗体
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		new Login();
	}

} // Class Login结束
