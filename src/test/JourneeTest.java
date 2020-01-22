package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;
import projetChirurgie.Chirurgie;
import projetChirurgie.Chirurgien;
import projetChirurgie.Conflit;
import projetChirurgie.ConflitType;
import projetChirurgie.Journee;
import projetChirurgie.Salle;


class JourneeTest extends TestCase {
	private static DateFormat dateFormat = new SimpleDateFormat("dd,mm,yyyy");
	private Chirurgie chir1;
	private Chirurgie chir2;
	private Chirurgie chir3;
	private Chirurgie chir4;
	private ArrayList<Chirurgie> chirs;
	private Journee j1;
	
	@Before
	protected void setUp() throws  Exception {
		super.setUp();
		
	}
	@Test
	void testAddChirurgie() throws ParseException {
		this.chir1 = new Chirurgie("0",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("JEAN"), LocalTime.of(8,00,00), LocalTime.of(9,00,00));
	    this.chir2 = new Chirurgie("1",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
		this.chir3 = new Chirurgie("2",dateFormat.parse("2014,1,1"), new Salle("U11"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
		this.chirs = new ArrayList<Chirurgie>();
		this.chirs.add(chir1);
		this.chirs.add(chir2);
		this.chirs.add(chir3);
		this.j1 = new Journee(dateFormat.parse("2014,1,1"),chirs);
		
		this.j1.addChirurgie(new Chirurgie("3",dateFormat.parse("2014,1,1"), new Salle("U11"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00)));
		
		assertTrue(this.j1.getChirurgies().size() == 4);
		//assertEquals(size,(Integer)(j.getChirurgies().size()-1));
	}

	@Test
	void testGetChirurgiesConflits() throws ParseException {
		this.chirs = null;
		this.chir1 = new Chirurgie("0",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("JEAN"), LocalTime.of(8,00,00), LocalTime.of(9,00,00));
	    this.chir2 = new Chirurgie("1",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
		this.chir3 = new Chirurgie("2",dateFormat.parse("2014,1,1"), new Salle("U11"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
		this.chirs = new ArrayList<Chirurgie>();
		this.chirs.add(chir1);
		this.chirs.add(chir2);
		this.chirs.add(chir3);
		this.j1 = new Journee(dateFormat.parse("2014,1,1"),chirs);
		this.j1.generateConflits();
		assertTrue(this.j1.GetChirurgiesConflits().size()==3);
		
	}

	@Test
	void testGetChirurgiesNoConflits() throws ParseException {

		this.chirs = null;
		this.chir1 = new Chirurgie("0",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("JEAN"), LocalTime.of(8,00,00), LocalTime.of(9,00,00));
	    this.chir2 = new Chirurgie("1",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
		this.chir3 = new Chirurgie("2",dateFormat.parse("2014,1,1"), new Salle("U11"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
		this.chir4 = new Chirurgie("3",dateFormat.parse("2016,2,4"), new Salle("E14"), new Chirurgien("Alain"), LocalTime.of(8,00,00), LocalTime.of(9,00,00));
		this.chirs = new ArrayList<Chirurgie>();
		this.chirs.add(chir1);
		this.chirs.add(chir2);
		this.chirs.add(chir3);
		this.chirs.add(chir4);
		this.j1 = new Journee(dateFormat.parse("2014,1,1"),chirs);
		this.j1.generateConflits();
		assertTrue(this.j1.GetChirurgiesNoConflits().size()==1);
		
	}

	@Test
	void testGetNbConflits() throws ParseException {
		this.chir1 = new Chirurgie("0",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("JEAN"), LocalTime.of(8,00,00), LocalTime.of(9,00,00));
	    this.chir2 = new Chirurgie("1",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
		this.chir3 = new Chirurgie("2",dateFormat.parse("2014,1,1"), new Salle("U11"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
		this.chirs = new ArrayList<Chirurgie>();
		this.chirs.add(chir1);
		this.chirs.add(chir2);
		this.chirs.add(chir3);
		this.j1 = new Journee(dateFormat.parse("2014,1,1"),chirs);
		this.j1.generateConflits();
		assertTrue(this.j1.GetChirurgiesConflits().size()==3);
	}

	@Test
	void testGetChirurgiensPresents() throws ParseException {
    this.chir2 = new Chirurgie("1",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
	this.chir3 = new Chirurgie("2",dateFormat.parse("2014,1,1"), new Salle("U11"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
	this.chir1 = new Chirurgie("2",dateFormat.parse("2014,1,2"), new Salle("E11"), new Chirurgien("Alain"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
	this.chirs = new ArrayList<Chirurgie>();
	this.chirs.add(chir1);
	this.chirs.add(chir2);
	this.chirs.add(chir3);
	this.j1 = new Journee(dateFormat.parse("2014,1,1"),chirs);
	this.j1.generateConflits();
	System.out.print(this.j1.getChirurgiensPresents(this.chirs).get(0).getNom());
	assertTrue(this.j1.getChirurgiensPresents(this.chirs).get(0).getNom().equals("Alain"));

	}
	/*
	@Test
	void testTest_ubi() throws ParseException {
		this.j1 = null;
		this.chir1 = new Chirurgie("0",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("JEAN"), LocalTime.of(8,00,00), LocalTime.of(9,00,00));
	    this.chir2 = new Chirurgie("1",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
		this.chir3 = new Chirurgie("2",dateFormat.parse("2014,1,1"), new Salle("U11"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
		this.chirs = new ArrayList<Chirurgie>();
		this.chirs.add(chir1);
		this.chirs.add(chir2);
		this.chirs.add(chir3);
		this.j1 = new Journee(dateFormat.parse("2014,1,1"),chirs);
		this.j1.generateConflits();
		int i = 0;
		boolean solved;
		solved = false;
		while(this.j1.getNbConflits() > 0 && i < this.j1.getNbConflits()){
			if (solved) {
				this.j1.generateConflits();
				i = 0;
			}
			Conflit c = this.j1.getConflits().get(i);
			solved = true;
			if(c.getType().equals(ConflitType.UBIQUITE)) {
				if(this.j1.test_ubi(c,this.j1.getChirurgiensPresents(this.j1.GetChirurgiesNoConflits())) == false) {
					if(this.j1.test_ubi(c,this.j1.getChirurgiensPresents(this.j1.GetChirurgiesConflits()))== false) {
						if(this.j1.test_ubi(c,Chirurgien.getListChir()) == false) {
							i = i + 1;	
							solved = false;
						}
					}	
				}
			}
		assertTrue(solved == true);
	}
	}

	    @Test 
		void testTest_chevauchement_decalage() throws ParseException {
		Salle salle1 = new Salle("E12");
		this.j1 = null;
		this.chirs = null;
		this.chir1 = new Chirurgie("0",dateFormat.parse("2014,1,1"), salle1, new Chirurgien("PIERRE"), LocalTime.of(8,00,00), LocalTime.of(9,00,00));
	    this.chir2 = new Chirurgie("1",dateFormat.parse("2014,1,1"), salle1, new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
		this.chir3 = new Chirurgie("2",dateFormat.parse("2014,1,1"), new Salle("E11"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
		
		this.chirs = new ArrayList<Chirurgie>();
		this.chirs.add(chir1);
		this.chirs.add(chir2);
		this.chirs.add(chir3);
		this.j1 = new Journee(dateFormat.parse("2014,1,1"),chirs);
		this.j1.generateConflits();
		int i = 0;
		boolean solved;
		solved = false;
		while(this.j1.getNbConflits() > 0 && i < this.j1.getNbConflits()){
			if (solved) {
				this.j1.generateConflits();
				i = 0;
			}
			Conflit c = this.j1.getConflits().get(i);
			solved = true;
			
		if(c.IsIntersection()) {
			if(this.j1.test_chevauchement_decalage(c) == false) {
				i = i+1;
				solved = false;
			}
		}
		assertTrue(solved == true);
		}
		
	}
	
	
	
	
	/**
	@Test
	void testTest_interf() throws ParseException {
		Salle salle1 = new Salle("E12");
		this.j1 = null;
		this.chir1 = new Chirurgie("0",dateFormat.parse("2014,1,1"), salle1, new Chirurgien("JEAN"), LocalTime.of(8,00,00), LocalTime.of(9,00,00));
	    this.chir2 = new Chirurgie("1",dateFormat.parse("2014,1,1"), salle1, new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
		this.chir3 = new Chirurgie("2",dateFormat.parse("2014,1,1"), new Salle("E11"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
		
		this.chirs = new ArrayList<Chirurgie>();
		this.chirs.add(chir1);
		this.chirs.add(chir2);
		this.chirs.add(chir3);
		this.chirs.add(new Chirurgie("2",dateFormat.parse("2014,1,2"), new Salle("E15"), new Chirurgien("JEJO"), LocalTime.of(8,30,00), LocalTime.of(9,30,00)));
		this.j1 = new Journee(dateFormat.parse("2014,1,1"),chirs);
		this.j1.generateConflits();
		int i = 0;
		boolean solved;
		solved = false;
		while(this.j1.getNbConflits() > 0 && i < this.j1.getNbConflits()){
			if (solved) {
				this.j1.generateConflits();
				i = 0;
			}
			Conflit c = this.j1.getConflits().get(i);
			solved = true;
			if(c.getType().equals(ConflitType.INTERFERENCE)) {
				if(this.j1.test_interf(c,this.j1.getSallesPresents(this.j1.GetChirurgiesNoConflits())) == false) {
					if(this.j1.test_interf(c,this.j1.getSallesPresents(this.j1.GetChirurgiesConflits())) == false) {
						if(this.j1.test_interf(c,Salle.getListSalle()) == false) {
							i = i + 1;
							solved = false;
						}
					}	
				}
			}
		
			
		assertTrue(solved == true);
		}
		
	}
*/		
	

	

}
