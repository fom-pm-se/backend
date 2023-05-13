package fom.pmse.crms.backend.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CrmTimeHelper {
    public static String format(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.GERMAN));
    }
}
