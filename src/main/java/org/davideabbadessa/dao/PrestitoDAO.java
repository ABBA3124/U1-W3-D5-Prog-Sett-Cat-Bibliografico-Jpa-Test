package org.davideabbadessa.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.davideabbadessa.entities.Prestito;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PrestitoDAO {
    private static EntityManager em;

    public PrestitoDAO(EntityManager em) {
        this.em = em;
    }

    public static void aggiungiPrestito(Prestito prestito) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(prestito);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Prestito> trovaPrestitiPerNumeroTessera(UUID numeroTessera) {
        try {
            return em.createQuery("SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :numeroTessera AND p.dataRestituzioneEffettiva IS NULL", Prestito.class)
                    .setParameter("numeroTessera", numeroTessera)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<Prestito> trovaPrestitiScadutiNonRestituiti() {
        try {
            return em.createQuery("SELECT p FROM Prestito p WHERE p.dataRestituzionePrevista < :oggi AND p.dataRestituzioneEffettiva IS NULL", Prestito.class)
                    .setParameter("oggi", LocalDate.now())
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
