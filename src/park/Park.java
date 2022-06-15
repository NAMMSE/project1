package park;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Park {

	
public static void main(String[] args) {
		
		// 1. Class.forName()���� jdbc ����̹��� �ε��Ѵ�
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("����̹� �ε� �Ϸ�.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("����̹� �ε� �� �����߻�");
		}
		
		// 2. DriverManager Ŭ������ ���� DB�� �����Ѵ�
		try {// DB ���� ��� sqldeveloper���� �Ӽ� �߰� �� ��Ʈ�� SID / localhost �ڸ��� pc ip �Է��ص� �ȴ�
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr", "1234"); 
			System.out.println("���� ���� �Ϸ� : " + conn);
			
			// 3. ������ ���� ��ü�� ���� �������� �����Ѵ�
			
			// 3-1. ���� �غ��ϱ� (�����ݷ��� �����Ѵ�)
			PreparedStatement pstmt = conn.prepareStatement("select br_total_charge, count(bs_id), bs_name from (((bus_info inner join discount using(dc_id)) "
					+ "inner join terminal using(tml_id)) "
					+ "inner join bus_seat using (bi_id)) "
					+ "inner join (bus_reservation inner join sign_up using(user_id)) using (bs_id)");
			
			// 3-2. ���� �����ϱ� 
			ResultSet rs = pstmt.executeQuery();
			
			// 4. �޾ƿ� ����� �����Ѵ�
			while( rs.next()) { // next : Ŀ���� �ű�鼭 �������� ������ true ������ false
				System.out.printf("%d %d %s\n", 
						rs.getInt("br_total_charge"),
						rs.getInt("(bs_id)"),
						rs.getString("bs_name"));
			}
			
			// 5. �� ����� ������ �ݾ���� �Ѵ�. ���߿� ������ ������� �ݾ�����Ѵ�
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("���� ���� ����");
		}
	}
	
}
