package brandao.rafael.NutriU.controller;

import brandao.rafael.NutriU.model.Ingredient;
import brandao.rafael.NutriU.service.IngredientService;
import brandao.rafael.NutriU.util.Helper;
import brandao.rafael.NutriU.util.Mappings;
import brandao.rafael.NutriU.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(Mappings.INGREDIENT)
@Controller
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping
    public String ingredient(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, @RequestParam(required=false) String name){

        int[] pageSizes = {5, 10, 20, 25, 50};

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(pageSizes[0]);

        Page<Ingredient> ingredientPage;

        if(name != null){
            ingredientPage = ingredientService.findIngredientsByNamePaginated(name, currentPage - 1, pageSize);
        } else {
            ingredientPage = ingredientService.getIngredientsPaginated(currentPage - 1, pageSize);
        }

        int fromItem;
        int toItem;
        if(ingredientPage.getNumberOfElements() > 0){
            fromItem = (ingredientPage.getPageable().getPageNumber() * ingredientPage.getPageable().getPageSize()) + 1;
            toItem = fromItem + ingredientPage.getNumberOfElements() - 1;
        } else {
            fromItem = toItem = 0;
        }
        String pagesMessage = "Showing " + fromItem + " to " + toItem + " of " + ingredientPage.getTotalElements();

        List<Integer> pages = new ArrayList<>();
        for(int i = Math.max(currentPage - 3, 0) ; i < Math.max(currentPage - 3, 0) + 5 && i < ingredientPage.getTotalPages(); i++){
            pages.add(i + 1);
        }
        if(pages.size() < 5 && ingredientPage.getTotalPages() > 3){
            pages.add(0, pages.get(0) - 1);
        }
        if(pages.size() < 5 && ingredientPage.getTotalPages() > 4){
            pages.add(0, pages.get(0) - 1);
        }

        model.addAttribute("pageSizes", pageSizes);
        model.addAttribute("pages", pages);
        model.addAttribute("ingredientPage", ingredientPage);
        model.addAttribute("pagesMessage", pagesMessage);
        model.addAttribute("name", name);

        return ViewNames.INGREDIENT;
    }

    @GetMapping(Mappings.ADD_INGREDIENT)
    public String addIngredient(Model model){

        Helper.addAttributeIfEmpty(model, "message", "Please add a new ingredient");
        Helper.addAttributeIfEmpty(model, "ingredient", new Ingredient());

        return ViewNames.ADD_INGREDIENT;
    }

    @PostMapping(Mappings.ADD_INGREDIENT)
    public String processAddIngredient(@Valid @ModelAttribute Ingredient ingredient, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("message", "Insert all values!");
        } else {
            if(ingredientService.addIngredient(ingredient)){
                return Mappings.REDIRECT_INGREDIENT;
            }
            redirectAttributes.addFlashAttribute("message", "This ingredient already exists!");
        }
        redirectAttributes.addFlashAttribute("ingredient", ingredient);

        return Mappings.REDIRECT_ADD_INGREDIENT;
    }

    @PostMapping(Mappings.DELETE_INGREDIENT)
    public String deleteIngredient(@RequestParam int id){
        ingredientService.removeIngredient(id);

        return Mappings.REDIRECT_INGREDIENT;
    }

    @GetMapping(Mappings.EDIT_INGREDIENT)
    public String editIngredient(@RequestParam int id, Model model){
        Ingredient ingredient = ingredientService.findIngredientById(id);
        log.info("ingredient = {}", ingredient);
        model.addAttribute("ingredient", ingredient);
        return ViewNames.EDIT_INGREDIENT;
    }

    @PostMapping(Mappings.EDIT_INGREDIENT)
    public String processEditIngredient(@RequestParam int id, @Valid @ModelAttribute Ingredient ingredient, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("message", "Values can't be blank!");
        } else {
            ingredient.setId(id);
            if(ingredientService.updateIngredient(ingredient)){
                return Mappings.REDIRECT_INGREDIENT;
            }
            redirectAttributes.addFlashAttribute("message", "This ingredient already exists!");
        }
        redirectAttributes.addAttribute("id", ingredient.getId());

        return Mappings.REDIRECT_EDIT_INGREDIENT;
    }
}
