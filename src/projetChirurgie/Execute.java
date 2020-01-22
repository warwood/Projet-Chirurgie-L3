package projetChirurgie;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class Execute {

	public static void main(String[] args) throws ParseException, IOException {
		Calendrier cal = new Calendrier();
		try {
			int nb_conf=0;
			int nb_res=0;
			//Choisissez une date pour visualiser la modificationd ans la console ; Enlevez les commentaires des m�thodes visualise.
			//Date date = Calendrier.getDateFormat().parse("04/01/2019");
			cal.remplissage();
			//cal.visualise(date);

			for(Journee j:cal.get_planning()) {
				j.generateConflits();
				nb_conf += j.getNbConflits();
				j.solve();
				nb_res += j.getNbConflits();
			}
			System.out.println("Nombre de conflits détéctés : " + nb_conf);
			System.out.println("Nombre de conflits résolus : " + (nb_conf - nb_res));
			cal.close_Historique();
			cal.exportToCsv("Base_correction");
			//cal.visualise(date);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
}
}
