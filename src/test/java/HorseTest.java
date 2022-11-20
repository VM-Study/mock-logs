import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HorseTest {

    @Test
    void testHorse_ShouldThrowException_WhenNameInputIsNull() {
        var expected = IllegalArgumentException.class;
        assertThrows(expected, () -> new Horse(null, 1, 1));
    }

    @Test
    void testHorse_ShouldThrowExceptionWithText_WhenNameInputIsNull() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 1, 1));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t"})
    void testHorse_ShouldThrowException_WhenNameInputIsSpaceTabSymbols(String name) {
        var expected = IllegalArgumentException.class;
        assertThrows(expected, () -> new Horse(name, 1, 1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t"})
    void testHorse_ShouldThrowExceptionWithText_WhenNameInputIsSpaceTabSymbols(String name) {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(name, 1, 1));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void testHorse_ShouldThrowException_WhenSpeedInputIsNegative() {
        var expected = IllegalArgumentException.class;
        assertThrows(expected, () -> new Horse("test", -100, 1));
    }

    @Test
    void testHorse_ShouldThrowExceptionWithText_WhenSpeedInputIsNegative() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse("test", -100, 1));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void testHorse_ShouldThrowExceptionWithText_WhenDistanceInputIsNegative() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse("test", 1, -100));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void testGetName_ShouldGetName_WhenNameInput() {
        Horse horse = new Horse("test", 1, 1);
        String expected = "test";
        String actual = horse.getName();
        assertEquals(expected, actual);
    }

    @Test
    void testGetSpeed_ShouldGetSpeed_WhenSpeedInput() {
        Horse horse = new Horse("test", 10, 1);
        double expected = 10;
        double actual = horse.getSpeed();
        assertEquals(expected, actual);
    }

    @Test
    void testGetDistance_ShouldGetDistance_WhenDistanceInput() {
        Horse horse = new Horse("test", 1, 10);
        double expected = 10;
        double actual = horse.getDistance();
        assertEquals(expected, actual);
    }

    @Test
    void testMove_ShouldRunStaticGetRandom_WhenInput() {
        try (MockedStatic<Horse> mockStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("test", 10, 10);
            horse.move();
            mockStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "20, 10, 0.5",
            "30, 20, 0.6",
            "40, 50, 0.7",
            "50, 50, 0.8",
    })
    void testMove_ShouldGetStaticGetRandom_WhenInput(int speed, int distance, double random) {
        try (MockedStatic<Horse> mockStatic = Mockito.mockStatic(Horse.class)) {
            mockStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            Horse horse = new Horse("test", speed, distance);
            horse.move();
            double expected = distance + (speed * random);
            double actual = horse.getDistance();
            assertEquals(expected, actual);
        }
    }
}