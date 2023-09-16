import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

/**
 * Client connects to Server and instantiates a new Thread each time the Client is run. Client reads messages from the Server,
 * and displays GUIS based on information sent from Client.
 *
 * @author Chun-Yang Lee, Joshua Ho, Hpung San Aung, Margaret Haydock, Soumya Kakarlapudi Purdue CS - L21
 * @version 12/13/2021
 */

public class Client {
    public static void main(String[] args) {

        Thread thread1 = new Thread(new Server());
        thread1.start();

        JOptionPane.showMessageDialog(null, "Welcome", "Log In", JOptionPane.INFORMATION_MESSAGE);
        try {
            //connects to server
            Socket socket = new Socket("localHost", 4242);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            //sends connected message
            String connectedMessage = reader.readLine();
            if (connectedMessage.equals("connected")) {
                JOptionPane.showMessageDialog(null, "Connection established!", "Connected!",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            //loop so user can login again if they log out
            while (true) {
                //drop down menu teacher or student
                String[] options = {"teacher", "student"};
                Object teacherOrStudent = JOptionPane.showInputDialog(null, "teacher or student", "login",
                        JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if (teacherOrStudent == null) {
                    JOptionPane.showMessageDialog(null, "Farewell!", "Exit", JOptionPane.PLAIN_MESSAGE);
                    writer.println("return");
                    writer.flush();
                    return;
                }
                String teacherOrStudentString = teacherOrStudent.toString();

                writer.println(teacherOrStudentString);
                writer.flush();
                reader.readLine();
                //drop down menu login or create account
                String[] options1 = {"login", "create account"};
                Object loginOrCreate = JOptionPane.showInputDialog(null, "Would you like to login " +
                        "or create an account", "login", JOptionPane.PLAIN_MESSAGE, null, options1, options1[0]);
                if (loginOrCreate == null) {
                    JOptionPane.showMessageDialog(null, "Farewell!", "Exit", JOptionPane.PLAIN_MESSAGE);
                    writer.println("return");
                    writer.flush();
                    return;
                }
                String loginOrCreateString = loginOrCreate.toString();

                writer.println(loginOrCreateString);
                writer.flush();
                reader.readLine();

                String choice1 = "";
                String choice2 = "";
                if (teacherOrStudentString.equals("teacher")) {
                    if (loginOrCreateString.equals("login")) {
                        //teacher login
                        String testUsername = JOptionPane.showInputDialog(null, "Username:",
                                "login", JOptionPane.PLAIN_MESSAGE);
                        if (testUsername == null) {
                            break;
                        }
                        String testPassword = JOptionPane.showInputDialog(null, "Password:",
                                "login", JOptionPane.PLAIN_MESSAGE);
                        if (testPassword == null) {
                            break;
                        }
                        writer.println(testUsername + " " + testPassword);
                        writer.flush();

                        String successfulLogin = reader.readLine();
                        if (successfulLogin.equals("true")) {
                            JOptionPane.showMessageDialog(null, "Successful login", "logged in",
                                    JOptionPane.PLAIN_MESSAGE);
                            choice1 = "";
                            while (true) {
                                boolean correctChoice = false;
                                while (!correctChoice) {
                                    choice1 = JOptionPane.showInputDialog(null, "Main Menu:\n1: Courses" +
                                            "\n2: Quizzes\n3: Account\n4: Save and Exit to Login Menu\n" +
                                            "5: Save and Exit App ", "teacher menu", JOptionPane.PLAIN_MESSAGE);
                                    if (choice1 == null) {
                                        writer.println("null");
                                        writer.flush();
                                        reader.readLine();
                                        break;
                                    }
                                    if (choice1.equals("1") || choice1.equals("2") || choice1.equals("3") ||
                                            choice1.equals("4") || choice1.equals("5")) {
                                        correctChoice = true;

                                    } else {
                                        JOptionPane.showMessageDialog(null, "Enter a correct choice",
                                                "error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                if (choice1 == null) {
                                    break;
                                }
                                writer.println(choice1);
                                writer.flush();
                                String received = reader.readLine();

                                switch (choice1) {
                                    //courses menu
                                    case "1":

                                        boolean correctOption = false;
                                        String courseOption = "1";
                                        while (!correctOption) {
                                            courseOption = JOptionPane.showInputDialog(null,
                                                    "Course Menu\n1: Create Course\n2: Remove Course\n3: View "
                                                            + "Courses\n4: Add Student to Course\n5: Return",
                                                    "Course menu", JOptionPane.PLAIN_MESSAGE);
                                            if (courseOption == null) {
                                                break;
                                            }
                                            if (courseOption.equals("1") || courseOption.equals("2") ||
                                                    courseOption.equals("3") || courseOption.equals("4") ||
                                                    courseOption.equals("5")) {
                                                correctOption = true;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Enter a correct choice",
                                                        "error", JOptionPane.ERROR_MESSAGE);
                                            }
                                        }
                                        if (courseOption == null) {
                                            break;
                                        }
                                        switch (courseOption) {
                                            //add course
                                            case "1":
                                                writer.println("1");
                                                writer.flush();
                                                reader.readLine();
                                                String courseName = "";

                                                courseName = JOptionPane.showInputDialog(null,
                                                        "Enter a course name you would like to add", "course name",
                                                        JOptionPane.QUESTION_MESSAGE);
                                                if (courseName == null) {
                                                    writer.println("break");
                                                    writer.flush();
                                                    reader.readLine();
                                                    break;
                                                }

                                                writer.println(courseName);
                                                writer.flush();
                                                String added = reader.readLine();
                                                if (added.equals("true")) {
                                                    JOptionPane.showMessageDialog(null, "Course added!",
                                                            "Course", JOptionPane.INFORMATION_MESSAGE);
                                                } else {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Course couldn't be added!", "Course",
                                                            JOptionPane.ERROR_MESSAGE);
                                                }
                                                break;

                                            //remove course
                                            case "2":
                                                writer.println("2");
                                                writer.flush();
                                                reader.readLine();
                                                String courseName2 = "";
                                                courseName2 = JOptionPane.showInputDialog(null,
                                                        "Enter a course name you would like to remove", "course name",
                                                        JOptionPane.QUESTION_MESSAGE);
                                                if (courseName2 == null) {
                                                    writer.println("break");
                                                    writer.flush();
                                                    reader.readLine();
                                                    break;
                                                }

                                                writer.println(courseName2);
                                                writer.flush();
                                                String removed = reader.readLine();
                                                if (removed.equals("true")) {
                                                    JOptionPane.showMessageDialog(null, "Course removed!",
                                                            "Course", JOptionPane.INFORMATION_MESSAGE);
                                                } else {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Course couldn't be removed!", "Course",
                                                            JOptionPane.ERROR_MESSAGE);
                                                }
                                                break;

                                            //view courses
                                            case "3":
                                                writer.println("3");
                                                writer.flush();
                                                String teachersCourses = "";
                                                String addedCourse = "";
                                                while (!addedCourse.equals("break")) {
                                                    addedCourse = reader.readLine();
                                                    if (addedCourse.equals("break")) {
                                                        break;
                                                    }
                                                    teachersCourses += addedCourse + "\n";
                                                    writer.println();
                                                    writer.flush();
                                                }

                                                JOptionPane.showMessageDialog(null, teachersCourses,
                                                        "Courses", JOptionPane.PLAIN_MESSAGE);
                                                break;

                                            //add student to course
                                            case "4":
                                                writer.println("4");
                                                writer.flush();
                                                reader.readLine();
                                                String courseAddStudent = JOptionPane.showInputDialog(null,
                                                        "Which course would you like to add a student to?",
                                                        "Add Student", JOptionPane.QUESTION_MESSAGE);
                                                writer.println(courseAddStudent);
                                                writer.flush();
                                                String exists = reader.readLine();
                                                if (exists.equals("true")) {
                                                    String studentName = JOptionPane.showInputDialog(null,
                                                            "Type a student email to add", "Add Student",
                                                            JOptionPane.QUESTION_MESSAGE);
                                                    writer.println(studentName);
                                                    writer.flush();
                                                    String studentExists = reader.readLine();
                                                    if (studentExists.equals("false")) {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Student doesn't exist", "Add Student",
                                                                JOptionPane.ERROR_MESSAGE);
                                                    } else {
                                                        writer.println("test"); //remove "test" after testing
                                                        writer.flush();
                                                        String added2 = reader.readLine();
                                                        if (added2.equals("true")) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "Student added", "Add Student",
                                                                    JOptionPane.PLAIN_MESSAGE);
                                                        } else {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "Student is already in course", "Add Student",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                        }
                                                    }
                                                } else {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Course doesn't exist", "Add Student",
                                                            JOptionPane.ERROR_MESSAGE);
                                                }
                                                writer.println();
                                                writer.flush();

                                                break;
                                            //return to main menu
                                            case "5":
                                                writer.println("5");
                                                writer.flush();
                                                break;
                                        }

                                        break;
                                    //quizzes menu
                                    case "2":
                                        try {
                                            String quizOption = "";
                                            String[] choices = {"1", "2", "3", "4", "5", "6", "7"};
                                            quizOption = (String) JOptionPane.showInputDialog(null,
                                                    "Quiz Menu\n1: Create Quiz\n2: Edit Quiz\n3: Delete Quiz\n4: View Quiz\n5: Grade Quiz\n6: See Student Submissions\n7: Return",
                                                    "Quiz menu", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
                                            if (quizOption == null) {
                                                break;
                                            }
                                            switch (quizOption) {
                                                //create quiz
                                                case "1":
                                                    writer.println("1");
                                                    writer.flush();
                                                    String b = reader.readLine();
                                                    String addquizOption = "0";
                                                    String[] choice = {"1", "2", "3"};
                                                    addquizOption = (String) JOptionPane.showInputDialog(null,
                                                            "Would you like to add a quiz\n1: From a File\n2: Manually\n3: Return",
                                                            "Quiz menu", JOptionPane.QUESTION_MESSAGE, null, choice, choice[0]);
                                                    if (addquizOption == null) {
                                                        break;
                                                    }
                                                    switch (addquizOption) {
                                                        //file
                                                        case "1":
                                                            writer.println("1");
                                                            writer.flush();
                                                            reader.readLine();
                                                            String quizFile = "";
                                                            quizFile = JOptionPane.showInputDialog(null,
                                                                    "Enter a file", "file name",
                                                                    JOptionPane.QUESTION_MESSAGE);
                                                            if (quizFile == null) {
                                                                break;
                                                            }
                                                            writer.println(quizFile);
                                                            writer.flush();
                                                            String accept = reader.readLine();
                                                            String course = "";
                                                            if (accept.equals("true")) {
                                                                course = JOptionPane.showInputDialog(null,
                                                                        "Which course would you like to add it to?", "course name",
                                                                        JOptionPane.QUESTION_MESSAGE);
                                                                if (course == null) {
                                                                    break;
                                                                }
                                                                writer.println(course);
                                                                writer.flush();
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "File doesn't exist",
                                                                        "error", JOptionPane.ERROR_MESSAGE);
                                                                break;
                                                            }
                                                            String success = reader.readLine();
                                                            if (success.equals("true")) {
                                                                JOptionPane.showMessageDialog(null, "Quiz added",
                                                                        "Quiz", JOptionPane.INFORMATION_MESSAGE);
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "Quiz couldn't be added",
                                                                        "error", JOptionPane.ERROR_MESSAGE);
                                                            }
                                                            break;
                                                        //manually
                                                        case "2":
                                                            writer.println("2");
                                                            writer.flush();
                                                            reader.readLine();
                                                            String course2 = "";
                                                            course2 = JOptionPane.showInputDialog(null,
                                                                    "Which course would you like to add it to?", "course name",
                                                                    JOptionPane.QUESTION_MESSAGE);
                                                            if (course2 == null) {
                                                                break;
                                                            }
                                                            writer.println(course2);
                                                            writer.flush();
                                                            String quizName = "";
                                                            String success2 = reader.readLine();
                                                            if (success2.equals("true")) {
                                                                quizName = JOptionPane.showInputDialog(null,
                                                                        "Enter a Quiz Name", "quiz name",
                                                                        JOptionPane.QUESTION_MESSAGE);
                                                                if (quizName == null) {
                                                                    writer.println("false");
                                                                    writer.flush();
                                                                    break;
                                                                }
                                                                writer.println(quizName);
                                                                writer.flush();
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "Course doesn't exist",
                                                                        "error", JOptionPane.ERROR_MESSAGE);
                                                                break;
                                                            }
                                                            reader.readLine();
                                                            String question = "";
                                                            question = JOptionPane.showInputDialog(null, "Enter the question you would like to add",
                                                                    "University Card", JOptionPane.QUESTION_MESSAGE);
                                                            if (question == null) {
                                                                writer.println("false");
                                                                writer.flush();
                                                                break;
                                                            }
                                                            writer.println(question);
                                                            writer.flush();
                                                            reader.readLine();
                                                            String c1;
                                                            c1 = JOptionPane.showInputDialog(null, "Enter first answer choice",
                                                                    "University Card", JOptionPane.QUESTION_MESSAGE);
                                                            if (c1 == null) {
                                                                writer.println("false");
                                                                writer.flush();
                                                                break;
                                                            }
                                                            writer.println(c1);
                                                            writer.flush();
                                                            reader.readLine();
                                                            String c2;
                                                            c2 = JOptionPane.showInputDialog(null, "Enter second answer choice",
                                                                    "University Card", JOptionPane.QUESTION_MESSAGE);
                                                            if (c2 == null) {
                                                                writer.println("false");
                                                                writer.flush();
                                                                break;
                                                            }
                                                            writer.println(c2);
                                                            writer.flush();
                                                            reader.readLine();
                                                            String c3;
                                                            c3 = JOptionPane.showInputDialog(null, "Enter third answer choice",
                                                                    "University Card", JOptionPane.QUESTION_MESSAGE);
                                                            if (c3 == null) {
                                                                writer.println("false");
                                                                writer.flush();
                                                                break;
                                                            }
                                                            writer.println(c3);
                                                            writer.flush();
                                                            reader.readLine();
                                                            String c4;
                                                            c4 = JOptionPane.showInputDialog(null, "Enter fourth answer choice",
                                                                    "University Card", JOptionPane.QUESTION_MESSAGE);
                                                            if (c4 == null) {
                                                                writer.println("false");
                                                                writer.flush();
                                                                break;
                                                            }
                                                            writer.println(c4);
                                                            writer.flush();
                                                            reader.readLine();
                                                            String str = "false";
                                                            if (JOptionPane.showConfirmDialog(null, "Would you like the choices to be randomized?"
                                                                    , "Statistics", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                                                str = "true";
                                                            }
                                                            writer.println(str);
                                                            writer.flush();
                                                            String confirm = reader.readLine();
                                                            if (confirm.equals("true")) {
                                                                JOptionPane.showMessageDialog(null, "Quiz added",
                                                                        "Quiz", JOptionPane.INFORMATION_MESSAGE);
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "Quiz can not be added",
                                                                        "error", JOptionPane.ERROR_MESSAGE);
                                                            }
                                                            break;
                                                        //return
                                                        case "3":
                                                            writer.println("3");
                                                            writer.flush();
                                                            break;
                                                    }
                                                    break;
                                                //edit quiz
                                                case "2":
                                                    writer.println("2");
                                                    writer.flush();
                                                    reader.readLine();
                                                    String course2 = "";
                                                    course2 = JOptionPane.showInputDialog(null,
                                                            "Which course would you like to edit a quiz in?", "course name",
                                                            JOptionPane.QUESTION_MESSAGE);
                                                    if (course2 == null) {
                                                        break;
                                                    }
                                                    writer.println(course2);
                                                    writer.flush();
                                                    String quizName = "";
                                                    String success2 = reader.readLine();
                                                    if (success2.equals("true")) {
                                                        quizName = JOptionPane.showInputDialog(null,
                                                                "Which quiz would you like to edit", "quiz name",
                                                                JOptionPane.QUESTION_MESSAGE);
                                                        if (quizName == null) {
                                                            break;
                                                        }
                                                        writer.println(quizName);
                                                        writer.flush();
                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "Course doesn't exist",
                                                                "error", JOptionPane.ERROR_MESSAGE);
                                                        break;
                                                    }
                                                    String sf = reader.readLine();
                                                    if (sf.equals("true")) {
                                                        String questionm = "";
                                                        String[] choce = {"1", "2", "3", "4"};
                                                        questionm = (String) JOptionPane.showInputDialog(null,
                                                                "Would you like to\n1: Add a Question\n2: Remove a Question\n3: Edit a Questions choices\n4: Return",
                                                                "Quiz menu", JOptionPane.QUESTION_MESSAGE, null, choce, choce[0]);
                                                        if (questionm == null) {
                                                            break;
                                                        }
                                                        writer.println(questionm);
                                                        writer.flush();
                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "Quiz doesn't exist",
                                                                "error", JOptionPane.ERROR_MESSAGE);
                                                        break;
                                                    }
                                                    String t = reader.readLine();
                                                    if (t.equals("1")) {
                                                        String question = "";
                                                        question = JOptionPane.showInputDialog(null, "Enter the question you would like to add",
                                                                "University Card", JOptionPane.QUESTION_MESSAGE);
                                                        if (question == null) {
                                                            writer.println("false");
                                                            writer.flush();
                                                            break;
                                                        }
                                                        writer.println(question);
                                                        writer.flush();
                                                        reader.readLine();
                                                        String c1;
                                                        c1 = JOptionPane.showInputDialog(null, "Enter first answer choice",
                                                                "University Card", JOptionPane.QUESTION_MESSAGE);
                                                        if (c1 == null) {
                                                            writer.println("false");
                                                            writer.flush();
                                                            break;
                                                        }
                                                        writer.println(c1);
                                                        writer.flush();
                                                        reader.readLine();
                                                        String c2;
                                                        c2 = JOptionPane.showInputDialog(null, "Enter second answer choice",
                                                                "University Card", JOptionPane.QUESTION_MESSAGE);
                                                        if (c2 == null) {
                                                            writer.println("false");
                                                            writer.flush();
                                                            break;
                                                        }
                                                        writer.println(c2);
                                                        writer.flush();
                                                        reader.readLine();
                                                        String c3;
                                                        c3 = JOptionPane.showInputDialog(null, "Enter third answer choice",
                                                                "University Card", JOptionPane.QUESTION_MESSAGE);
                                                        if (c3 == null) {
                                                            writer.println("false");
                                                            writer.flush();
                                                            break;
                                                        }
                                                        writer.println(c3);
                                                        writer.flush();
                                                        reader.readLine();
                                                        String c4;
                                                        c4 = JOptionPane.showInputDialog(null, "Enter fourth answer choice",
                                                                "University Card", JOptionPane.QUESTION_MESSAGE);
                                                        if (c4 == null) {
                                                            writer.println("false");
                                                            writer.flush();
                                                            break;
                                                        }
                                                        writer.println(c4);
                                                        writer.flush();
                                                        reader.readLine();
                                                        String str = "false";
                                                        if (JOptionPane.showConfirmDialog(null, "Would you like the choices to be randomized?"
                                                                , "Statistics", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                                            str = "true";
                                                        }
                                                        writer.println(str);
                                                        writer.flush();
                                                    } else if (t.equals("2")) {
                                                        String question;
                                                        question = JOptionPane.showInputDialog(null, "Enter the question that you would like to remove",
                                                                "University Card", JOptionPane.QUESTION_MESSAGE);
                                                        if (question == null) {
                                                            writer.println("false");
                                                            writer.flush();
                                                            break;
                                                        }
                                                        writer.println(question);
                                                        writer.flush();
                                                        reader.readLine();
                                                    } else if (t.equals("3")) {
                                                        String question;
                                                        question = JOptionPane.showInputDialog(null, "Enter a question that you would " +
                                                                "like to edit the choices of", "University Card", JOptionPane.QUESTION_MESSAGE);
                                                        if (question == null) {
                                                            writer.println("false");
                                                            writer.flush();
                                                            break;
                                                        }
                                                        writer.println(question);
                                                        writer.flush();
                                                        reader.readLine();
                                                        String whichchoice = "Which choice would you like to change?";
                                                        String[] chces = {"A", "B", "C", "D"};
                                                        String changechoice = "What would you like to change it to?";
                                                        String choiceChange;
                                                        choiceChange = (String) JOptionPane.showInputDialog(null, whichchoice,
                                                                "University Card", JOptionPane.QUESTION_MESSAGE, null, chces, chces[0]);
                                                        if (choiceChange == null) {
                                                            writer.println("false");
                                                            writer.flush();
                                                            break;
                                                        }
                                                        writer.println(choiceChange);
                                                        writer.flush();
                                                        reader.readLine();
                                                        if (choiceChange.equals("A")) {
                                                            String change;
                                                            change = JOptionPane.showInputDialog(null, changechoice,
                                                                    "University Card", JOptionPane.QUESTION_MESSAGE);
                                                            if (change == null) {
                                                                writer.println("false");
                                                                writer.flush();
                                                                break;
                                                            }
                                                            writer.println(change);
                                                            writer.flush();
                                                        } else if (choiceChange.equals("B")) {
                                                            String change;
                                                            change = JOptionPane.showInputDialog(null, changechoice,
                                                                    "University Card", JOptionPane.QUESTION_MESSAGE);
                                                            if (change == null) {
                                                                writer.println("false");
                                                                writer.flush();
                                                                break;
                                                            }
                                                            writer.println(change);
                                                            writer.flush();
                                                        } else if (choiceChange.equals("C")) {
                                                            String change;
                                                            change = JOptionPane.showInputDialog(null, changechoice,
                                                                    "University Card", JOptionPane.QUESTION_MESSAGE);
                                                            if (change == null) {
                                                                writer.println("false");
                                                                writer.flush();
                                                                break;
                                                            }
                                                            writer.println(change);
                                                            writer.flush();
                                                        } else if (choiceChange.equals("D")) {
                                                            String change;
                                                            change = JOptionPane.showInputDialog(null, changechoice,
                                                                    "University Card", JOptionPane.QUESTION_MESSAGE);
                                                            if (change == null) {
                                                                writer.println("false");
                                                                writer.flush();
                                                                break;
                                                            }
                                                            writer.println(change);
                                                            writer.flush();
                                                        }
                                                    } else if (t.equals("4")) {
                                                        break;
                                                    }
                                                    String confirm = reader.readLine();
                                                    if (confirm.equals("true")) {
                                                        JOptionPane.showMessageDialog(null, "Quiz edited",
                                                                "Quiz", JOptionPane.INFORMATION_MESSAGE);
                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "Quiz can not be edited",
                                                                "error", JOptionPane.ERROR_MESSAGE);
                                                    }
                                                    break;
                                                //remove quiz
                                                case "3":
                                                    writer.println("3");
                                                    writer.flush();
                                                    reader.readLine();
                                                    String cName = "";
                                                    cName = JOptionPane.showInputDialog(null,
                                                            "Which course would you like to delete a quiz from?", "course name",
                                                            JOptionPane.QUESTION_MESSAGE);
                                                    if (cName == null) {
                                                        break;
                                                    }
                                                    writer.println(cName);
                                                    writer.flush();
                                                    String qName = "";
                                                    String accepted = reader.readLine();
                                                    if (accepted.equals("true")) {
                                                        qName = JOptionPane.showInputDialog(null,
                                                                "Which quiz would you like to delete?", "quiz name",
                                                                JOptionPane.QUESTION_MESSAGE);
                                                        if (qName == null) {
                                                            break;
                                                        }
                                                        writer.println(qName);
                                                        writer.flush();
                                                    } else {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Course doesn't exist", "Quiz",
                                                                JOptionPane.ERROR_MESSAGE);
                                                        break;
                                                    }
                                                    String success = reader.readLine();
                                                    if (success.equals("true")) {
                                                        JOptionPane.showMessageDialog(null, "Quiz removed!",
                                                                "Quiz", JOptionPane.INFORMATION_MESSAGE);
                                                    } else {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Quiz couldn't be removed!", "Quiz",
                                                                JOptionPane.WARNING_MESSAGE);
                                                    }
                                                    break;

                                                //view quiz
                                                case "4":
                                                    writer.println("4");
                                                    writer.flush();
                                                    reader.readLine();
                                                    String course = "";
                                                    course = JOptionPane.showInputDialog(null,
                                                            "Which course would you like to view a quiz in?", "course name",
                                                            JOptionPane.QUESTION_MESSAGE);
                                                    if (course == null) {
                                                        break;
                                                    }
                                                    writer.println(course);
                                                    writer.flush();
                                                    String vq = "";
                                                    String accept = reader.readLine();
                                                    if (accept.equals("true")) {
                                                        vq = JOptionPane.showInputDialog(null,
                                                                "Which quiz would you like to view?", "quiz name",
                                                                JOptionPane.QUESTION_MESSAGE);
                                                        if (vq == null) {
                                                            break;
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Course doesn't exist", "Quiz",
                                                                JOptionPane.ERROR_MESSAGE);
                                                        break;
                                                    }
                                                    writer.println(vq);
                                                    writer.flush();
                                                    String result = reader.readLine();
                                                    if (!result.equals("false")) {
                                                        String g = "";
                                                        String resultb = "";
                                                        while (!((result = reader.readLine()).equals("0"))) {
                                                            resultb = result;
                                                            g = g + resultb + "\n";
                                                            if (resultb.equals("0")) {
                                                                break;
                                                            }
                                                        }
                                                        JOptionPane.showMessageDialog(null, g,
                                                                "Quiz", JOptionPane.INFORMATION_MESSAGE);
                                                    } else {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Quiz doesn't exist", "Quiz",
                                                                JOptionPane.ERROR_MESSAGE);
                                                        break;
                                                    }
                                                    break;
                                                //grade quiz
                                                case "5":
                                                    writer.println("5");
                                                    writer.flush();
                                                    reader.readLine();
                                                    String user = "";
                                                    user = JOptionPane.showInputDialog(null,
                                                            "Enter Student's email", "file name",
                                                            JOptionPane.QUESTION_MESSAGE);
                                                    if (user == null) {
                                                        break;
                                                    }
                                                    writer.println(user);
                                                    writer.flush();
                                                    reader.readLine();
                                                    String ID = "";
                                                    ID = JOptionPane.showInputDialog(null,
                                                            "Enter PUID", "file name",
                                                            JOptionPane.QUESTION_MESSAGE);
                                                    if (ID == null) {
                                                        break;
                                                    }
                                                    writer.println(ID);
                                                    writer.flush();
                                                    reader.readLine();
                                                    String existed = reader.readLine();
                                                    String c = "";
                                                    if (existed.equals("true")) {
                                                        c = JOptionPane.showInputDialog(null,
                                                                "Which course would you like to grade a quiz in?", "quiz name",
                                                                JOptionPane.QUESTION_MESSAGE);
                                                        if (c == null) {
                                                            break;
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Student doesn't exist", "Quiz",
                                                                JOptionPane.ERROR_MESSAGE);
                                                        break;
                                                    }
                                                    writer.println(c);
                                                    writer.flush();
                                                    String can = reader.readLine();
                                                    String q = "";
                                                    if (can.equals("true")) {
                                                        q = JOptionPane.showInputDialog(null,
                                                                "Which quiz would you like to grade?", "quiz name",
                                                                JOptionPane.QUESTION_MESSAGE);
                                                        if (q == null) {
                                                            break;
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Course doesn't exist", "Quiz",
                                                                JOptionPane.ERROR_MESSAGE);
                                                        break;
                                                    }
                                                    writer.println(q);
                                                    writer.flush();
                                                    String result1 = reader.readLine();
                                                    if (result1.equals("true")) {
                                                        String k = "";
                                                        String resultc = "";
                                                        String a = "";
                                                        while (!((a = reader.readLine()).equals("0"))) {
                                                            resultc = a;
                                                            k = k + resultc + "\n";
                                                            if (resultc.equals("0")) {
                                                                break;
                                                            }
                                                        }
                                                        JOptionPane.showMessageDialog(null, k,
                                                                "Quiz", JOptionPane.PLAIN_MESSAGE);
                                                        String az = JOptionPane.showInputDialog(null, "Enter the maximum grade.",
                                                                "University Card", JOptionPane.QUESTION_MESSAGE);
                                                        if (az == null) {
                                                            writer.println("false");
                                                            writer.flush();
                                                            break;
                                                        } else {
                                                            writer.println(az);
                                                            writer.flush();
                                                            reader.readLine();
                                                            String bg = JOptionPane.showInputDialog(null, "Enter the student's grade.",
                                                                    "University Card", JOptionPane.QUESTION_MESSAGE);
                                                            if (bg == null) {
                                                                writer.println("false");
                                                                writer.flush();
                                                                break;
                                                            } else {
                                                                writer.println(bg);
                                                                writer.flush();
                                                                JOptionPane.showMessageDialog(null, "Quiz graded",
                                                                        "Quiz", JOptionPane.INFORMATION_MESSAGE);
                                                            }
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Quiz doesn't exist", "Quiz",
                                                                JOptionPane.ERROR_MESSAGE);
                                                    }
                                                    break;
                                                //see submission
                                                case "6":
                                                    writer.println("6");
                                                    writer.flush();
                                                    reader.readLine();
                                                    String user2 = "";
                                                    user2 = JOptionPane.showInputDialog(null,
                                                            "Enter Student's Email", "file name",
                                                            JOptionPane.QUESTION_MESSAGE);
                                                    if (user2 == null) {
                                                        break;
                                                    }
                                                    writer.println(user2);
                                                    writer.flush();
                                                    reader.readLine();
                                                    String ID2 = "";
                                                    ID2 = JOptionPane.showInputDialog(null,
                                                            "Enter PUID", "file name",
                                                            JOptionPane.QUESTION_MESSAGE);
                                                    if (ID2 == null) {
                                                        break;
                                                    }
                                                    writer.println(ID2);
                                                    writer.flush();
                                                    reader.readLine();
                                                    String existence = reader.readLine();
                                                    String see = "";
                                                    if (existence.equals("true")) {
                                                        see = JOptionPane.showInputDialog(null,
                                                                "Which course would you like to see a submission in?", "quiz name",
                                                                JOptionPane.QUESTION_MESSAGE);
                                                        if (see == null) {
                                                            break;
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Student doesn't exist", "Quiz",
                                                                JOptionPane.ERROR_MESSAGE);
                                                        break;
                                                    }
                                                    writer.println(see);
                                                    writer.flush();
                                                    String cou = reader.readLine();
                                                    String qu = "";
                                                    if (cou.equals("true")) {
                                                        qu = JOptionPane.showInputDialog(null,
                                                                "Which quiz submission would you like to see", "quiz name",
                                                                JOptionPane.QUESTION_MESSAGE);
                                                        if (qu == null) {
                                                            break;
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Course doesn't exist", "Quiz",
                                                                JOptionPane.ERROR_MESSAGE);
                                                        break;
                                                    }
                                                    writer.println(qu);
                                                    writer.flush();
                                                    String result2 = reader.readLine();
                                                    String k = "";
                                                    String resultc = "";
                                                    if (result2.equals("true")) {
                                                        String a = "";
                                                        while (!((a = reader.readLine()).equals("0"))) {
                                                            resultc = a;
                                                            k = k + resultc + "\n";
                                                            if (resultc.equals("0")) {
                                                                break;
                                                            }
                                                        }
                                                        JOptionPane.showMessageDialog(null, k,
                                                                "Quiz", JOptionPane.INFORMATION_MESSAGE);
                                                    } else {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Quiz doesn't exist", "Quiz",
                                                                JOptionPane.ERROR_MESSAGE);
                                                    }
                                                    break;
                                                //return
                                                case "7":
                                                    writer.println("7");
                                                    writer.flush();
                                                    break;
                                                default:
                                                    break;
                                            }
                                            break;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        //account menu
                                    case "3":
                                        String accMen = "";
                                        String[] choices = {"1", "2", "3", "4"};
                                        accMen = (String) JOptionPane.showInputDialog(null,
                                                "Account Menu\n1: Edit Account\n2: Delete Account\n3: See Current Login Info\n4: Return",
                                                "Account menu", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
                                        if (accMen == null) {
                                            break;
                                        }

                                        //Acc Menu Options
                                        switch (accMen) {
                                            //Edit Account
                                            case "1":
                                                writer.println("1");
                                                writer.flush();
                                                //gui
                                                String editaccMen = "";
                                                String[] choice = {"1", "2", "3"};
                                                editaccMen = (String) JOptionPane.showInputDialog(null,
                                                        "Edit Account Menu:\n1: Edit Username and Password\n2: " +
                                                                "Edit Password Only\n3: Return", "Account menu",
                                                        JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
                                                if (editaccMen == null) {
                                                    break;
                                                }

                                                //Edit Acc Menu
                                                switch (editaccMen) {
                                                    case "1":
                                                        writer.println("1");
                                                        writer.flush();
                                                        String accDetails = "";
                                                        accDetails = JOptionPane.showInputDialog(null,
                                                                "Enter the username and password as: user,pass " +
                                                                        "for the account you would like to edit as: " +
                                                                        "user,pass", "Account Info",
                                                                JOptionPane.QUESTION_MESSAGE);
                                                        if (accDetails == null) {
                                                            break;
                                                        }
                                                        writer.println(accDetails);
                                                        writer.flush();

                                                        String yes1 = reader.readLine();
                                                        String newInfo = " ";
                                                        if (yes1.equals("true")) {
                                                            newInfo = JOptionPane.showInputDialog(null,
                                                                    "Enter the new account info for the" +
                                                                            " account as: user,password", "New Info",
                                                                    JOptionPane.QUESTION_MESSAGE);
                                                            if (newInfo == null) {
                                                                break;
                                                            }
                                                            writer.println(newInfo);
                                                            writer.flush();
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "File doesn't exist",
                                                                    "error", JOptionPane.ERROR_MESSAGE);
                                                            break;
                                                        }
                                                        String editDone = reader.readLine();
                                                        if (editDone.equals("alreadyexists")) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "Username already exists",
                                                                    "error", JOptionPane.ERROR_MESSAGE);
                                                            break;
                                                        } else if (editDone.equals("invalidinfo")) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "Account with that info does not exits",
                                                                    "error", JOptionPane.ERROR_MESSAGE);

                                                        } else if (editDone.equals("success")) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "Your new Username and Password are:" + newInfo,
                                                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                                                        }
                                                        break;
                                                    case "2":
                                                        writer.println("2");
                                                        writer.flush();
                                                        //old pasas
                                                        String passaccDetails = "";
                                                        passaccDetails = JOptionPane.showInputDialog(null,
                                                                "Enter the password for the current account",
                                                                "Password Info", JOptionPane.QUESTION_MESSAGE);
                                                        if (passaccDetails == null) {
                                                            break;
                                                        }
                                                        writer.println(passaccDetails);
                                                        writer.flush();

                                                        //new pass
                                                        String newpasss = "";
                                                        newpasss = JOptionPane.showInputDialog(null,
                                                                "Enter the new password for the account",
                                                                "Password Info", JOptionPane.QUESTION_MESSAGE);
                                                        if (newpasss == null) {
                                                            break;
                                                        }
                                                        writer.println(newpasss);
                                                        writer.flush();

                                                        String passeditDone = reader.readLine();
                                                        if (passeditDone.equals("wrongcred")) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "Wrong Credentials",
                                                                    "Error", JOptionPane.ERROR_MESSAGE);

                                                        } else if (passeditDone.equals("success")) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "Your new Password is:" + newpasss,
                                                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                                                        } else if (passeditDone.equals("nouser")) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "Username cannot be edited",
                                                                    "Error", JOptionPane.ERROR_MESSAGE);
                                                        }
                                                        break;
                                                    case "3":
                                                        break;
                                                }
                                                break;
                                            //Deletes Account
                                            case "2":
                                                writer.println("2");
                                                writer.flush();
                                                //gui
                                                String delMen = "";
                                                delMen = JOptionPane.showInputDialog(null,
                                                        "Enter the account username and password as: user,pass",
                                                        "Account Info",
                                                        JOptionPane.QUESTION_MESSAGE);
                                                if (delMen == null) {
                                                    break;
                                                }
                                                writer.println(delMen);
                                                writer.flush();


                                                String accDel = reader.readLine();
                                                if (accDel.equals("failed")) {
                                                    JOptionPane.showMessageDialog(null, "Account was not found",
                                                            "Error", JOptionPane.ERROR_MESSAGE);

                                                } else if (accDel.equals("successlogout")) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Account Successfully Deleted,You Will be Logged Out",
                                                            "Success", JOptionPane.INFORMATION_MESSAGE);
                                                    choice1 = "5";
                                                    break;
                                                } else if (accDel.equals("success")) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Account Successfully Deleted",
                                                            "Success", JOptionPane.INFORMATION_MESSAGE);
                                                }

                                                break;
                                            case "3":
                                                writer.println("3");
                                                writer.flush();
                                                String accinfoRes = reader.readLine();

                                                if (accinfoRes.equals("failed")) {
                                                    JOptionPane.showMessageDialog(null, "Does not exist",
                                                            "error", JOptionPane.ERROR_MESSAGE);

                                                } else if (accinfoRes.equals("success")) {
                                                    String accInfo = reader.readLine();
                                                    JOptionPane.showMessageDialog(null,
                                                            "Account Info is: " + accInfo,
                                                            "Log In", JOptionPane.INFORMATION_MESSAGE);
                                                }
                                                break;
                                            case "4":
                                                writer.println("4");
                                                writer.flush();
                                                break;
                                        }
                                        break;
                                    //exits to login
                                    case "4":
                                        break;
                                    //exit program
                                    case "5":
                                        writer.println("break");
                                        writer.flush();
                                        break;
                                }

                                if (choice1.equals("5") || choice1.equals("4") || choice1 == null) {
                                    break;
                                }


                            }


                        } else {
                            JOptionPane.showMessageDialog(null, "UnSuccessful login", "error",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    } else if (loginOrCreateString.equals("create account")) {
                        //create teacher account
                        String username = "";
                        String password = "";
                        while (true) {
                            username = JOptionPane.showInputDialog(null, "Enter a username",
                                    "Create Teacher Account", JOptionPane.QUESTION_MESSAGE);
                            if (username == null) {
                                return;
                            }
                            writer.println(username);
                            writer.flush();
                            String valid = reader.readLine();
                            if (valid.equals("true")) {
                                break;
                            } else {
                                JOptionPane.showMessageDialog(null, "Username not available",
                                        "Username", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        if (username == null) {
                            break;
                        }

                        password = JOptionPane.showInputDialog(null, "Enter a password",
                                "Password", JOptionPane.QUESTION_MESSAGE);
                        if (password == null) {
                            break;
                        }
                        writer.println(password);
                        writer.flush();
                        String success = reader.readLine();
                        if (success.equals("success")) {
                            JOptionPane.showMessageDialog(null, "Successfully created",
                                    "Created teacher account", JOptionPane.INFORMATION_MESSAGE);
                        }

                    } else {
                        break;
                    }
                } else if (teacherOrStudentString.equals("student")) {
                    if (loginOrCreateString.equals("login")) {

                        boolean verifiedUserBool = false;
                        boolean verifiedPassBool = false;
                        String currentStudentFileName = "";
                        String studentUser = "";
                        String studentPass = "";

                        //student login
                        boolean pass = false;
                        String testUsername = "";
                        String testPassword = "";
                        do {
                            testUsername = JOptionPane.showInputDialog(null, "Username:",
                                    "login", JOptionPane.PLAIN_MESSAGE);
                            if (testUsername == null) {
                                break;
                            }
                            testPassword = JOptionPane.showInputDialog(null, "Password:",
                                    "login", JOptionPane.PLAIN_MESSAGE);
                            if (testPassword == null) {
                                break;
                            }

                            //this is to get everything on one line
                            writer.println(testUsername + " " + testPassword);
                            writer.flush();
                            //reading from the server information
                            String loginAttempt = reader.readLine();
                            /**
                             * Cases for username and password
                             *
                             * Case 1: Username is wrong and Password is correct
                             * Case 2: Username is correct and Password is wrong
                             * Case 3: Username is wrong and Password is wrong
                             */

                            if (loginAttempt.equals("error")) {
                                JOptionPane.showMessageDialog(null, "An error occurred while logging in, " +
                                        "please try again", "Log In Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                pass = true;
                            }
                        } while (pass == false);

                        if (testUsername == null || testPassword == null) {
                            break;
                        }

                        //if (successfulLogin.equals("true")) {
                        //pass = true;
                        JOptionPane.showMessageDialog(null, "Successful login", "logged in",
                                JOptionPane.PLAIN_MESSAGE);
                        choice2 = "";
                        while (true) {
                            String[] opt = {"Course Selection", "Delete Account", "Save and Exit App"};
                            Object choice = JOptionPane.showInputDialog(null, "select an option", "main menu",
                                    JOptionPane.PLAIN_MESSAGE, null, opt, opt[0]);

                            choice2 = choice.toString();
                            writer.println(choice2);
                            writer.flush();
                            String received = reader.readLine();

                            switch (choice2) {
                                case "Course Selection":
                                    //writer.println("1");
                                    //writer.flush();
                                    String courseSelection = reader.readLine();
                                    String newCourseSelection = courseSelection.replaceAll("\\[", "");
                                    String newnewCourseSelection = newCourseSelection.replaceAll("]", "");
                                    String[] courses = newnewCourseSelection.split(",");
                                    for (int i = 0; i < courses.length; i++) {
                                        //System.out.println(courses[i]);
                                    }
                                    Object course = JOptionPane.showInputDialog(null, "select a course", "course selection",
                                            JOptionPane.PLAIN_MESSAGE, null, courses, courses[0]);
                                    if (course == null) {
                                        JOptionPane.showMessageDialog(null, "Farewell!", "Exit", JOptionPane.PLAIN_MESSAGE);
                                        writer.println("return");
                                        writer.flush();
                                        return;
                                    }
                                    String courseSelectionString = course.toString();

                                    writer.println(courseSelectionString);
                                    writer.flush();

                                    String quizSelection = reader.readLine();
                                    String newQuizSelection = quizSelection.replaceAll("\\[", "");
                                    String newnewQuizSelection = newQuizSelection.replaceAll("]", "");
                                    String newnewnewQuizSelection = newnewQuizSelection.replaceAll(" ", "");
                                    String[] quizzess = newnewQuizSelection.split(",");
                                    Object quiz = JOptionPane.showInputDialog(null, "select a quiz", "quiz",
                                            JOptionPane.PLAIN_MESSAGE, null, quizzess, quizzess[0]);
                                    if (quiz == null) {
                                        JOptionPane.showMessageDialog(null, "Farewell!", "Exit", JOptionPane.PLAIN_MESSAGE);
                                        writer.println("return");
                                        writer.flush();
                                        return;
                                    }
                                    String quizSelectionString = quiz.toString();

                                    writer.println(quizSelectionString);
                                    writer.flush();

                                    String takeQuizorViewScore = "";
                                    String[] quizChoices = {"Take Quiz", "View Quiz Score"};
                                    Object quizChoice = JOptionPane.showInputDialog(null, "take quiz or view quiz score", "quiz",
                                            JOptionPane.PLAIN_MESSAGE, null, quizChoices, quizChoices[0]);


                                    String quizChoiceString = quizChoice.toString();
                                    writer.println(quizChoiceString);
                                    writer.flush();

                                    String receivingString = "";
                                    String viewQuizScore = "";


                                    for (int i = 0; i < 5; i++) {
                                        viewQuizScore = reader.readLine();
                                        receivingString += viewQuizScore + "\n";
                                    }
                                    JOptionPane.showMessageDialog(null, receivingString, "Quiz Score", JOptionPane.PLAIN_MESSAGE);

                                    //System.out.println(receivingString);

                                    switch (takeQuizorViewScore) {
                                        case "View Quiz Score":
                                            JOptionPane.showMessageDialog(null, receivingString, "Quiz Score", JOptionPane.PLAIN_MESSAGE);

                                    }
                                    break;
                                case "Delete Account":
                                    JOptionPane.showMessageDialog(null, "Your account was deleted!", "account deactivated", JOptionPane.INFORMATION_MESSAGE);
                                    return;
                                case "Save and Exit App":
                                    return;

                            }

                        }
                        //}
                        //} while (pass = false);

                    } else if (loginOrCreateString.equals("create account")) {
                        //create student account
                        boolean correctEmailFormat = false;
                        String email = "";
                        do {
                            String emailInput = JOptionPane.showInputDialog(null, "Enter Email",
                                    "Username", JOptionPane.PLAIN_MESSAGE);
                            if (emailInput.contains("@purdue.edu")) {
                                email = emailInput;
                                correctEmailFormat = true;
                            } else {
                                JOptionPane.showMessageDialog(null, "You need to add @purdue.edu at the end."
                                        , "Create Account Error Message", JOptionPane.ERROR_MESSAGE);
                            }
                        } while (correctEmailFormat == false);
                        writer.println(email);
                        writer.flush();
                        //password
                        boolean PUID10 = false;
                        String password = "";
                        do {
                            String passwordInput = JOptionPane.showInputDialog(null, "Enter Password",
                                    "Password", JOptionPane.PLAIN_MESSAGE);
                            if (passwordInput.length() > 10) {
                                //show this in the client
                                JOptionPane.showMessageDialog(null, "That is too many digits!",
                                        "Create Account Error Message", JOptionPane.ERROR_MESSAGE);
                            } else if (passwordInput.length() < 10) {
                                JOptionPane.showMessageDialog(null, "That is not enough digits!",
                                        "Create Account Error Message", JOptionPane.ERROR_MESSAGE);
                            } else {
                                //send the information over to server
                                writer.println(passwordInput);
                                writer.flush();
                                PUID10 = true;
                            }
                        } while (PUID10 == false);
                    } else {
                        break;
                    }
                } else {
                    break;
                }
                //don't remove this it is essential
                if (choice1 == null) {
                    break;
                }
                if (choice1.equals("5")) {
                    break;
                }

            }


            JOptionPane.showMessageDialog(null, "Farewell!", "Exit", JOptionPane.PLAIN_MESSAGE);

            writer.close();
            reader.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Connection not established!", "Connecting...",
                    JOptionPane.ERROR_MESSAGE);
        }


    }


}
