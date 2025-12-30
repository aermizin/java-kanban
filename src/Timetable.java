import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {

        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        timetable.putIfAbsent(dayOfWeek, new TreeMap<>());

        timetable.get(dayOfWeek).putIfAbsent(timeOfDay, new ArrayList<>());
        timetable.get(dayOfWeek).get(timeOfDay).add(trainingSession);

    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {

        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForDay =
                timetable.getOrDefault(dayOfWeek, new TreeMap<>());

        List<TrainingSession> resultSessionsForDay = new ArrayList<>();

        for (List<TrainingSession> sessionList : trainingSessionsForDay.values()) {
            resultSessionsForDay.addAll(sessionList);
        }

        return resultSessionsForDay;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {

        TreeMap<TimeOfDay, List<TrainingSession>> scheduleForDay =
                timetable.getOrDefault(dayOfWeek, new TreeMap<>());


        List<TrainingSession> resultSessionsForDayAndTime =
                scheduleForDay.getOrDefault(timeOfDay, new ArrayList<>());

        return resultSessionsForDayAndTime;
    }

    public TreeSet<CounterOfTrainings> getCountByCoaches() {

        TreeSet<CounterOfTrainings> counterOfTrainings = new TreeSet<>();
        Map<Coach, Integer> coachWeeklyTrainingsCount = new HashMap<>();

        for (Map.Entry<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> entry : timetable.entrySet()) {

            TreeMap<TimeOfDay, List<TrainingSession>> dailySessions = entry.getValue();

            for (List<TrainingSession> trainingSessions : dailySessions.values()) {
                for (TrainingSession trainingSession : trainingSessions) {
                    Coach coach = trainingSession.getCoach();
                    coachWeeklyTrainingsCount.merge(coach, 1, Integer::sum);
                }
            }
        }

        for (Map.Entry<Coach, Integer> entry : coachWeeklyTrainingsCount.entrySet()) {
            counterOfTrainings.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        return counterOfTrainings;
    }
}