package brandao.rafael.NutriU.service;

import brandao.rafael.NutriU.DAO.IngredientDAO;
import brandao.rafael.NutriU.model.Ingredient;
import brandao.rafael.NutriU.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{

    private final IngredientDAO ingredientDAO;

    @Autowired
    public IngredientServiceImpl(IngredientDAO ingredientDAO) {
        this.ingredientDAO = ingredientDAO;
    }

    @Override
    public boolean addIngredient(Ingredient ingredient) {
        return ingredientDAO.save(ingredient);
    }

    @Override
    public Ingredient findIngredientById(int id) {
        return ingredientDAO.findById(Ingredient.class, id);
    }

    @Override
    public List<Ingredient> findIngredientsByName(String name) {
        return ingredientDAO.findByName(name);
    }

    @Override
    public Page<Ingredient> findIngredientsByNamePaginated(String name, int currentPage, int pageSize) {
        return Helper.turnListToPage(findIngredientsByName(name), pageSize, currentPage);
    }

    @Override
    public boolean updateIngredient(Ingredient ingredient) {
        return ingredientDAO.update(ingredient);
    }

    @Override
    public boolean removeIngredient(int id) {
        return ingredientDAO.delete(Ingredient.class, id);
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientDAO.loadAllData(Ingredient.class);
    }

    @Override
    public Page<Ingredient> getIngredientsPaginated(int currentPage, int pageSize) {
        return Helper.turnListToPage(getAllIngredients(), pageSize, currentPage);
    }
}
