package service;
import corbaBanque.Compte;
import corbaBanque.IBanqueRemotePOA;
import java.util.HashMap;
import java.util.Map;

// ...existing code...
public class BanqueImpl<tabComptes> extends IBanqueRemotePOA {
    private Map<Integer, Compte> comptes = new HashMap<>();
    private int nextCode = 1;

    @Override
    public synchronized void creerCompte(Compte cpte) {
        // si client envoie code=0 ou code déjà utilisé, on assigne un code unique
        if (cpte.code == 0 || comptes.containsKey(cpte.code)) {
            cpte.code = nextCode++;
        }
        comptes.put(cpte.code, cpte);
        System.out.println("Compte créé : code=" + cpte.code + " solde=" + cpte.solde);
    }

    @Override
    public synchronized void verser(float mt, int code) {
        Compte c = comptes.get(code);
        if (c != null) {
            c.solde += mt;
        } else {
            System.out.println("verser: compte introuvable " + code);
        }
    }

    @Override
    public synchronized void retirer(float mt, int code) {
        Compte c = comptes.get(code);
        if (c != null) {
            c.solde -= mt;
        } else {
            System.out.println("retirer: compte introuvable " + code);
        }
    }

    @Override
    public synchronized Compte getCompte(int code) {
        Compte c = comptes.get(code);
        if (c == null) {
            Compte notFound = new Compte();
            notFound.code = -1;
            notFound.solde = 0f;
            return notFound;
        }
        return c;
    }

    @Override
    public synchronized tabComptes getComptes() {
        tabComptes seq = new tabComptes();
        seq.value = new Compte[comptes.size()];
        int i = 0;
        for (Compte c : comptes.values()) {
            seq.value[i++] = c;
        }
        return seq;
    }

    @Override
    public double conversion(float mt) {
        // exemple: 1 EUR = 3.3 DT (adapter si besoin)
        double rate = 3.3;
        return mt * rate;
    }
}
// ...existing code...