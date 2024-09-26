package draganbjedov.netbeans.csv.options;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/*
 * LimitedPlainDocument.java
 *
 * Created on 03.04.2012., 15.31.02
 *
 * @author Dragan Bjedov
 */
public class LimitedPlainDocument extends PlainDocument {

    private int limit;
    private boolean toUppercase = false;

    public LimitedPlainDocument(int limit) {
        super();
        this.limit = limit;
    }

    public LimitedPlainDocument(int limit, boolean upper) {
        super();
        this.limit = limit;
        toUppercase = upper;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {
        if (str == null)
            return;

        if ((getLength() + str.length()) <= limit) {
            if (toUppercase)
                str = str.toUpperCase();
            super.insertString(offset, str, attr);
        } else {
            int cnt = limit - getLength();
            if (cnt > 0) {
                str = str.substring(0, cnt);
                if (toUppercase)
                    str = str.toUpperCase();
                super.insertString(offset, str, attr);
            }
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isToUppercase() {
        return toUppercase;
    }

    public void setToUppercase(boolean toUppercase) {
        this.toUppercase = toUppercase;
    }

}
