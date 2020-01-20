package brandao.rafael.NutriU.util;

import brandao.rafael.NutriU.model.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

public final class Helper {

    private Helper(){}

    public static void addAttributeIfEmpty(Model model, String attributeName, Object attribute){
        if(!model.containsAttribute(attributeName)){
            model.addAttribute(attributeName, attribute);
        }
    }

    public static <T> Page<T> turnListToPage(List<T> list, int pageSize, int currentPage){
        int startItem = currentPage * pageSize;
        List<T> pagedList;

        if (list.size() < startItem) {
            pagedList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, list.size());
            pagedList = list.subList(startItem, toIndex);
        }

        Page<T> page = new PageImpl<>(pagedList, PageRequest.of(currentPage, pageSize), list.size());

        return page;
    }

}
