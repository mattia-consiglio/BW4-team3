package team3.entities.suppliers;

import team3.entities.Biglietto;
import team3.entities.Emettitore;
import team3.entities.Mezzo;
import team3.entities.Utente;

import java.util.List;

@FunctionalInterface
public interface BigliettoSupplier {
    Biglietto get(List<Emettitore> emettitoreList, List<Mezzo> mezzi, List<Utente> utenti);
}
