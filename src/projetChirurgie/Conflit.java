package projetChirurgie;

import java.time.Duration;

public class Conflit {

	private Chirurgie a;
	private Chirurgie b;
	private ConflitType type;
	
	public Conflit(Chirurgie a, Chirurgie b, ConflitType type) {
		this.a = a;
		this.b = b;
		this.type = type;
	}
	
	public ConflitType getType() {
		return this.type;
	}
	
	public Chirurgie getChira() {
		return this.a;
	}
	
	public Chirurgie getChirb() {
		return this.b;
	}
	
	/**
	 * @return renvoie faux si une des chirugies du conflit est incluse dans l'autre (en terme de plage horaire)
	 */
	public boolean IsIntersection() {
		if(this.a.getH_deb().isBefore(this.b.getH_deb()) && this.a.getH_fin().isBefore(this.b.getH_fin()))
			return true;
		return false;
	}

		
	public boolean IsSameHoraire() {
		if(this.a.getH_deb().equals(this.b.getH_deb()) && this.a.getH_fin().equals(this.b.getH_fin()))
			return true;
		return false;
	}
	
	/**
	 * 
	 * @return le temps de l'intersection
	 */
	public Long intersectionTime() {
		return Duration.between(this.getChirb().getH_deb(),this.getChira().getH_fin()).toMinutes();
	}
		
	public boolean contains(Chirurgie chir) {
		if (this.a.equals(chir) || this.b.equals(chir))
			return true;
		return false;
	}
	
}
