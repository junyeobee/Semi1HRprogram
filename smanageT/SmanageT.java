package smanageT;

import java.sql.*;
import java.util.*;

public class SmanageT {

	// 메뉴 출력
	void PrintMenu() {
		System.out.println("===================");
		System.out.println("1.학생 정보 입력");
		System.out.println("2.학생 성적 입력");
		System.out.println("3.학생 정보 출력");
		System.out.println("4.모든 학생 정보 입력");
		System.out.println("5.종료");
		System.out.println("===================");
	}

	// 학생 정보 입력받는 메소드
	void SetStudent(Connection con, Scanner sc) {
		try {
			String sql = "insert into student values(std_seq.nextval,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			System.out.print("이름을 입력하세요\n입력>");
			ps.setString(1, sc.nextLine());
			System.out.print("나이를 입력하세요\n입력>");
			ps.setInt(2, sc.nextInt());
			sc.nextLine();
			System.out.print("주소를 입력하세요\n입력>");
			ps.setString(3, sc.nextLine());
			System.out.print("전화번호를 입력하세요\n입력>");
			ps.setString(4, sc.nextLine());

			int a = ps.executeUpdate();

			if (a != 0) {
				System.out.println("입력 완료, " + a + "회 실행됨 ");
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 학생 점수 입력받는 메소드
	void SetScore(Scanner sc, Connection con) {
		try {
			String sql2 = "select * from student order by snum";
			PreparedStatement ps = con.prepareStatement(sql2);
			ResultSet rs = ps.executeQuery();
			System.out.println("==================학생정보=======================");

			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4)
						+ "\t" + rs.getString(5));
			}

			System.out.println("===============================================");
			System.out.print("점수를 입력할 학생의 학번을 적어주세요\n>");
			int snum = sc.nextInt();
			sc.nextLine();
			HashMap<Integer, Boolean> arr = getId(con);
			if (!arr.get(snum)) {
				System.out.println("=================================================================");
				String sql = "insert into score values((select snum from student where snum = ?),?,?,?)";
				ps = con.prepareStatement(sql);
				ps.setInt(1, snum);
				System.out.print("===국어 점수===\n입력>");
				ps.setInt(2, sc.nextInt());
				sc.nextLine();
				System.out.print("===영어 점수===\n입력>");
				ps.setInt(3, sc.nextInt());
				sc.nextLine();
				System.out.print("===수학 점수===\n입력>");
				ps.setInt(4, sc.nextInt());
				sc.nextLine();
				ps.executeUpdate();
				System.out.println("입력 완료되었습니다.");
			} else if (arr.get(snum)) {
				System.out.print("이미 등록된 학생입니다. 성적을 업데이트 하시겠습니까?(y/n)\n입력>");
				String sel = sc.nextLine();
				if (sel.equals("y") || sel.equals("Y")) {
					System.out.println("=================================================================");
					String sql = "update score set kor = ?, eng = ?, mat = ? where snum = ?";
					ps = con.prepareStatement(sql);
					ps.setInt(4, snum);
					
					System.out.print("국어 점수를 입력하세요\n입력>");
					ps.setInt(1,sc.nextInt());
					sc.nextLine();
					
					System.out.print("영어 점수를 입력하세요\n입력>");
					ps.setInt(2,sc.nextInt());
					sc.nextLine();
					
					System.out.print("수학 점수를 입력하세요\n입력>");
					ps.setInt(3,sc.nextInt());
					sc.nextLine();
					
					ps.executeQuery();
					System.out.println("수정되었습니다.");
					
				} else if (sel.equals("n") || sel.equals("N")) {
					System.out.println("취소하셨습니다.");
				}
			}
			System.out.println("=================================");
			ps.close();
		} catch (NullPointerException e) {
			System.out.println("없는 학생입니다.");
		} catch (Exception e) {
			System.out.println("정의되지 않은 에러입니다.");
			try {
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.exit(0);
		}
	}

	// 특정 학생 출력
	void printStu(Connection con, Scanner sc) {
		String sql2 = "select * from student order by snum";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql2);
			ResultSet rs = ps.executeQuery();
			System.out.println("==================학생정보=======================");

			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4)
						+ "\t" + rs.getString(5));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		System.out.println("=============== ================================");
		
		ArrayList<Integer> arr = returnArr(con);

		System.out.print("학번을 입력하세요\n입력>");
		int hb = sc.nextInt();

		boolean bool = false;
		for (int i = 0; i < arr.size(); i++) {
			if (hb == arr.get(i)) {
				bool = true;
			}
		}
		if (bool) {
			try {
				String sql = "select * from student,score where score.snum = student.snum and score.snum = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, hb);
				ResultSet rs = ps.executeQuery();
				System.out.println("학번\t이름\t나이\t주소\t전화번호\t\t국어\t수학\t영어");
				System.out.println("=======================================================================");
				while (rs.next()) {
					System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t"
							+ rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getInt(7) + "\t" + rs.getInt(8)
							+ "\t" + rs.getInt(9));
				}
				System.out.println("========================================================================");
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("====================");
			System.out.println("없는 학생입니다.");
			System.out.println("====================");
		}

	}

	void printAllStu(Connection con) {

		String sql = "SELECT * FROM student LEFT OUTER JOIN score ON student.snum = score.snum where score.snum is null order by student.snum";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			System.out.println("============================================================================");
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4)
						+ "\t" + rs.getString(5));

			}
			sql = "SELECT * FROM student LEFT OUTER JOIN score ON student.snum = score.snum where score.snum is not null order by student.snum";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4)
						+ "\t" + rs.getString(5) + "\t\t" + rs.getInt(7) + "\t" + rs.getInt(8) + "\t" + rs.getInt(9));

			}
			System.out.println("============================================================================");
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 학번 배열로 받음
	ArrayList<Integer> returnArr(Connection con) {

		String sql = "select snum from student";
		ArrayList<Integer> arr = new ArrayList<Integer>();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				arr.add(rs.getInt(1));
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		for(int i = 0; i < arr.size(); i++) {
//			System.out.println(arr.get(i));
//		}
		return arr;
	}

	// 해당 학번을 가진 학생의 성적을 구하는 메소드, 성적있으면 true, 없으면 false
	HashMap<Integer, Boolean> getId(Connection con) {
		HashMap<Integer, Boolean> arr = new HashMap<Integer, Boolean>();
		try {
			String sql = "select snum from student";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int a = rs.getInt(1);
				arr.put(a, true);
			}
			sql = "select snum from student minus select snum from score";
			while (rs.next()) {
				int a = rs.getInt(1);
				arr.put(a, false);
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}

	//
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		SmanageT smt = new SmanageT();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String usr = "scott";
			String pwd = "1234";
			Connection con = DriverManager.getConnection(url, usr, pwd);
			System.out.println("===학사관리 프로그램===");
			int i = 0;
			while (i != 5) {
				smt.PrintMenu();
				System.out.print("입력>");
				i = sc.nextInt();
				sc.nextLine();
				switch (i) {
				case 1:
					smt.SetStudent(con, sc);
					break;
				case 2:
					smt.SetScore(sc, con);
					break;
				case 3:
					smt.printStu(con, sc);
					break;
				case 4:
					smt.printAllStu(con);
					break;
				case 5:
					System.out.println("종료합니다.");
					con.close();
					System.exit(0);
				default:
					System.out.println("올바른 숫자를 입력하세요");
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("올바른 값을 입력하세요");
			System.exit(0);
		}

	}

}
