package bolts;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.IOUtils;
/* loaded from: classes.dex */
public class AggregateException extends Exception {
    private static final String DEFAULT_MESSAGE = "There were multiple errors.";
    private static final long serialVersionUID = 1;
    private List<Throwable> innerThrowables;

    public AggregateException(String detailMessage, Throwable[] innerThrowables) {
        this(detailMessage, Arrays.asList(innerThrowables));
    }

    public AggregateException(String detailMessage, List<? extends Throwable> innerThrowables) {
        super(detailMessage, (innerThrowables == null || innerThrowables.size() <= 0) ? null : (Throwable) innerThrowables.get(0));
        this.innerThrowables = Collections.unmodifiableList(innerThrowables);
    }

    public AggregateException(List<? extends Throwable> innerThrowables) {
        this(DEFAULT_MESSAGE, innerThrowables);
    }

    public List<Throwable> getInnerThrowables() {
        return this.innerThrowables;
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintStream err) {
        super.printStackTrace(err);
        int currentIndex = -1;
        for (Throwable throwable : this.innerThrowables) {
            err.append(IOUtils.LINE_SEPARATOR_UNIX);
            err.append("  Inner throwable #");
            currentIndex++;
            err.append((CharSequence) Integer.toString(currentIndex));
            err.append(": ");
            throwable.printStackTrace(err);
            err.append(IOUtils.LINE_SEPARATOR_UNIX);
        }
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintWriter err) {
        super.printStackTrace(err);
        int currentIndex = -1;
        for (Throwable throwable : this.innerThrowables) {
            err.append(IOUtils.LINE_SEPARATOR_UNIX);
            err.append("  Inner throwable #");
            currentIndex++;
            err.append((CharSequence) Integer.toString(currentIndex));
            err.append(": ");
            throwable.printStackTrace(err);
            err.append(IOUtils.LINE_SEPARATOR_UNIX);
        }
    }

    @Deprecated
    public List<Exception> getErrors() {
        List<Exception> errors = new ArrayList<>();
        List<Throwable> list = this.innerThrowables;
        if (list == null) {
            return errors;
        }
        for (Throwable cause : list) {
            if (cause instanceof Exception) {
                errors.add((Exception) cause);
            } else {
                errors.add(new Exception(cause));
            }
        }
        return errors;
    }

    @Deprecated
    public Throwable[] getCauses() {
        List<Throwable> list = this.innerThrowables;
        return (Throwable[]) list.toArray(new Throwable[list.size()]);
    }
}
