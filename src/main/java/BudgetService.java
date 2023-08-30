import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class BudgetService {
    private final BudgetRepo budgetRepo;

    BudgetService() {
        budgetRepo = new BudgetRepo();
    }

    public BudgetRepo getBudgetRepo() {
        return this.budgetRepo;
    }

    public double query(LocalDate start, LocalDate end) {
        if (end.isBefore(start)) {
            return 0;
        }
        List<Budget> listBudgets = budgetRepo.getAll();
        List<YearMonth> yearMonthBetween = new ArrayList<>();
        YearMonth startYearMonth = YearMonth.from(start);
        YearMonth endYearMonth = YearMonth.from(end);

        YearMonth currentYearMonth = startYearMonth;
        while (!currentYearMonth.isAfter(endYearMonth)) {
            yearMonthBetween.add(currentYearMonth);
            currentYearMonth = currentYearMonth.plusMonths(1);
        }

        long daysBetween;
        double rtBudget = 0;
        for (YearMonth yearMonth: yearMonthBetween){


            if (yearMonth.equals(startYearMonth) ) {
                for (Budget budget : listBudgets) {
                    if (budget.getYearMonthInstance().equals(startYearMonth)) {
                        if (end.isAfter(yearMonthBetween.get(0).atEndOfMonth())) {
                            daysBetween = DAYS.between(start, yearMonthBetween.get(0).atEndOfMonth());
                        } else {
                            daysBetween = DAYS.between(start, end);
                        }
                        rtBudget += budget.dailyAmount(budget) * (daysBetween + 1);
                    }
                }
            }
            else if (yearMonth.equals(endYearMonth)  ) {
                for (Budget budget : listBudgets) {
                    if (budget.getYearMonthInstance().equals(yearMonth)) {
                        daysBetween = DAYS.between(yearMonth.atDay(1), end);
                        rtBudget += budget.dailyAmount(budget) * (daysBetween + 1);
                    }
                }
            }
            else {
                for (Budget budget : listBudgets) {
                    if (yearMonth.equals(budget.getYearMonthInstance())) {
                        daysBetween = DAYS.between(budget.getYearMonthInstance().atDay(1),budget.getYearMonthInstance().atEndOfMonth());
                        rtBudget += budget.dailyAmount(budget)  *  (daysBetween + 1) ;
                    }
                }
            }
        }

        return rtBudget;
    }


}