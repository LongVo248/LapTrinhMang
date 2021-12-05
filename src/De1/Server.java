package De1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

	public static int PORT_SERVER = 3456;

	// tạo key
	public static String generateKey(String str, String key) {
		int x = str.length();

		for (int i = 0;; i++) {
			if (x == i)
				i = 0;
			if (key.length() == str.length())
				break;
			key += (key.charAt(i));
		}
		return key;
	}
	public static String vigenereUNICODE(String plaintext, String key, boolean encrypt) {

	    final int textSize = plaintext.length();
	    final int keySize = key.length();

	    final StringBuilder encryptedText = new StringBuilder(textSize);
	    for (int i = 0; i < textSize; i++) {
	        final int plainNR = plaintext.codePointAt(i);
	        final int keyNR = key.codePointAt(i % keySize);

	        final long cipherNR;
	        if (encrypt) {
	            cipherNR = ((long) plainNR + (long) keyNR) & 0xFFFFFFFFL;
	        } else {
	            cipherNR = ((long) plainNR - (long) keyNR) & 0xFFFFFFFFL;
	        }

	        encryptedText.appendCodePoint((int) cipherNR);
	    }

	    return encryptedText.toString();
	}
	static String cipherText(String str, String key) {
		String cipher_text = "";

		for (int i = 0; i < str.length(); i++) {
			// converting in range 0-25
			
			int x = (str.charAt(i) + key.charAt(i)) % 26;

			// convert into alphabets(ASCII)
			x += 'A';
			System.out.print("x= "+x);
			if(str.charAt(i) != ' ') {
				cipher_text+=(char) (x);
			} else {
				cipher_text+=' ';
			}
			//cipher_text += (char) (x);
		}
		return cipher_text;
	}

	static String originalText(String cipher_text, String key) {
		String orig_text = "";

		for (int i = 0; i < cipher_text.length() && i < key.length(); i++) {
			// converting in range 0-25
			int x = (cipher_text.charAt(i) - key.charAt(i) + 26) % 26;

			// convert into alphabets(ASCII)
			x += 'A';
			orig_text += (char) (x);
		}
		return orig_text;
	}

	public static String timChuoiDaiNhat(String text) {
		// tách từ
		String[] input = text.split(" ");

		int index = 0;
		int lengthMax = 0; // độ dài max của từ trong xâu
		for (int i = 0; i < input.length; i++) {
			int length = input[i].length();
			if (length > lengthMax) {
				lengthMax = length;
				index = i;
			}
		}
		System.out.println("Từ dài nhất: " + input[index] + " có độ dài " + lengthMax);
		String output = input[index];
		return output;
	}

	public static String listSoLanXuatHien(String text) {
		String[] arr = text.split("");
		String xuatHien="";
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String x : arr) {
			if (map.containsKey(x)) {
				map.put(x, map.get(x) + 1);
			} else {
				map.put(x, 1);
			}
		}
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if(!entry.getKey().equals(" ")) {
				xuatHien+='#'+"Ta được " + entry.getKey() + " xuất hiện " + entry.getValue() + " lần!";
			}
			//System.out.println("Ta được " + entry.getKey() + " xuất hiện " + entry.getValue() + " lần!");
			
		}
		return xuatHien;
	}

	static String LowerToUpper(String s) {
		StringBuffer str = new StringBuffer(s);
		for (int i = 0; i < s.length(); i++) {
			if (Character.isLowerCase(s.charAt(i))) {
				str.setCharAt(i, Character.toUpperCase(s.charAt(i)));
			}
		}
		s = str.toString();
		return s;
	}
	
	public static void main(String[] args)throws IOException{
		//Tạo server
		ServerSocket server=new ServerSocket(PORT_SERVER);
		while(true) {
			System.out.println("server start");
			DataInputStream dis = null;
			DataOutputStream dos=null;
			String text="";
			String key="";
			String data="";
			String fileSave="";
			//acept server from client
			Socket client=server.accept();
			try {
				//Nhận data từ client
				dos=new DataOutputStream(client.getOutputStream());
				dis=new DataInputStream(client.getInputStream());

				data= dis.readUTF();
				key= dis.readUTF();
				fileSave= dis.readUTF()+".txt";
				System.out.println(data+" "+key+" "+fileSave);							
				
				//kiem tra ton tai cua file va ghi file
				File file= new File(fileSave);
				if(!file.exists()) {
					file.createNewFile();
					System.out.println("Tao moi file "+fileSave);
					FileWriter fw= new FileWriter("C:\\Users\\vohoa\\eclipse-workspace\\LapTrinhMang\\"+fileSave);
					BufferedWriter bw= new BufferedWriter(fw);
					bw.write(data);
					bw.write(";");
					bw.write(key);
					bw.close();
				} else {
					System.out.println("File da ton tai");
					FileWriter fw= new FileWriter("C:\\Users\\vohoa\\eclipse-workspace\\LapTrinhMang\\"+fileSave);
					BufferedWriter bw= new BufferedWriter(fw);
					bw.write(data);
					bw.write(";");
					bw.write(key);
					bw.close();
				}								
				
				//doc file
				FileReader fr= new FileReader("C:\\Users\\vohoa\\eclipse-workspace\\LapTrinhMang\\"+fileSave);
				BufferedReader br= new BufferedReader(fr);
				String line= "";
				while(true) {
					line= br.readLine();
                    if(line==null) break;
                    String txt[]= line.split(";");
                    data=txt[0];
                    key=txt[1];
                    System.out.print("Doc file thanh cong !!!");
				}
				br.close();
				
				String dataLowerToUpper=LowerToUpper(data);
				String keyLowerToUpper=LowerToUpper(key);
				
				String keyGenerateKey=generateKey(dataLowerToUpper, keyLowerToUpper);
				String cipherText=cipherText(dataLowerToUpper, keyGenerateKey);
				
				System.out.println("Ciphertext : "+cipherText);
				//System.out.println("Original text: "+originalText(cipherText, cipherText));
				//System.out.println("Test"+vigenereUNICODE(cipherText,cipherText,true));
				text=listSoLanXuatHien(cipherText);
				String xauDaiNhat= timChuoiDaiNhat(cipherText);
				dos.writeUTF(cipherText);
				dos.writeUTF(xauDaiNhat);
				dos.writeUTF(text);
			}
			catch(IOException e) {
				e.printStackTrace();
				
			}
			//server.close();
		}
	}
}
