package com.mycompany.lab_project;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.BufferedReader;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author shawa
 */
public class Social_Media_Helper {

    private static int currentId;
    private static String currentEmail;
    private static int selectedUserId;

    public static int getCurrentId() {
        return currentId;
    }

    public static int getSelectedUserId() {
        return selectedUserId;
    }

    public static String getCurrentEmail() {
        return currentEmail;
    }

    static String connectionString = "jdbc:mysql://127.0.0.1:3306/"
            + "socialmedia?user=root&password=password";

    static Connection conn = null;
    static Connection conn2 = null;

    public static boolean isEmailRegistered(String email) {
        try {
            conn2 = DriverManager.getConnection(connectionString);
            Statement stmt = conn2.createStatement();
            String query = "SELECT * FROM user WHERE email='" + email + "'";
            ResultSet rs = stmt.executeQuery(query);
            return rs.next();
        } catch (SQLException ex) {
            System.out.println("Error checking email registration: " + ex.getMessage());
            return false;
        } finally {
            if (conn2 != null) {
                try {
                    conn2.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Social_Media_Helper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static boolean InsertNewUser(User user) {

        try {
            conn = DriverManager.getConnection(connectionString);
            if (isEmailRegistered(user.email)) {
                JOptionPane.showMessageDialog(null, "Email is already registered!", "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            Statement stmt = conn.createStatement();
            String query = String.format(
                    "INSERT INTO user (name, middlename, lastname, phonenumber, day, month, year, email, password, gender, nationality) "
                    + "VALUES ('%s', '%s', '%s', '%s', %d, '%s', %d, '%s', '%s', '%s', '%s')",
                    user.name, user.middlename, user.lastname, user.phonenumber, user.day, user.month,
                    user.year, user.email, user.password, user.gender, user.nationality
            );

            stmt.executeUpdate(query);

            conn.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Social_Media_Helper.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Social_Media_Helper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static User SignIn(String email, String password) {
        User ruser = null;
        try {
            conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM user WHERE email='" + email + "' AND password='" + password + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                ruser = new User();
                ruser.iduser = rs.getInt("iduser");
                ruser.name = rs.getString("name");
                ruser.middlename = rs.getString("middlename");
                ruser.lastname = rs.getString("lastname");
                ruser.phonenumber = rs.getString("phonenumber");
                ruser.email = rs.getString("email");
                ruser.password = rs.getString("password");
                ruser.gender = rs.getString("gender");
                ruser.nationality = rs.getString("nationality");
                currentId = ruser.iduser;

            }
            conn.close();
        } catch (SQLException ex) {

            System.out.println(" error:" + ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Social_Media_Helper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return ruser;

    }

    public static boolean deleteUser(int userId) {
        try {
            conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();
            String Query = "DELETE FROM user WHERE iduser = " + userId;
            int result = stmt.executeUpdate(Query);
            return result > 0;
        } catch (SQLException ex) {
            System.out.println("error deleting user: " + ex.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Social_Media_Helper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static User getCurrentUser() {
        try {
            conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM user WHERE iduser=" + currentId;
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                User currentUser = new User();
                currentUser.iduser = rs.getInt("iduser");
                currentUser.name = rs.getString("name");
                currentUser.middlename = rs.getString("middlename");
                currentUser.lastname = rs.getString("lastname");
                currentUser.phonenumber = rs.getString("phonenumber");
                currentUser.email = rs.getString("email");
                currentUser.password = rs.getString("password");
                currentUser.gender = rs.getString("gender");
                currentUser.nationality = rs.getString("nationality");

                conn.close();
                return currentUser;
            }
        } catch (SQLException ex) {
            System.out.println("Error getting current user: " + ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Social_Media_Helper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public static User getCurrentUserFromEmail(String email) {
        try {
            conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM user WHERE email='" + email + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                User currentUser = new User();
                currentUser.iduser = rs.getInt("iduser");
                currentUser.name = rs.getString("name");
                currentUser.middlename = rs.getString("middlename");
                currentUser.lastname = rs.getString("lastname");
                currentUser.phonenumber = rs.getString("phonenumber");
                currentUser.email = rs.getString("email");
                currentUser.password = rs.getString("password");
                currentUser.gender = rs.getString("gender");
                currentUser.nationality = rs.getString("nationality");

                selectedUserId = currentUser.iduser;

                conn.close();
                return currentUser;
            }
        } catch (SQLException ex) {
            System.out.println("Error getting current user: " + ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Social_Media_Helper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public static User getCurrentUserFromId(int ID) {
        try {
            Connection conn;
            conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM user WHERE iduser='" + ID + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                User currentUser = new User();
                currentUser.iduser = rs.getInt("iduser");
                currentUser.name = rs.getString("name");
                currentUser.middlename = rs.getString("middlename");
                currentUser.lastname = rs.getString("lastname");
                currentUser.phonenumber = rs.getString("phonenumber");
                currentUser.email = rs.getString("email");
                currentUser.password = rs.getString("password");
                currentUser.gender = rs.getString("gender");
                currentUser.nationality = rs.getString("nationality");

                conn.close();
                return currentUser;
            }
        } catch (SQLException ex) {
            System.out.println("Error getting current user: " + ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Social_Media_Helper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return null;
    }

    public static boolean editUserInfo(int userId, String newName, String newLastName, String newPhoneNumber, String newPassword) {
        try {
            conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            String updateQuery = "UPDATE user SET " + "name = '" + newName + "', " + "lastname = '" + newLastName + "', " + "phonenumber = '" + newPhoneNumber + "',"
                    + " " + "password = '" + newPassword + "'"
                    + "WHERE iduser = " + userId;

            int res = stmt.executeUpdate(updateQuery);

            conn.close();

            return res > 0;
        } catch (SQLException ex) {
            System.out.println("Error editing user info: " + ex.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Social_Media_Helper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static boolean addPost(Post post) {
        try {
            conn = DriverManager.getConnection(connectionString);

            Statement stmt = conn.createStatement();
            String query = String.format(
                    "INSERT INTO user_posts (iduser, post_text) VALUES (%d, '%s')",
                    post.iduser, post.text
            );

            stmt.executeUpdate(query);

            conn.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error adding post: " + ex.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();

                } catch (SQLException ex) {
                    Logger.getLogger(Social_Media_Helper.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static boolean addFriend(int userId, int friendId) {
        try {
            conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            String checkQuery = String.format(
                    "SELECT * FROM user_friends WHERE (iduser = %d AND idfriend = %d) OR (iduser = %d AND idfriend = %d)",
                    userId, friendId, friendId, userId
            );

            ResultSet rs = stmt.executeQuery(checkQuery);

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "User is already your friend", "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            String insertQuery = String.format(
                    "INSERT INTO user_friends (iduser, idfriend) VALUES (%d, %d)",
                    userId, friendId
            );
            stmt.executeUpdate(insertQuery);

            return true;
        } catch (SQLException ex) {
            System.out.println("Error adding friend: " + ex.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();

                } catch (SQLException ex) {
                    Logger.getLogger(Social_Media_Helper.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static boolean deleteFriend(int userId, int friendId) {

        try {
            conn = DriverManager.getConnection(connectionString);
            Statement stm = conn.createStatement();

            // Create the SQL query using String.format
            String query = String.format("DELETE FROM user_friends WHERE (iduser = %d AND idfriend = %d)",
                    userId, friendId);

            // Execute the delete
            int rowsAffected = stm.executeUpdate(query);

            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println("Error deleting friend: " + ex.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();

                } catch (SQLException ex) {
                    Logger.getLogger(Social_Media_Helper.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static ArrayList<String> getUserPosts(int userId) {
        ArrayList<Integer> friendsIds = new ArrayList<>();
        ArrayList<String> userPosts = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM user_posts";
            ResultSet rs = stmt.executeQuery(query);

            friendsIds = getCurrentUserFriendsIds(userId);
            while (rs.next()) {
                int Id = rs.getInt("iduser");
                String postText = rs.getString("post_text");
                if (Id == userId || friendsIds.contains(Id)) {
                    userPosts.add(postText);
                }
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error retrieving user posts: " + ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();

                } catch (SQLException ex) {
                    Logger.getLogger(Social_Media_Helper.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return userPosts;
    }

    public static DefaultTableModel getCurrentUserPosts(int userId) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Post ID", "Post Text"});
        try {
            conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM user_posts WHERE iduser = " + userId;
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int postId = rs.getInt("iduser_posts");
                int userID = rs.getInt("iduser");
                String postText = rs.getString("post_text");

                tableModel.addRow(new Object[]{postId, postText});
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error retrieving user posts: " + ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();

                } catch (SQLException ex) {
                    Logger.getLogger(Social_Media_Helper.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return tableModel;
    }

    public static boolean deletePost(int postId) {
        try {
            conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            String deleteQuery = "DELETE FROM user_posts WHERE iduser_posts = " + postId;

            int res = stmt.executeUpdate(deleteQuery);

            conn.close();

            return res > 0;
        } catch (SQLException ex) {
            System.out.println("Error deleting post: " + ex.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Social_Media_Helper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static boolean editPost(int postId, String newText) {
        try {
            conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            String updateQuery = "UPDATE user_posts SET post_text = '" + newText + "' WHERE iduser_posts = " + postId;

            int res = stmt.executeUpdate(updateQuery);

            conn.close();

            return res > 0;
        } catch (SQLException ex) {
            System.out.println("Error editing post: " + ex.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Social_Media_Helper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static ArrayList<Integer> getCurrentUserFriendsIds(int userId) {
        ArrayList<Integer> friendIds = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();
            String query = "SELECT idfriend FROM user_friends WHERE iduser = " + userId;
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int friendId = rs.getInt("idfriend");
                friendIds.add(friendId);
            }
        } catch (SQLException ex) {
            System.out.println("Error getting friend IDs: " + ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();

                } catch (SQLException ex) {
                    Logger.getLogger(Social_Media_Helper.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return friendIds;
    }

    public static void writeToFile(String filePath, List<String> data) {
        try {
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
            fileWriter.close();
            System.out.println("Data has been written to the file.");
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());

        }

    }

    public static List<String> readFromFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(filePath);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            System.out.println("Data has been read from the file.");
            return lines;
        } catch (IOException e) {
            System.err.println("Error reading from the file: " + e.getMessage());
        }
        return null;
    }
}
