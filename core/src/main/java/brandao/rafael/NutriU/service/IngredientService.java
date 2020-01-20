package brandao.rafael.NutriU.service;

import brandao.rafael.NutriU.model.Ingredient;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IngredientService {

    boolean addIngredient(Ingredient ingredient);

    Ingredient findIngredientById(int id);

    List<Ingredient> findIngredientsByName(String name);

    Page<Ingredient> findIngredientsByNamePaginated(String name, int currentPage, int pageSize);

    boolean updateIngredient(Ingredient ingredient);

    boolean removeIngredient(int id);

    List<Ingredient> getAllIngredients();

    Page<Ingredient> getIngredientsPaginated(int currentPage, int pageSize);

}
