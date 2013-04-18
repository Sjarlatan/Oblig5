	import java.io.*;
	import java.util.*;
	import java.util.Scanner;
	import javax.swing.JFileChooser;

//Huskeliste:
//ARGS
//UTSKRIFT TIL FIl


//Utskrift til fil funker ikke
//Og args-funksjonalitet mangler
//Tom 4x4 gir bare 72 losninger.

//Programmets GUI er Stein Gjessing sitt, med noen tilpassede modifikasjoner.

	class Benjamin5 {
		public static void main (String args[]) {
			System.out.println("*** VELKOMMEN TIL BENJAMINS FEMTE OBLIG ***");

			LesFraFil les = new LesFraFil();

			les.filtest();


		}
	}

	class Beholder {
	    //LELEM
	    //Se oblig4, men tilpass oblig5-beskrivnung.
	    int antall = 0;

	    Lelem forste;

	    Beholder() {
		//
	    }

	    class Lelem {
		Lelem neste;

		Rute[][] brett;

		Lelem(Rute[][] brett) {
		    this.brett = brett;
		}
	    }

	    void settInn(Rute[][] brett) {

		if (forste==null) {
		    Lelem lel = new Lelem(brett);
		    forste = lel;
		    antall++;
		    //   System.out.println("A: " + antall);
		    //Test av oppdatert verdi;
		    //System.out.println(lel.brett[0][0].verdi);
		    return;
		}

		if (antall==1) {
		    Lelem lel = new Lelem(brett);
        
		    lel.neste = forste;
		    forste = lel;

		    antall++;
		    //   System.out.println("B: " + antall);
		    return;
		} 

		Lelem lel = new Lelem(brett);
		//	for (Lelem en = forste; en!=null; en=en.neste) {
		
		//	en.neste = lel;

		Lelem tmp = forste;
		lel.neste = tmp;
		forste = lel;

		    
		antall++;
		//	System.out.println("C: " + antall);
		return;
		// }
        

	    }

	    Rute[][] taUt() {

		Lelem tmp = forste;
		forste = tmp.neste;
		antall--;
		//	System.out.println("D: " + antall);

		return tmp.brett;
		

	    }

	    int hentAntall() {
		return antall;
	    }

	}

abstract class Rute {//kanskje ikke abstract
    int verdi;
    int tVerdi;//Testverdi som programmet bruker naar den tester losninger. //Fortid? Fortid. Har igjen metoder for eventuell bruk.

    int kordI;
    int kordJ;

    int losTeller = 0;
    int forsok = 0; //Test i random. Skal muligens kastes.

    boolean tom;
    Boks boks;
    Kolonne kolonne;
    Rad rad;
    Rute neste;
    static Brett brett;

    Rute(int verdi) {
	this.verdi = verdi;
	tVerdi = verdi;
    }

    void settInnVerdi(int i) {
	verdi = i;
    }

    boolean erTestVerdiLovlig(int testverdi) {//Nytt forsok...

	for (int i = 0; i<boks.BKR.length; i++) {
	    if (boks.BKR[i] != this) {
	        //	System.out.println("BKRBOKS1");
		if (boks.BKR[i].verdi == testverdi) {
		    //	System.out.println("BKRBOKS2");
		    return false;
		}
	    }
	}

	for (int i = 0; i<rad.BKR.length; i++) {
	    if (rad.BKR[i] != this) {
		if (rad.BKR[i].verdi == testverdi) {
		    return false;
		}
	    }
	}

	for (int i = 0; i<kolonne.BKR.length; i++)
	    {
		if (kolonne.BKR[i] != this) {
		    if (kolonne.BKR[i].verdi == testverdi) {
			return false;
		    }
		}

	    }
	return true;
    }

    boolean fyllUtRestenAvBrettet() throws IOException {
	//	System.out.println("F1");
	int b = brett.bredde;
	//	System.out.println(b);
	boolean lost = false;

	if (verdi==0) {

	    for (int testverdi = 1; testverdi<=b; testverdi++) {
		if (erTestVerdiLovlig(testverdi)) {
		    verdi = testverdi;

		    if (neste != null) {
        
		        //	if (neste.fyllUtRestenAvBrettet()) {//??
			    neste.fyllUtRestenAvBrettet();
			    
			    //    return true;

			verdi = 0;

		    }

		}
	    }

	    if (verdi == 0) {//	Finner ingen lovlige verdier for denne ruten
		return false;
	    }

	}

	if (neste != null) {

	    return neste.fyllUtRestenAvBrettet();

	} else {
	    lost=true;
	    if (brett.erJegLost()) {//???
		lost=false;
	    }

	}

	if (lost) {
	    losTeller++;
	    // brett.skrivUt();
	    //System.out.println(losTeller);

	    //Klonetid!
	    //For at losningene som ligger i beholderen skal forbli forskjellige.
	    Rute[][] losning = new Rute[b][b];
	    for (int i = 0; i<b; i++) {
		for (int j = 0; j<b; j++) {

		    FyltRute tmp = new FyltRute(brett.brettet[i][j].verdi);
		    losning[i][j] = tmp;

		}

	    }

	    if (losTeller < 500) {
		brett.beholder.settInn(losning);//brett.brettet
	    }
	    //Utskrift-tester:
	    // System.out.println("Verdi: " + brett.brettet[0][0].verdi);
	    brett.utskrift(brett.brettet);
	    // System.out.println(brett.beholder.hentAntall());
	}

	return true;
    }

