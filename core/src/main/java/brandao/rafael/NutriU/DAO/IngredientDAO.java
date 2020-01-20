package brandao.rafael.NutriU.DAO;

import brandao.rafael.NutriU.model.Ingredient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class IngredientDAO extends BaseDAO<Ingredient> {

    public IngredientDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Ingredient> findByName(String name) {
        Session session = openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Ingredient> criteria = builder.createQuery(Ingredient.class);
        Root<Ingredient> ingredientRoot = criteria.from(Ingredient.class);
        criteria.select(ingredientRoot).where(builder.like(ingredientRoot.get("name"),"%" + name + "%"));
        List<Ingredient> list = session.createQuery(criteria).getResultList();
        closeSession(session);
        return list;
    }
}
