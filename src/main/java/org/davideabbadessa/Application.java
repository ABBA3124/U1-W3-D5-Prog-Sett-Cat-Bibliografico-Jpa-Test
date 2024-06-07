package org.davideabbadessa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.davideabbadessa.dao.ElementoCatalogoDAO;
import org.davideabbadessa.entities.Libro;
import org.davideabbadessa.entities.Periodicita;
import org.davideabbadessa.entities.Rivista;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("U1-W3-D5-Prog-Sett-Cat-Bibliografico-Jpa-Test");

        EntityManager em = emf.createEntityManager();

        ElementoCatalogoDAO dao = new ElementoCatalogoDAO(em);

        //aggiugno un libro
        Libro libro = new Libro();
        libro.setCodiceISBN("65411");
        libro.setTitolo("Java Programming");
        libro.setAnnoPubblicazione(2025);
        libro.setNumeroPagine(20);
        libro.setAutore("i");
        libro.setGenere("Programming");
        dao.aggiungiElemento(libro);
        System.out.println(libro + " okok libro ");

        //aggiungo una rivista
        Rivista rivista = new Rivista();
        rivista.setCodiceISBN("61564156411");
        rivista.setTitolo("nuova rivista 5");
        rivista.setAnnoPubblicazione(2031);
        rivista.setNumeroPagine(36);
        rivista.setPeriodicita(Periodicita.MENSILE);
        dao.aggiungiElemento(rivista);
        System.out.println(rivista + " okok rivista ");

    }
}
