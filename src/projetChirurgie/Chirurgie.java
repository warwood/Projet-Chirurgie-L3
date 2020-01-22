package projetChirurgie;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Date;
import static java.time.temporal.ChronoUnit.MINUTES;

public class Chirurgie {
	
	private String id;
	private Date date;
	private Salle salle;
	private Chirurgien chirurgien;
	private LocalTime h_deb;
	private LocalTime h_fin;
	public static Comparator<Chirurgie> byDate = new Comparator<Chirurgie>() {

		public int compare(Chirurgie ch1, Chirurgie ch2) {
			if (ch1.getH_deb().compareTo(ch2.getH_deb()) == 0)
				return ch1.getH_fin().compareTo(ch2.getH_fin());	
			return ch1.getH_deb().compareTo(ch2.getH_deb());
		}
	};
	
	public Chirurgie(String id, Date date, Salle salle, Chirurgien chirurgien, LocalTime h_deb, LocalTime h_fin) {
		this.id = id;
		this.date = date;
		this.salle = salle;
		this.chirurgien = chirurgien;
		this.h_deb = h_deb;
		this.h_fin = h_fin;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Salle getSalle() {
		return salle;
	}
	
	public void setSalle(Salle salle) {
		this.salle = salle;
	}
	
	public Chirurgien getChirurgien() {
		return chirurgien;
	}
	
	public void setChirurgien(Chirurgien chirurgien) {
		this.chirurgien = chirurgien;
	}
	
	public LocalTime getH_deb() {
		return h_deb;
	}
	
	public void setH_deb(LocalTime h_deb) {
		this.h_deb = h_deb;
	}
	
	public LocalTime getH_fin() {
		return h_fin;
	}
	
	public void setH_fin(LocalTime h_fin) {
		this.h_fin = h_fin;
	}
	
	public long getDuree() {
		return Duration.between(getH_deb(),getH_fin()).toMinutes();
	}
	/**
	 * Translate un temps dans le passé par un certains nombres de minutes
	 * @param min - Temps de translation
	 */
	public void translateToLeft(long min) {
		this.h_deb = h_deb.minus(min,MINUTES);
		this.h_fin = h_fin.minus(min,MINUTES);
	}
	/**
	 * Translate un temps dans le futur par un certains nombres de minutes
	 * @param min - Temps de translation
	 */
	public void translateToRight(long min) {
		this.h_deb = h_deb.plus(min,MINUTES);
		this.h_fin = h_fin.plus(min,MINUTES);
	}
	/**
	 * Informe de savoir si deux chirurgies partage une plage horaire
	 * @param chir la chirurgie comparée
	 * @return true si partage de plage horaire
	 */
	public boolean share_horaire(Chirurgie chir) {
		//Partage de tout ou partie de la plage horaire
		if(!chir.getH_deb().isAfter(this.getH_fin())){
			return true;
		}
		return false;
	}
	/**
	 * Informe si deux chirurgies partagent une salle
	 * @param chir la chirurgie comparée
	 * @return true si les chirurgies partagent une salle
	 */
	public boolean share_salle(Chirurgie chir) {
		if(this.salle.equals(chir.salle)) {
			return true;
		}
		return false;
	}
	/**
	 * Informe si deux chirurgies partagent un meme chirurgien
	 * @param chir le chirurgie comparée
	 * @return true si les deux chirurgies partagent un chirurgien
	 */
	public boolean share_chirurgien(Chirurgie chir) {
		if(this.chirurgien.equals(chir.chirurgien)) {
			return true;
		}
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
		Chirurgie other = (Chirurgie) obj;
		if (chirurgien == null) {
			if (other.chirurgien != null)
				return false;
		} else if (!chirurgien.equals(other.chirurgien))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (h_deb == null) {
			if (other.h_deb != null)
				return false;
		} else if (!h_deb.equals(other.h_deb))
			return false;
		if (h_fin == null) {
			if (other.h_fin != null)
				return false;
		} else if (!h_fin.equals(other.h_fin))
			return false;
		if (id != other.id)
			return false;
		if (salle == null) {
			if (other.salle != null)
				return false;
		} else if (!salle.equals(other.salle))
			return false;
		return true;
	}
	

}
