package verarbeitung;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;
/**
 * @author Jakob Wendt, s0559720
 */
public class StudentTest {
    private Student student;
    @Before
    public void assignStudent() {
        student = new Student("Erika", "Mustermann", "Heidestrasse 17, 51147 Koeln", LocalDate.of(1964,8,12),"HTW Berlin", "Angewandte Informatik");
    }
    @Test
    public void getUniversitaet() {
        assertEquals("HTW Berlin", student.getUniversitaet());
    }

    @Test
    public void setUniversitaet() {
        student.setUniversitaet("HU Berlin");
        assertEquals("HU Berlin", student.getUniversitaet());
    }

    @Test
    public void getStudienfach() {
        assertEquals("Angewandte Informatik", student.getStudienfach());
    }

    @Test
    public void setStudienfach() {
        student.setStudienfach("Internationale Medieninformatik");
        assertEquals("Internationale Medieninformatik", student.getStudienfach());

    }

    @Test
    public void studienbescheinigungsTest() {

        Student.Semester sose2017 = new Student.Semester(Student.Semester.Zeitraum.SOMMERSEMESTER, "2017");
        assertFalse(student.hatStudienbescheinigung(sose2017));
        assertFalse(student.hatStudienbescheinigung("2017", Student.Semester.Zeitraum.SOMMERSEMESTER));

        student.studienbescheinigungEinreichen(sose2017);
        assertTrue(student.hatStudienbescheinigung(sose2017));

        student.studienbescheinigungLoeschen(sose2017);
        assertFalse(student.hatStudienbescheinigung(sose2017));



    }


}