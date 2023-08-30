import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.temporal.ChronoUnit.DAYS;

public class Period {
    private final LocalDate start;
    private final LocalDate end;

    public Period(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    long getOverlappingDays(Budget budget) {
        LocalDate overlappingStart;
        LocalDate overlappingEnd;
        if (YearMonth.from(getStart()).equals(YearMonth.from(getEnd()))) {
            overlappingStart = getStart();
            overlappingEnd = getEnd();
        } else if (budget.getYearMonthInstance().equals(YearMonth.from(getStart()))) {
            overlappingStart = getStart();
            overlappingEnd = budget.getOverlappingEnd();
        } else if (budget.getYearMonthInstance().equals(YearMonth.from(getEnd()))) {
            overlappingStart = budget.getOverlappingStart();
            overlappingEnd = getEnd();
        } else {
            overlappingStart = budget.getOverlappingStart();
            overlappingEnd = budget.getOverlappingEnd();
        }
        return  DAYS.between(overlappingStart, overlappingEnd) + 1;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }
}
