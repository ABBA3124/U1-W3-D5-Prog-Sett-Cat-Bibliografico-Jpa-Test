package org.davideabbadessa.dao;

import jakarta.persistence.EntityManager;
import org.davideabbadessa.entities.ElementoCatalogo;

public class ElementoCatalogoDAO {
    private EntityManager em;

    public ElementoCatalogoDAO(EntityManager em) {
        this.em = em;
    }

    public void aggiungiElemento(ElementoCatalogo elemento) {
        em.getTransaction().begin();
        em.persist(elemento);
        em.getTransaction().commit();
    }

    
}
