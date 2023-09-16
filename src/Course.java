import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
/**
 * create a course containing one or more quizzes;
 * contains add, remove, edit quiz method and write
 * quiz file method that can store a quiz content in a file
 * so data won't be lost when a user disconnects, the methods are
 * utilized in Teacher class
 *
 * @author Chun-Yang Lee, Joshua Ho, Hpung San Aung, Margaret Haydock, Soumya Kakarlapudi Purdue CS - L21
 * @version 11/25/2021
 */
public class Course {
    private ArrayList<Quiz> quizzes;
    private String name;
    private ArrayList<Student> students;
    ArrayList<String> answers;

    public Course(ArrayList<Quiz> quizzes, String name) {
        this.quizzes = quizzes;
        this.name = name;
        this.students = new ArrayList<Student>();
        answers = new ArrayList<String>();
    }

    public Course(String name) {
        this.name = name;
        this.quizzes = new ArrayList<Quiz>();
        this.students = new ArrayList<Student>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Quiz> getQuizzes() {
        return quizzes;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public synchronized boolean removeStudent(String studentName) {
        boolean removed = false;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getName().equals(studentName)) {
                students.remove(i);
                removed = true;
                break;
            }
        }
        return removed;
    }

    public synchronized boolean addStudent(Student student) {
        boolean added = true;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getName().equals(student.getName())) {
                added = false;
                break;
            }
        }
        if (added) {
            students.add(student);
        }
        return added;
    }

    //Adds a new quiz to the quizzes arrayList
    //makes sure the quiz doesn't already exist before adding
    //returns boolean of whether it's added or not
    public synchronized boolean addQuiz(Quiz quiz) {
        boolean add = true;
        for (int i = 0; i < quizzes.size(); i++) {
            if (quizzes.get(i).getName().equals(quiz.getName())) {
                add = false;
                break;
            }
        }
        if (add) {
            quizzes.add(quiz);
        }
        return add;
    }

    //Removes a quiz from quizzes arrayList
    //makes sure quiz actually exists before removing
    //returns boolean whether it's removed or not
    public synchronized boolean removeQuiz(Quiz quiz) {
        boolean removed = false;
        for (int i = 0; i < quizzes.size(); i++) {
            if (quizzes.get(i).getName().equals(quiz.getName())) {
                quizzes.remove(i);
                removed = true;
                break;
            }
        }
        return removed;
    }

    public boolean equals(Object o) {
        if (o instanceof Course) {
            if (((Course) o).getName().equals(name)) {
                if (((Course) o).getQuizzes().equals(quizzes)) {
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized void writeQuizFile(Quiz quiz) {
        try {
            String fileName = "";
            if (quiz.getName().contains(".txt")) {
                fileName = quiz.getName().substring(0, quiz.getName().indexOf(".txt")).replaceAll(" ",
                        "_") + "_Quiz.txt";
            } else {
                fileName = (quiz.getName() + "_Quiz.txt").replaceAll(" ", "_");
            }
            FileOutputStream fos = new FileOutputStream(fileName, false);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(quiz.getName());
            if (quiz.isRandomizequestion()) {
                pw.println("true true");
            } else {
                pw.println("false false");
            }

            for (int i = 0; i < quiz.getQuestions().size(); i++) {
                pw.println("Question_" + (i + 1) + ":" + quiz.getQuestions().get(i).getQuestion());

                pw.print("Choices: A)" + quiz.getQuestions().get(i).getChoices().get(0));
                pw.print(", B)" + quiz.getQuestions().get(i).getChoices().get(1));
                pw.print(", C)" + quiz.getQuestions().get(i).getChoices().get(2));
                pw.println(", D)" + quiz.getQuestions().get(i).getChoices().get(3));
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized String toString() {
        String a = "";
        String b = name + "\n";
        for (int i = 0; i < quizzes.size(); i++) {
            a = String.format("%s\n", quizzes.get(i).getName());
        }
        String s = b + a;
        return s;
    }
}
