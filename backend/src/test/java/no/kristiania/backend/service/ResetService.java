package no.kristiania.backend.service;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.Review;
import no.kristiania.backend.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
@Transactional
public class ResetService {

    @PersistenceContext
    private EntityManager em;

    public void resetDatabase(){
        Query query = em.createNativeQuery("delete from user_roles");
        query.executeUpdate();

        deleteEntities(User.class);
        deleteEntities(Movie.class);
        deleteEntities(Review.class);


    }

    private void deleteEntities(Class<?> entity){
        if(entity == null || entity.getAnnotation(Entity.class) == null){
            throw new IllegalArgumentException("Invalid non entity class");
        }

        String name = entity.getSimpleName();

        Query query = em.createQuery("DELETE from " + name);
        query.executeUpdate();

    }
}
