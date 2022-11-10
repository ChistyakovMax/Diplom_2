package site.stellarburgers.order;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Order {

    private List<String> ingredients = new ArrayList<>();

    public void addIngredient(String ingredient){
        ingredients.add(ingredient);
    }

}
