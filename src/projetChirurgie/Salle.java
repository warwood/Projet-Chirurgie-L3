package projetChirurgie;

import java.util.ArrayList;
import java.util.List;

public class Salle {
	
	private String nom;
	private static List<Salle> ListSalle = new ArrayList<Salle>();

	public Salle(String nom) {
		this.nom = nom;
		Salle.ListSalle.add(this);
	}
	
	public String getNom() {
		return nom;
	}
	
	public static List<Salle> getListSalle() {
		return ListSalle;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * Informe si la salle est une salle d'urgence
	 * @return retournes true si la salle est une salle d'urgence
	 */
	public boolean isUrgence() {
		if(this.getNom().toUpperCase().contains("U"))
			return true;
		return false;
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
		Salle other = (Salle) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}
}
