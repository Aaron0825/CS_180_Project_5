import java.util.ArrayList;
import java.util.*;

/**
 * create a multiple choices question; contains add, remove,
 * edit choice method and a ramdomize choices method utilized in Teacher class
 *
 * @author Chun-Yang Lee, Joshua Ho, Hpung San Aung, Margaret Haydock, Soumya Kakarlapudi Purdue CS - L21
 * @version 12/13/2021
 */
public class Question {
    private String question;
    private ArrayList<Choice> choices;
    private boolean randomizechoice;

    public Question(String question, ArrayList<Choice> choices, boolean randomizechoice) {
        this.question = question;
        this.choices = choices;
        this.randomizechoice = randomizechoice;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<Choice> getChoices() {
        return choices;
    }

    public boolean isRandomizechoice() {
        return randomizechoice;
    }

    public void setRandomizechoice(boolean randomizechoice) {
        this.randomizechoice = randomizechoice;
    }

    public synchronized void addChoice(Choice choice) {
        boolean added = true;
        for (int i = 0; i < choices.size(); i++) {
            if (choices.get(i).equals(choice)) {
                added = false;
                break;
            }
        }
        if (added) {
            choices.add(choice);
        }
    }

    public synchronized void removeChoice(Choice choice) {
        boolean removed = false;
        for (int i = 0; i < choices.size(); i++) {
            if (choices.get(i).equals(choice)) {
                choices.remove(i);
                removed = true;
                break;
            }
        }
    }

    public synchronized void modifyChoice(Choice choice, String newChoice) {
        int i = choices.indexOf(choice);
        Choice r = new Choice(newChoice);
        choices.set(i, r);
    }

    //returns new arrayList of randomized choices
    public synchronized ArrayList<Choice> RandomizeChoice() {
        ArrayList<Choice> shuffledChoices = choices;
        if (randomizechoice == true) {
            Collections.shuffle(shuffledChoices);
        }
        return shuffledChoices;
    }

    @Override
    public synchronized String toString() {
        String a = "";
        String b = question + "\n";
        String[] abcd = {
                "A) ",
                "B) ",
                "C) ",
                "D) "
        };
        if (!isRandomizechoice()) {
            for (int i = 0; i < choices.size(); i++) {
                a += abcd[i];
                a += String.format("%s\n", choices.get(i));
            }
        } else {
            ArrayList<Choice> random = RandomizeChoice();
            for (int i = 0; i < random.size(); i++) {
                a += abcd[i];
                a += String.format("%s\n", random.get(i).toString());
            }
        }
        String s = b + a;
        return s;
    }
}
