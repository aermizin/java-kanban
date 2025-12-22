import java.util.Objects;

public class TimeOfDay implements Comparable<TimeOfDay> {

    //часы (от 0 до 23)
    private int hours;
    //минуты (от 0 до 59)
    private int minutes;

    public TimeOfDay(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    @Override
    public String toString() {
        return "TimeOfDay{" +
                "hours=" + hours +
                ", minutes=" + minutes +
                '}';
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public int compareTo(TimeOfDay other) {
        // Сначала сравниваем часы
        int hourComparison = Integer.compare(this.hours, other.hours);

        // Если часы равны, сравниваем минуты
        if (hourComparison == 0) {
            return Integer.compare(this.minutes, other.minutes);
        }

        return hourComparison;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TimeOfDay)) return false;
        TimeOfDay timeOfDay = (TimeOfDay) obj;

        return hours == timeOfDay.hours &&
                minutes == timeOfDay.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes);
    }
}
