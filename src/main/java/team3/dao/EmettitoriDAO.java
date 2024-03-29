package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team3.entities.Emettitore;
import team3.entities.EmettitoreStato;

import java.util.List;

public class EmettitoriDAO {
    private EntityManager em;

    public EmettitoriDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Emettitore emettitore) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(emettitore);
            t.commit();
            System.out.println("Emettitore inserito: " + emettitore);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Emettitore findById(long id) {
        return em.find(Emettitore.class, id);
    }

    public void findByIdAndDelete(long id) {
        try {
            EntityTransaction t = em.getTransaction();
            Emettitore found = em.find(Emettitore.class, id);
            if (found != null) {
                t.begin();
                em.remove(found);
                t.commit();
                System.out.println("Emettitore eliminato");
            } else {
                System.out.println("Emettitore non trovato");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Emettitore> getAll() {
        TypedQuery<Emettitore> query = em.createQuery("SELECT e FROM Emettitore e", Emettitore.class);
        return query.getResultList();
    }

    public List<Emettitore> getAllDistributoriAttivi() {
        TypedQuery<Emettitore> query = em.createQuery("SELECT e FROM Emettitore e WHERE e.tipologia = EmettitoreTipo.DISTRIBUTORE AND stato = EmettitoreStato.ATTIVO", Emettitore.class);
        return query.getResultList();
    }

    public List<Emettitore> getAllRivenditori() {
        TypedQuery<Emettitore> query = em.createQuery("SELECT e FROM Emettitore e WHERE e.tipologia = EmettitoreTipo.RIVENDITORE", Emettitore.class);
        return query.getResultList();
    }

    public void updateNomeAndStatoById(long id, String newNome, EmettitoreStato newStato) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            // Recupera l'emettitore con l'ID specificato
            Emettitore emettitore = em.find(Emettitore.class, id);

            if (emettitore != null) {
                // Imposta i nuovi valori per i campi nome e stato
                emettitore.setNome(newNome);
                emettitore.setStato(newStato);

                // Esegui l'aggiornamento effettivo utilizzando il metodo merge
                em.merge(emettitore);

                // Fai il commit della transazione
                transaction.commit();
                System.out.println("Nome e stato aggiornati per l'emettitore con ID " + id);
            } else {
                System.out.println("Emettitore con ID " + id + " non trovato");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}