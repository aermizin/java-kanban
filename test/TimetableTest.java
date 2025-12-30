import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

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
    void testAttemptToAddDuplicateSessions() {

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
    void  testAddBoundaryTrainingSessionsByTimeToTimetable() {

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
    void  testAbilityToScheduleDifferentTrainingsAtTheSameTime() {

        Group childGroup = new Group("Акробатика для детей", Age.CHILD, 60);
        Group adultsGroup = new Group("Акробатика для взрослых", Age.ADULT, 90);

        Coach childrenCoach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach adultsCoach = new Coach("Петрова", "Анна", "Владимировна");

        TrainingSession  mondayChildsTrainingSession = new TrainingSession(childGroup, childrenCoach,
                DayOfWeek.MONDAY, new TimeOfDay(12, 0));
        TrainingSession  mondayAdultsTrainingSession = new TrainingSession(adultsGroup, adultsCoach,
                DayOfWeek.MONDAY, new TimeOfDay(12, 0));

        timetable.addNewTrainingSession(mondayChildsTrainingSession);
        timetable.addNewTrainingSession(mondayAdultsTrainingSession);

        List<TrainingSession> sessionsOnMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        assertEquals(2, sessionsOnMonday.size(), "Должно вернуться два занятия в понедельник 12:00.");
    }

    @Test
    void testGetTrainingSessionsForDaySingleSession() {

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> sessionsOnMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        assertEquals(1, sessionsOnMonday.size(), "Должно вернуться одно занятие в понедельник.");

        List<TrainingSession> sessionsOnTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        assertTrue(sessionsOnTuesday.isEmpty(), "Во вторник не должно быть занятий, список должен быть пуст.");


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

        List<TrainingSession> sessionsOnMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        assertEquals(1, sessionsOnMonday.size(), "Должно вернуться одно занятие в понедельник.");

        List<TrainingSession> sessionsOnThursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        assertEquals(2, sessionsOnThursday.size(), "Должно вернуться два занятие в четверг.");

        boolean correctTrainingOrder =
                sessionsOnThursday.get(0).getTimeOfDay().equals(new TimeOfDay(13, 0)) &&
                        sessionsOnThursday.get(1).getTimeOfDay().equals(new TimeOfDay(20, 0));

        assertTrue(correctTrainingOrder, "Первым в списке должно быть занятие в 13:00, вторым в 20:00.");

        List<TrainingSession> sessionsOnTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        assertTrue(sessionsOnTuesday.isEmpty(), "Во вторник не должно быть занятий, список должен быть пуст.");

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

        List<TrainingSession> sessionsOnMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        assertEquals(1, sessionsOnMonday.size(), "Должно вернуться одно занятие в понедельник.");

        boolean correctTrainingOrder =
                sessionsOnMonday.get(0).getTimeOfDay().equals(new TimeOfDay(13, 0));

        assertTrue(correctTrainingOrder);

        List<TrainingSession> sessionsOnMondayAt14 =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        assertTrue(sessionsOnMondayAt14.isEmpty(),
                "В понедельник 14:00 не должно быть занятий, список должен быть пуст.");

        /* Попытался применить assertEmpty(), но идея выдает ошибку и говорит, что у меня 4 версия тестов, хотя на самом
        деле junit-jupiter-engine-5.9.2 и junit-jupiter-api-5.9.2 в папке lib !!! */

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        //Проверить, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    void checkInitialCoachesSessionsAreZero() {

        TreeSet<CounterOfTrainings> counterOfTrainings = timetable.getCountByCoaches();
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

        TreeSet <CounterOfTrainings> counterOfTrainings = timetable.getCountByCoaches();

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

        TreeSet <CounterOfTrainings> counterOfTrainings = timetable.getCountByCoaches();

        assertEquals(childrenCoach, counterOfTrainings.getLast().getCoach(), "Первым должен быть тренер 'Васильев'.");
        assertEquals(3, counterOfTrainings.getFirst().getNumberOfLessons(),
                "Должно вернуться 3 тренировки для тренера 'Васильев'.");

        assertEquals(teensCoach, counterOfTrainings.getLast().getCoach(), "Третьим должен быть тренер 'Смирнов'.");
        assertEquals(1, counterOfTrainings.getLast().getNumberOfLessons(),
                "Должно вернуться 1 тренировка для тренера 'Смирнов'.");
    }
}
