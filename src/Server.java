import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Server class that stores all methods uses in client, and messages are sent for the client to display.
 * <p>Purdue University -- CS18000 -- Fall 2021</p>*
 *
 * @author Chun-Yang Lee, Joshua Ho, Hpung San Aung, Margaret Haydock, Soumya Kakarlapudi Purdue CS - L21
 * @version 12/13/2021
 */

public class Server implements Runnable {

    public Server() {

    }

    private synchronized static ArrayList<String> readStudentFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //make a readfile for student directory
    private synchronized static ArrayList<String> readFileDirectory(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //write file for File Directory
    private synchronized static void writeFileDirectory(String filename, ArrayList<String> studentFileNames) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            for (String names : studentFileNames) {
                pw.write(names);
                pw.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Read File for a quiz
    private synchronized static ArrayList<String> readQuizFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            return null;
        }
    }

    /*
    //removing a quiz
    private synchronized static void removeQuizFile(String filename, String quizName) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            ArrayList<String> currentStudentFileArr = readStudentFile(filename);

            ArrayList<ArrayList<String>> allCourses = new ArrayList<>();
            for (int i = 2; i < currentStudentFileArr.size(); i++) {
                ArrayList<String> currentCourse = new ArrayList<>();
                String[] coursesAndQuizzes = currentStudentFileArr.get(i).split("; ");
                //Course:
                String courseName = coursesAndQuizzes[0].substring(7, coursesAndQuizzes[0].length());
                currentCourse.add(courseName);

                String[] quizzesArr = coursesAndQuizzes[1].split(", ");
                //Quizzes:
                quizzesArr[0] = quizzesArr[0].substring(8, quizzesArr[0].length());
                for (int j = 0; j < quizzesArr.length; j++) {
                    if (quizzesArr[j].equals(quizName)) {
                        quizzesArr[j] = null;
                    }
                }

                for (String name : quizzesArr) {
                    currentCourse.add(name);
                }
                allCourses.add(currentCourse);
            }

            //writing to the student file
            pw.write(currentStudentFileArr.get(0));
            pw.write("\n");
            pw.write(currentStudentFileArr.get(0));
            pw.write("\n");

            for (ArrayList<String> beep : allCourses) {
                pw.write("Courses: " + beep);
                for (int boop = 1; boop < beep.size(); boop++) {

                }
            }

        } catch (IOException e) {
           e.printStackTrace();
        }
    }
     */

