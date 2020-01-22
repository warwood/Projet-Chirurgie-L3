package projetChirurgie;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Journee {
	
	private Date date;
	private List<Chirurgie> chirurgies;
	private List<Conflit> conflits;
	private Journee projection;
	/**
	 * Constructeur
	 * @param date - date de la journée
	 */
	public Journee(Date date) {
		this.date = date;
		this.chirurgies = new ArrayList<Chirurgie>();
	}
	
	/**
	 * Constructeur
	 * @param date - date de la journée
	 * @param chirurgies liste de chirurgies
	 */
	public Journee(Date date, List<Chirurgie> chirurgies) {
		this.date = date;
		this.chirurgies = chirurgies;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public boolean isDate(Date date) {
		if (this.getDate().equals(date))
			return true;
		return false;
	}
	
	/**
	 * tri les chirurgies par date
	 */
	public void sortByDate() {
		Collections.sort(this.chirurgies, Chirurgie.byDate);
	}
	
	/**
	 * Ajoute une chirurgie a la journée
	 * @param chir chirurgie a ajouter
	 */
	public void addChirurgie(Chirurgie chir) {
		this.chirurgies.add(chir);
	}
	
	public List<Chirurgie> getChirurgies(){
		return this.chirurgies;
	}
	/**
	 * Retourne une liste contenant tous les chirurgies ayant des conflits dans la journée
	 * 
	 * @return la liste des chirurgies impliquée dans des conflits.
	 */
	public List<Chirurgie> GetChirurgiesConflits(){
		List<Chirurgie> ls = new ArrayList<Chirurgie>();
		for(Conflit c:this.conflits) {
			if(!ls.contains(c.getChira()))
				ls.add(c.getChira());
			if(!ls.contains(c.getChirb()))
				ls.add(c.getChirb());
		}
		return ls;
	}
	
	/**
	 * Retournes la liste des chirurgies n'étant impliqué dans aucun conflit
	 * @return Chirurgies n'ayant pas de conflits
	 */
	public List<Chirurgie> GetChirurgiesNoConflits(){
		List<Chirurgie> ls = new ArrayList<Chirurgie>();
		ls.addAll(this.chirurgies);
		for(Conflit c:this.conflits) {
			if(ls.contains(c.getChira()))
				ls.remove(c.getChira());
			if(ls.contains(c.getChirb()))
				ls.remove(c.getChirb());
		}
		return ls;
	}
	
	public List<Conflit> getConflits(){
		return this.conflits;
	}
	
	public int getNbConflits() {
		return this.conflits.size();
	}
				
	/**
	 * 
	 * @return liste des chirurgiens n'ayant pas de conflits.
	 */
	public List<Chirurgien> getChirurgiensPresents(List<Chirurgie> listchir){
		List<Chirurgien> ls = new ArrayList<Chirurgien>();
		for (Chirurgie chir:listchir){
			if (!ls.contains(chir.getChirurgien()))
					ls.add(chir.getChirurgien());
		}
		return ls;
	}
	/**
	 * @return Retourne la liste des chirurgiens n'etant pas present
	 */
	public List<Chirurgien> notPresentChirurgien(){
		List<Chirurgien> ls = new ArrayList<Chirurgien>();
		ls.addAll(Chirurgien.getListChir());
		ls.removeAll(this.getChirurgiensPresents(this.GetChirurgiesConflits()));
		ls.removeAll(this.getChirurgiensPresents(this.GetChirurgiesNoConflits()));
		return ls;

	}
	
	 
	/**
	 * Retourne les différentes salles utilisées dans une certaine listes de chirurgies 
	 * @param listchir - les chirurgies sur dans lesquels on va rechercher les salles
 	 * @return La liste des differents salles
	 */
	public List<Salle> getSallesPresents(List<Chirurgie>listchir){
		List<Salle> ls = new ArrayList<Salle>();
		for (Chirurgie chir:listchir){
			if (!ls.contains(chir.getSalle()))
					ls.add(chir.getSalle());
		}
		return ls;
	}
	/**
	 * Creer de nouveaux conflits en leur attribuant un typeConflit : Ubiquité, Interference, Chevauchement.
	 * Si deux chirurgies partagent deux horaires : c'est un conflit. <br/>
	 *  Si pendant ce partage, la salle est la ressource en commun : C'est une interference <br/>
	 *  Si pendant ce partage, le chirurgien est la ressource en commun : C'est une ubiquité <br/>	 *  
	 *  Si pendant ce partage, le chirurgien et la salle sont partagées : C'est un chevauchement <br/>
	 */
	public void generateConflits() {
		this.conflits = new ArrayList<Conflit>();
		int i=0;
		int j;
		this.sortByDate();
		//Parcours des chirurgies depuis la 1�re jusqu'� l'avant derni�re
		for(this.chirurgies.get(i);i < this.chirurgies.size() - 1;i++) {
			j=i+1;
			//Parcours des chirurgies depuis la i+1�me jusqu'� la derni�re
			for (this.chirurgies.get(j);j < this.chirurgies.size();j++) {
				if(this.chirurgies.get(i).share_horaire(this.chirurgies.get(j))) {
					if(this.chirurgies.get(i).share_salle(this.chirurgies.get(j))) {
						if(this.chirurgies.get(i).share_chirurgien(this.chirurgies.get(j))) 
							this.conflits.add(new Conflit(this.chirurgies.get(i),this.chirurgies.get(j),ConflitType.CHEVAUCHEMENT));
						else 
							this.conflits.add(new Conflit(this.chirurgies.get(i),this.chirurgies.get(j),ConflitType.INTERFERENCE));		
					}
					else{
						if(this.chirurgies.get(i).share_chirurgien(this.chirurgies.get(j))) 
							this.conflits.add(new Conflit(this.chirurgies.get(i),this.chirurgies.get(j),ConflitType.UBIQUITE));	
					}	
				}
			}
		}
	}
	/**
	 * Retourne le nombre de conflit dans la journée lié a une chirurgie spécifiée
	 * @param chir - La chirurgie donto n veut compter le nombre de conflits
	 * @return le nombre de conflits
	 */
	public int nbOfConflitsWith(Chirurgie chir) {
		int compteur = 0;
		for(Conflit c:this.getConflits()) {
			if(c.contains(chir))
				compteur = compteur + 1;
		}
		return compteur;
	}
	
	public  Map<String,Integer> NbofConflitsMap(){
		Map<String,Integer> mp = new HashMap<String,Integer>();
		for(Conflit c1:this.conflits){
			if (!mp.containsKey(c1.getChira().getId())) 
				mp.put(c1.getChira().getId(),nbOfConflitsWith(c1.getChira()));
			if (!mp.containsKey(c1.getChirb().getId())) 
				mp.put(c1.getChira().getId(),nbOfConflitsWith(c1.getChirb()));
		}
		return mp;
	}
	
	
	public void solve(Journee j) {
		for (Conflit c:this.getConflits()) {
			//appeler méthode résoudre conflit c
			if (this.projection.getNbConflits()<j.getNbConflits() && this.projection.getNbConflits()>0) {
				solve(this.projection);
			}
		}
	}
	
	/**
	 * Methode qui resout un conflit de type ubiquité en plus de mettre a jour l'historique
	 * @param c conflit provoquant une ubiquité
	 * @param ls la liste des salles
	 * @return true si la modification a bien été effectué
	 */
	public boolean test_ubi(Conflit c,List<Chirurgien> ls) {
		Collections.shuffle(ls);
		Chirurgien chir_originel = c.getChira().getChirurgien();
		int nb_conf = this.getNbConflits();
		int i = 0;
		while(i < ls.size() && this.getNbConflits() >= nb_conf) {
			c.getChira().setChirurgien(ls.get(i));
			this.generateConflits();
			i++;
		}
		if (this.getNbConflits() >= nb_conf ) {
			c.getChira().setChirurgien(chir_originel);
			chir_originel = c.getChirb().getChirurgien();
			i = 0;
			while(i < ls.size() && this.getNbConflits() >= nb_conf) {
				c.getChirb().setChirurgien(ls.get(i));
				this.generateConflits();
				i++;
			}
			if (this.getNbConflits() >= nb_conf ) {
				c.getChirb().setChirurgien(chir_originel);
				this.generateConflits();
				return false;
			}
			else {
				Calendrier.getHistorique().println("UBIQUITE RESOLUE");
				Calendrier.getHistorique().println("Modification chirurgie " + c.getChirb().getId() + " : " + chir_originel.getNom() + " --> " + ls.get(i-1).getNom());
			}
		}
		else {
			Calendrier.getHistorique().println("UBIQUITE RESOLUE");
			Calendrier.getHistorique().println("Modification chirurgie " +c.getChira().getId() + " : " + chir_originel.getNom() + " --> " + ls.get(i-1).getNom());
		}
		return true;
	}
	
	/**
	 * Methode qui resout un conflit de type interference en plus de mettre a jour l'historique
	 * @param c conflit provoquant une interference 
	 * @param ls la liste des salles
	 * @return true si la modification a bien été effectué
	 */
	public boolean test_interf(Conflit c,List<Salle> ls) {
		Collections.shuffle(ls);
		Salle salle_originel = c.getChira().getSalle();
		int nb_conf = this.getNbConflits();
		int i = 0;
		while(i < ls.size() && this.getNbConflits() >= nb_conf) {
			c.getChira().setSalle(ls.get(i));
			this.generateConflits();
			i++;
		}
		if (this.getNbConflits() >= nb_conf ) {
			c.getChira().setSalle(salle_originel);
			salle_originel = c.getChirb().getSalle();
			i = 0;
			while(i < ls.size() && this.getNbConflits() >= nb_conf) {
				c.getChirb().setSalle(ls.get(i));
				this.generateConflits();
				i++;
			}
			if (this.getNbConflits() >= nb_conf ) {
				c.getChirb().setSalle(salle_originel);
				this.generateConflits();
				return false;
			}
			else {
				Calendrier.getHistorique().println("INTERFERENCE RESOLUE");
				Calendrier.getHistorique().println("Modification chirurgie " + c.getChirb().getId() + " : " + salle_originel.getNom() + " --> " + ls.get(i-1).getNom());
			}
		}
		else {
			Calendrier.getHistorique().println("INTERFERENCE RESOLUE");
			Calendrier.getHistorique().println("Modification chirurgie " +c.getChira().getId() + " : " + salle_originel.getNom() + " --> " + ls.get(i-1).getNom());
		}
		return true;
	}
	public boolean test_chevauchement_decalage(Conflit c) {
		int nb_conf = this.getNbConflits();
		LocalTime h_Deb_Origin_a = c.getChira().getH_deb();
		LocalTime h_Fin_Origin_a = c.getChira().getH_fin();
		LocalTime h_Deb_Origin_b = c.getChirb().getH_deb();
		LocalTime h_Fin_Origin_b = c.getChirb().getH_fin();
		c.getChira().translateToLeft(c.intersectionTime() + Calendrier.getDureePostOp());
		this.generateConflits();
		if (this.getNbConflits() >= nb_conf || (c.getChira().getH_deb().isBefore(LocalTime.parse("08:00")) && !c.getChira().getSalle().isUrgence())) { 
			c.getChira().setH_deb(h_Deb_Origin_a);
			c.getChira().setH_fin(h_Fin_Origin_a);
			c.getChirb().translateToRight(c.intersectionTime() + Calendrier.getDureePostOp());
			this.generateConflits();
			if (this.getNbConflits() >= nb_conf || (c.getChirb().getH_fin().isAfter(LocalTime.parse("22:00")) && !c.getChira().getSalle().isUrgence())) {
				c.getChirb().setH_deb(h_Deb_Origin_b);
				c.getChirb().setH_fin(h_Fin_Origin_b);
				this.generateConflits();
				return false;
			}
			else {
				Calendrier.getHistorique().println("CHEVAUCHEMENT RESOLU");
				Calendrier.getHistorique().println("Modification chirurgie " + c.getChirb().getId() + " : " + "Decalage � droite");
			}
		}
		else {
			Calendrier.getHistorique().println("CHEVAUCHEMENT RESOLU");
			Calendrier.getHistorique().println("Modification chirurgie " + c.getChira().getId() + " : " + "Decalage � gauche");
		}
		return true;
	}
	
	public boolean test_chevauchement_double_change(Conflit c,List<Chirurgien> lchir,List<Salle> lsalle ) {
		Collections.shuffle(lchir);
		Collections.shuffle(lsalle);
		Chirurgien chir_originel = c.getChira().getChirurgien();
		Salle salle_originel = c.getChira().getSalle();
		int nb_conf = this.getNbConflits();
		int i = 0;
		int j = 0;
		while(i < lchir.size() && this.getNbConflits() >= nb_conf) {
			c.getChira().setChirurgien(lchir.get(i));
			j=0;
			while(j < lsalle.size() && this.getNbConflits() >= nb_conf) {
				c.getChira().setSalle(lsalle.get(j));
				this.generateConflits();
				j++;
			}
			i++;
		}
		if (this.getNbConflits() >= nb_conf) {
			c.getChira().setChirurgien(chir_originel);
			c.getChira().setSalle(salle_originel);
			chir_originel = c.getChirb().getChirurgien();
			salle_originel = c.getChirb().getSalle();
			i = 0;
			while(i < lchir.size() && this.getNbConflits() >= nb_conf) {
				c.getChirb().setChirurgien(lchir.get(i));
				j=0;
				while(j < lsalle.size() && this.getNbConflits() >= nb_conf) {
					c.getChirb().setSalle(lsalle.get(j));
					this.generateConflits();
					j++;
				}
				i++;
			}
			if (this.getNbConflits() >= nb_conf) {
				c.getChirb().setChirurgien(chir_originel);
				c.getChirb().setSalle(salle_originel);
				this.generateConflits();
				return false;
			}
			else {
				Calendrier.getHistorique().println("CHEVAUCHEMENT RESOLU");
				Calendrier.getHistorique().println("Modification chirurgie " + c.getChirb().getId() + " : " + chir_originel.getNom() + " --> " + lchir.get(i-1).getNom() + " & " + salle_originel.getNom() + " --> " + lsalle.get(j-1).getNom());
			}
		}
		else {
			Calendrier.getHistorique().println("CHEVAUCHEMENT RESOLU");
			Calendrier.getHistorique().println("Modification chirurgie " + c.getChira().getId() + " : " + chir_originel.getNom() + " --> " + lchir.get(i-1).getNom() + " & " + salle_originel.getNom() + " --> " + lsalle.get(j-1).getNom());
		}
		return true;
	}
	
	/**
	 * Résoud les differents types de conflits en fonction de la priorité : chirugiens sans conflits > Chirurgiens en conflits > Chirurgiens non present
	 * Utilise les methodes test_ubi(), test_interf, test_Chevauc </br>
	 * + Remplie l'historique
	 */
	public void solve() {
		int i = 0;
		boolean solved;
		solved = false;
		this.generateConflits();
		Calendrier.getHistorique().println(("Journée du " + Calendrier.getDateFormat().format(this.date)));
		Calendrier.getHistorique().println(("---------------------"));
		Calendrier.getHistorique().println(this.conflits.size() + " conflit(s)");
		while(this.getNbConflits() > 0 && i < this.getNbConflits()){
				if (solved) {
					this.generateConflits();
					i = 0;
				}
				Conflit c = this.getConflits().get(i);
				solved = true;
				//gestion du conflit ubiquité
				if(c.getType().equals(ConflitType.UBIQUITE)) {
					if(test_ubi(c,this.getChirurgiensPresents(this.GetChirurgiesNoConflits())) == false) {
						if(test_ubi(c,this.getChirurgiensPresents(this.GetChirurgiesConflits()))== false) {
							if(test_ubi(c,this.notPresentChirurgien()) == false) {
								i = i + 1;	
								solved = false;
							}
						}	
					}
				}
				else if(c.getType().equals(ConflitType.INTERFERENCE)) {
					if(test_interf(c,this.getSallesPresents(this.GetChirurgiesNoConflits())) == false) {
						if(test_interf(c,this.getSallesPresents(this.GetChirurgiesConflits())) == false) {
							if(test_interf(c,Salle.getListSalle()) == false) {
								i = i + 1;
								solved = false;
							}
						}	
					}
				}
				else {
					if(c.IsIntersection()) {
						if(test_chevauchement_decalage(c) == false) {
							i = i+1;
							solved = false;
						}
					}
					else {	
						if(test_chevauchement_double_change(c,this.getChirurgiensPresents(this.GetChirurgiesNoConflits()),this.getSallesPresents(this.GetChirurgiesNoConflits())) == false){
							if(test_chevauchement_double_change(c,this.getChirurgiensPresents(this.GetChirurgiesConflits()),this.getSallesPresents(this.GetChirurgiesConflits())) == false){
								if(test_chevauchement_double_change(c,this.notPresentChirurgien(),Salle.getListSalle()) == false){
									i=i+1;
									solved=false;
								}
							}
						}
					}
				}
			}
		Calendrier.getHistorique().println('\n');
		}
}
