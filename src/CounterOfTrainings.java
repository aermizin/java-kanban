import java.util.Objects;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {

    private Coach coach;
    private int numberOfLessons;

    public CounterOfTrainings(Coach coach, int numberOfLessons) {
        this.coach = coach;
        this.numberOfLessons = numberOfLessons;
    }

    @Override
    public String toString() {
        return "CounterOfTrainings{" +
                "coach=" + coach +
                ", numberOfLessons=" + numberOfLessons +
                '}';
    }

    public Coach getCoach() {
        return coach;
    }

    public int getNumberOfLessons() {
        return numberOfLessons;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CounterOfTrainings that = (CounterOfTrainings) o;
        return numberOfLessons == that.numberOfLessons && Objects.equals(coach, that.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coach, numberOfLessons);
    }

    @ Override
    public int compareTo(CounterOfTrainings o) {
        int lessonComparison = Integer.compare(o.numberOfLessons, this.numberOfLessons);

        if (lessonComparison == 0) {
            return coach.compareTo(o.coach); // дополнительная сортировка по тренерам
        }

        return lessonComparison;
    }
}
