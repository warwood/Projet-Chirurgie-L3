package projetChirurgie;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Calendrier {
	
	private List<Journee> planning;
	private static PrintWriter historique;
	private static DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
	private static int dureePostOp = 20;
	
	
	public Calendrier() {
		this.planning = new ArrayList<Journee>();
		try {
			historique = new PrintWriter(new File("../historique_correction.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 * @return L'historique accessible de n'importe ou
	 */
	public static PrintWriter getHistorique() {
		return historique;
	}
	
	public void close_Historique() {
		historique.close();
	}
	
	public static int getDureePostOp() {
		return dureePostOp;
	}
	
	public static DateFormat getDateFormat() {
		return dateFormat;
	}
	
	public boolean hasChirurgien(String name) {
		if(!Chirurgien.getListChir().isEmpty()) {
			for (Chirurgien c:Chirurgien.getListChir()) {
				if (c.hasNom(name))
					return true;
			}
		}
		return false;
	}

	public Chirurgien getChirurgien(String name) {
		for (Chirurgien c:Chirurgien.getListChir()) {
			if (c.hasNom(name))
				return c;
		}
		return null;
	}
		
	public boolean hasSalle(String name) {
		if(!Salle.getListSalle().isEmpty()) {
			for (Salle s:Salle.getListSalle()) {
				if(s.hasNom(name))
					return true;
			}
		}
		return false;
	}
	
	public Journee getDay(Date date) {
		for(Journee j:this.planning) {
			if (j.isDate(date))
				return j;
		}
		return null;
	}
	
	public Salle getSalle(String name) {
		for (Salle s:Salle.getListSalle()) {
			if (s.hasNom(name))
				return s;
		}
		return null;
	}
	
	public List<Journee> get_planning(){
		return this.planning;
	}
	/**
	 * Lie le fichier CSV de 6 colonnes et ajoute au planning chaque occurence du fichier en les stockant dans les variables adaptés.
	 * ID DATE SALLE CHIRURGIEN H_DEB _H_FIN
	 * @throws IOException
	 * @throws ParseException
	 */
	public void remplissage() throws IOException, ParseException {
		//Ouverture d'une boîte de dialogue pour selectionner le fichier csv
	    FileDialog dialog = new FileDialog(new Frame(), "Sélectionner la base de données en csv", FileDialog.LOAD);
	    dialog.setDirectory("../");
	    dialog.setFile("*.csv");
	    dialog.setVisible(true);
	    String file =  dialog.getDirectory() + dialog.getFile();
	    Chirurgien chir;
	    Salle salle;
	    	    
	    String line="";
	    int i = 0;
	    int index = 0; // correspond au numero de la journée actuelle
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null){
				if (i > 0) {
					String[] tab = line.split(",");
					Date date = new Date();
					date = dateFormat.parse(tab[1]);
					LocalTime h_deb = LocalTime.parse(tab[2]);
					LocalTime h_fin = LocalTime.parse(tab[3]);
					
					//Ajout d'un chirurgien si pas encore rentré, sinon renvoie le chirurgien correspondant
					if (!this.hasChirurgien(tab[5])) {
						chir = new Chirurgien(tab[5]);
					}
					else
						chir = this.getChirurgien(tab[5]);
					
					//Même chose mais pour la salle
					if (!this.hasSalle(tab[4])) {
						salle = new Salle(tab[4]);
					}
					else
						salle = this.getSalle(tab[4]);
					
					//Attention premiere iteration planning vide
					if (!this.planning.isEmpty()) {
						if (!this.planning.get(index).isDate(date)) {
							this.planning.add(new Journee(date));
							index++;
						}
					}
					else 
						this.planning.add(new Journee(date));
					//Ajout d'une nouvelle chirurgie à la journée correspondante
					this.planning.get(index).addChirurgie(new Chirurgie(tab[0],date,salle,chir,h_deb,h_fin));
				}
				i++;	
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * Creer une visualisation du planning d'une journée
	 */
	public void visualise(Date date) {
		Journee j = this.getDay(date);
		if(j == null) {
			System.out.print("Le jour " + date + " n'existe pas dans le planning.");
		}
		else {
			j.sortByDate();
		    System.out.println(dateFormat.format(j.getDate()));
		    System.out.println("----------");
		    System.out.print("                    ");
			for (int i=8;i<=22;i++) {
				System.out.print(i+"h         ");
				if (i<10)
					System.out.print(" ");
			}
			System.out.print("\n");
			for(Chirurgien prac:Chirurgien.getListChir()) {
				System.out.print(prac.getNom());
				for (int i=0; i< 20 - prac.getNom().length(); i++)
					System.out.print(" ");
				for (int i=8;i<=22;i++) {
					System.out.print(".     .     ");
				}
				for(Chirurgie chir:j.getChirurgies()) {
					if (chir.getChirurgien().equals(prac)) {
						System.out.print("\n");
						System.out.print(chir.getId());
						for (int i=0;i<20 - chir.getId().length();i++)
							System.out.print(" ");
						for(int i=0;i < Duration.between(LocalTime.parse("08:00:00"), chir.getH_deb()).toMinutes()/5;i++)
							System.out.print(" ");
						System.out.print(chir.getSalle().getNom());
						for(int i=0;i< (chir.getDuree())/5 - chir.getSalle().getNom().length();i++) {
							System.out.print("x");
						}
					}
				}
				System.out.print("\n");
			}	
		}	
	}
	
	/**
	 * Export de la base dans un nouveau CSV
	 * @param nomFichier : Nom a donner au fichier
	 * 
	 */
	public void exportToCsv(String nomFichier) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new File("../"+nomFichier+".csv"));
		StringBuilder sb = new StringBuilder();
		// en tête
		sb.append("ID CHIRUGIE");
		sb.append(",");
		sb.append("DATE CHIRUGIE");
		sb.append(",");
		sb.append("HEURE_DEBUT CHIRUGIE");
		sb.append(",");
		sb.append("HEURE_FIN CHIRUGIE");
		sb.append(",");
		sb.append("SALLE CHIRUGIE");
		sb.append(",");
		sb.append("CHIRURGIEN CHIRUGIE");
		sb.append('\n');
		writer.write(sb.toString());		
		for(Journee journee : this.planning) {
			
				for(Chirurgie chir:journee.getChirurgies()) {
					sb = new StringBuilder();
					sb.append(chir.getId());
					sb.append(",");
					sb.append(dateFormat.format(chir.getDate()));
					sb.append(",");
					sb.append(chir.getH_deb().toString());
					sb.append(",");
					sb.append(chir.getH_fin());
					sb.append(",");
					sb.append(chir.getSalle().getNom());
					sb.append(",");
					sb.append(chir.getChirurgien().getNom());
					sb.append('\n');
					writer.write(sb.toString());				
				}
		}
		writer.close();	
	}
	

}
