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

class ConflitTest {

	@Test
	void testIsIntersection() throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat("dd,mm,yyyy");
		Chirurgie chir1 = new Chirurgie("0",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("JEAN"), LocalTime.of(8,00,00), LocalTime.of(9,00,00));
	    Chirurgie chir2 = new Chirurgie("1",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
	    Conflit conflit = new Conflit(chir1, chir2,ConflitType.CHEVAUCHEMENT);
	    assertTrue(conflit.IsIntersection());
	}

	@Test
	void testIntersectionTime() throws ParseException {
		
		DateFormat dateFormat = new SimpleDateFormat("dd,mm,yyyy");
		Chirurgie chir1 = new Chirurgie("0",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("JEAN"), LocalTime.of(8,00,00), LocalTime.of(9,00,00));
	    Chirurgie chir2 = new Chirurgie("1",dateFormat.parse("2014,1,1"), new Salle("U12"), new Chirurgien("PIERRE"), LocalTime.of(8,30,00), LocalTime.of(9,30,00));
	    Conflit conflit = new Conflit(chir1, chir2,ConflitType.CHEVAUCHEMENT);
	    assertTrue(conflit.intersectionTime()==30);
	}

}
