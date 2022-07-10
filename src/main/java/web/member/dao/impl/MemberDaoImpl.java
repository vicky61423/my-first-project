package web.member.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import web.member.dao.MemberDao;
import web.member.vo.Member;

public class MemberDaoImpl implements MemberDao {

	private DataSource datasource;

	public MemberDaoImpl() throws NamingException {
		datasource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/Yokult");
	}

	@Override
	public Integer insert(Member member) {
		try (Connection conn = datasource.getConnection();
				PreparedStatement ps = conn.prepareStatement(
						"insert into MEMBER (MEMID, EMAIL, PASSWORD, FIRSTNAME, LASTNAME, BIRTH, CELLPHONE, PHONE, ADDR)"
								+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?);");) {
			ps.setString(1, member.getMemID());
			ps.setString(2, member.getMemEmail());
			ps.setString(3, member.getMemPassword());
			ps.setString(4, member.getMemFirstName());
			ps.setString(5, member.getMemLastName());
			ps.setDate(6, member.getMemBirth());
			ps.setString(7, member.getMemCellPhone());
			ps.setString(8, member.getMemPhone());
			ps.setString(9, member.getMemAddress());
			int rowCount = ps.executeUpdate();
			System.out.println("insert " + rowCount + "member.");
			return rowCount;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	@Override
	public Member selectByMemberIdAndPassword(Member member) {
		try (Connection conn = datasource.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("Select MEMID, FIRSTNAME, LASTNAME, EMAIL, BIRTH, CELLPHONE, ADDR from MEMBER where MEMID = ? and PASSWORD = ?");) {
			pstmt.setString(1, member.getMemID());
			pstmt.setString(2, member.getMemPassword());
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					System.out.println("存取成功");
					Member resultMember = new Member();
					resultMember.setMemID(rs.getString("MEMID"));
					resultMember.setMemFirstName(rs.getString("FIRSTNAME"));
					resultMember.setMemLastName(rs.getString("LASTNAME"));
					resultMember.setMemEmail(rs.getString("EMAIL"));
					resultMember.setMemBirth(rs.getDate("BIRTH"));
					resultMember.setMemCellPhone(rs.getString("CELLPHONE"));
					resultMember.setMemAddress(rs.getString("ADDR"));
					return resultMember;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Member update(Member member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(Member member) {
		// TODO Auto-generated method stub
		return 0;
	}

}
