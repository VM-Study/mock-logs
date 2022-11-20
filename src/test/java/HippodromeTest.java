import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HippodromeTest {
    @Test
    void testHippodrome_ShouldThrowException_WhenInputIsNull() {
        var expected = IllegalArgumentException.class;
        assertThrows(expected, () -> new Hippodrome(null));
    }

    @Test
    void testHippodrome_ShouldThrowExceptionWithText_WhenInputIsNull() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void testHippodrome_ShouldThrowException_WhenInputEmptyList() {
        var expected = IllegalArgumentException.class;
        assertThrows(expected, () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    void testHippodrome_ShouldThrowExceptionWithText_WhenEmptyList() {
        List<Horse> emptyList = new ArrayList<>();
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(emptyList));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void testGetHorses_ShouldGetHorseList_WhenInputIsList() {
        List<Horse> expected = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            expected.add(new Horse("Horse_" + i, i, i));
        }
        Hippodrome hippodrome = new Hippodrome(expected);
        List<Horse> actual = hippodrome.getHorses();
        assertEquals(expected, actual);
    }

    @Test
    @ExtendWith(MockitoExtension.class)
    void testMove_ShouldRunAllHorses_WhenInputList() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            horseList.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horseList);
        hippodrome.move();
        for (Horse horse : horseList) {
            Mockito.verify(horse, Mockito.times(1)).move();
        }
    }


    @Test
    void testGetWinner_ShouldGetTopDistanceHorse_WhenInputIsList() {
        List<Horse> horseList = new ArrayList<>();
        horseList.add(new Horse("Horse_1", 10, 10));
        horseList.add(new Horse("Horse_2", 10, 30));
        horseList.add(new Horse("Horse_3", 10, 20));
        Hippodrome hippodrome = new Hippodrome(horseList);
        String expected = "Horse_2";
        Horse actual = hippodrome.getWinner();
        assertEquals(expected, actual.getName());
    }
}