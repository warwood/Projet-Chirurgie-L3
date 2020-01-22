package test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projetChirurgie.Chirurgie;
import projetChirurgie.Chirurgien;
import projetChirurgie.Conflit;
import projetChirurgie.ConflitType;
import projetChirurgie.Salle;

class ChirurgieTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testTranslateToLeft() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd,mm,yyyy");
	    Chirurgie chir2 = new Chirurgie("1",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
	    chir2.translateToLeft(30);
	    assertTrue(chir2.getH_deb()==LocalTime.of(8,00,00));
	    assertTrue(chir2.getH_fin()==LocalTime.of(9,00,00));
	}

	@Test
	void testTranslateToRight() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd,mm,yyyy");
		Chirurgie chir1 = new Chirurgie("0",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("JEAN"), LocalTime.of(8,00,00), LocalTime.of(9,00,00));
	    Chirurgie chir2 = new Chirurgie("1",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
	    chir2.translateToRight(30);
	    assertTrue(chir2.getH_deb()==LocalTime.of(9,00,00));
	    assertTrue(chir2.getH_fin()==LocalTime.of(10,00,00));
	}

	@Test
	void testShare_horaire() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("dd,mm,yyyy");
		Chirurgie chir1 = new Chirurgie("0",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("JEAN"), LocalTime.of(8,00,00), LocalTime.of(9,00,00));
	    Chirurgie chir2 = new Chirurgie("1",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("PIERRE"), LocalTime.of(8,00,00), LocalTime.of(9,00,00));
	    assertTrue(chir2.share_horaire(chir1));
	}

	@Test
	void testShare_salle() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd,mm,yyyy");
		Chirurgie chir1 = new Chirurgie("0",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("JEAN"), LocalTime.of(8,00,00), LocalTime.of(9,00,00));
	    Chirurgie chir2 = new Chirurgie("1",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("PIERRE"), LocalTime.of(9,00,00), LocalTime.of(10,00,00));
	    assertTrue(chir2.share_salle(chir1));
	}

	@Test
	void testShare_chirurgien() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd,mm,yyyy");
		Chirurgie chir1 = new Chirurgie("0",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("JEAN"), LocalTime.of(8,00,00), LocalTime.of(9,00,00));
	    Chirurgie chir2 = new Chirurgie("1",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("JEAN"), LocalTime.of(9,00,00), LocalTime.of(10,00,00));
	    assertTrue(chir2.share_chirurgien(chir1));
	}

}
