import java.util.ArrayList;
import java.util.List;

public class BudgetRepo {
    List<Budget> listBudget = new ArrayList<>();
    public BudgetRepo(){}

    public List<Budget> getAll(){
        return this.listBudget;
    }

    public void createMonthBudget(){

    }
}
