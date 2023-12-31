import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BudgetService {
    private final BudgetRepo budgetRepo;
    BudgetService(){
        budgetRepo = new BudgetRepo();

    }

    public double query(LocalDate start, LocalDate end) {
        if (end.isBefore(start)) {
            return 0;
        }
        List<Budget> listBudgets = budgetRepo.getAll();
        List<YearMonth> yearMonthsBetween = getYearMonthsBetween(start, end);

        long daysBetween;
        double rtBudget = 0;
        for (int i = 0; i < yearMonthsBetween.size(); i++) {
            if (i == 0) {
                for (Budget budget : listBudgets) {
                    if (isDateInYearMonth(start, toYearMonth(budget.yearMonth))) {

                        if (end.isAfter(yearMonthsBetween.get(0).atEndOfMonth())){
                            daysBetween = ChronoUnit.DAYS.between(start, yearMonthsBetween.get(0).atEndOfMonth());
                        }else{
                            daysBetween = ChronoUnit.DAYS.between(start, end);
                        }

                        rtBudget += budget.amount / toYearMonth(budget.yearMonth).lengthOfMonth() * (daysBetween + 1);
                    }
                }
            } else if (i == yearMonthsBetween.size() - 1) {
                for (Budget budget : listBudgets) {
                    if (isDateInYearMonth(end, toYearMonth(budget.yearMonth))) {
                        daysBetween = ChronoUnit.DAYS.between(yearMonthsBetween.get(i).atDay(1), end);
                        rtBudget += budget.amount / toYearMonth(budget.yearMonth).lengthOfMonth() * (daysBetween +1);
                    }
                }
            } else{
                for (Budget budget : listBudgets) {
                    if (yearMonthsBetween.get(i).equals(toYearMonth(budget.yearMonth)) ) {
                        rtBudget += budget.amount ;
                    }
                }
            }
        }

        return rtBudget; // this seems to be a placeholder, you may want to change it to return rtBudget.
    }

    public BudgetRepo getBudgetRepo() {
        return this.budgetRepo;
    }

    private static boolean isDateInYearMonth(LocalDate date, YearMonth yearMonth) {
        YearMonth dateYearMonth = YearMonth.from(date);
        return yearMonth.equals(dateYearMonth);
    }

    public static YearMonth toYearMonth(String yearMonthString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        return YearMonth.parse(yearMonthString, formatter);
    }

    public static List<YearMonth> getYearMonthsBetween(LocalDate startDate, LocalDate endDate) {
        List<YearMonth> yearMonths = new ArrayList<>();
        YearMonth start = YearMonth.from(startDate);
        YearMonth end = YearMonth.from(endDate);

        while (!start.isAfter(end)) {
            yearMonths.add(start);
            start = start.plusMonths(1);
        }

        return yearMonths;
    }

}