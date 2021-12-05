package De1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Window;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class Client extends JFrame {

	private JPanel contentPane;
	private JTextField txtDiaChi;
	private JTextField txtPort;
	Socket client;
    DataInputStream din;
    DataOutputStream dout;
	private Window lblThongBao;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public void resetKetNoi() {
		txtDiaChi.setText("");
		txtPort.setText("");
		txtDiaChi.requestFocus();
		lblThongBao = null;
		lblThongBao.setVisible(false);
	}
	public Client() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 548, 587);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Địa chỉ:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel.setBounds(22, 21, 60, 31);
		contentPane.add(lblNewLabel);
		
		JLabel lblPt = new JLabel("Port:");
		lblPt.setHorizontalAlignment(SwingConstants.CENTER);
		lblPt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblPt.setBounds(21, 66, 60, 31);
		contentPane.add(lblPt);
		
		txtDiaChi = new JTextField();
		txtDiaChi.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtDiaChi.setColumns(10);
		txtDiaChi.setBounds(92, 21, 239, 31);
		contentPane.add(txtDiaChi);
		
		txtPort = new JTextField();
		txtPort.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtPort.setColumns(10);
		txtPort.setBounds(92, 63, 239, 31);
		contentPane.add(txtPort);
		
		JButton btnKetNoi = new JButton("Connect to Server");
		btnKetNoi.setBounds(361, 21, 143, 71);
		contentPane.add(btnKetNoi);
		
		JLabel lblThongBao = new JLabel("");
		lblThongBao.setOpaque(true);
		lblThongBao.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongBao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblThongBao.setBackground(Color.GREEN);
		lblThongBao.setBounds(10, 108, 513, 31);
		contentPane.add(lblThongBao);
		
		btnKetNoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String diaChi= txtDiaChi.getText().trim();
					int port= Integer.parseInt(txtPort.getText().trim());
					client= new Socket(diaChi,port);	
					din = new DataInputStream(client.getInputStream());
		            dout = new DataOutputStream(client.getOutputStream());
		            lblThongBao.setText("Kết nối thành công!!");
		            FormNhap formNhap= new FormNhap();		            
		            formNhap.setVisible(true);		            
		            client.close();
		            
		            
				}catch(Exception ex) {
					ex.printStackTrace();
					lblThongBao.setText("Kết nối tới Server không thành công!! Nhập lại!!");
				}
			}
		});
		
	}
	
}
