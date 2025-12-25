public class TrainingSession implements Comparable<TrainingSession> {

    //группа
    private Group group;
    //тренер
    private Coach coach;
    //день недели
    private DayOfWeek dayOfWeek;
    //время начала занятия
    private TimeOfDay timeOfDay;

    public TrainingSession(Group group, Coach coach, DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        this.group = group;
        this.coach = coach;
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
    }

    public Group getGroup() {
        return group;
    }

    public Coach getCoach() {
        return coach;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    @Override
    public int compareTo(TrainingSession o) {
        // Сначала сравниваем дни недели
        int daysComparison = this.dayOfWeek.compareTo(o.dayOfWeek);

        if (daysComparison == 0) {
            return this.timeOfDay.compareTo(o.timeOfDay);
        }

        return daysComparison;
    }

    @Override
    public String toString() {
        return "TrainingSession{" +
                "group=" + group +
                ", coach=" + coach +
                ", dayOfWeek=" + dayOfWeek +
                ", timeOfDay=" + timeOfDay +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainingSession trainingSession = (TrainingSession) o;

        return timeOfDay.equals(trainingSession.timeOfDay)
                && dayOfWeek.equals(trainingSession.dayOfWeek);
    }

    @Override
    public int hashCode() {
        return timeOfDay.hashCode();
    }
}