    //returning a student object
    private synchronized static Student returnStudentObject(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            //initialzing the student object
            Student returnedStudent = new Student();
            ArrayList<String> studentFile = readStudentFile(filename);
            returnedStudent.setName(studentFile.get(0));
            returnedStudent.setPUID(studentFile.get(1));
            //removing the username and password from the list
            studentFile.remove(1);
            studentFile.remove(0);

            //turns the initial arraylist into an iterable list
            ArrayList<ArrayList<String>> coursesAndQuizzes = new ArrayList<>();

            //adding the course and quizzes to the array
            for (int i = 0; i < studentFile.size(); i++) {
                ArrayList<String> currentCoursesAndQuizzes = new ArrayList<>();
                //splits the line between courses and quizzes
                String[] splitLine = studentFile.get(i).split(";");
                //Courses: fjdalk
                String courseName = splitLine[0].substring(8);
                //so course name is always the first element in the array
                currentCoursesAndQuizzes.add(courseName);
                // Quizzes: jfdkladfd,fjdalfjdlkaf
                String quizNames = splitLine[1].substring(9);
                String[] splittedQuizzes = quizNames.split(",");
                //this is because the first element naturally has no space in front of it so you can add the element directly into the list
                currentCoursesAndQuizzes.add(splittedQuizzes[0].substring(1));
                //however, the rest of the elements have a space i the front, so the for loop removes that
                for (int j = 1; j < splittedQuizzes.length; j++) {
                    String currentQuizName = splittedQuizzes[j].substring(1);
                    currentCoursesAndQuizzes.add(currentQuizName);
                }
                //adding the whole arraylist in to the other arraylist
                coursesAndQuizzes.add(currentCoursesAndQuizzes);
            }

            ArrayList<Course> courseList = new ArrayList<>();

            //interating over the created list in order to create a course list for student
            for (int i = 0; i < coursesAndQuizzes.size(); i++) {
                //so i dont always have to type out courseAndQuizzes.get(i)
                //[math, spongebob.txt, tree.txt]
                ArrayList<String> currentCourseList = coursesAndQuizzes.get(i);
                String courseName = currentCourseList.get(0);
                ArrayList<Quiz> currentQuizArrObj = new ArrayList<>();
                for (int j = 1; j < currentCourseList.size(); j++) {
                    //read in all the quiz file
                    ArrayList<String> currentQuizArr = readQuizFile(currentCourseList.get(j));
                    if (currentQuizArr == null) {
                        break;
                    }
                    //reference the individual quiz
                    String quizName = currentQuizArr.get(0);
                    //quiz booleans
                    String quizBooleans = currentQuizArr.get(1);
                    String[] quizBooleansSplit = quizBooleans.split(" ");
                    boolean[] quizBooleansArr = new boolean[2];
                    for (int bool = 0; bool < quizBooleansSplit.length; bool++) {
                        if (quizBooleansSplit[bool].equals("true")) {
                            quizBooleansArr[bool] = true;
                        }
                        //can only be false
                        else {
                            quizBooleansArr[bool] = false;
                        }
                    }

                    //going through the questions and whatnot
                    ArrayList<Question> currentQuestionArrObj = new ArrayList<>();
                    for (int k = 2; k < currentQuizArr.size(); k += 2) {
                        String currentQuestion = currentQuizArr.get(k);
                        //Question_#: jfjlajfldk -> jfdklajlfkd
                        currentQuestion = currentQuestion.substring(11);

                        String choices = currentQuizArr.get(k + 1);
                        //Choices: kfldajlkdfj -> jfdajfdl
                        choices = choices.substring(9);
                        String[] cutUpChoices = choices.split(", ");
                        /**
                         * Choices ArrayList
                         */
                        ArrayList<Choice> choicesArrObj = new ArrayList<>();
                        for (String choice : cutUpChoices) {
                            choice = choice.substring(2);
                            Choice currentChoiceObj = new Choice(choice);
                            choicesArrObj.add(currentChoiceObj);
                        }
                        /**
                         * Question Object
                         */
                        Question currentQuestionObj = new Question(currentQuestion, choicesArrObj, quizBooleansArr[0]);
                        currentQuestionArrObj.add(currentQuestionObj);
                    }
                    /**
                     * Make the Quiz Object here
                     */
                    Quiz currentQuizObj = new Quiz(quizName, currentQuestionArrObj, quizBooleansArr[1]);
                    currentQuizArrObj.add(currentQuizObj);
                }
                Course currentCourseObj = new Course(currentQuizArrObj, courseName);
                courseList.add(currentCourseObj);
            }
            returnedStudent.setCourses(courseList);
            return returnedStudent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //returns an arrayList of all the student objects using method above (returnStudentObject)
    private synchronized static ArrayList<Student> getAllStudents() {
        ArrayList<String> studentsFiles = readFileDirectory("FileDirectory.txt");
        ArrayList<Student> students = new ArrayList<Student>();
        if (studentsFiles != null) {
            for (int i = 0; i < studentsFiles.size(); i++) {
                String student1 = studentsFiles.get(i);
                Student addingS = returnStudentObject(student1);
                students.add(addingS);
            }
        }

        return students;
    }

    private synchronized static void savingTeacherCoursesAndQuizzes(Teacher teacher, ArrayList<Course> courses) {
        try {
            String teacherCoursesFile = (teacher.getName() + "_Courses.txt").replaceAll(" ", "_");
            FileOutputStream fos = new FileOutputStream(teacherCoursesFile, false);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(teacher.getName());
            for (int i = 0; i < courses.size(); i++) {
                String courseName = courses.get(i).getName();
                pw.println(courseName);
                pw.print("Quizzes: ");
                for (int j = 0; j < courses.get(i).getQuizzes().size(); j++) {
                    String quizFile = courses.get(i).getQuizzes().get(j).getName();
                    pw.print(quizFile + ("_Quiz.txt "));
                }
                pw.println();
                pw.print("Students: ");
                for (int j = 0; j < courses.get(i).getStudents().size(); j++) {
                    String studentName = courses.get(i).getStudents().get(j).getName();
                    pw.print(studentName + " ");
                }
                pw.println();
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private synchronized static Teacher readingTeacherCoursesAndQuizzes(Teacher teacher) {
        try {
            String teacherCourseFile = (teacher.getName() + "_Courses.txt").replaceAll(" ", "_");
            FileReader fr = new FileReader(teacherCourseFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            String teacherName = line;
            Teacher teacher1 = new Teacher(teacherName, teacher.getPassword());

            line = bfr.readLine();
            while (line != null) {

                String course = line;
                Course addCourse1 = new Course(course);
                teacher1.addCourse(addCourse1);

                line = bfr.readLine();
                if (line == null) {
                    break;
                }
                line = line.substring(line.indexOf(" ") + 1);

                while (line.contains(" ")) {
                    String addQuiz1 = line.substring(0, line.indexOf(" "));
                    if (addQuiz1.length() <= 1) {
                        break;
                    }
                    teacher1.addQuiz(addQuiz1, addCourse1);
                    line = line.substring(line.indexOf(" ") + 1);
                }

                line = bfr.readLine();
                if (line == null) {
                    break;
                }

                line = line.substring(line.indexOf(" ") + 1);

                while (line.contains(" ")) {
                    String addingStudent = line.substring(0, line.indexOf(" "));
                    if (addingStudent.length() <= 1) {
                        break;
                    }
                    addingStudent = addingStudent.substring(0, addingStudent.indexOf("@"));
                    String readingFileName = addingStudent + "-student.txt";
                    Student addedS = returnStudentObject(readingFileName);

                    teacher1.addStudent(addedS, addCourse1);
                    line = line.substring(line.indexOf(" ") + 1);
                }
                line = bfr.readLine();

            }
            bfr.close();
            return teacher1;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private synchronized static ArrayList<String[]> readTeacherFile(File filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            //new array list for final return
            ArrayList<String[]> lines = new ArrayList<>();
            String returnLine;

            //adds the read line to the arraylist as long as there more/is not null
            while ((returnLine = br.readLine()) != null) {
                String[] currentTeacher = returnLine.split(",");
                lines.add(currentTeacher);
            }
            return lines;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Write File for Teacher, Adds each account into a new line.
    private synchronized static boolean writeTeacherFile(File filename, String username, String pass) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {

            String teacherInfo = username + "," + pass + "\n";
            bw.write(teacherInfo);
            bw.flush();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static ArrayList<String[]> readStudentFile(File filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            //new array list for final return
            ArrayList<String[]> lines = new ArrayList<>();
            String returnLine;

            //adds the read line to the arraylist as long as there more/is not null
            while ((returnLine = br.readLine()) != null) {
                String[] currentStudent = returnLine.split(",");
                lines.add(currentStudent);
            }
            return lines;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Remove Teacher Account...reads the entire file, and rewrite it into a temp file excluding the account that is
    //being removed, renames the file afterwards
    private synchronized static String removeTeacherAccount(String accInfo) {
        boolean success = false;
        try {
            File teacherAcc = new File("src/Teacher.txt");
            File tempFile = new File("src/TempFile.txt");
            File newtempFile = new File(tempFile.getAbsolutePath());
            File newteacherAcc = new File(teacherAcc.getAbsolutePath());

            BufferedReader reader = new BufferedReader(new FileReader(newteacherAcc));
            BufferedWriter writer = new BufferedWriter(new FileWriter(newtempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();

                if (!(trimmedLine.equals(accInfo))) {
                    writer.write(currentLine + "\n");
                }
                if (trimmedLine.equals(accInfo)) {
                    success = true;
                }
            }
            writer.close();
            reader.close();
            if (success) {
                success = teacherAcc.delete();
                success = tempFile.renameTo(teacherAcc);
                return "Account was successfully deleted";
            } else {
                return "Account did not exist";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error has occurred";
        }
    }

    //Similar to remove teacher account excepts this time it writes the new acc info also
    private synchronized static String editTeacherAccount(String accInfo, String newaccInfo) {
        boolean success = false;
        try {
            File teacherAcc = new File("src/Teacher.txt");
            File tempFile = new File("src/TempFile.txt");
            File newtempFile = new File(tempFile.getAbsolutePath());
            File newteacherAcc = new File(teacherAcc.getAbsolutePath());

            BufferedReader reader = new BufferedReader(new FileReader(newteacherAcc));
            BufferedWriter writer = new BufferedWriter(new FileWriter(newtempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();

                if (!(trimmedLine.equals(accInfo))) {
                    writer.write(currentLine + "\n");
                }
                if (trimmedLine.equals(accInfo)) {
                    writer.write(newaccInfo + "\n");
                    success = true;
                }
            }
            writer.close();
            reader.close();
            if (success) {
                success = teacherAcc.delete();
                success = tempFile.renameTo(teacherAcc);
                return "Account was successfully edited";
            } else {
                return "Account did not exist";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error has occurred";
        }
    }

    //write student file
    private synchronized static void writeStudentFile(String filename, String name, String PUID) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            pw.write(name);
            pw.write("\n");
            pw.write(PUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void reWriteStudentFile(String filename, ArrayList<String[]> studentList) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            for (String[] student : studentList) {
                pw.write(student[0] + "," + student[1]);
            }
        } catch (IOException e) {
            //System.out.println("Error occurred writing to file!");
            e.printStackTrace();
        }
    }

    private static ArrayList<String[]> readStudentCourses(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            ArrayList<String[]> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] currentStudent = line.split(",");
                lines.add(currentStudent);
            }
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //returns the list of all student quizzes
    private static ArrayList<String[]> readStudentQuizzes(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            ArrayList<String[]> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] currentStudent = line.split(",");
                lines.add(currentStudent);
            }
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void reWriteStudentQuizzes(String filename, ArrayList<String[]> quizList) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            for (String[] quiz : quizList) {
                for (int i = 0; i < quiz.length; i++) {
                    if (i == quiz.length - 1) {
                        pw.write(quiz[i]);
                    } else {
                        pw.write(quiz[i] + ",");
                    }
                }
                pw.write("\n");
            }
        } catch (IOException e) {
            //System.out.println("Error occurred writing to file!");
            e.printStackTrace();
        }
    }

    //reads in the quiz for student
    private static ArrayList<String> readQuiz(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ArrayList<String> readQuizFileNames(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private synchronized void remakeTeachFile(File filename) {
        boolean success = false;
        try {
            File teacherAcc = new File("src/Teacher.txt");
            File tempFile = new File("src/TempFile.txt");
            File newtempFile = new File(tempFile.getAbsolutePath());
            File newteacherAcc = new File(teacherAcc.getAbsolutePath());

            BufferedReader reader = new BufferedReader(new FileReader(newteacherAcc));
            BufferedWriter writer = new BufferedWriter(new FileWriter(newtempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                writer.write(currentLine + "\n");
            }
            writer.close();
            reader.close();
            success = teacherAcc.delete();
            success = tempFile.renameTo(teacherAcc);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void run() {
        try {
            //universal variables
            File teacherAcc = new File("src/Teacher.txt");
            File newteacherAcc = new File(teacherAcc.getAbsolutePath());
            File studentAcc = new File("FileDirectory.txt");
            File newstudentAcc = new File(studentAcc.getAbsolutePath());

            String teacherChoice = "";
            String studentChoice = "";

            //connecting client
            ServerSocket serverSocket = new ServerSocket(4242);

            Socket socket = serverSocket.accept();

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            writer.println("connected");
            writer.flush();

            //loop so user can keep using the program until they say close
            while (true) {
                String teacherOrStudent = reader.readLine();
                if (teacherOrStudent == null) {
                    break;
                }
                if (teacherOrStudent.equals("return")) {
                    break;
                }

                writer.println();
                writer.flush();

                String loginOrCreate = reader.readLine();
                if (loginOrCreate.equals("return")) {
                    break;
                }

                writer.println();
                writer.flush();

                if (teacherOrStudent.equals("teacher")) {
                    if (loginOrCreate.equals("login")) {
                        String accountInfo = reader.readLine();
                        if (accountInfo == null) {
                            break;
                        }
                        String teacherUsername = accountInfo.substring(0, accountInfo.indexOf(" "));
                        String teacherPassword = accountInfo.substring(accountInfo.indexOf(" ") + 1);

                        ArrayList<String[]> allTeacher = readTeacherFile(teacherAcc);
                        //boolean for whether the login was successful or not
                        boolean success = false;
                        //Goes through the allteacher arraylist and gets the string at the certain spot. It then compares
                        // those two
                        //to the username and pass, determining whether the login are valid
                        Teacher curTeacher = new Teacher(teacherUsername, teacherPassword);
                        assert allTeacher != null;
                        for (String[] teacher : allTeacher) {
                            if (teacherUsername.equals(teacher[0]) && teacherPassword.equals(teacher[1])) {
                                curTeacher.setName(teacherUsername);
                                curTeacher.setPassword(teacherPassword);
                                success = true;
                                break;
                            }
                        }
                        if (success) {
                            curTeacher = readingTeacherCoursesAndQuizzes(curTeacher);
                        }
                        writer.println(success);
                        writer.flush();


                        if (success) {
                            //teacher menu stuff
                            while (true) {
                                //reads choice1 from client
                                teacherChoice = reader.readLine();
                                if (teacherChoice.equals("null")) {
                                    writer.println();
                                    writer.flush();
                                    break;
                                }
                                if (teacherChoice.equals("return")) {
                                    break;
                                }
                                switch (teacherChoice) {
                                    //course menu stuff
                                    case "1":
                                        writer.println("receivedCourseMenu");
                                        writer.flush();
                                        String courseChoice = reader.readLine();
                                        if (courseChoice == null) {
                                            break;
                                        }
                                        switch (courseChoice) {
                                            //add course
                                            case "1":
                                                writer.println();
                                                writer.flush();
                                                String courseName = reader.readLine();
                                                if (!courseName.equals("break")) {
                                                    Course cour = new Course(courseName);
                                                    boolean added = curTeacher.addCourse(cour);
                                                    if (added) {
                                                        savingTeacherCoursesAndQuizzes(curTeacher,
                                                                curTeacher.getCourses());
                                                    }

                                                    writer.println(added);
                                                    writer.flush();
                                                } else {
                                                    writer.println("break");
                                                    writer.flush();
                                                }
                                                break;

                                            //remove course
                                            case "2":
                                                writer.println();
                                                writer.flush();
                                                String courseName2 = reader.readLine();
                                                if (!courseName2.equals("break")) {
                                                    Course cour = new Course(courseName2);
                                                    boolean removed = curTeacher.removeCourse(cour);
                                                    savingTeacherCoursesAndQuizzes(curTeacher, curTeacher.getCourses());
                                                    writer.println(removed);
                                                    writer.flush();
                                                } else {
                                                    writer.println("break");
                                                    writer.flush();
                                                }
                                                break;

                                            //view courses
                                            case "3":
                                                String teachersCourses = "";
                                                for (int i = 0; i < curTeacher.getCourses().size(); i++) {
                                                    teachersCourses = curTeacher.getCourses().get(i).getName() +
                                                            "\n  quizzes: ";
                                                    for (int j = 0; j < curTeacher.getCourses().get(i).getQuizzes().size();
                                                         j++) {
                                                        teachersCourses += curTeacher.getCourses().get(i).getQuizzes().
                                                                get(j).getName() + " ";
                                                    }
                                                    writer.println(teachersCourses);
                                                    writer.flush();
                                                    reader.readLine();
                                                }
                                                writer.println("break");
                                                writer.flush();
                                                break;
                                            //add student to course
                                            case "4":
                                                writer.println();
                                                writer.flush();
                                                String courseAddStudent = reader.readLine();
                                                Course addToThisCourse = new Course(courseAddStudent);
                                                boolean exists = false;
                                                //makes sure course exists and initializes course object
                                                for (int i = 0; i < curTeacher.getCourses().size(); i++) {
                                                    if (curTeacher.getCourses().get(i).getName().
                                                            equals(courseAddStudent)) {
                                                        addToThisCourse = curTeacher.getCourses().get(i);
                                                        exists = true;
                                                        break;
                                                    }
                                                }
                                                //returns whether the course exists or not to client
                                                writer.println(exists);
                                                writer.flush();

                                                if (exists) {
                                                    String studentName = reader.readLine();
                                                    Student addingStudent = new Student();
                                                    boolean added = false;
                                                    boolean studentExists = false;
                                                    ArrayList<Student> allStudents = getAllStudents();
                                                    for (int i = 0; i < allStudents.size(); i++) {
                                                        if (allStudents.get(i).getName().equals(studentName)) {
                                                            addingStudent = returnStudentObject(studentName.
                                                                    substring(0, studentName.indexOf("@"))
                                                                    + "-student.txt");
                                                            studentExists = true;
                                                        }
                                                    }
                                                    writer.println(studentExists);
                                                    writer.flush();

                                                    if (studentExists) {
                                                        String test = reader.readLine();
                                                        if (curTeacher.addStudent(addingStudent, addToThisCourse)) {
                                                            added = true;
                                                            addingStudent.addCourse(addToThisCourse);
                                                            savingTeacherCoursesAndQuizzes(curTeacher,
                                                                    curTeacher.getCourses());
                                                        } else {
                                                            added = false;
                                                        }
                                                        writer.println(added);
                                                        writer.flush();
                                                    }
                                                }

                                                break;
                                            //return to main menu
                                            case "5":
                                                break;
                                        }
                                        break;

                                    //quiz menu
                                    case "2":
                                        writer.println("receivedQuizMenu");
                                        writer.flush();
                                        String quizChoice = reader.readLine();
                                        switch (quizChoice) {
                                            //create quiz
                                            case "1":
                                                String c = "Create Quiz Menu";
                                                writer.println(c);
                                                writer.flush();
                                                String cc = reader.readLine();
                                                switch (cc) {
                                                    //file
                                                    case "1":
                                                        writer.println("file");
                                                        writer.flush();
                                                        String quizfile = reader.readLine();
                                                        writer.println("true");
                                                        writer.flush();
                                                        String coursename = reader.readLine();
                                                        boolean added = false;
                                                        for (int i = 0; i < curTeacher.getCourses().size(); i++) {
                                                            if (curTeacher.getCourses().get(i).getName().equals(coursename)) {
                                                                //prints when quiz is added
                                                                added = curTeacher.addQuiz(quizfile, curTeacher.getCourses().get(i));
                                                                savingTeacherCoursesAndQuizzes(curTeacher, curTeacher.getCourses());
                                                                break;
                                                            }
                                                        }
                                                        writer.println(added);
                                                        writer.flush();
                                                        break;
                                                    //manually
                                                    case "2":
                                                        writer.println("manually");
                                                        writer.flush();
                                                        String courseName = reader.readLine();
                                                        Course course = new Course(courseName);
                                                        boolean exists = false;
                                                        for (int i = 0; i < curTeacher.getCourses().size(); i++) {
                                                            if (curTeacher.getCourses().get(i).getName().equals(courseName)) {
                                                                course = curTeacher.getCourses().get(i);
                                                                exists = true;
                                                                writer.println("true");
                                                                writer.flush();
                                                                break;
                                                            }
                                                        }
                                                        if (!exists) {
                                                            writer.println("false");
                                                            writer.flush();
                                                        } else {
                                                            String quizName = reader.readLine();
                                                            if (quizName.equals("false")) {
                                                                break;
                                                            } else {
                                                                writer.println("get");
                                                                writer.flush();
                                                            }
                                                            Quiz quiz = new Quiz(quizName);
                                                            String q1 = reader.readLine();
                                                            if (q1.equals("false")) {
                                                                break;
                                                            } else {
                                                                writer.println("get");
                                                                writer.flush();
                                                            }
                                                            String cc1 = reader.readLine();
                                                            if (cc1.equals("false")) {
                                                                break;
                                                            } else {
                                                                writer.println("get");
                                                                writer.flush();
                                                            }
                                                            String cc2 = reader.readLine();
                                                            if (cc2.equals("false")) {
                                                                break;
                                                            } else {
                                                                writer.println("get");
                                                                writer.flush();
                                                            }
                                                            String cc3 = reader.readLine();
                                                            if (cc3.equals("false")) {
                                                                break;
                                                            } else {
                                                                writer.println("get");
                                                                writer.flush();
                                                            }
                                                            String cc4 = reader.readLine();
                                                            if (cc4.equals("false")) {
                                                                break;
                                                            } else {
                                                                writer.println("get");
                                                                writer.flush();
                                                            }
                                                            Choice c1 = new Choice(cc1);
                                                            Choice c2 = new Choice(cc2);
                                                            Choice c3 = new Choice(cc3);
                                                            Choice c4 = new Choice(cc4);
                                                            ArrayList<Choice> choices = new ArrayList<Choice>();
                                                            choices.add(c1);
                                                            choices.add(c2);
                                                            choices.add(c3);
                                                            choices.add(c4);
                                                            String gbf = reader.readLine();
                                                            boolean randomized;
                                                            if (gbf.equals("true")) {
                                                                randomized = true;
                                                            } else if (gbf.equals("false")) {
                                                                randomized = false;
                                                            } else {
                                                                break;
                                                            }
                                                            Question quest = new Question(q1, choices, randomized);
                                                            quiz.addQuestion(quest);
                                                            course.writeQuizFile(quiz);
                                                            String File = quizName + "_Quiz.txt";
                                                            curTeacher.addQuiz(File, course);
                                                            savingTeacherCoursesAndQuizzes(curTeacher, curTeacher.getCourses());
                                                            writer.println("true");
                                                            writer.flush();
                                                        }
                                                        break;
                                                    //return
                                                    case "3":
                                                        break;
                                                }
                                                break;
                                            //edit quiz
                                            case "2":
                                                writer.println("edit");
                                                writer.flush();
                                                String courseName = reader.readLine();
                                                Course course = new Course(courseName);
                                                boolean exists = false;
                                                for (int i = 0; i < curTeacher.getCourses().size(); i++) {
                                                    if (curTeacher.getCourses().get(i).getName().equals(courseName)) {
                                                        course = curTeacher.getCourses().get(i);
                                                        exists = true;
                                                        break;
                                                    }
                                                }
                                                if (!exists) {
                                                    writer.println("false");
                                                    writer.flush();
                                                } else {
                                                    writer.println("true");
                                                    writer.flush();
                                                    String quizName = reader.readLine();
                                                    Quiz quiz = new Quiz(quizName);
                                                    boolean exists2 = false;
                                                    for (int i = 0; i < course.getQuizzes().size(); i++) {
                                                        if (course.getQuizzes().get(i).getName().equals(quizName)) {
                                                            quiz = course.getQuizzes().get(i);
                                                            exists2 = true;
                                                            break;
                                                        }
                                                    }
                                                    if (!exists2) {
                                                        writer.println("false");
                                                        writer.flush();
                                                    } else {
                                                        writer.println("true");
                                                        writer.flush();
                                                        String p = reader.readLine();
                                                        writer.println(p);
                                                        writer.flush();
                                                        if (p.equals("1")) {
                                                            String q1 = reader.readLine();
                                                            if (q1.equals("false")) {
                                                                break;
                                                            } else {
                                                                writer.println("get");
                                                                writer.flush();
                                                            }
                                                            String cc1 = reader.readLine();
                                                            if (cc1.equals("false")) {
                                                                break;
                                                            } else {
                                                                writer.println("get");
                                                                writer.flush();
                                                            }
                                                            String cc2 = reader.readLine();
                                                            if (cc2.equals("false")) {
                                                                break;
                                                            } else {
                                                                writer.println("get");
                                                                writer.flush();
                                                            }
                                                            String cc3 = reader.readLine();
                                                            if (cc3.equals("false")) {
                                                                break;
                                                            } else {
                                                                writer.println("get");
                                                                writer.flush();
                                                            }
                                                            String cc4 = reader.readLine();
                                                            if (cc4.equals("false")) {
                                                                break;
                                                            } else {
                                                                writer.println("get");
                                                                writer.flush();
                                                            }
                                                            Choice c1 = new Choice(cc1);
                                                            Choice c2 = new Choice(cc2);
                                                            Choice c3 = new Choice(cc3);
                                                            Choice c4 = new Choice(cc4);
                                                            ArrayList<Choice> choices = new ArrayList<Choice>();
                                                            choices.add(c1);
                                                            choices.add(c2);
                                                            choices.add(c3);
                                                            choices.add(c4);
                                                            String gbf = reader.readLine();
                                                            boolean randomized;
                                                            if (gbf.equals("true")) {
                                                                randomized = true;
                                                            } else if (gbf.equals("false")) {
                                                                randomized = false;
                                                            } else {
                                                                break;
                                                            }
                                                            Question quest = new Question(q1, choices, randomized);
                                                            quiz.addQuestion(quest);
                                                            course.writeQuizFile(quiz);
                                                            writer.println("true");
                                                            writer.flush();
                                                        } else if (p.equals("2")) {
                                                            String q2 = reader.readLine();
                                                            if (q2.equals("false")) {
                                                                break;
                                                            } else {
                                                                writer.println("get");
                                                                writer.flush();
                                                            }
                                                            for (int i = 0; i < quiz.getQuestions().size(); i++) {
                                                                if (quiz.getQuestions().get(i).getQuestion().equals(q2)) {
                                                                    quiz.removeQuestion(quiz.getQuestions().get(i));
                                                                    break;
                                                                }
                                                            }
                                                            course.writeQuizFile(quiz);
                                                            writer.println("true");
                                                            writer.flush();
                                                        } else if (p.equals("3")) {
                                                            String q3 = reader.readLine();
                                                            if (q3.equals("false")) {
                                                                break;
                                                            } else {
                                                                writer.println("get");
                                                                writer.flush();
                                                            }
                                                            for (int i = 0; i < quiz.getQuestions().size(); i++) {
                                                                if (quiz.getQuestions().get(i).getQuestion().equals(q3)) {
                                                                    String choiceChange = reader.readLine();
                                                                    writer.println("get");
                                                                    writer.flush();
                                                                    if (choiceChange.equals("A")) {
                                                                        String change = reader.readLine();
                                                                        if (change.equals("false")) {
                                                                            break;
                                                                        }
                                                                        Choice c1 = quiz.getQuestions().get(i).getChoices().get(0);
                                                                        quiz.getQuestions().get(i).modifyChoice(c1, change);
                                                                    } else if (choiceChange.equals("B")) {
                                                                        String change = reader.readLine();
                                                                        if (change.equals("false")) {
                                                                            break;
                                                                        }
                                                                        Choice c2 = quiz.getQuestions().get(i).getChoices().get(1);
                                                                        quiz.getQuestions().get(i).modifyChoice(c2, change);
                                                                    } else if (choiceChange.equals("C")) {
                                                                        String change = reader.readLine();
                                                                        if (change.equals("false")) {
                                                                            break;
                                                                        }
                                                                        Choice c3 = quiz.getQuestions().get(i).getChoices().get(2);
                                                                        quiz.getQuestions().get(i).modifyChoice(c3, change);
                                                                    } else if (choiceChange.equals("D")) {
                                                                        String change = reader.readLine();
                                                                        if (change.equals("false")) {
                                                                            break;
                                                                        }
                                                                        Choice c4 = quiz.getQuestions().get(i).getChoices().get(3);
                                                                        quiz.getQuestions().get(i).modifyChoice(c4, change);
                                                                    } else if (choiceChange.equals("false")) {
                                                                        break;
                                                                    }
                                                                    course.writeQuizFile(quiz);
                                                                    writer.println("true");
                                                                    writer.flush();
                                                                }
                                                            }
                                                        } else if (p.equals("4")) {
                                                        }
                                                    }
                                                }
                                                break;
                                            //remove quiz
                                            case "3":
                                                writer.println("remove");
                                                writer.flush();
                                                String quizName2 = reader.readLine();
                                                Course removingAQuizCour = new Course(quizName2);
                                                boolean exists1 = false;
                                                for (int i = 0; i < curTeacher.getCourses().size(); i++) {
                                                    if (curTeacher.getCourses().get(i).getName().equals
                                                            (quizName2)) {
                                                        removingAQuizCour = curTeacher.getCourses().get(i);
                                                        exists1 = true;
                                                        break;
                                                    }
                                                }
                                                if (!exists1) {
                                                    writer.println("false");
                                                } else {
                                                    writer.println("true");
                                                    writer.flush();
                                                    String removeQuiz = reader.readLine();
                                                    Quiz removeQ = new Quiz(removeQuiz);
                                                    curTeacher.removeQuiz(removeQ, removingAQuizCour);
                                                    savingTeacherCoursesAndQuizzes(curTeacher, curTeacher.getCourses());
                                                    writer.println("true");
                                                }
                                                writer.flush();
                                                break;

                                            //view quizzes
                                            case "4":
                                                writer.println("view");
                                                writer.flush();
                                                String viewCourse = reader.readLine();
                                                Course viewingCourse = new Course(viewCourse);
                                                boolean exists2 = false;
                                                for (int i = 0; i < curTeacher.getCourses().size(); i++) {
                                                    if (curTeacher.getCourses().get(i).getName().equals(viewCourse)) {
                                                        viewingCourse = curTeacher.getCourses().get(i);
                                                        exists2 = true;
                                                        break;
                                                    }
                                                }
                                                if (!exists2) {
                                                    writer.println("false");
                                                    writer.flush();
                                                } else {
                                                    writer.println("true");
                                                    writer.flush();
                                                    String viewQuiz = reader.readLine();
                                                    boolean exists3 = false;
                                                    for (int i = 0; i < viewingCourse.getQuizzes().size(); i++) {
                                                        if (viewingCourse.getQuizzes().get(i).getName().equals
                                                                (viewQuiz)) {
                                                            String gb = viewingCourse.getQuizzes().get(i).toString() + "0";
                                                            writer.println(gb);
                                                            writer.flush();
                                                            exists3 = true;
                                                            break;
                                                        }
                                                    }
                                                    if (!exists3) {
                                                        writer.println("false");
                                                        writer.flush();
                                                    }

                                                }
                                                break;
                                            //grade quiz
                                            case "5":
                                                writer.println("grade");
                                                writer.flush();
                                                String stuUser = reader.readLine();
                                                writer.println("get");
                                                writer.flush();
                                                String stuPUID = reader.readLine();
                                                writer.println("get");
                                                writer.flush();
                                                //new student
                                                Student gradedStu = new Student(stuUser, stuPUID);
                                                String studentExists = "false";
                                                ArrayList<Student> allStudents = getAllStudents();
                                                for (int i = 0; i < allStudents.size(); i++) {
                                                    if (allStudents.get(i).getName().equals(stuUser)) {
                                                        gradedStu = allStudents.get(i);
                                                        studentExists = "true";
                                                    }
                                                }
                                                writer.println(studentExists);
                                                writer.flush();
                                                String gradeCourse = reader.readLine();
                                                Course gradecourseView = new Course(gradeCourse);
                                                //boolean to check if course exists
                                                boolean courseExist = false;
                                                //Checks for the course
                                                for (int i = 0; i < curTeacher.getCourses().size(); i++) {
                                                    if (curTeacher.getCourses().get(i).getName().equals(gradeCourse)) {
                                                        gradecourseView = curTeacher.getCourses().get(i);
                                                        courseExist = true;
                                                        break;
                                                    }
                                                }
                                                //Course doesn't exist
                                                if (!courseExist) {
                                                    writer.println("false");
                                                    writer.flush();
                                                }
                                                //Course exists, asks for the quiz to grade
                                                else {
                                                    writer.println("true");
                                                    writer.flush();
                                                    String asksgradeQuiz = reader.readLine();
                                                    //Quiz that needs to be graded
                                                    Quiz gradeQuiz = new Quiz(asksgradeQuiz);
                                                    //boolean to check if course exists
                                                    boolean courseExist1 = false;
                                                    //gets the quiz
                                                    for (int i = 0; i < gradecourseView.getQuizzes().size(); i++) {
                                                        if (gradecourseView.getQuizzes().get(i).getName().equals
                                                                (asksgradeQuiz)) {
                                                            gradeQuiz = gradecourseView.getQuizzes().get(i);
                                                            courseExist1 = true;
                                                            break;
                                                        }
                                                    }
                                                    //if the quiz doesn't exist
                                                    if (!courseExist1) {
                                                        writer.println("false");
                                                        writer.flush();
                                                        //if it exists
                                                    } else {
                                                        //grades the quiz
                                                        writer.println("true");
                                                        writer.flush();
                                                        ArrayList<String> list = curTeacher.seeStudentSubmissions(gradedStu, gradeQuiz, gradecourseView);
                                                        String listString = "";
                                                        for (String s : list) {
                                                            listString = listString + s;
                                                        }
                                                        listString = listString + "0";
                                                        writer.println(listString);
                                                        writer.flush();
                                                        String points = reader.readLine();
                                                        if (points.equals("false")) {
                                                            break;
                                                        }
                                                        int pointsPossible = Integer.parseInt(points);
                                                        writer.println("get");
                                                        writer.flush();
                                                        String studentpoints = reader.readLine();
                                                        if (studentpoints.equals("false")) {
                                                            break;
                                                        }
                                                        double totalPoints = Double.parseDouble(studentpoints);
                                                        String grade = totalPoints + "/" + pointsPossible;
                                                        double percentageGrade = (totalPoints / pointsPossible) * 100;
                                                        String percent = percentageGrade + "%";
                                                        //write a graded submissions file with updated grade (file title format is StudentName_QuizName_Grades.txt)
                                                        try {
                                                            String gb = gradedStu.getName().replaceAll("@purdue.edu", "");
                                                            String fileName = (gb + "_" + gradeQuiz.getName() + "_" + gradecourseView.getName() + "_Grades.txt").
                                                                    replaceAll(" ", "_");
                                                            FileOutputStream fos = new FileOutputStream(fileName, true);
                                                            PrintWriter pw = new PrintWriter(fos);
                                                            pw.println("Quiz: " + gradeQuiz.getName());
                                                            pw.println("Student: " + gradedStu.getName());
                                                            pw.println();
                                                            pw.println("Total Points: " + grade);
                                                            pw.println("Grade: " + percent);
                                                            pw.close();
                                                        } catch (FileNotFoundException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //needs current course object in parameter
                                                    }
                                                }

                                                break;
                                            //see submission
                                            case "6":
                                                writer.println("see");
                                                writer.flush();
                                                String stuUsersee = reader.readLine();
                                                writer.println("get");
                                                writer.flush();
                                                String stuPUIDsee = reader.readLine();
                                                writer.println("get");
                                                writer.flush();
                                                //new student
                                                Student seeStu = new Student(stuUsersee, stuPUIDsee);
                                                String studentExist = "false";
                                                ArrayList<Student> allStudent = getAllStudents();
                                                for (int i = 0; i < allStudent.size(); i++) {
                                                    if (allStudent.get(i).getName().equals(stuUsersee)) {
                                                        seeStu = allStudent.get(i);
                                                        studentExist = "true";
                                                    }
                                                }
                                                writer.println(studentExist);
                                                writer.flush();
                                                String seeCourse = reader.readLine();
                                                Course seecourseView = new Course(seeCourse);

                                                //boolean to check if course exists
                                                boolean seeExist = false;
                                                //Checks for the course
                                                for (int i = 0; i < curTeacher.getCourses().size(); i++) {
                                                    if (curTeacher.getCourses().get(i).getName().equals(seeCourse)) {
                                                        seecourseView = curTeacher.getCourses().get(i);
                                                        seeExist = true;
                                                        break;
                                                    }
                                                }

                                                //Course doesn't exist
                                                if (!seeExist) {
                                                    writer.println("false");
                                                    writer.flush();
                                                }
                                                //Course exists, asks for the quiz to grade
                                                else {
                                                    writer.println("true");
                                                    writer.flush();
                                                    String asksseeQuiz = reader.readLine();
                                                    //Quiz that needs to be graded

                                                    Quiz seesubQuiz = new Quiz(asksseeQuiz);
                                                    //boolean to check if course exists
                                                    boolean seeExist1 = false;

                                                    //gets the quiz
                                                    for (int i = 0; i < seecourseView.getQuizzes().size(); i++) {
                                                        if (seecourseView.getQuizzes().get(i).getName().equals
                                                                (asksseeQuiz)) {
                                                            seesubQuiz = seecourseView.getQuizzes().get(i);
                                                            seeExist1 = true;
                                                            break;
                                                        }
                                                    }
                                                    //if the quiz doesn't exist
                                                    if (!seeExist1) {
                                                        writer.println("false");
                                                        writer.flush();
                                                        //if it exists
                                                    } else {
                                                        //grades the quiz
                                                        writer.println("true");
                                                        writer.flush();
                                                        ArrayList<String> list = curTeacher.seeStudentSubmissions(seeStu, seesubQuiz, seecourseView);
                                                        String listString = "";
                                                        for (String s : list) {
                                                            listString = listString + s;
                                                        }
                                                        listString = listString + "0";
                                                        writer.println(listString);
                                                        writer.flush();
                                                    }
                                                }
                                                break;
                                            //return to main menu
                                            case "7":
                                                break;
                                        }
                                        break;

                                    case "3":
                                        writer.println("receivedAccountMenu");
                                        writer.flush();
                                        String accChoice = reader.readLine();
                                        switch (accChoice) {
                                            //Edit Account
                                            case "1":
                                                String accOp1 = reader.readLine();
                                                //switch
                                                switch (accOp1) {
                                                    case "1":
                                                        //old info
                                                        String oldInfo = reader.readLine();
                                                        writer.println("true");
                                                        writer.flush();
                                                        //new info
                                                        String newInfo = reader.readLine();

                                                        //Calculations
                                                        //Current Login User and Pass before the new one is set
                                                        String currentLogin = curTeacher.getName() + "," + curTeacher.getPassword();
                                                        String currentUser = curTeacher.getName();
                                                        String currentPass = curTeacher.getPassword();

                                                        //If the edited account is the one logged in, then reset
                                                        // the currentTeacher
                                                        ArrayList<String> testAcc = new ArrayList<>(Arrays.asList(newInfo.split(",")));
                                                        String newUser = testAcc.get(0);
                                                        String newPass = testAcc.get(1);

                                                        //All of the teacher accs in the database
                                                        ArrayList<String[]> currentList = readTeacherFile(newteacherAcc);
                                                        boolean existsAlready = false;
                                                        boolean invalidInfo = true;

                                                        //Goes through the allteacher arraylist and gets the
                                                        // string at the certain spot. It then compares the
                                                        //to the username and determines whether the username
                                                        // already exists
                                                        assert currentList != null;
                                                        for (String[] teacher : currentList) {
                                                            if (newUser.equals(teacher[0])) {
                                                                existsAlready = true;
                                                                break;
                                                            }
                                                        }

                                                        String teachUS = oldInfo.substring(0, oldInfo.indexOf(","));
                                                        String teachPWD = oldInfo.substring(oldInfo.indexOf(",") + 1);
                                                        ArrayList<String[]> allAcc = readTeacherFile(teacherAcc);

                                                        assert allAcc != null;
                                                        for (String[] teacher : allAcc) {
                                                            if (teachUS.equals(teacher[0]) && teachPWD.equals(teacher[1])) {
                                                                invalidInfo = false;
                                                                break;
                                                            }
                                                        }

                                                        //Only edits if username doesn't already exist
                                                        if (existsAlready) {
                                                            writer.println("alreadyexists");
                                                            writer.flush();
                                                            break;
                                                        } else if (invalidInfo) {
                                                            writer.println("invalidinfo");
                                                            writer.flush();
                                                            break;
                                                        } else {
                                                            editTeacherAccount(oldInfo, newInfo);
                                                            //Sets the new currentTeacher after putting the old one
                                                            // into a string
                                                            boolean delyes = false;
                                                            //Deletes the old txt file
                                                            if (!teachUS.equals(newUser)) {
                                                                File deledAcc = new File(teachUS + "_Courses.txt");
                                                                if (deledAcc.delete()) {
                                                                    delyes = true;
                                                                }

                                                            }

                                                            if (oldInfo.equals(currentLogin)) {
                                                                assert curTeacher != null;
                                                                curTeacher.setPassword(newPass);
                                                                curTeacher.setName(newUser);
                                                                savingTeacherCoursesAndQuizzes(curTeacher, curTeacher.getCourses());

                                                            }

                                                            writer.println("success");
                                                            writer.flush();
                                                        }


                                                        break;
                                                    case "2":
                                                        String oldpassInfo = reader.readLine();
                                                        String editAcc = curTeacher.getName() + "," + oldpassInfo;

                                                        String newPassInfo = reader.readLine();
                                                        String newaccInfo = curTeacher.getName() + "," + newPassInfo;
                                                        //Calculations
                                                        //checks if the username is also changed, if is was, then say error
                                                        String[] newaccHolder = newaccInfo.split(",");

                                                        //String to check for comma

                                                        //If user is the same, then proceed
                                                        if (Objects.equals(newaccHolder[0], curTeacher.getName()) && !newPassInfo.contains(",")) {
                                                            //user and pass
                                                            ArrayList<String> passiltestAcc = new ArrayList<>
                                                                    (Arrays.asList(newaccInfo.split(",")));
                                                            String passnewUser = passiltestAcc.get(0);
                                                            String pasasnewPass = passiltestAcc.get(1);

                                                            if (editTeacherAccount(editAcc, newaccInfo).equals
                                                                    ("Account did not exist")) {
                                                                writer.println("wrongcred");
                                                                writer.flush();

                                                            } else {

                                                                //Current Login User and Pass before the new one is set
                                                                String paassscurrentLogin = curTeacher.getName() + "," +
                                                                        curTeacher.getPassword();

                                                                //Sets the new currentTeacher after putting the old
                                                                // one into a string
                                                                curTeacher = new Teacher(passnewUser, pasasnewPass);
                                                                savingTeacherCoursesAndQuizzes(curTeacher,
                                                                        curTeacher.getCourses());
                                                                if (editAcc.equals(paassscurrentLogin)) {
                                                                    curTeacher.setPassword(pasasnewPass);
                                                                    curTeacher.setName(passnewUser);
                                                                }

                                                                writer.println("success");
                                                                writer.flush();

                                                            }


                                                        } else {
                                                            writer.println("nouser");
                                                            writer.flush();

                                                        }

                                                        break;
                                                    case "3":
                                                        break;
                                                }
                                                break;
                                                //Delete Account
                                            case "2":
                                                String delacc = reader.readLine();



                                                ArrayList<String> testAcc = new ArrayList<>(Arrays.asList(delacc.split(",")));
                                                //String of username of the account
                                                String delUser = testAcc.get(0);
                                                String deelPass = testAcc.get(1);



                                                String res = removeTeacherAccount(delacc);
                                                if (res.equals("Account was successfully deleted")) {
                                                    boolean delyes = false;
                                                    File deledAcc = new File(delUser+"_Courses.txt");
                                                    if(deledAcc.delete()) {
                                                        delyes = true;
                                                    }

                                                    //If the username of the account equals the one that is logged in then exit the program
                                                    if (delUser.equals(curTeacher.getName())) {
                                                        writer.println("successlogout");
                                                        writer.flush();

                                                    } else {
                                                        writer.println("success");
                                                        writer.flush();
                                                    }

                                                } else if (res.equals("Account did not exist")) {
                                                    writer.println("failed");
                                                    writer.flush();
                                                }
                                                break;
                                                //Current Login Details
                                            case "3":
                                                if (curTeacher.getCourses() != null && curTeacher.getPassword() !=
                                                        null) {
                                                    writer.println("success");
                                                    writer.flush();
                                                    writer.println(curTeacher.getName() + "," + curTeacher.getPassword());
                                                    writer.flush();

                                                } else {
                                                    writer.println("failed");
                                                    writer.flush();
                                                }
                                                break;
                                            case "4":
                                                break;

                                        }

                                        break;
                                    //exit to login
                                    case "4":
                                        writer.println("receivedExitToLogin");
                                        writer.flush();

                                        //stuff in client
                                        break;
                                    //exit program
                                    case "5":
                                        writer.println("receivedExitProgram");
                                        writer.flush();
                                        String breakString = reader.readLine();
                                        break;
                                }
                                if (teacherChoice.equals("5") || teacherChoice.equals("4")) {
                                    break;
                                }
                            }
                            if (teacherChoice.equals("5") || teacherChoice == null) {
                                break;
                            }

                        }

                    } else if (loginOrCreate.equals("create account")) {
                        //create teacher account
                        boolean valid = false;
                        String username = "";
                        while (!valid) {
                            username = reader.readLine();
                            valid = true;
                            //All of the teacher accs in the database
                            ArrayList<String[]> allTeacher = readTeacherFile(teacherAcc);
                            //Goes through the allteacher arraylist and gets the string at the certain spot. It then compares
                            //the to the username and determines whether the username already exists
                            for (String[] teacher : allTeacher) {
                                if (username.equals(teacher[0])) {
                                    valid = false;
                                    break;
                                }
                            }
                            writer.println(valid);
                            writer.flush();
                        }
                        if (valid) {
                            String password = reader.readLine();
                            writeTeacherFile(teacherAcc, username, password);
                            Teacher teach = new Teacher(username, password);
                            savingTeacherCoursesAndQuizzes(teach, teach.getCourses());
                            writer.println("success");
                            writer.flush();
                        }

                    }
                }
                /**
                 * Student Stuff
                 */
                else if (teacherOrStudent.equals("student")) {
                    if (loginOrCreate.equals("login")) {

                        boolean verifiedUserBool = false;
                        boolean verifiedPassBool = false;
                        boolean success = false;

                        String currentStudentFileName = "";
                        String studentUser = "";
                        String studentPass = "";
                        Student currentStudent = new Student();
                        String studentAccount = "";
                        //student login
                        do {
                            studentAccount = reader.readLine();
                            if (studentAccount == null) {
                                break;
                            }
                            //splitting the inputted username and password
                            String[] studentAccountArr = studentAccount.split(" ");

                            String usernameInput = studentAccountArr[0];
                            String passwordInput = studentAccountArr[1];

                            String verifiedTxtFile = usernameInput + "-student.txt";

                            ArrayList<String> fileDirectory = readFileDirectory("FileDirectory.txt");
                            //addition by intellij
                            assert fileDirectory != null;
                            //searching for the file in the directory
                            try {
                                for (String fileName : fileDirectory) {
                                    if (fileName.equals(verifiedTxtFile)) {
                                        studentUser = usernameInput;
                                        verifiedUserBool = true;
                                        currentStudentFileName = verifiedTxtFile;
                                    }
                                    //if it doesn't check it, that means that the verified userBool is false
                                }
                            } catch (Exception e) {
                                writer.println("error");
                                writer.flush();
                            }
                            //reading in the filename for student
                            ArrayList<String> studentFileName = readStudentFile(verifiedTxtFile);
                            //intellij addition
                            assert studentFileName != null;
                            //seeing if the password is correct
                            try {
                                if (passwordInput.equals(studentFileName.get(1))) {
                                    studentPass = passwordInput;
                                    verifiedPassBool = true;
                                }
                            } catch (Exception e) {
                                writer.println("error");
                                writer.flush();
                            }

                            if (verifiedUserBool == true && verifiedPassBool == true) {
                                success = true;
                            } else {
                                success = false;
                            }
                            //resetting the values to false
                            verifiedUserBool = false;
                            verifiedPassBool = false;
                        } while (success == false);

                        if (studentAccount == null) {
                            break;
                        }
                        ArrayList<String> allStudent = readStudentFile(currentStudentFileName);
                        //boolean for whether the login was successful or not
                        //success = true;
                        //Goes through the allstudent arraylist and gets the string at the certain spot. It then compares
                        // those two
                        //to the username and pass, determining whether the login are valid
                        Student curStudent = new Student(studentUser, studentPass);

                        writer.println(success);
                        writer.flush();

                        if (success) {
                            //student menu stuff
                            while (true) {
                                //reads choice1 from client
                                studentChoice = reader.readLine();
                                if (studentChoice == null) {
                                    break;
                                }
                                if (studentChoice.equals("return")) {
                                    return;
                                }
                                switch (studentChoice) {
                                    case "Course Selection":
                                        //Course Selection
                                        writer.println("receivedCourseMenu");
                                        writer.flush();

                                        allStudent.remove(1);
                                        allStudent.remove(0);

                                        //ArrayList of ArrayList String
                                        ArrayList<ArrayList<String>> courseList = new ArrayList<>();

                                        for (int i = 0; i < allStudent.size(); i++) {
                                            //List should be:
                                            //Course: whatever; Quizzes: jdfajlfk -> [whatever, jdajlfk]
                                            ArrayList<String> currentLineList = new ArrayList<>();
                                            String currentLine = allStudent.get(i);

                                            //parsing the string here
                                            String[] coursesAndQuizzes = currentLine.split(";");
                                            String currentCourse = coursesAndQuizzes[0];
                                            String currentQuizzes = coursesAndQuizzes[1];

                                            //cutting off the unnecessary parts for course
                                            currentCourse = currentCourse.substring(8, currentCourse.length());
                                            currentLineList.add(currentCourse);

                                            //cutting off the unnecessary parts for quiz - however it does not make them into
                                            //a list if there are multiple quizzes
                                            currentQuizzes = currentQuizzes.substring(10, currentQuizzes.length());
                                            currentLineList.add(currentQuizzes);

                                            //adding it to the huge list
                                            courseList.add(currentLineList);
                                        }


                                        String courseLists = "";
                                        for (int i = 0; i < courseList.size(); i++) {
                                            courseLists += courseList.get(i).get(0);
                                            if (i < courseList.size()) {
                                                courseLists += ",";
                                            }
                                        }

                                        writer.println(courseLists);
                                        writer.flush();
                                        String courseSelectionChoice = reader.readLine();
                                        if (courseSelectionChoice == null) {
                                            break;
                                        }

                                        int courseSelected = 0;

                                        for (int i = 0; i < courseList.size(); i++) {
                                            if (courseList.get(i).get(0).equals(courseSelectionChoice)) {
                                                courseSelected = i;
                                            }
                                        }

                                        ArrayList<String> currentCourse = courseList.get(courseSelected);
                                        String uncutQuizList = currentCourse.get(1);


                                        ArrayList<String> currentCourseQuizzes = new ArrayList<>();
                                        String[] cutQuizList = uncutQuizList.split(",");
                                        //adding the first element in a list because it does not have a space in the front
                                        currentCourseQuizzes.add(cutQuizList[0]);
                                        //adding the rest of the elements
                                        for (int i = 1; i < cutQuizList.length; i++) {
                                            String currentQ = cutQuizList[i].substring(1, cutQuizList[i].length());
                                            currentCourseQuizzes.add(currentQ);
                                        }

                                        writer.println(currentCourseQuizzes);
                                        writer.flush();
                                        String quizSelectionChoice = reader.readLine();
                                        if (quizSelectionChoice == null) {
                                            break;
                                        }

                                        int getQuizNumber = 0;
                                        //System.out.println(currentCourseQuizzes);
                                        String quizSelect = quizSelectionChoice.replaceAll(" ", "");
                                        for (int i = 0; i < currentCourseQuizzes.size(); i++) {
                                            //System.out.println(currentCourseQuizzes.get(i));
                                            //System.out.println(quizSelectionChoice);
                                            if (currentCourseQuizzes.get(i).equalsIgnoreCase(quizSelect)) {
                                                getQuizNumber = i;
                                            }
                                        }

                                        //System.out.println(getQuizNumber);


                                        String selectedQuizName = currentCourseQuizzes.get(getQuizNumber);
                                        String currentQuizName = currentCourseQuizzes.get(getQuizNumber);
                                        ArrayList<String> quizNotFormatted = readQuizFile(currentQuizName);
                                        String quizName = quizNotFormatted.get(0);
                                        String quizBooleans = quizNotFormatted.get(1);
                                        String[] quizBooleasStr = quizBooleans.split(" ");

                                        /**
                                         * Quiz Booleans
                                         */
                                        boolean[] quizBooleansArr = new boolean[2];

                                        if (quizBooleasStr[0].equals("true")) {
                                            quizBooleansArr[0] = true;
                                        } else {
                                            quizBooleansArr[0] = false;
                                        }

                                        if (quizBooleasStr[1].equals("true")) {
                                            quizBooleansArr[1] = true;
                                        } else {
                                            quizBooleansArr[1] = false;
                                        }

                                        ArrayList<Question> currentQuizQuestionArr = new ArrayList<>();
                                        //skips every two
                                        for (int i = 2; i < quizNotFormatted.size(); i += 2) {
                                            String currentQuestion = quizNotFormatted.get(i);
                                            //Question_#: jfaljfdlkajfd -> jflajflkdajfdklj
                                            currentQuestion = currentQuestion.substring(11, currentQuestion.length());

                                            String currentChoicesUncut = quizNotFormatted.get(i + 1);
                                            //Choices: jfkadljflkdajfdkljdfaslk -> jfkldajfldkjldf
                                            currentChoicesUncut = currentChoicesUncut.substring(8, currentChoicesUncut.length());
                                            String[] choicesSplit = currentChoicesUncut.split(",");


                                            ArrayList<Choice> currentChoicesArr = new ArrayList<>();

                                            for (String choice : choicesSplit) {
                                                // A)jfdklajf -> jfklajfdlk
                                                String cutChoice = choice.substring(3, choice.length());
                                                Choice currentChoice = new Choice(cutChoice);
                                                currentChoicesArr.add(currentChoice);
                                            }

                                            Question currentQuestionObject = new Question(currentQuestion, currentChoicesArr, quizBooleansArr[0]);
                                            currentQuizQuestionArr.add(currentQuestionObject);
                                        }

                                        //initiallizing the quiz object
                                        Quiz currentQuizObject = new Quiz(quizName, currentQuizQuestionArr, quizBooleansArr[1]);

                                        //Dont really need every quiz in the ArrayList you just need the quiz that the student is currently taking
                                        ArrayList<Quiz> currentQuizArr = new ArrayList<>();
                                        currentQuizArr.add(currentQuizObject);
                                        String currentCourseName = courseList.get(courseSelected).get(0);

                                        Course currentCourseObject = new Course(currentQuizArr, currentCourseName);

                                        String takeQuizorViewScore = reader.readLine();

                                        switch (takeQuizorViewScore) {
                                            case "Take Quiz":
                                                currentStudent.takeQuiz(currentQuizObject, curStudent, currentCourseObject);
                                                currentStudent.viewQuizResponsesFile(curStudent, currentQuizObject, currentCourseObject);
                                                break;
                                            case "View Quiz Score":
                                                writer.println(currentStudent.viewQuizScore(curStudent, currentQuizObject, currentCourseObject));
                                                writer.flush();
                                                break;
                                        }
                                        break;

                                    case "Delete Account":
                                        File currentStudentFile = new File(currentStudentFileName);
                                        currentStudentFile.delete();
                                        //delete it from the directory
                                        //read file, kick the student file out, and rewrite the file
                                        ArrayList<String> allStudents = readFileDirectory("FileDirectory.txt");
                                        for (int i = 0; i < allStudents.size(); i++) {
                                            if (allStudents.get(i).equals(currentStudentFileName)) {
                                                allStudents.remove(i);
                                            }
                                        }
                                        //writing the rest of the students back to the file
                                        writeFileDirectory("FileDirectory.txt", allStudents);
                                        writer.println("Account deleted");
                                        writer.flush();
                                        break;
                                    case "Save and Exit App":
                                        return;

                                }
                            }

                        }

                    } else if (loginOrCreate.equals("create account")) {
                        //student create account
                        //error handling is in client
                        String usernameCreateAccount = reader.readLine();
                        String[] choppedEmailName = usernameCreateAccount.split("@");
                        String constructedStudentFileName = choppedEmailName[0] + "-student.txt";

                        String passwordCreateAccount = reader.readLine();
                        writeStudentFile(constructedStudentFileName, usernameCreateAccount, passwordCreateAccount);
                        ArrayList<String> allStudents = readFileDirectory("FileDirectory.txt");
                        allStudents.add(constructedStudentFileName);
                        writeFileDirectory("FileDirectory.txt", allStudents);
                    }
                }

                //don't remove this it's essential
                if (teacherChoice.equals("5")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
