import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class Budget {
    String yearMonth;
    int amount;
    public Budget (String yearMonth, int amount){
        this.yearMonth = yearMonth;
        this.amount = amount;
    }

    public double dailyAmount(Budget budget) {
        return (double) budget.amount / budget.getYearMonthInstance().lengthOfMonth();
    }

    public YearMonth getYearMonthInstance() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        return YearMonth.parse(this.yearMonth, formatter);
    }



}
