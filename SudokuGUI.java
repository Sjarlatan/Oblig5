import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.*;
import java.awt.event.*;

/** 
* Tegner ut et Sudoku-brett.
* @author Christian Tryti
* @author Stein Gjessing
*/
public class SudokuGUI extends JFrame implements ActionListener {//implements ActionListener//

    //losningbrett
    //losningsbrett.brettet[0

    private final int RUTE_STRELSE = 50;	/* Strrelsen p hver rute */
        private final int PLASS_TOPP = 50;	/* Plass p toppen av brettet */

	private JTextField[][] brett;   /* for tegne ut alle rutene */
	private int dimensjon;		/* strrelsen p brettet (n) */
	private int vertikalAntall;	/* antall ruter i en boks loddrett */
	private int horisontalAntall;	/* antall ruter i en boks vannrett */

    Brett mittB;
    JButton finnSvarKnapp;

	    JButton nesteKnapp;
    Rute[][] neste;

	int fusTeller = 0;

	/** Lager et brett med knapper langs toppen. */
    public SudokuGUI(int dim, int hd, int br, Brett mittB) {
       		dimensjon = dim;
		vertikalAntall = hd;
		horisontalAntall = br;
		this.mittB = mittB;
		

		brett = new JTextField[dimensjon][dimensjon];

		setPreferredSize(new Dimension(dimensjon * RUTE_STRELSE, 
                                        dimensjon  * RUTE_STRELSE + PLASS_TOPP));
		setTitle("Sudoku " + dimensjon +" x "+ dimensjon );
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel knappePanel = lagKnapper();
		JPanel brettPanel = lagBrettet();

		getContentPane().add(knappePanel,BorderLayout.NORTH);
		getContentPane().add(brettPanel,BorderLayout.CENTER);
		pack();
		setVisible(true);
	}

	/** 
	* Lager et panel med alle rutene. 
	* @return en peker til panelet som er laget.
	*/
	private JPanel lagBrettet() {
		int topp, venstre;
		JPanel brettPanel = new JPanel();
		brettPanel.setLayout(new GridLayout(dimensjon,dimensjon));
		brettPanel.setAlignmentX(CENTER_ALIGNMENT);
		brettPanel.setAlignmentY(CENTER_ALIGNMENT);
		setPreferredSize(new Dimension(new Dimension(dimensjon * RUTE_STRELSE, 
                                                             dimensjon * RUTE_STRELSE)));		
		for(int i = 0; i < dimensjon; i++) {
			/* finn ut om denne raden trenger en tykker linje paa toppen: */
			topp = (i % vertikalAntall == 0 && i != 0) ? 4 : 1;
			for(int j = 0; j < dimensjon; j++) {
				/* finn ut om denne ruten er en del av en kolonne 
				   som skal ha en tykkere linje til venstre:       */
				venstre = (j % horisontalAntall == 0 && j != 0) ? 4 : 1;

				JTextField ruten = new JTextField();
				ruten.setBorder(BorderFactory.createMatteBorder
					(topp,venstre,1,1, Color.black));
				ruten.setHorizontalAlignment(SwingConstants.CENTER);
				ruten.setPreferredSize(new Dimension(RUTE_STRELSE, RUTE_STRELSE));
				ruten.setText("A");
				brett[i][j] = ruten;
				brettPanel.add(ruten);
			}
		}
		return brettPanel;
	}

	/** 
	* Lager et panel med noen knapper. 
	* @return en peker til panelet som er laget.
	*/
	private JPanel lagKnapper() {
		JPanel knappPanel = new JPanel();
		knappPanel.setLayout(new BoxLayout(knappPanel, BoxLayout.X_AXIS));

		finnSvarKnapp = new JButton("Finn lning(er)");
		finnSvarKnapp.addActionListener(this);

		nesteKnapp = new JButton("Neste lng");
		nesteKnapp.addActionListener(this);

		knappPanel.add(finnSvarKnapp);
                knappPanel.add(nesteKnapp);
		return knappPanel;
	}

    public void actionPerformed(ActionEvent e) {

		if(fusTeller>0) {
	    finnSvarKnapp.setEnabled(false);

	} 

	if (e.getSource()==finnSvarKnapp) {
	    //Denne er egentlig meningslos, men jeg har ikke tid til aa endre den.
	    //og trykk av den om det kun er en losning forer til heng.
	    Rute[][] forste = mittB.beholder.forste.brett;

	    for (int i = 0; i<dimensjon; i++) {
		for (int j = 0; j<dimensjon; j++) {

		    if(forste[i][j].verdi<10) {

		    String o = ""+forste[i][j].verdi;
		    brett[i][j].setText(o);

		    } else {

			char c = (char) (forste[i][j].verdi + 55);
			String d = Character.toString(c);

			brett[i][j].setText(d);

		    }

		}

	    }

	    fusTeller++;

	    //   neste = forste.neste;
	    //   nesteTeller = 0;

	    //Test:
	    // System.out.println("LOL");
	    // System.out.println(mittB.beholder.forste.brett[0][0].verdi);
	}


	if(mittB.beholder.antall==0) {
	    nesteKnapp.setEnabled(false);

	} 

	if(mittB.beholder.antall!=0) {
	    if (e.getSource()==nesteKnapp) {

		fusTeller++;

		neste = mittB.beholder.taUt();

		//Den over kunne vaert lik. Jeg tar jo ut forste, saa det blir 

			    

		for (int i = 0; i<dimensjon; i++) {
		    for (int j = 0; j<dimensjon; j++) {

			if(neste!=null) {

			    if(neste[i][j].verdi<10) {
				    String o = ""+neste[i][j].verdi;
			
				    brett[i][j].setText(o);
				} else {

				    char c = (char) (neste[i][j].verdi + 55);
				    String d = Character.toString(c);

				    brett[i][j].setText(d);
				}

			    }

		    }

		}
		
		//Test:
		//  System.out.println("LOL2");
	    }

	}
		
    }

    //    public static void main(String[] args) {

	//	Brett tomBrett = new Brett(6);

	//	new SudokuGUI(6, 2, 3, tomBrett);		}

    //}



