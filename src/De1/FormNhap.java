package De1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class FormNhap extends JFrame {

	DataInputStream din;
    DataOutputStream dout;
	private JPanel contentPane;
	private JTextField txtTapTin;
	public static String diaChi;
	public static int port;
	private JTextField txtKhoa;
	private JTextField txtChuoiDaiNhat;
	private JTextField txtChuoiMaHoa;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormNhap frame = new FormNhap();
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
	public FormNhap() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 623);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nhập đoạn văn:");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel.setBounds(32, 11, 104, 31);
		contentPane.add(lblNewLabel);
		
		txtTapTin = new JTextField();
		txtTapTin.setBounds(165, 196, 340, 36);
		contentPane.add(txtTapTin);
		txtTapTin.setColumns(10);
		
		JLabel lblTB1 = new JLabel("");
		lblTB1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblTB1.setBounds(32, 291, 460, 21);
		contentPane.add(lblTB1);
		
		Socket client;
		try {
			client = new Socket("localhost",3456 );
			din = new DataInputStream(client.getInputStream());
			dout = new DataOutputStream(client.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}                    
		
		
		JLabel lblNewLabel_1 = new JLabel("Nhập khóa");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(32, 152, 104, 31);
		contentPane.add(lblNewLabel_1);
		
		txtKhoa = new JTextField();
		txtKhoa.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtKhoa.setBounds(165, 149, 340, 36);
		contentPane.add(txtKhoa);
		txtKhoa.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Tên tập tin");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(32, 197, 104, 31);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("Chuỗi dài nhất là:");
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(30, 373, 123, 31);
		contentPane.add(lblNewLabel_2);
		
		txtChuoiDaiNhat = new JTextField();
		txtChuoiDaiNhat.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtChuoiDaiNhat.setBounds(163, 374, 342, 28);
		contentPane.add(txtChuoiDaiNhat);
		txtChuoiDaiNhat.setColumns(10);
        
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(166, 11, 339, 127);
		contentPane.add(scrollPane);
		
		JTextArea txtA_DoanVan = new JTextArea();
		scrollPane.setViewportView(txtA_DoanVan);
		txtA_DoanVan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(165, 428, 340, 145);
		contentPane.add(scrollPane_1);
		
		JTextArea txtA_XuatHien = new JTextArea();
		scrollPane_1.setViewportView(txtA_XuatHien);
		txtA_XuatHien.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		JLabel lblNewLabel_2_1 = new JLabel("Chuỗi mã hóa:");
		lblNewLabel_2_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel_2_1.setBounds(30, 331, 123, 31);
		contentPane.add(lblNewLabel_2_1);
		
		txtChuoiMaHoa = new JTextField();
		txtChuoiMaHoa.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtChuoiMaHoa.setColumns(10);
		txtChuoiMaHoa.setBounds(163, 332, 342, 28);
		contentPane.add(txtChuoiMaHoa);
		
		JButton btnThucHien = new JButton("Thực hiện");
		btnThucHien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Socket client = new Socket("localhost",3456 );
					
					String doanVan= txtA_DoanVan.getText();
					String khoa= txtKhoa.getText();
					String tenFile= txtTapTin.getText();
					
					dout.writeUTF(doanVan);
					dout.writeUTF(khoa);
					dout.writeUTF(tenFile);
				
					System.out.println(doanVan+" "+khoa+" "+ tenFile);
					String xauMaHoa= din.readUTF();
					String ketqua= din.readUTF();	
					String textA= din.readUTF();
					txtChuoiMaHoa.setText(xauMaHoa);
					txtChuoiDaiNhat.setText(ketqua);
					String[] soLanXuatHien= textA.split("#");
					for(int i=0;i<soLanXuatHien.length;i++) {
						txtA_XuatHien.append(soLanXuatHien[i]+"\n");
					}
					client.close();
				}catch (Exception ex) {
					ex.printStackTrace();
				}
				
			}
		});
		
		btnThucHien.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnThucHien.setBounds(217, 243, 123, 38);
		contentPane.add(btnThucHien);
		
		
		
		
		
	}
}
