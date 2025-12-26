import java.util.List;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TimetableTest {

    private Timetable timetable;

    @BeforeEach
    void setUp() {
        timetable = new Timetable();
    }

    @Test
    void testInitialStateHasNoSessions() {
        TreeMap<TimeOfDay, List<TrainingSession>> sessionsOnMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertNull(sessionsOnMonday, "В понедельник не должно быть занятий, возвращается null!");

        TreeMap<TimeOfDay, List<TrainingSession>> sessionsOnTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertNull(sessionsOnTuesday, "Во вторник не должно быть занятий, возвращается null!");

        TreeMap<TimeOfDay, List<TrainingSession>> sessionsOnWednesday = timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY);
        assertNull(sessionsOnWednesday, "В среду не должно быть занятий, возвращается null!");

        TreeMap<TimeOfDay, List<TrainingSession>> sessionsOnThursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertNull(sessionsOnThursday, "В четверг не должно быть занятий, возвращается null!");

        TreeMap<TimeOfDay, List<TrainingSession>> sessionsOnFriday = timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY);
        assertNull(sessionsOnFriday, "В пятницу не должно быть занятий, возвращается null!");

        TreeMap<TimeOfDay, List<TrainingSession>> sessionsOnSaturday = timetable.getTrainingSessionsForDay(DayOfWeek.SATURDAY);
        assertNull(sessionsOnSaturday, "В субботу не должно быть занятий, возвращается null!");

        TreeMap<TimeOfDay, List<TrainingSession>> sessionsOnSunday = timetable.getTrainingSessionsForDay(DayOfWeek.SUNDAY);
        assertNull(sessionsOnSunday, "В воскресенье не должно быть занятий, возвращается null!");
    }

    @Test
    void testDuplicateSessions() {

        Group group = new Group("Йога", Age.ADULT, 60);
        Coach coach = new Coach("Иванов", "Сергей", "Петрович");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(singleTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY).size(),
                "Дублирование занятий недопустимо");
    }

    @Test
    void testEdgeCaseTimes() {
        Group group = new Group("Медитация", Age.ADULT, 60);
        Coach coach = new Coach("Петрова", "Анна", "Владимировна");

        TrainingSession earliestSession =
                new TrainingSession(group, coach, DayOfWeek.WEDNESDAY, new TimeOfDay(0, 0));
        TrainingSession latestSession =
                new TrainingSession(group, coach, DayOfWeek.WEDNESDAY, new TimeOfDay(23, 59));

        timetable.addNewTrainingSession(earliestSession);
        timetable.addNewTrainingSession(latestSession);

        assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY).size(),
                "Граничные времена должны быть добавлены в таблицу.");
    }

    @Test
    void testGetTrainingSessionsForDaySingleSession() {

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        TreeMap<TimeOfDay, List<TrainingSession>> sessionsOnMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        assertEquals(1, sessionsOnMonday.size(), "Должно вернуться одно занятие в понедельник.");

        TreeMap<TimeOfDay, List<TrainingSession>> sessionsOnTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        assertNull(sessionsOnTuesday, "Во вторник не должно быть занятий, возвращается null!");


        //Проверить, что за понедельник вернулось одно занятие
        //Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        TreeMap<TimeOfDay, List<TrainingSession>> sessionsOnMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        assertEquals(1, sessionsOnMonday.size(), "Должно вернуться одно занятие в понедельник.");

        TreeMap<TimeOfDay, List<TrainingSession>> sessionsOnThursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        assertEquals(2, sessionsOnThursday.size(), "Должно вернуться два занятие в четверг.");

        boolean correctTrainingOrder =
                sessionsOnThursday.firstEntry().getKey().equals(new TimeOfDay(13, 0)) &&
                        sessionsOnThursday.lastEntry().getKey().equals(new TimeOfDay(20, 0));

        assertTrue(correctTrainingOrder);

        TreeMap<TimeOfDay, List<TrainingSession>> sessionsOnTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        assertNull(sessionsOnTuesday, "Во вторник не должно быть занятий, возвращается null!");
        // Проверить, что за понедельник вернулось одно занятие
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        TreeMap<TimeOfDay, List<TrainingSession>> sessionsOnMonday =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        assertEquals(1, sessionsOnMonday.size(), "Должно вернуться одно занятие в понедельник.");

        boolean correctTrainingOrder =
                sessionsOnMonday.firstEntry().getKey().equals(new TimeOfDay(13, 0));

        assertTrue(correctTrainingOrder);

        List<TrainingSession> sessionsOnMondayAt14 =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        assertNull(sessionsOnMondayAt14, "В понедельник 14:00 не должно быть занятий, возвращается null!");
        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        //Проверить, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    void checkInitialCoachesSessionsAreZero() {

        List<CounterOfTrainings> counterOfTrainings = timetable.getCountByCoaches();
        assertTrue(counterOfTrainings.isEmpty(),
                "Проверка начального списка тренировок, должен быть пустым.");
    }

    @Test
    void addedSessionsMatchExpectedCount() {

        Group childrenGroup = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach childrenCoach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession childTrainingSession = new TrainingSession(childrenGroup, childrenCoach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Group adultsGroup = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Coach adultsCoach = new Coach("Петров", "Александр", "Евгеньевич");
        TrainingSession adultTrainingSession = new TrainingSession(adultsGroup, adultsCoach,
                DayOfWeek.SATURDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(childTrainingSession);
        timetable.addNewTrainingSession(adultTrainingSession);

        List<CounterOfTrainings> counterOfTrainings = timetable.getCountByCoaches();

        assertEquals(2,  counterOfTrainings.size(), "Должен вернуться список с двумя тренерами.");
    }

    @Test
    void testCoachesSortedDescendingByTrainingCount() {

        Group childrenGroup = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach childrenCoach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession childTrainingSessionOnMonday = new TrainingSession(childrenGroup, childrenCoach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession childTrainingSessionOnTuesday = new TrainingSession(childrenGroup, childrenCoach,
                DayOfWeek.TUESDAY, new TimeOfDay(15, 0));
        TrainingSession childTrainingSessionOnWednesday = new TrainingSession(childrenGroup, childrenCoach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(childTrainingSessionOnMonday);
        timetable.addNewTrainingSession(childTrainingSessionOnTuesday);
        timetable.addNewTrainingSession(childTrainingSessionOnWednesday);

        Group adultsGroup = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Coach adultsCoach = new Coach("Петров", "Александр", "Евгеньевич");
        TrainingSession adultTrainingSessionOnFriday = new TrainingSession(adultsGroup, adultsCoach,
                DayOfWeek.FRIDAY, new TimeOfDay(9, 0));
        TrainingSession adultTrainingSessionOnSaturday = new TrainingSession(adultsGroup, adultsCoach,
                DayOfWeek.SATURDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(adultTrainingSessionOnFriday);
        timetable.addNewTrainingSession(adultTrainingSessionOnSaturday);

        Group teensGroup = new Group("Футбол для подростков", Age.ADULT, 90);
        Coach teensCoach = new Coach("Смирнов", "Алексей", "Викторович");
        TrainingSession teenTrainingSessionOnSunday = new TrainingSession(teensGroup, teensCoach,
                DayOfWeek.SUNDAY, new TimeOfDay(11, 0));

        timetable.addNewTrainingSession(teenTrainingSessionOnSunday);

        List<CounterOfTrainings> counterOfTrainings = timetable.getCountByCoaches();

        assertEquals(childrenCoach, counterOfTrainings.get(0).getCoach(), "Первым должен быть тренер 'Васильев'.");
        assertEquals(3, counterOfTrainings.get(0).getNumberOfLessons(),
                "Должно вернуться 3 тренировки для тренера 'Васильев'.");

        assertEquals(adultsCoach, counterOfTrainings.get(1).getCoach(), "Вторым должен быть тренер 'Петров'.");
        assertEquals(2, counterOfTrainings.get(1).getNumberOfLessons(),
                "Должно вернуться 2 тренировки для тренера 'Петров'.");

        assertEquals(teensCoach, counterOfTrainings.get(2).getCoach(), "Третьим должен быть тренер 'Смирнов'.");
        assertEquals(1, counterOfTrainings.get(2).getNumberOfLessons(),
                "Должно вернуться 1 тренировка для тренера 'Смирнов'.");
    }
}
