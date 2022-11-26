package org.example;

import java.sql.*;

public class UserDao {
//    public void create(User user) throws SQLException {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//
//        try{
//            con = ConnectionManager.getConnection();
//            String sql = "INSERT INTO USERS VALUES (?,?,?,?)";
//            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1,user.getUserId());
//            pstmt.setString(2,user.getPassword());
//            pstmt.setString(3,user.getName());
//            pstmt.setString(4,user.getEmail());
//
//            pstmt.executeUpdate();
//        }finally {
//            if(pstmt != null){
//                pstmt.close();
//            }
//
//            if(con != null){
//                con.close();
//            }
//        }
//    }

    public void create(User user) throws SQLException{
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        String sql = "INSERT INTO USERS VALUES (?,?,?,?)";
        jdbcTemplate.executeUpdate(user, sql, new PreparedStatementSetter() {
            @Override
            public void setter(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1,user.getUserId());
                pstmt.setString(2,user.getPassword());
                pstmt.setString(3,user.getName());
                pstmt.setString(4,user.getEmail());
            }
        });
    }

//    public User findByUserId(String userId) throws SQLException {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//
//        try{
//            con = ConnectionManager.getConnection();
//            String sql = "SELECT userId,password,name,email FROM USERS WHERE userid = ?";
//            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1,userId);
//
//
//            rs = pstmt.executeQuery();
//
//            User user = null;
//            if(rs.next()){ // 값이 있다면
//                user = new User(
//                        rs.getString("userId"),
//                        rs.getString("password"),
//                        rs.getString("name"),
//                        rs.getString("email")
//                );
//            }
//            return user;
//        }finally {// 자원해제
//            if(rs != null){
//                rs.close();
//            }
//            if(pstmt != null){
//                pstmt.close();
//            }
//            if(con != null){
//                con.close();
//            }
//        }
//    }

    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        String sql = "SELECT userId,password,name,email FROM USERS WHERE userid = ?";

        return (User) jdbcTemplate.executeQuery(userId, sql, new PreparedStatementSetter() {
            @Override
            public void setter(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        }, new RowMapper() {
            @Override
            public Object map(ResultSet resultSet) throws SQLException {
                return new User(
                        resultSet.getString("userId"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                );
            }
        });
    }
}
