import java.io.*;
import java.util.*;
import java.util.Iterator;
import java.util.Scanner;
import javax.swing.JFileChooser;
//Maa ryddes i om ikke alle benyttes.

//SKAL BRUKE JFILECHOOSER!!!

class Benjamin5 {
    public static void main (String args[]) {
	System.out.println("*** VELKOMMEN TIL BENJAMINS FEMTE OBLIG ***");

	LesFraFil les = new LesFraFil();
	les.filtest();
    }
}

class Sudokobeholder {
    //LELEM
    //Se oblig4, men tilpass oblig5-beskrivnung.
}

abstract class Rute {//kanskje ikke abstract
    int verdi;
    boolean tom;

    Rute(int verdi) {
        this.verdi = verdi;
    }

    void fyllUtRestenAvBrettet() {
    }    

}

class TomRute extends Rute {
    int verdi;

    TomRute(int verdi) {
	super(verdi);
	tom = true;
    }

}

class FyltRute extends Rute {
    int verdi;
    //    super(verdi);
    FyltRute(int verdi) {
	super(verdi);
	tom = false;
    }
}

class Brett {
    Rute[][] brettet;

    Brett(int i) {
	brettet = new Rute[i][i];
    }
}

abstract class BoksKolonneRad {
}

class Boks extends BoksKolonneRad {
}

class Kolonne extends BoksKolonneRad {
}

class Rad extends BoksKolonneRad {
}

class LesFraFil {

    void filtest() {

	//	final JFileChooser fc = new JFileChooser();
	//	int returnVal = fc.showOpenDialog(null);
	//	System.out.println(fc.getSelectedFile());


	File fil1 = new File("6x6oppg28losn.txt");
	int teller = 0;

	try {
	    Scanner f = new Scanner(fil1);//

	    int brettStr = f.nextInt();//6
	    int radStr = f.nextInt();//2 //Bokser innad i brettet. Sporr Linn. Jepp.
	    int kolStr = f.nextInt();//3
	    // System.out.println(brettStr + " " + radStr + " " + kolStr);
	    
	    Brett brett = new Brett(brettStr);
	    int total = brettStr;

	    for (int i = 0; i != brettStr; i++) {
		
		String linje = f.next();
		char[] myChar;
		myChar = linje.toCharArray();
		//	System.out.println(myChar);

		for (int j = 0; j != myChar.length; j++) {

		    if (myChar[j] == '.') {
			brett.brettet[i][j] = new TomRute(0);			
		    } else {

			int bruk = (int) myChar[j] - 48;
			//	System.out.println(bruk);
		        brett.brettet[i][j] = new FyltRute(bruk);

		    }

		    // System.out.println(myChar);

		}

	    }

	    for (int i = 0; i!=brettStr; i++) {
		for (int j = 0; j< brettStr; j++) {
		    if (!brett.brettet[i][j].tom) {
			//		System.out.print(brett.brettet[i][j].verdi);
		    } else {
			//	System.out.print(".");
		    }
		}
		//	System.out.println("");
	    }

	    f.close();

	} catch (FileNotFoundException e) {
	    System.out.println("Fil 1 ikke funnet.");
	    e.printStackTrace();
	}


    }


}

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
