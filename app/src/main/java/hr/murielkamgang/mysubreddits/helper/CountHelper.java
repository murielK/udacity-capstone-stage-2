package hr.murielkamgang.mysubreddits.helper;

import android.content.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountHelper {

    private static final String[] PREFIX = {"k", "m", "B", "T"};
    private final Context context;
    private Logger logger = LoggerFactory.getLogger(CountHelper.class);

    public CountHelper(Context context) {
        this.context = context;
    }

    public String showCountPretty(int singleTextRes, int pluralTextRes, long count) {
        logger.debug("count {}", count);

        final StringBuilder sb = new StringBuilder();
        int index = 0;
        int secondDigit = 0;
        while (count >= 1000 && index < 4) {
            index++;
            secondDigit = (int) (count % 1000);
            secondDigit = secondDigit > 100 ? secondDigit / 100 : 0;
            count /= 1000;
        }

        sb.append(count);
        if (count < 10 && secondDigit > 0) {
            sb.append(".").append(secondDigit);
        }

        if (index > 0) {
            sb.append(PREFIX[index - 1]);
        }
        return count <= 1 ? context.getString(singleTextRes, sb.toString())
                : context.getString(pluralTextRes, sb.toString());
    }
}
