package smanageT;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class EmpInsert extends JDialog{
	
	private File selectedFile;
	
	public EmpInsert(Connection con) {
		
		// 입력 전체패널
		JPanel ins_panel = new JPanel(new BorderLayout());
		add(ins_panel, BorderLayout.CENTER);
		
		// 입력상단 패널 및 버튼 추가
		JPanel ins_top = new JPanel();
		ins_top.setLayout(new FlowLayout(0,10,5));
		JRadioButton ins_job1 = new JRadioButton("정규직");
		JRadioButton ins_job2 = new JRadioButton("비정규직");
		ins_job1.setBackground(new Color(46, 139, 87));
		ins_job1.setForeground(new Color(255, 255, 255));
		ins_job2.setBackground(new Color(46, 139, 87));
		ins_job2.setForeground(new Color(255, 255, 255));
		ButtonGroup group = new ButtonGroup();
		group.add(ins_job1);
		group.add(ins_job2);
		ins_top.add(ins_job1);
		ins_top.add(ins_job2);
		
		ins_top.setBackground(new Color(143, 188, 143));
		ins_panel.add(ins_top, BorderLayout.NORTH);
		
		// 입력센터 패널 및 정보 입력칸 추가
		JPanel ins_center = new JPanel(new GridLayout(5, 2, 20, 30));
		JPanel bgl = new JPanel(new GridLayout(1, 2, 10, 10));
		
		// 센터 라벨
		JLabel name, tel, level, addr, dept, hiredate, email, msgid, brd, male;
		name = new JLabel("사원명: ");
		tel = new JLabel("연락처: ");
		level = new JLabel("직급: ");
		addr = new JLabel("주소: ");
		dept = new JLabel("부서: ");
		hiredate = new JLabel("입사일: ");
		email = new JLabel("이메일: ");
		msgid = new JLabel("메신저ID: ");
		brd = new JLabel("생년월일: ");
		male = new JLabel("성별: ");
		
		// 센터 텍스트 필드
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		JTextField i_name, i_tel, i_level, i_addr, i_dept, i_hiredate, i_email, i_msgid, i_brd;
		i_name = new JTextField(20);
		i_tel = new JTextField(20);
		i_tel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String enteredText = i_tel.getText();
                // 전화번호 형식을 체크하는 정규 표현식 패턴
                Pattern pattern = Pattern.compile("\\d{3}-\\d{4}-\\d{4}");
                Matcher matcher = pattern.matcher(enteredText);

                if (matcher.matches()) {
                    JOptionPane.showMessageDialog(null, "올바른 전화번호 형식입니다.");
                } else {
                    JOptionPane.showMessageDialog(null, "전화번호 형식은 '010-1234-1234'와 같이 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                    // 텍스트 필드를 초기화하거나 이전 값으로 되돌리기
                    i_tel.setText("");
                }
            }
        });
		i_level = new JTextField(20);
		i_addr = new JTextField(20);
		i_dept = new JTextField(20);
		i_hiredate = new JTextField(20);
		i_hiredate.setText(dateFormat.format(new Date()));     // 현재날짜 삽입
		i_hiredate.setEditable(false);      // 입사일 필드 고정
		i_email = new JTextField(20);
		i_msgid = new JTextField(20);
		i_brd = new JTextField(20);
		i_brd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String enteredText = i_brd.getText();
                // 전화번호 형식을 체크하는 정규 표현식 패턴
                Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
                Matcher matcher = pattern.matcher(enteredText);

                if (matcher.matches()) {
                    JOptionPane.showMessageDialog(null, "올바른 생년월일 형식입니다.");
                } else {
                    JOptionPane.showMessageDialog(null, "생년월일 형식은 'yyyy-mm-dd'와 같이 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                    // 텍스트 필드를 초기화하거나 이전 값으로 되돌리기
                    i_brd.setText("");
                }
            }
        });
		JRadioButton i_gender1 = new JRadioButton("남");
		JRadioButton i_gender2 = new JRadioButton("여");
		ButtonGroup group2 = new ButtonGroup();
		group2.add(i_gender1);
		group2.add(i_gender2);
		
		// 센터 라벨 및 텍스트 필드 추가
		ins_center.add(name);
		ins_center.add(i_name);
		ins_center.add(tel);
		ins_center.add(i_tel);
		ins_center.add(level);
		ins_center.add(i_level);
		ins_center.add(addr);
		ins_center.add(i_addr);
		ins_center.add(dept);
		ins_center.add(i_dept);
		ins_center.add(hiredate);
		ins_center.add(i_hiredate);
		ins_center.add(email);
		ins_center.add(i_email);
		ins_center.add(msgid);
		ins_center.add(i_msgid);
		ins_center.add(brd);
		ins_center.add(i_brd);
		ins_center.add(male);
		bgl.add(i_gender1);
		bgl.add(i_gender2);
		ins_center.add(bgl);
		
		TitledBorder border = BorderFactory.createTitledBorder("상세정보");  // 패널 구분선 생성
		border.setTitleColor(new Color(47, 79, 79));	// 구분선 제목 색상 설정
		border.setBorder(BorderFactory.createLineBorder(Color.GRAY));    // 구분선 색상 설정
		ins_center.setBorder(border);
		
		ins_center.setBackground(new Color(255, 250, 250));
		bgl.setBackground(new Color(255, 250, 250));
		i_gender1.setBackground(new Color(255, 250, 250));
		i_gender2.setBackground(new Color(255, 250, 250));
		ins_panel.add(ins_center, BorderLayout.CENTER);
		
		
		// 입력하단 패널 및 버튼 추가
		JPanel ins_bottom = new JPanel();
		ins_bottom.setLayout(new FlowLayout(2,20,5));
		JLabel img = new JLabel("[이미지 첨부파일] ");
		img.setForeground(new Color(47, 79, 79));
		JLabel pathLabel = new JLabel("선택한 파일 경로 여기에 표시됩니다.");
		pathLabel.setForeground(new Color(0, 0, 139));
		JButton chooseButton = new JButton("파일 선택");
		chooseButton.setUI(new RoundedButton());
		chooseButton.setBackground(new Color(60, 179, 113));
		chooseButton.setForeground(new Color(255, 255, 255));
		JButton i_save = new JButton("저장");
		i_save.setUI(new RoundedButton());
		i_save.setBackground(new Color(60, 179, 113));
		i_save.setForeground(new Color(255, 255, 255));
		JButton i_cancel = new JButton("취소");
		i_cancel.setUI(new RoundedButton());
		i_cancel.setBackground(new Color(60, 179, 113));
		i_cancel.setForeground(new Color(255, 255, 255));
		
		// 파일 선택 버튼을 눌렀을때 작동
		chooseButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	// 파일 선택기
	        	JFileChooser i_imgfile = new JFileChooser();
	        	// 사용자가 파일 선택하도록함
	        	int returnValue = i_imgfile.showOpenDialog(null);
	        	// 사용자가 열기 버튼을 눌렀을때
	        	if (returnValue == JFileChooser.APPROVE_OPTION) {
	        		selectedFile = i_imgfile.getSelectedFile();
		            pathLabel.setText(selectedFile.getName());
		        }
	        }
		});
	    
		// 저장 버튼을 눌렀을때 작동
		i_save.addActionListener(new ActionListener() {
	        @Override
			public void actionPerformed(ActionEvent e) {

				if (
						(ins_job1.isSelected() || ins_job2.isSelected()) && (i_gender1.isSelected() || i_gender2.isSelected()) 
						&& !i_name.getText().equals("") && !i_tel.getText().equals("") && !i_level.getText().equals("")
						&& !i_addr.getText().equals("") && !i_dept.getText().equals("") && !i_email.getText().equals("")
						&& !i_msgid.getText().equals("") && !i_brd.getText().equals("")
						)
						{
					JFrame frame = new JFrame("알림창");
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					int confirmed = JOptionPane.showConfirmDialog(frame, "정보를 저장하시겠습니까?", "입력 확인",
							JOptionPane.OK_CANCEL_OPTION);

					if (confirmed == JOptionPane.OK_OPTION) {
						// 데이터 삽입 로직
						try {
							// 메인 사원조회 쿼리
							String sql = " insert into emptable "
									+ " values(seq_empno.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?) ";

							PreparedStatement ps = con.prepareStatement(sql);
							ps.setString(1, i_name.getText());
							ps.setString(2, i_tel.getText());
							ps.setString(3, i_level.getText());
							if (!chooseButton.getModel().isPressed()) {
								ps.setString(4, "no img");
							} else {
								ps.setString(4, pathLabel.getText());
							}
							ps.setString(5, i_addr.getText());
							ps.setString(6, i_dept.getText());
							ps.setString(7, i_email.getText());
							if (ins_job1.isSelected()) {
								ps.setString(8, ins_job1.getText());
							} else if (ins_job2.isSelected()) {
								ps.setString(8, ins_job2.getText());
							}
							ps.setString(9, i_msgid.getText());
							if (i_gender1.isSelected()) {
								ps.setString(10, i_gender1.getText());
							} else if (i_gender2.isSelected()) {
								ps.setString(10, i_gender2.getText());
							}
							ps.setString(11, i_brd.getText());
							ps.setString(12, "계좌번호 입력");

							ps.executeUpdate();

							int empno = -1;
							sql = "SELECT seq_empno.CURRVAL FROM dual";
							ps = con.prepareStatement(sql);
							ResultSet rs = ps.executeQuery();
							if (rs.next()) {
								empno = rs.getInt(1);
							}
							rs.close();

							// jobst 테이블에 데이터 추가
							sql = "INSERT INTO jobst (empno, estate, hiredate, quitdate) "
									+ "VALUES (?, '재직', TRUNC(SYSDATE), NULL)";
							ps = con.prepareStatement(sql);
							ps.setInt(1, empno);
							ps.executeUpdate();

							// paytable 테이블에 데이터 추가
							sql = "INSERT INTO paytable (empno) VALUES (?)";
							ps = con.prepareStatement(sql);
							ps.setInt(1, empno);
							ps.executeUpdate();

							// annual 테이블에 데이터 추가
							sql = "INSERT INTO annual (empno, ename, a_left) "
						             + "SELECT empno, ename, 11 FROM emptable WHERE empno = ?";
							ps = con.prepareStatement(sql);
							ps.setInt(1, empno);
							ps.executeUpdate();
							
					

							ps.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}

						if (selectedFile != null) {
							// 예시 경로에 이미지를 업로드하는 메서드 호출
							try {
								uploadImage(selectedFile);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						// 데이터 다시 불러오기
						dispose();
					} else {
						setVisible(true);
					}
				} else {
					JOptionPane.showMessageDialog(null, "전부 입력하세요!!");
				}

			}
		});

		// 취소 버튼을 눌렀을때 작동
		i_cancel.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	dispose();
		    }
		});
	    
		// 입력하단 버튼 및 라벨 추가
		ins_bottom.add(img);
		ins_bottom.add(pathLabel);
		ins_bottom.add(chooseButton);
		ins_bottom.add(i_save);
		ins_bottom.add(i_cancel);
		
		ins_bottom.setBackground(new Color(240, 255, 240));
		ins_panel.add(ins_bottom, BorderLayout.SOUTH);
		
		setTitle("새 사원정보 입력");
		setSize(700,400);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	// 예시 경로에 이미지를 업로드하는 메서드
    private void uploadImage(File imageFile) throws IOException {
        // 업로드할 경로 설정 (경로 바꿔주기)
    	// Path destinationPath = Path.of("D:\\Class\\WorkSpace\\JDBCClass\\HRPro\\src\\Image\\" + imageFile.getName());
    	Path directoryPath = Paths.get("C:\\test");
     	Files.createDirectories(directoryPath);
        Path destinationPath = Path.of(directoryPath + "\\" + imageFile.getName());
         
        // 이미지 파일을 복사
        Files.copy(imageFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }
    
}
