package brandao.rafael.NutriU.util;

public final class Mappings {

    private Mappings() {}

    public static final String INGREDIENT = "ingredient";
    public static final String REDIRECT_INGREDIENT = "redirect:/"+ INGREDIENT;
    public static final String ADD_INGREDIENT = "addIngredient";
    public static final String REDIRECT_ADD_INGREDIENT = "redirect:/"+ INGREDIENT + "/" + ADD_INGREDIENT;
    public static final String DELETE_INGREDIENT = "deleteIngredient";
    public static final String EDIT_INGREDIENT = "editIngredient";
    public static final String REDIRECT_EDIT_INGREDIENT = "redirect:/"+ INGREDIENT + "/" + EDIT_INGREDIENT;
}
