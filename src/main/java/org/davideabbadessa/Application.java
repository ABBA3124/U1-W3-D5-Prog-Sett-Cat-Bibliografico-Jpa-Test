package org.davideabbadessa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.davideabbadessa.dao.ElementoCatalogoDAO;
import org.davideabbadessa.entities.ElementoCatalogo;
import org.davideabbadessa.entities.Libro;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("U1-W3-D5-Prog-Sett-Cat-Bibliografico-Jpa-Test");

        EntityManager em = emf.createEntityManager();

        ElementoCatalogoDAO dao = new ElementoCatalogoDAO(em);

        /*--------------------------------------------Aggiunta di un elemento del catalogo-------------------------------------------*/

        //aggiugno un libro

//        Libro libro = new Libro();
//        libro.setCodiceISBN("65411");
//        libro.setTitolo("Java Programming");
//        libro.setAnnoPubblicazione(2025);
//        libro.setNumeroPagine(20);
//        libro.setAutore("i");
//        libro.setGenere("Programming");
//        dao.aggiungiElemento(libro);
//        System.out.println(libro + " okok libro ");
//
        //aggiungo una rivista

//        Rivista rivista = new Rivista();
//        rivista.setCodiceISBN("61564156411");
//        rivista.setTitolo("nuova rivista 5");
//        rivista.setAnnoPubblicazione(2031);
//        rivista.setNumeroPagine(36);
//        rivista.setPeriodicita(Periodicita.MENSILE);
//        dao.aggiungiElemento(rivista);
//        System.out.println(rivista + " okok rivista ");


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
        

    }
}
