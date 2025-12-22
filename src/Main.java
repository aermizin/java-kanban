public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");


        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Group group1 = new Group("Дрочильня", Age.ADULT, 90);
        Group group12 = new Group("Дрочильня", Age.ADULT, 90);
        Coach coach1 = new Coach("Бодрова", "Екатерина", "Сергеевна");
        TrainingSession singleTrainingSession1 = new TrainingSession(group1, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 30));
        TrainingSession singleTrainingSession12 = new TrainingSession(group12, coach1,
                DayOfWeek.FRIDAY, new TimeOfDay(19, 30));

        Group group2 = new Group("Хай Хилс", Age.ADULT, 60);
        Group group3 = new Group("Хай АЫФ", Age.ADULT, 60);
        Group group4 = new Group("Хай АЫФ", Age.ADULT, 60);
        Coach coach2 = new Coach("Шарова", "Елизавета", "Васильевна");
        TrainingSession singleTrainingSession2 = new TrainingSession(group2, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0));
        TrainingSession singleTrainingSession3 = new TrainingSession(group3, coach2,
                DayOfWeek.SUNDAY, new TimeOfDay(15, 0));
        TrainingSession singleTrainingSession4 = new TrainingSession(group4, coach2,
                DayOfWeek.SATURDAY, new TimeOfDay(16, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(singleTrainingSession1);
        timetable.addNewTrainingSession(singleTrainingSession12);
        timetable.addNewTrainingSession(singleTrainingSession2);
        timetable.addNewTrainingSession(singleTrainingSession3);
        timetable.addNewTrainingSession(singleTrainingSession4);

        System.out.println(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.SATURDAY,new TimeOfDay(16, 0)));

        System.out.println(timetable.getCountByCoaches());

    }
}
