package corbaServer;


import corbaBanque.*;
import service.BanqueImpl;


import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;


public class BanqueServer {
public static void main(String[] args) {
try {
ORB orb = ORB.init(args, null);


POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
rootpoa.the_POAManager().activate();


BanqueImpl servant = new BanqueImpl();


org.omg.CORBA.Object ref = rootpoa.servant_to_reference(servant);
IBanqueRemote href = IBanqueRemoteHelper.narrow(ref);


// Enregistrement dans le service de noms
org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);


NameComponent path[] = ncRef.to_name("BanqueService");
ncRef.rebind(path, href);


System.out.println("BanqueServer prêt et enregistré comme 'BanqueService'. En attente...");
orb.run();
} catch (Exception e) {
e.printStackTrace();
}
}
}