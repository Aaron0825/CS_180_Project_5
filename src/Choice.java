/**
 * create a choice for a multiple choices question; contains getter
 * and setter utilized to add, remove, and edit choice in Question class
 *
 * @author Chun-Yang Lee, Joshua Ho, Hpung San Aung, Margaret Haydock, Soumya Kakarlapudi Purdue CS - L21
 * @version 12/13/2021
 */
public class Choice {
    private String choices;

    public Choice(String choices) {
        this.choices = choices;
    }

    public String getChoices() {
        return choices;
    }

    public void setChoices(String choices) {
        this.choices = choices;
    }

    public synchronized String toString() {
        return choices;
    }

}
