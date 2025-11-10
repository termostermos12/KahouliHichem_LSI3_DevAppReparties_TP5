package corbaClient;


import corbaBanque.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;


public class BanqueClient {
public static void main(String[] args) {
try {
ORB orb = ORB.init(args, null);


org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);


IBanqueRemote bank = IBanqueRemoteHelper.narrow(ncRef.resolve_str("BanqueService"));


// Test créer compte
Compte c = new Compte();
c.code = 0; // laisser serveur assigner
c.solde = 100f;
bank.creerCompte(c);
System.out.println("Compte créé avec code (retour non direct) : " + c.code);


// Récupérer la liste
tabComptes comptes = bank.getComptes();
System.out.println("Nombre de comptes: " + comptes.length);


// Convertir
double dt = bank.conversion(50f);
System.out.println("50 EUR = " + dt + " DT");


} catch (Exception e) {
e.printStackTrace();
}
}
}