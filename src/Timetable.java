import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, Group>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        TreeMap<TimeOfDay, Group> timetableByTime = timetable.get(trainingSession.getDayOfWeek());

        if (timetableByTime == null) {
            timetableByTime = new TreeMap<>();
            timetable.put(trainingSession.getDayOfWeek(), timetableByTime);
        }

        timetableByTime.put(trainingSession.getTimeOfDay(), trainingSession.getGroup());
    }

    public /* непонятно, что возвращать */ getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

    public /* непонятно, что возвращать */ getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

}
