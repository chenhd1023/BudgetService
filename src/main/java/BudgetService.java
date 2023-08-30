import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        YearMonth currentYearMonth = YearMonth.from(start);
        while (!currentYearMonth.isAfter(YearMonth.from(end))) {
            yearMonthBetween.add(currentYearMonth);
            currentYearMonth = currentYearMonth.plusMonths(1);
        }

        double rtBudget = 0;
        for (YearMonth yearMonth : yearMonthBetween) {
            Optional<Budget> findBudget = listBudgets.stream().filter(b -> b.getYearMonthInstance().equals(yearMonth)).findFirst();
            if (findBudget.isEmpty()) {
                continue;
            }
            Budget budget = findBudget.get();

            long overlappingDays = new Period(start, end).getOverlappingDays(budget);
            rtBudget += budget.dailyAmount(budget) * overlappingDays;
        }

        return rtBudget;
    }


}