    //	boolean lost = true;
	//System.out.println("FOR FOR");
    //	for (int i = 0; i!=brett.brettet.length; i++) {
    //	    for (int j = 0; j< brett.brettet.length; j++) {
    //		if (brett.brettet[i][j].verdi==0 && erTestVerdiLovlig(brett.brettet[i][j].verdi)) {
    //		    lost = false;
    //		}
    //	    }
    //	}
    //		if (lost) {
    //	brett.skrivUt();
    //	System.out.println("");
    //		}


    boolean alt(int i) {//Ute av bruk?
	if (!boks.plass(i)) {
	    return false;
	}
	if (!rad.plass(i)) {
	    return false;
	}
	if (!kolonne.plass(i)) {
	    return false;
	}
	return true;
    }

    boolean erIiBKR(int i) {//Ute av bruk?
	for (int j = 0; j<boks.BKR.length; j++) {
	    if (i == boks.BKR[j].tVerdi || i == kolonne.BKR[j].tVerdi || i == rad.BKR[j].tVerdi) {
		return true;
	    }
	}
	return false;
    }
}

	class TomRute extends Rute {
		int verdi;

		TomRute(int verdi) {
			super(verdi);
			tom = true;
			tVerdi=0;
		}

	}

	class FyltRute extends Rute {
		int verdi;
	    //    super(verdi);
		FyltRute(int verdi) {
			super(verdi);
			tVerdi=verdi;
			tom = false;
		}
	}

class Brett {

    static Beholder beholder;

    Rute[][] brettet;
    Rad[] rad;
    Kolonne[] kolonne;
    Boks[] boks;

    int bredde;

    int antUtskrift = 0;

    Brett(int i) {
	brettet = new Rute[i][i];
	rad = new Rad[i];
	kolonne = new Kolonne[i];
	boks = new Boks[i];
	bredde = i;
	beholder = new Beholder();

	for (int j = 0; j!=i; j++) {
	    rad[j] = new Rad(i);
	    kolonne[j] = new Kolonne(i);
	    boks[j] = new Boks(i);
	}
    }

    //		void resetBrett() {
    //			for (int i = 0; i!=brettet.length; i++) {
    //				for (int j = 0; j< brettet.length; j++) {
    //					if (brettet[i][j].tom) {
    //						brettet[i][j].settInnTempVerdi(0);
    //					}
    //				}
    //			}
    //		}

    void losMeg() throws IOException {
	brettet[0][0].fyllUtRestenAvBrettet();//brettet[0][0]
    }

    boolean erJegLost() {
	for (int i = 0; i!=brettet.length; i++) {
	    for (int j = 0; j< brettet.length; j++) {
		if (brettet[i][j].erIiBKR(brettet[i][j].verdi)) {
		    return false;
		}
	    }
	}
	return true;
    }

    void skrivUtT() {
	for (int i = 0; i!=brettet.length; i++) {
	    for (int j = 0; j< brettet.length; j++) {
		System.out.print(brettet[i][j].tVerdi);
	    }
	    System.out.println("");
	}

    }

    void skrivUt() {//int a

	for (int i = 0; i!=brettet.length; i++) {
	    for (int j = 0; j< brettet.length; j++) {
		//	if (!brettet[i][j].tom) { 
		if (brettet[i][j].verdi!=0) {
		    System.out.print(brettet[i][j].verdi);
		} else {
		    System.out.print(".");
		}
	    }
	    System.out.println("");
	}
    }

    void utskrift (Rute[][] brett) throws IOException {//throws IOException
	antUtskrift++;

	//Denne funker ikke

	//	System.out.println("LENGTH: " + brett.length);
	try {
	    //    System.out.println("LENGTH: " + brett.length);
	String utfil = "utfil.txt";
	BufferedWriter polse = new BufferedWriter (new FileWriter(utfil, true));
	//	polse.write

	for (int i = 0; i<brett.length; i++) {
	    // System.out.println("LOLOLOLOLOL");
	    for (int j = 0; j<brett.length; j++) {
		//	System.out.println(brett[i][j].verdi);
		polse.write(brett[i][j].verdi+"");
		polse.write("JAN");
		//	polse.write("TING");

	    }
	    polse.newLine();
	}

	} catch (FileNotFoundException e) {
	    e.printStackTrace();

	}

    }

}

	abstract class BoksKolonneRad {
		Rute[] BKR;
		BoksKolonneRad(int i) {
			BKR = new Rute[i];
		}

	    boolean plass(int j) {
		for (int i = 0; i<BKR.length; i++) {
		    if (j==BKR[i].tVerdi) {
			return false;
		    }
		}
		return true;
	    }
	}

	class Boks extends BoksKolonneRad {
		Boks(int i) {
			super(i);
		}
	}

	class Kolonne extends BoksKolonneRad {
		Kolonne(int i) {
			super(i);
		}
	}

	class Rad extends BoksKolonneRad {
		Rad(int i) {
			super(i);
		}
	}

