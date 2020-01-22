package projetChirurgie;

import java.util.ArrayList;
import java.util.List;

public class Chirurgien {

	private String nom;
	private static List<Chirurgien> ListChir = new ArrayList<Chirurgien>();

	public Chirurgien(String nom) {
		this.nom = nom;
		Chirurgien.ListChir.add(this);
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public static List<Chirurgien> getListChir(){
		return ListChir;
	}
	/**
	 * Compare deux noms	
	 * @param name un nom
	 * @return true si les noms sont les memes
	 */
	public boolean hasNom(String name) {
		if(this.getNom().equals(name))
			return true;
		else
			return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chirurgien other = (Chirurgien) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}
}
