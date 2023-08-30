import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Period {
    private final LocalDate start;
    private final LocalDate end;

    public Period(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    long getOverlappingDays(Budget budget) {
        Period another = new Period(budget.firstDay(),budget.lastDay());
        LocalDate overlappingStart = start.isAfter(another.start) ? start : another.start;
        LocalDate overlappingEnd = end.isBefore(another.end) ? end : another.end;

        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }


}
