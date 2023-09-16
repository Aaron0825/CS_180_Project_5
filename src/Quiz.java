import java.util.ArrayList;
import java.util.Objects;
import java.util.*;
/**
 * create a quiz containing one or more multiple choices questions with answers to the questions; contains add, remove, edit question method
 * and a ramdomize questions method utilized in Teacher class, answers to the questions are not shown to Student class but can be used by
 * Teacher class for grading
 *
 * @author Chun-Yang Lee, Joshua Ho, Hpung San Aung, Margaret Haydock, Soumya Kakarlapudi Purdue CS - L21
 * @version 12/13/2021
 */

public class Quiz {
    private String name;
    private ArrayList < Question > questions;
    private boolean randomizequestion;
    private Student student;
    ArrayList < String > answersToEachQuestion;

    public Quiz(String name, ArrayList < Question > questions, boolean randomizequestion) {
        this.name = name;
        this.questions = questions;
        this.randomizequestion = randomizequestion;
        answersToEachQuestion = new ArrayList < String > ();
    }

    public Quiz(String name) {
        this.name = name;
        this.questions = new ArrayList < Question > ();
        this.randomizequestion = false;
    }

    public ArrayList < String > getAnswersToEachQuestion() {
        return answersToEachQuestion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList < Question > getQuestions() {
        return questions;
    }

    public boolean isRandomizequestion() {
        return randomizequestion;
    }

    public void setRandomizequestion(boolean randomizequestion) {
        this.randomizequestion = randomizequestion;
    }

    public synchronized void addQuestion(Question question) {
        boolean added = true;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).equals(question)) {
                added = false;
                break;
            }
        }
        if (added) {
            questions.add(question);
        }
    }

    public synchronized void removeQuestion(Question question) {
        boolean removed = false;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).equals(question)) {
                questions.remove(i);
                removed = true;
                break;
            }
        }
    }

    //returns a new arrayList of randomized questions
    public synchronized ArrayList < Question > RandomizeQuestion() {
        ArrayList < Question > shuffledQuestions = questions;
        if (randomizequestion == true) {
            Collections.shuffle(shuffledQuestions);
        }
        return shuffledQuestions;
    }



    @Override
    public boolean equals(Object o) {
        if (o instanceof Quiz) {
            boolean equal;
            equal = Objects.equals(this.name, ((Quiz) o).name);
            equal &= (this.questions == ((Quiz) o).questions);
            return equal;
        }
        return false;
    }

    @Override
    public synchronized String toString() {
        String a = "";
        String b = name + "\n";

        if (isRandomizequestion()) {
            ArrayList < Question > random = RandomizeQuestion();
            for (int i = 0; i < random.size(); i++) {
                a += String.format("%s\n", random.get(i).toString());
            }
        } else {
            for (int i = 0; i < questions.size(); i++) {
                a += String.format("%s\n", questions.get(i));
            }
        }
        String s = b + a;
        return s;
    }
}
