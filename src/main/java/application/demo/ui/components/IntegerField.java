package application.demo.ui.components;

import com.vaadin.event.FieldEvents;
import com.vaadin.ui.TextField;

public class IntegerField extends TextField implements FieldEvents.TextChangeListener {
    String lastValue;

    public IntegerField(String value) {
        lastValue = value;

        setImmediate(true);
        setTextChangeEventMode(TextChangeEventMode.LAZY);
        addTextChangeListener(this);
    }

    @Override
    public void textChange(FieldEvents.TextChangeEvent event) {
        String text = event.getText();
        try {
            new Integer(text);
            lastValue = text;
        } catch (NumberFormatException e) {
            setValue(lastValue);
        }
    }
}