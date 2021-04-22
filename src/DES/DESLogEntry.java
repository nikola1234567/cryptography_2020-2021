package DES;

import java.util.LinkedList;
import java.util.Objects;

public class DESLogEntry {
    String text;
    String key;
    LinkedList<String> sBoxInputs;

    public DESLogEntry(String text, String key) {
        this.text = text;
        this.key = key;
        sBoxInputs = new LinkedList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DESLogEntry that = (DESLogEntry) o;
        return Objects.equals(text, that.text) &&
                Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, key);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LinkedList<String> getsBoxInputs() {
        return sBoxInputs;
    }

    public void setsBoxInputs(LinkedList<String> sBoxInputs) {
        this.sBoxInputs = sBoxInputs;
    }

    public void insertNewSBoxInput(String input) {
        this.sBoxInputs.add(input);
    }
}
