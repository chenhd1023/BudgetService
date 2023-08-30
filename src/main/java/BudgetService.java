import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        for (YearMonth yearMonth : yearMonthBetween) {


            Optional<Budget> findBudget = listBudgets.stream().filter(b -> b.getYearMonthInstance().equals(yearMonth)).findFirst();
            if (findBudget.isEmpty()) {
                continue;
            }
            Budget budget = findBudget.get();

            if (budget.getYearMonthInstance().equals(startYearMonth)) {
                if (end.isAfter(yearMonthBetween.get(0).atEndOfMonth())) {
                    daysBetween = DAYS.between(start, yearMonthBetween.get(0).atEndOfMonth());
                } else {
                    daysBetween = DAYS.between(start, end);
                }
            } else if (budget.getYearMonthInstance().equals(endYearMonth)) {
                daysBetween = DAYS.between(yearMonth.atDay(1), end);
            } else {
                daysBetween = DAYS.between(budget.getYearMonthInstance().atDay(1), budget.getYearMonthInstance().atEndOfMonth());

            }
            rtBudget += budget.dailyAmount(budget) * (daysBetween + 1);
        }

        return rtBudget;
    }


}