class LesFraFil {

    Rute lagRute(char myChar) {
	if (myChar == '.') {

	    return new TomRute(0);
	}
	int bruk = 0;

	int bruk2 = (int) myChar - 48;

	if(bruk2>9) {
	    bruk = bruk2 - 7;
	} else {
	    bruk = bruk2;
	}

	//System.out.println(bruk);
	return new FyltRute(bruk);
    }

    void filtest() {//Bytt til jFileChooser

	final JFileChooser fc = new JFileChooser();
	int returnVal = fc.showOpenDialog(null);
	System.out.println(fc.getSelectedFile());

	//Testfil: 
	//File fil1 = new File("6x6oppg28losn.txt");

	//JfileChooser
	//File fil1 = new File(fc.getSelectedFile());

	File fil1 = new File("6x6oppg28losn.txt");
	int teller = 0;

	try {
	    Scanner f = new Scanner(fc.getSelectedFile());

	    int brettStr = f.nextInt();//6	  
	    int radStr = f.nextInt();//2 //Bokser innad i brettet.
	    int kolStr = f.nextInt();//3

	    int radTeller = 0;
	    int kolTeller = 0;

	    Rute forrige = null;

	    //   int kolTeller = 0;
	    //  System.out.println(brettStr + " " + radStr + " " + kolStr);
		    
	    Brett brett = new Brett(brettStr);
	    

	    for (int i = 0; i != brettStr; i++) {

		String linje = f.next();
		char[] myChar;
		myChar = linje.toCharArray();
		//System.out.println(myChar);

		for (int j = 0; j != myChar.length; j++) {

		    brett.brettet[i][j] = lagRute(myChar[j]);

		    if (forrige != null) {
			forrige.neste = brett.brettet[i][j];
		    }
		    forrige = brett.brettet[i][j];

		    //Gir korrekt boks. Testet og fungerende paa 6x6,9x9 og 16x16.
		    int boks = (i/radStr)*radStr + (j/kolStr);

		    int a = 0;
		    if (brett.boks[boks].BKR[0]==null) {
			brett.boks[boks].BKR[0] = brett.brettet[i][j];
		    } else {
			while (brett.boks[boks].BKR[a]!=null) {
			    a++;
			}
			brett.boks[boks].BKR[a] = brett.brettet[i][j];
			//System.out.println(brett.boks[boks].boks[+1].verdi);
		    }

		    brett.rad[i].BKR[j] = brett.brettet[i][j];
		    brett.kolonne[j].BKR[i] = brett.brettet[i][j];

		    brett.brettet[i][j].boks = brett.boks[boks];
		    brett.brettet[i][j].rad = brett.rad[i];
		    brett.brettet[i][j].kolonne = brett.kolonne[j];
		    brett.brettet[i][j].brett = brett;
		    brett.brettet[i][j].kordI = i;//Usikker om bruk
		    brett.brettet[i][j].kordJ = j;//Usikker om bruk
			
			    

		    //	if (myChar[j] == '.')  else 
		    //	radTeller++;
		}
		//kolTeller++;
	    }

	    //Test av bokseplassering!
	    //int boks = (i/radStr)*radStr + (j/kolStr);
	    //	System.out.println(boks);
	    //	kolTeller++;
	    //	System.out.println("Teller = " + kolTeller);

	    //Test av boks, rad og kolonneplassering.
	    for (int i = 0; i<brettStr; i++) {
		//System.out.println("Kolonner: " + brett.kolonne[0].kolonne[i].verdi);
		//System.out.println("Rad: " + brett.rad[0].rad[i].verdi);
		for (int j = 0; j<brettStr; j++) {
		    //	    System.out.println("Boks " + i + ": " + brett.boks[i].BKR[j].verdi);
		    //Test av neste:
		    //    System.out.println("---");
		    //	    System.out.println(brett.brettet[i][j].verdi);
		    //	    if (brett.brettet[i][j].neste != null) {
		    //	    System.out.println(brett.brettet[i][j].neste.verdi);
		    //	    }
		    //	    System.out.println("---");
		}
		//	System.out.println("");
	    }

	    //Utskrift av brettet
	    //   brett.skrivUt();

	    f.close();


	    try {
		brett.losMeg();
	    } catch (IOException e) {
		e.printStackTrace();
	    }

	    //Brettet er naa lost. Derfor kaller jeg GUIet herfra.
	    new SudokuGUI (brettStr, radStr, kolStr, brett);



	    //Neste-peker-test
	    //  Rute tmp = brett.brettet[0][0];
	    //   System.out.println("TMPTEST");
	    //   while(tmp!=null) {

	    //	System.out.println(tmp.tVerdi);
	    //	tmp = tmp.neste;
	    //   }

	} catch (FileNotFoundException e) {
	    System.out.println("Fil 1 ikke funnet.");
	    e.printStackTrace();
	}


    }

}


