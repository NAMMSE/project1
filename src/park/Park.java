package park;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Park {

	
public static void main(String[] args) {
		
		// 1. Class.forName()으로 jdbc 드라이버를 로드한다
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로딩 완료.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩 중 에러발생");
		}
		
		// 2. DriverManager 클래스를 통해 DB에 연결한다
		try {// DB 접속 경로 sqldeveloper에서 속성 추가 시 포트랑 SID / localhost 자리에 pc ip 입력해도 된다
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr", "1234"); 
			System.out.println("연결 생성 완료 : " + conn);
			
			// 3. 생성된 연결 객체를 통해 쿼리문을 전달한다
			
			// 3-1. 구문 준비하기 (세미콜론은 생략한다)
			PreparedStatement pstmt = conn.prepareStatement("select br_total_charge, count(bs_id), bs_name from (((bus_info inner join discount using(dc_id)) "
					+ "inner join terminal using(tml_id)) "
					+ "inner join bus_seat using (bi_id)) "
					+ "inner join (bus_reservation inner join sign_up using(user_id)) using (bs_id)");
			
			// 3-2. 구문 실행하기 
			ResultSet rs = pstmt.executeQuery();
			
			// 4. 받아온 결과를 실행한다
			while( rs.next()) { // next : 커서를 옮기면서 다음행이 있으면 true 없으면 false
				System.out.printf("%d %d %s\n", 
						rs.getInt("br_total_charge"),
						rs.getInt("(bs_id)"),
						rs.getString("bs_name"));
			}
			
			// 5. 다 사용한 연결은 닫아줘야 한다. 나중에 생성한 순서대로 닫아줘야한다
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("연결 생성 실패");
		}
	}
	
}
