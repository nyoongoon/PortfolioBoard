package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import vo.Member;

public class MemberDao {
	
	DataSource ds;
	
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
	

	public List<Member> selectList() throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT MNO, MNAME, EMAIL, CRE_DATE" + " FROM MEMBERS" + " ORDER BY MNO ASC");
			ArrayList<Member> members = new ArrayList<Member>();
			while (rs.next()) {
				members.add(new Member().setNo(rs.getInt("MNO")).setName(rs.getString("MNAME"))
						.setEmail(rs.getString("EMAIL")).setCreatedDate(rs.getDate("CRE_DATE")));
			}
			return members;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}
		}
	}

	public void insert(Member member) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = ds.getConnection();
			pstmt = connection.prepareStatement(
					"INSERT INTO MEMBERS(EMAIL, PWD, MNAME, CRE_DATE, MOD_DATE)" + " VALUES(?, ?, ?, NOW(), NOW())");
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}
		}
	}

	public void delete(int no) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			stmt.executeUpdate("DELETE FROM MEMBERS WHERE MNO =" + no);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}
		}
	}

	public Member selectOne(int no) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select MNO, EMAIL, MNAME, CRE_DATE from MEMBERS" + " where MNO=" + no);
			rs.next();
			Member member = new Member().setNo(rs.getInt("MNO")).setName(rs.getString("MNAME"))
					.setEmail(rs.getString("EMAIL")).setCreatedDate(rs.getDate("CRE_DATE"));
			return member;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}
		}
	}

	public void update(Member member) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = ds.getConnection();
			pstmt = connection.prepareStatement("UPDATE MEMBERS SET EMAIL=?, MNAME=?, MOD_DATE=now()" + " WHERE MNO=?");
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getName());
			pstmt.setInt(3, member.getNo());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			pstmt.setString(1, member.getEmail());
			throw e;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}
		}
	}
	
	public Member exist(String email, String password)throws Exception{
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		connection = ds.getConnection();
		pstmt = connection.prepareStatement("SELECT MNAME, EMAIL FROM MEMBERS WHERE EMAIL=? AND PWD=?");
		pstmt.setString(1, email);
		pstmt.setString(2, password);
		rs = pstmt.executeQuery();
		rs.next();
		Member member = new Member().setName(rs.getString("MNAME")).setEmail(rs.getString("EMAIL"));
		return member;
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}
		}
	}
}
