package site.stellarburgers.order;

import lombok.Data;

import java.util.List;

@Data
public class AllTheIngredients {
    private boolean success;
    private List<Ingredient> data;

    public AllTheIngredients(boolean success, List<Ingredient> data) {
        this.success = success;
        this.data = data;
    }
}
