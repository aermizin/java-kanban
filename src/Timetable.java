import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession (TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        TreeMap<TimeOfDay, List<TrainingSession>> timetableByDay = timetable.get(trainingSession.getDayOfWeek());

        if (timetableByDay == null) {
            timetableByDay = new TreeMap<>();
            timetable.put(trainingSession.getDayOfWeek(), timetableByDay);
        }

        List<TrainingSession> sessionsAtTime = timetableByDay.get(trainingSession.getTimeOfDay());

        if (sessionsAtTime == null) {
            sessionsAtTime = new ArrayList<>();
            timetableByDay.put(trainingSession.getTimeOfDay(), sessionsAtTime);
        }

        sessionsAtTime.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {

        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForDay = timetable.get(dayOfWeek);

        if (trainingSessionsForDay == null) {
            System.out.println("В этот день " + dayOfWeek + " отсутствуют тренировки.");
            return null;
        }
        return trainingSessionsForDay;
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime (DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {

        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForDay = timetable.get(dayOfWeek);

        if (trainingSessionsForDay == null) {
            System.out.println("В этот день " + dayOfWeek + " отсутствуют тренировки.");
            return null;
        }

        List<TrainingSession> groupsAtTime = trainingSessionsForDay.get(timeOfDay);

        if (groupsAtTime == null) {
            System.out.println("В этот день " + dayOfWeek + " в это время " + timeOfDay + " отсутствуют тренировки.");
            return null;
        }

        return groupsAtTime;
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

    public List<CounterOfTrainings> getCountByCoaches() {

        Map<Coach,Integer> coachWeeklyTrainingsCount = new HashMap<>();

        for (Map.Entry<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> entry : timetable.entrySet()) {

            TreeMap<TimeOfDay, List<TrainingSession>> dailySessions = entry.getValue();

            for (List<TrainingSession> trainingSessions : dailySessions.values()) {
                for (TrainingSession trainingSession : trainingSessions) {
                    Coach coach = trainingSession.getCoach();
                    coachWeeklyTrainingsCount.merge(coach, 1, Integer::sum);
                }
            }
        }

        List<CounterOfTrainings> counterOfTrainings = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry : coachWeeklyTrainingsCount.entrySet()) {
            counterOfTrainings.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        Collections.sort(counterOfTrainings);

        return counterOfTrainings;
    }
}
