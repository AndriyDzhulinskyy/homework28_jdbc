package org.example;

import lombok.*;
import java.sql.*;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Std {
    private int id;
    private int age;
    private int groupId;
    private String name;

    private static final String DB_URL = "jdbc:mysql://robot-do-user-1968994-0.b.db.ondigitalocean.com:25060/dzhulinskyy";
    private static final String DB_USER = "doadmin";
    private static final String DB_PASSWORD = "AVNS_I6wlDKjGszZn1wvLr9t";
    public static final String SELECT_FROM_STUDENTS = "SELECT * FROM student_homework28";
    public static final String INSERT_STUDENT = "INSERT INTO student_homework28 (name, age, group_id) VALUES (?, ?, ?)";

    public void save(Std student) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(INSERT_STUDENT);

            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setInt(3, student.getGroupId());
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                assert conn != null;
                conn.rollback();
            } catch (SQLException ex) {

            }
            e.printStackTrace();
        } finally {
            try {
                assert conn != null;
                conn.close();
                assert ps != null;
                ps.close();
            } catch (SQLException e) {

            }
        }
    }

    public List<Std> findAll() {
        List<Std> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_FROM_STUDENTS);) {
            while (rs.next()) {
                Std student = buildStudent(rs);

                result.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Std buildStudent(ResultSet rs) throws SQLException {
        Std student = Std.builder()
                .id(rs.getInt("id"))
                .age(rs.getInt("age"))
                .groupId(rs.getInt("group_id"))
                .name(rs.getString("name"))
                .build();
        return student;
    }

    private static void close(PreparedStatement ps, ResultSet rs) {
        try {
            ps.close();
            rs.close();
        } catch (SQLException e) {

        }
    }
}
