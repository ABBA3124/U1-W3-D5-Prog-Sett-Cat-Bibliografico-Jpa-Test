package org.davideabbadessa.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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


    public void rimuoviElemento(String codiceISBN) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            ElementoCatalogo elemento = em.createQuery("SELECT e FROM ElementoCatalogo e WHERE e.codiceISBN = :codiceISBN", ElementoCatalogo.class)
                    .setParameter("codiceISBN", codiceISBN)
                    .getSingleResult();
            if (elemento != null) {
                em.remove(elemento);
                System.out.println("Elemento con codice ISBN " + codiceISBN + " rimosso.");
            } else {
                System.out.println("Elemento con codice ISBN " + codiceISBN + " non trovato.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}