package org.example;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate { //라이브러
    public void executeUpdate(User user, String sql, PreparedStatementSetter pss) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setter(pstmt);
//            pstmt.setString(1,user.getUserId());
//            pstmt.setString(2,user.getPassword());
//            pstmt.setString(3,user.getName());
//            pstmt.setString(4,user.getEmail());

            pstmt.executeUpdate();
        }finally {
            if(pstmt != null){
                pstmt.close();
            }

            if(con != null){
                con.close();
            }
        }
    }

    // 아이디로 검색 되는 데이터 찾기 함수
    public Object executeQuery(String userId,String sql,PreparedStatementSetter pss,RowMapper rowMapper) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = ConnectionManager.getConnection();
//            String sql = "SELECT userId,password,name,email FROM USERS WHERE userid = ?";
            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1,userId);
            pss.setter(pstmt);

            rs = pstmt.executeQuery();

            Object obj = null;
            if(rs.next()){ // 값이 있다면
                return rowMapper.map(rs);
//                obj = new User(
//                        rs.getString("userId"),
//                        rs.getString("password"),
//                        rs.getString("name"),
//                        rs.getString("email")
//                );
            }
            return obj;
        }finally {// 자원해제
            if(rs != null){
                rs.close();
            }
            if(pstmt != null){
                pstmt.close();
            }
            if(con != null){
                con.close();
            }
        }
    }
}
