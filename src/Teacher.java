import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.*;

/**
 * Teacher.java
 * <p>
 * Creates teacher objects that have a username, an array of courses, and a password.
 * Each teacher can add courses to their array and then quizzes to those courses.
 * Any edits or additions of quizzes are rewritten in a quiz file using Course.java.
 * Teachers can also see a student's submission and grade a student's submission.
 *
 * @author Chun-Yang Lee, Joshua Ho, Hpung San Aung, Margaret Haydock, Soumya Kakarlapudi Purdue CS - L21
 * @version 12/13/2021
 */

public class Teacher {

    private String name;
    private ArrayList<Course> courses;
    private String password;

    //constructor for a Teacher who already has courses
    public Teacher(String name, ArrayList<Course> courses, String password) {
        this.name = name;
        this.courses = courses;
        this.password = password;
    }

    //constructor in case a Teacher doesn't have any courses yet
    public Teacher(String name, String password) {
        this.name = name;
        this.password = password;
        courses = new ArrayList<Course>();
    }

    //getters and setters for teacher an object
    public String getName() {
        return name;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //adds course to teachers courses
    //makes sure course doesn't already exist before adding
    public synchronized boolean addCourse(Course course) {
        boolean added = true;
        if (courses == null) {
            courses.add(course);
        } else {
            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).getName().equals(course.getName())) {
                    added = false;
                    break;
                }
            }
        }
        if (added) {
            courses.add(course);
        }
        return added;
    }

    //removes course from teachers courses
    //makes sure course exists before removing
    public synchronized boolean removeCourse(Course course) {
        boolean removed = false;
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getName().equals(course.getName())) {
                for (int j = 0; j < courses.get(i).getStudents().size(); j++) {
                    courses.get(i).getStudents().get(j).removeCourse(course);
                }
                courses.remove(i);
                removed = true;
                break;
            }
        }
        return removed;
    }

    public synchronized boolean addStudent(Student student, Course course) {
        boolean added = false;
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getName().equals(course.getName())) {
                added = courses.get(i).addStudent(student);
                break;
            }
        }
        return added;
    }

    public synchronized boolean removeStudent(Student student, Course course) {
        boolean removed = false;
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getName().equals(course.getName())) {
                removed = course.removeStudent(student.getName());
                break;
            }
        }
        return removed;
    }

    //adds a quiz if given a quiz object
    //makes sure quiz doesn't already exist before adding
    //other addQuiz method also uses this after reading the file
    //no longer prints when quiz is added so that when teacher logs in nothing is printed
    public synchronized boolean addQuiz(Quiz quiz, Course course, boolean randomizedChoices, boolean randomizedQuestions) {
        boolean added = false;
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getName().equals(course.getName())) {
                if (course.addQuiz(quiz)) {
                    course.addQuiz(quiz);
                    added = true;
                    break;
                } else {
                    //couldn't add quiz because it already exists
                    return false;
                }
            }
        }
        if (added) {
            //quiz added
            course.writeQuizFile(quiz);
            for (int i = 0; i < course.getStudents().size(); i++) {
                course.getStudents().get(i).updateACourse(course);
            }
            return true;
        } else {
            //quiz not added
            return false;
        }
    }

    //adds a quiz if given a file
    //uses other addQuiz method after reading
    //returns boolean whether it added or not
    public synchronized boolean addQuiz(String filename, Course course) {

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader bfr = new BufferedReader(fr);

            String line = bfr.readLine();
            String quizName = line;
            ArrayList<Question> questions = new ArrayList<Question>();
            String randomized = bfr.readLine();
            boolean randomQuestions = false;
            boolean randomChoices = false;
            if (randomized.substring(0, randomized.indexOf(" ")).equals("true")) {
                randomQuestions = true;
            }
            if (randomized.substring(randomized.indexOf(" ") + 1).equals("true")) {
                randomChoices = true;
            }

            //loop creates questions arraylist
            while (line != null) {
                line = bfr.readLine();
                if (line == null) {
                    break;
                }
                String question = line.substring(line.indexOf(":") + 1);
                line = bfr.readLine();
                line = line.substring(line.indexOf(")") + 1);
                String choiceA = line.substring(0, line.indexOf(", "));
                line = line.substring(line.indexOf(")") + 1);
                String choiceB = line.substring(0, line.indexOf(", "));
                line = line.substring(line.indexOf(")") + 1);
                String choiceC = line.substring(0, line.indexOf(", "));
                line = line.substring(line.indexOf(")") + 1);
                String choiceD = line;

                Choice cA = new Choice(choiceA);
                Choice cB = new Choice(choiceB);
                Choice cC = new Choice(choiceC);
                Choice cD = new Choice(choiceD);
                ArrayList<Choice> choices = new ArrayList<Choice>();
                choices.add(cA);
                choices.add(cB);
                choices.add(cC);
                choices.add(cD);

                Question addedQuestion = new Question(question, choices, randomChoices);
                questions.add(addedQuestion);
            }
            Quiz addedQuiz = new Quiz(quizName, questions, randomQuestions);
            if (addQuiz(addedQuiz, course, randomChoices, randomQuestions)) {
                //adds quiz to the course through other method
                return true;
            }
            bfr.close();
            fr.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //removes a quiz if given a quiz object
    //makes sure quiz exists before removing
    //other remove quiz method also uses this after reading the file
    public synchronized boolean removeQuiz(Quiz quiz, Course course) {
        boolean removed = false;
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).equals(course)) {
                if (course.removeQuiz(quiz)) {
                    removed = true;
                    break;
                } else {
                    return false;
                }
            }
        }
        if (removed) {
            for (int i = 0; i < course.getStudents().size(); i++) {
                course.getStudents().get(i).updateACourse(course);
            }
            return true;
        } else {
            return false;
        }
    }

    //removes a quiz if given a file (file must be formatted like NewQuiz.txt)
    //uses other removeQuiz method after reading
    public synchronized void removeQuiz(String filename, Course course) {
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader bfr = new BufferedReader(fr);

            String line = bfr.readLine();
            String quizName = line.replaceAll("_", " ");
            ArrayList<Question> questions = new ArrayList<Question>();
            String randomized = bfr.readLine();
            boolean randomQuestions = false;
            boolean randomChoices = false;
            if (randomized.substring(0, randomized.indexOf(" ")).equals("true")) {
                randomQuestions = true;
            }
            if (randomized.substring(randomized.indexOf(" ") + 1).equals("true")) {
                randomChoices = true;
            }

            //loop creates questions arraylist based off the formatted file NewQuiz.txt
            while (line != null) {
                line = bfr.readLine();
                if (line == null) {
                    break;
                }
                String question = line.substring(line.indexOf(" ") + 1);
                line = bfr.readLine();
                line = line.substring(line.indexOf("A)") + 1);
                String choiceA = line.substring(0, line.indexOf(" ")).replaceAll("_", " ");
                line = line.substring(line.indexOf(", B)") + 1);
                String choiceB = line.substring(0, line.indexOf(" ")).replaceAll("_", " ");
                line = line.substring(line.indexOf(", C)") + 1);
                String choiceC = line.substring(0, line.indexOf(" ")).replaceAll("_", " ");
                line = line.substring(line.indexOf("D)") + 1);
                String choiceD = line;

                Choice cA = new Choice(choiceA);
                Choice cB = new Choice(choiceB);
                Choice cC = new Choice(choiceC);
                Choice cD = new Choice(choiceD);
                ArrayList<Choice> choices = new ArrayList<Choice>();
                choices.add(cA);
                choices.add(cB);
                choices.add(cC);
                choices.add(cD);
                Question removedQuizQuestion = new Question(question, choices, randomChoices);
                questions.add(removedQuizQuestion);
            }
            Quiz removedQuiz = new Quiz(quizName, questions, randomQuestions);
            removeQuiz(removedQuiz, course);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //allows teacher to edit quizzes by adding questions removing questions and editing question choices
    public synchronized Quiz editQuiz(Quiz quiz, Course course, int choice) {
        //Edit Quiz Options 1: Add a Question 2: Remove a Question 3: Edit a Questions choices
        choice = choice;
        if (choice == 1) {
            String question;
            do {
                question = JOptionPane.showInputDialog(null, "Enter the question you would like to add",
                        "University Card", JOptionPane.QUESTION_MESSAGE);
                if ((question == null) || (question.isBlank())) {
                    JOptionPane.showMessageDialog(null, "Invalid Input", "University Card",
                            JOptionPane.ERROR_MESSAGE);
                }
            } while ((question == null) || (question.isBlank()));
            String choice1;
            do {
                choice1 = JOptionPane.showInputDialog(null, "Enter first answer choice",
                        "University Card", JOptionPane.QUESTION_MESSAGE);
                if ((choice1 == null) || (choice1.isBlank())) {
                    JOptionPane.showMessageDialog(null, "Invalid Input", "University Card",
                            JOptionPane.ERROR_MESSAGE);
                }
            } while ((choice1 == null) || (choice1.isBlank()));
            String choice2;
            do {
                choice2 = JOptionPane.showInputDialog(null, "Enter second answer choice",
                        "University Card", JOptionPane.QUESTION_MESSAGE);
                if ((choice2 == null) || (choice2.isBlank())) {
                    JOptionPane.showMessageDialog(null, "Invalid Input", "University Card",
                            JOptionPane.ERROR_MESSAGE);
                }
            } while ((choice2 == null) || (choice2.isBlank()));
            String choice3;
            do {
                choice3 = JOptionPane.showInputDialog(null, "Enter third answer choice",
                        "University Card", JOptionPane.QUESTION_MESSAGE);
                if ((choice3 == null) || (choice3.isBlank())) {
                    JOptionPane.showMessageDialog(null, "Invalid Input", "University Card",
                            JOptionPane.ERROR_MESSAGE);
                }
            } while ((choice3 == null) || (choice3.isBlank()));
            String choice4;
            do {
                choice4 = JOptionPane.showInputDialog(null, "Enter fourth answer choice",
                        "University Card", JOptionPane.QUESTION_MESSAGE);
                if ((choice4 == null) || (choice4.isBlank())) {
                    JOptionPane.showMessageDialog(null, "Invalid Input", "University Card",
                            JOptionPane.ERROR_MESSAGE);
                }
            } while ((choice4 == null) || (choice4.isBlank()));
            Choice c1 = new Choice(choice1);
            Choice c2 = new Choice(choice2);
            Choice c3 = new Choice(choice3);
            Choice c4 = new Choice(choice4);
            ArrayList<Choice> choices = new ArrayList<Choice>();
            choices.add(c1);
            choices.add(c2);
            choices.add(c3);
            choices.add(c4);
            boolean randomized = false;
            if (JOptionPane.showConfirmDialog(null, "Would you like the choices to be randomized?"
                    , "Statistics", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                randomized = true;
            }
            Question quest = new Question(question, choices, randomized);
            quiz.addQuestion(quest);
            course.writeQuizFile(quiz);
            return quiz;
        }
        if (choice == 2) {
            String question;
            do {
                question = JOptionPane.showInputDialog(null, "Enter the question that you would like to remove",
                        "University Card", JOptionPane.QUESTION_MESSAGE);
                if ((question == null) || (question.isBlank())) {
                    JOptionPane.showMessageDialog(null, "Invalid Input", "University Card",
                            JOptionPane.ERROR_MESSAGE);
                }
            } while ((question == null) || (question.isBlank()));
            for (int i = 0; i < quiz.getQuestions().size(); i++) {
                if (quiz.getQuestions().get(i).getQuestion().equals(question)) {
                    quiz.removeQuestion(quiz.getQuestions().get(i));
                    break;
                }
            }
            course.writeQuizFile(quiz);
            return quiz;
        }
        if (choice == 3) {
            String question;
            do {
                question = JOptionPane.showInputDialog(null, "Enter a question that you would " +
                        "like to edit the choices of", "University Card", JOptionPane.QUESTION_MESSAGE);
                if ((question == null) || (question.isBlank())) {
                    JOptionPane.showMessageDialog(null, "Invalid Input", "University Card",
                            JOptionPane.ERROR_MESSAGE);
                }
            } while ((question == null) || (question.isBlank()));
            for (int i = 0; i < quiz.getQuestions().size(); i++) {
                String whichchoice = "Which choice would you like to change?";
                String[] choices = {"A", "B", "C", "D"};
                String changechoice = "What would you like to change it to?";
                if (quiz.getQuestions().get(i).getQuestion().equals(question)) {
                    String choiceChange;
                    do {
                        choiceChange = (String) JOptionPane.showInputDialog(null, whichchoice,
                                "University Card", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
                        if (choiceChange == null) {
                            JOptionPane.showMessageDialog(null, "Choice cannot be empty!", "University Card",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } while (choiceChange == null);
                    if (choiceChange.equals("A")) {
                        String change;
                        do {
                            change = JOptionPane.showInputDialog(null, changechoice,
                                    "University Card", JOptionPane.QUESTION_MESSAGE);
                            if ((change == null) || (change.isBlank())) {
                                JOptionPane.showMessageDialog(null, "Invalid Input", "University Card",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } while ((change == null) || (change.isBlank()));
                        Choice c1 = quiz.getQuestions().get(i).getChoices().get(0);
                        quiz.getQuestions().get(i).modifyChoice(c1, change);
                    } else if (choiceChange.equals("B")) {
                        String change;
                        do {
                            change = JOptionPane.showInputDialog(null, changechoice,
                                    "University Card", JOptionPane.QUESTION_MESSAGE);
                            if ((change == null) || (change.isBlank())) {
                                JOptionPane.showMessageDialog(null, "Invalid Input", "University Card",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } while ((change == null) || (change.isBlank()));
                        Choice c2 = quiz.getQuestions().get(i).getChoices().get(1);
                        quiz.getQuestions().get(i).modifyChoice(c2, change);
                    } else if (choiceChange.equals("C")) {
                        String change;
                        do {
                            change = JOptionPane.showInputDialog(null, changechoice,
                                    "University Card", JOptionPane.QUESTION_MESSAGE);
                            if ((change == null) || (change.isBlank())) {
                                JOptionPane.showMessageDialog(null, "Invalid Input", "University Card",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } while ((change == null) || (change.isBlank()));
                        Choice c3 = quiz.getQuestions().get(i).getChoices().get(2);
                        quiz.getQuestions().get(i).modifyChoice(c3, change);
                    } else if (choiceChange.equals("D")) {
                        String change;
                        do {
                            change = JOptionPane.showInputDialog(null, changechoice,
                                    "University Card", JOptionPane.QUESTION_MESSAGE);
                            if ((change == null) || (change.isBlank())) {
                                JOptionPane.showMessageDialog(null, "Invalid Input", "University Card",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } while ((change == null) || (change.isBlank()));
                        Choice c4 = quiz.getQuestions().get(i).getChoices().get(3);
                        quiz.getQuestions().get(i).modifyChoice(c4, change);
                    }
                    course.writeQuizFile(quiz);
                    return quiz;
                }
            }
        }
        return quiz;
    }

    //reads students submissions
    //doesn't have any user interaction just a String return
    public ArrayList<String> seeStudentSubmissions(Student student, Quiz quiz, Course course) {

        try {
            String s1 = student.getName().replaceAll("@purdue.edu", "");
            String fileName = (s1 + "_" + quiz.getName() + "_" + course.getName()).
                    replaceAll(" ", "_") + ".txt";
            FileReader fr = new FileReader(fileName);
            BufferedReader bfr = new BufferedReader(fr);
            ArrayList<String> studentSubmissions = new ArrayList<>();
            String line = bfr.readLine();
            while (line != null) {
                studentSubmissions.add(line + "\n");
                line = bfr.readLine();
            }
            return studentSubmissions;

        } catch (FileNotFoundException e) {
            String fault = "Student: " + student.getName() + " has not taken quiz: " + quiz.getName();
            JOptionPane.showMessageDialog(null, fault, "University Card",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    //allows teacher to grade student submissions
    public synchronized void gradeStudentSubmissions(Student student, Quiz quiz, Course course) {
        //writes out the student's submission for a specific quiz
        //Scanner input = new Scanner(System.in);
        if (seeStudentSubmissions(student, quiz, course).equals("")) {
            return;
        }

        //System.out.println(seeStudentSubmissions(student, quiz));
        //allows the teacher to enter a grade for the quiz
        int pointsPossible = 0;
        boolean b;
        do {
            b = false;
            try {
                pointsPossible = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the points possible.",
                        "University Card", JOptionPane.QUESTION_MESSAGE));
            } catch (NumberFormatException e) {
                b = true;
                JOptionPane.showMessageDialog(null, "Invalid Input", "University Card",
                        JOptionPane.ERROR_MESSAGE);
            }
        } while (b);
        ArrayList<Double> points = new ArrayList<Double>();
        ArrayList<String> justQuestion = new ArrayList<String>();
        for (int i = 2; i < seeStudentSubmissions(student, quiz, course).size(); i += 2) {
            justQuestion.add(seeStudentSubmissions(student, quiz, course).get(i));
        }

        for (int i = 0; i < justQuestion.size(); i++) {
            String prompt = "Enter point for question " + (i + 1);
            boolean c = false;
            do {
                try {
                    pointsPossible = Integer.parseInt(JOptionPane.showInputDialog(null, prompt,
                            "University Card", JOptionPane.QUESTION_MESSAGE));
                } catch (NumberFormatException e) {
                    c = true;
                    JOptionPane.showMessageDialog(null, "Invalid Input", "University Card",
                            JOptionPane.ERROR_MESSAGE);
                }
            } while (c);
            //points.add(i, input.nextDouble());
        }
        double totalPoints = 0;
        double eachPoint = 0;
        for (int i = 0; i < points.size(); i++) {
            eachPoint = points.get(i);
            totalPoints += eachPoint;
        }
        String grade = totalPoints + "/" + pointsPossible;
        double percentageGrade = (totalPoints / pointsPossible) * 100;
        String percent = percentageGrade + "%";
        //write a graded submissions file with updated grade (file title format is StudentName_QuizName_Grades.txt)
        try {
            String fileName = (student.getName() + "_" + quiz.getName() + "_" + course.getName() + "_Grades.txt").
                    replaceAll(" ", "_");
            FileOutputStream fos = new FileOutputStream(fileName, true);
            PrintWriter pw = new PrintWriter(fos);
            pw.println("Quiz: " + quiz.getName());
            pw.println("Student: " + student.getName());
            pw.println();
            for (int i = 0; i < points.size(); i++) {
                pw.println(justQuestion.get(i) + "(Points: +" + points.get(i) + ")");
            }
            pw.println();
            pw.println("Total Points: " + grade);
            pw.println("Grade: " + percent);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    //Teacher equals method
    public boolean equals(Object o) {
        if (o instanceof Teacher) {
            if (((Teacher) o).getName().equals(name)) {
                if (((Teacher) o).getCourses().equals(courses)) {
                    return true;
                }
            }
        }
        return false;
    }

}
