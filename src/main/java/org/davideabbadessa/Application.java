package org.davideabbadessa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.davideabbadessa.dao.ElementoCatalogoDAO;
import org.davideabbadessa.dao.PrestitoDAO;
import org.davideabbadessa.entities.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("U1-W3-D5-Prog-Sett-Cat-Bibliografico-Jpa-Test");

        EntityManager em = emf.createEntityManager();

        ElementoCatalogoDAO dao = new ElementoCatalogoDAO(em);
        PrestitoDAO prestitoDao = new PrestitoDAO(em);

        /*--------------------------------------------Aggiunta di un elemento del catalogo-------------------------------------------*/

        //aggiugno un libro

        Libro libro = new Libro();
        libro.setCodiceISBN("54111");
        libro.setTitolo("Java Programming");
        libro.setAnnoPubblicazione(2025);
        libro.setNumeroPagine(20);
        libro.setAutore("iufhgvh");
        libro.setGenere("Programming");
        dao.aggiungiElemento(libro);
        System.out.println(libro + " okok libro ");

        //aggiungo una rivista

        Rivista rivista = new Rivista();
        rivista.setCodiceISBN("61556411");
        rivista.setTitolo("nuova rivista 5");
        rivista.setAnnoPubblicazione(2031);
        rivista.setNumeroPagine(36);
        rivista.setPeriodicita(Periodicita.MENSILE);
        dao.aggiungiElemento(rivista);
        System.out.println(rivista + " okok rivista ");

        // Aggiungi un utente
        Utente utente = new Utente();
        utente.setNumeroTessera(UUID.randomUUID());
        utente.setNome("davide ");
        utente.setCognome("abbadessa");
        utente.setDataNascita(LocalDate.of(1999, 3, 2));
        em.getTransaction().begin();
        em.persist(utente);
        em.getTransaction().commit();
        System.out.println(utente + " okok utente ");


        // Aggiungi un prestito
        Prestito prestito = new Prestito();
        prestito.setUtente(utente);
        prestito.setElementoPrestato(libro);
        prestito.setDataInizioPrestito(LocalDate.now());
        prestito.setDataRestituzionePrevista(LocalDate.now().plusDays(30));
        PrestitoDAO.aggiungiPrestito(prestito);
        System.out.println(prestito + " okok prestito ");


        /*-------------------------------------------Rimozione di un elemento del catalogo dato un codice ISBN-------------------------------------------*/

        //rimuovo un elementocatalogo tramite ISBN

        dao.rimuoviElemento("16516514");

        /*--------------------------------------------Ricerca per ISBN-------------------------------------------*/

        //ricerca elemento per codice ISBN
        ElementoCatalogo elemento = dao.trovaElementoPerISBN("654169461");
        if (elemento != null) {
            System.out.println("Elemento trovato: " + elemento);
        } else {
            System.out.println("Elemento con codice ISBN non trovato.");
        }

        /*---------------------------------------------Ricerca per anno pubblicazione-------------------------------------------*/

        //ricerca elementi per anno di pubblicazione
        List<ElementoCatalogo> elementi = dao.trovaElementiPerAnno(2025);
        if (elementi != null && !elementi.isEmpty()) {
            System.out.println("Elementi trovati per l'anno: ");
            for (ElementoCatalogo e : elementi) {
                System.out.println("- " + e.getTitolo());
            }
        } else {
            System.out.println("Nessun elemento trovato per l'anno inserito.");
        }

        /*----------------------------------------------Ricerca per autore-------------------------------------------*/
        //ricerca elementi per autore
        List<Libro> elementiAutore = dao.trovaElementiPerAutore("davide vulpinari");
        if (elementiAutore != null && !elementiAutore.isEmpty()) {
            System.out.println("Elementi trovati per autore: ");
            for (ElementoCatalogo e : elementiAutore) {
                System.out.println("- " + e.getTitolo());
            }
        } else {
            System.out.println("Nessun elemento trovato per l'autore cercato.");
        }

        /*-----------------------------------------------Ricerca per titolo o parte di esso-------------------------------------------*/
        //ricerca elementi per titolo o parte di esso
        List<ElementoCatalogo> elementiTitolo = dao.trovaElementiPerTitolo("Java");
        if (elementiTitolo != null && !elementiTitolo.isEmpty()) {
            System.out.println("Elementi trovati per ricerca titolo: ");
            for (ElementoCatalogo e : elementiTitolo) {
                System.out.println("- " + e.getTitolo());
            }
        } else {
            System.out.println("Nessun elemento trovato per ricerca titolo.");
        }

        /*-----------------------------------------------Ricerca degli elementi attualmente in prestito dato un numero di tessera utente-------------------------------------------*/

        //ricerca prestiti per numero di tessera
        List<Prestito> prestiti = prestitoDao.trovaPrestitiPerNumeroTessera(utente.getNumeroTessera());
        if (prestiti != null && !prestiti.isEmpty()) {
            System.out.println("Prestiti trovati per numero di tessera " + utente.getNumeroTessera() + ":");
            for (Prestito p : prestiti) {
                System.out.println("- " + p.getElementoPrestato().getTitolo());
            }
        } else {
            System.out.println("Nessun prestito trovato per numero di tessera " + utente.getNumeroTessera() + ".");
        }

        /*-----------------------------------------------Ricerca di tutti i prestiti scaduti e non ancora restituiti-------------------------------------------*/

        //ricerca prestiti scaduti e non restituiti
        List<Prestito> prestitiScaduti = prestitoDao.trovaPrestitiScadutiNonRestituiti();
        if (prestitiScaduti != null && !prestitiScaduti.isEmpty()) {
            System.out.println("Prestiti scaduti e non ancora restituiti:");
            for (Prestito p : prestitiScaduti) {
                System.out.println("- " + p.getElementoPrestato().getTitolo() + " (scaduto il " + p.getDataRestituzionePrevista() + ")");
            }
        } else {
            System.out.println("Nessun prestito scaduto e non ancora restituito.");
        }

        em.close();
        emf.close();
    }
}