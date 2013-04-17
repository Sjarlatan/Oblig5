	import java.io.*;
	import java.util.*;
	import java.util.Iterator;//...
	import java.util.Scanner;
	import javax.swing.JFileChooser;
	import java.util.Random; //For tilfeldig utvalg...
	//Maa ryddes i om ikke alle benyttes.

	//SKAL BRUKE JFILECHOOSER!!!

	//ELLER ARGS HUSK DET

	//private jpanel lagBrettet(+neste?)
	//frame, underframer jTextField ruten = new JTextField;

	class Benjamin5 {
		public static void main (String args[]) {
			System.out.println("*** VELKOMMEN TIL BENJAMINS FEMTE OBLIG ***");

			LesFraFil les = new LesFraFil();

			TestTom tt = new TestTom();
		//Random rand = new Random();
		//rand.nextInt();
		//myArray(rand.nextInt(myArray.length))];
			les.filtest();

		//Test av char-utskrift
		//	char a = 'A';
		//	char f = 'F';
		//	int achar = (int) a - 48;
		//	int fchar = (int) f - 48;
		//	System.out.println(achar);
		//	System.out.println(fchar);

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

	    void settInn() {
	    }

	    int hentAntall() {
		return antall;
	    }

	}

	abstract class Rute {//kanskje ikke abstract
		int verdi;
	    int tVerdi;//Testverdi som programmet bruker naar den tester losninger.

	    int losTeller = 0;
	    int forsok = 0; //Test i random. Skal muligens kastes.

	    boolean tom;
	    Boks boks;
	    Kolonne kolonne;
	    Rad rad;
	    Rute neste;
	    Brett brett;

	    Rute(int verdi) {
	    	this.verdi = verdi;
	    	tVerdi = verdi;
	    }

	    void settInnTempVerdi(int i) {
	    	tVerdi = i;
	    }

	    void fyllUtRestenAvBrettet() {//Rute tmp
		//	System.out.println("!1");
		boolean lost = false;
		if (neste==null) {
		    System.out.println("!2");

		    if (tom) {
			for (int i = 0; i<brett.brettet.length; i++) {
			    if (alt(i)) {
				tVerdi=i;
				lost=true;
			    }
			}
		    } else {
			lost = true;
		    }

		}

		if (neste!=null) {

		if (!tom) {
		    neste.fyllUtRestenAvBrettet();
		}

		if (tom) {
		    for (int i = 1; i<brett.brettet.length+1; i++) {
		        

			if (alt(i)) {
			    System.out.println(i);
			    tVerdi=i;
			    System.out.println(tVerdi);
			    neste.fyllUtRestenAvBrettet();
			}
			
		    }

		    if (tVerdi==0) {
			System.out.println("Return!");
			return;
		    }

		}

		}

		if (lost) {//lost
		    brett.skrivUtT();
		    System.out.println("");
		}

	    }

	    boolean alt(int i) {
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

	    boolean erIiBKR(int i) {
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
		Rute[][] brettet;
		Rad[] rad;
		Kolonne[] kolonne;
		Boks[] boks;

		int bredde;

		Brett(int i) {
			brettet = new Rute[i][i];
			rad = new Rad[i];
			kolonne = new Kolonne[i];
			boks = new Boks[i];
			bredde = i;

			for (int j = 0; j!=i; j++) {
				rad[j] = new Rad(i);
				kolonne[j] = new Kolonne(i);
				boks[j] = new Boks(i);
			}
		}

		void resetBrett() {
			for (int i = 0; i!=brettet.length; i++) {
				for (int j = 0; j< brettet.length; j++) {
					if (brettet[i][j].tom) {
						brettet[i][j].settInnTempVerdi(0);
					}
				}
			}
		}

		void losMeg() {
			int b = brettet.length;
			//System.out.println(b);
			//	int[][] losning= new int [b][b];
			//	for (int i = 0; i!=brettet.length; i++) {
			//		for (int j = 0; j< brettet.length; j++) {
			//			if (!brettet[i][j].tom) {
					    //				losning[i][j] = brettet[i][j].verdi;
					//Her lager jeg et midlertidig dobbelarray av int og fyller det med verdiene som er der.
					//Enkelt og greit.
			//			} else {
			//				losning[i][j] = 0;
			//			}
			//	//		}
			//	}
			//int k = 0;
			//int l = 0;
			brettet[0][0].fyllUtRestenAvBrettet();//brettet[0][0]
		}

		boolean erJegLost() {
			for (int i = 0; i!=brettet.length; i++) {
				for (int j = 0; j< brettet.length; j++) {
					if (brettet[i][j].erIiBKR(brettet[i][j].tVerdi)) {
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
	    			if (!brettet[i][j].tom) {
	    				System.out.print(brettet[i][j].verdi);
	    			} else {
	    				System.out.print(".");
	    			}
	    		}
	    		System.out.println("");
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

		void filtest() {

		//final JFileChooser fc = new JFileChooser();
		//int returnVal = fc.showOpenDialog(null);
		//System.out.println(fc.getSelectedFile());

		//Testfil: 
		//File fil1 = new File("6x6oppg28losn.txt");

		//JfileChooser
		//File fil1 = new File(fc.getSelectedFile());

			File fil1 = new File("6x6oppg28losn.txt");
			int teller = 0;

			try {
				Scanner f = new Scanner(fil1);

		    int brettStr = f.nextInt();//6
		    //OBS: Skal vaere omvendt. radStr = kol. kolStr = rad
		    int radStr = f.nextInt();//2 //Bokser innad i brettet. Sporr Linn. Jepp.
		    int kolStr = f.nextInt();//3

		    int radTeller = 0;
		    int kolTeller = 0;

		    Rute forrige = null;

		    //   int kolTeller = 0;
		    // System.out.println(brettStr + " " + radStr + " " + kolStr);
		    
		    Brett brett = new Brett(brettStr);

		    for (int i = 0; i != brettStr; i++) {

		    	String linje = f.next();
		    	char[] myChar;
		    	myChar = linje.toCharArray();
			//	System.out.println(myChar);

		    	for (int j = 0; j != myChar.length; j++) {

		    		if (myChar[j] == '.') {

		    			brett.brettet[i][j] = new TomRute(0);

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
		    			
		    		} else {

		    			int bruk = (int) myChar[j] - 48;
		    			brett.brettet[i][j] = new FyltRute(bruk);

		    			if (forrige != null) {
		    				forrige.neste = brett.brettet[i][j];
		    			} 
		    			forrige = brett.brettet[i][j];

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

		    		}
		    		radTeller++;
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

		    for (int i = 0; i<1; i++) {
		    	brett.losMeg();
		    }

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


//SOPPEL
	class TestTom {

		TestTom() {
			Brett brett = new Brett(4);

			for (int i = 0; i<4; i++) {
				for (int j = 0; j<4; j++) {
					brett.brettet[i][j] = new TomRute(0);
				}
			}
		}
	}

	//Kanskje kaste.
	class Losning {
		Rute[][] lostBrett;

		Losning(int i) {
			lostBrett = new Rute[i][i];
		}
	}
