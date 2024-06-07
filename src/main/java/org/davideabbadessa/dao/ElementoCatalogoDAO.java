package org.davideabbadessa.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.davideabbadessa.entities.ElementoCatalogo;
import org.davideabbadessa.entities.Libro;

import java.util.List;

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

    public ElementoCatalogo trovaElementoPerISBN(String codiceISBN) {
        try {
            return em.createQuery("SELECT e FROM ElementoCatalogo e WHERE e.codiceISBN = :codiceISBN", ElementoCatalogo.class)
                    .setParameter("codiceISBN", codiceISBN)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ElementoCatalogo> trovaElementiPerAnno(int annoPubblicazione) {
        try {
            return em.createQuery("SELECT e FROM ElementoCatalogo e WHERE e.annoPubblicazione = :annoPubblicazione", ElementoCatalogo.class)
                    .setParameter("annoPubblicazione", annoPubblicazione)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Libro> trovaElementiPerAutore(String autore) {
        try {
            return em.createQuery("SELECT l FROM Libro l WHERE l.autore = :autore", Libro.class)
                    .setParameter("autore", autore)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}