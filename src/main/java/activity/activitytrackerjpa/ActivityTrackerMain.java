package activity.activitytrackerjpa;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class ActivityTrackerMain {

    public static void main(String[] args) {

        Activity activity1 = new Activity(LocalDate.of(2021, 06, 25), "Biking on the road", ActivityType.BIKING);
        Activity activity2 = new Activity(LocalDate.of(2022, 05, 23), "Hiking anywhere", ActivityType.HIKING);
        Activity activity3 = new Activity(LocalDate.of(2019, 04, 21), "Running Marathon in Munich", ActivityType.RUNNING);
        Activity activity4 = new Activity(LocalDate.of(2018, 03, 17), "Basketball in the hall", ActivityType.BASKETBALL);
        Activity activity5 = new Activity(LocalDate.of(2017, 02, 13), "Streetball on the place", ActivityType.BASKETBALL);
        Activity activity6 = new Activity(LocalDate.of(2016, 01, 04), "Running on the road", ActivityType.RUNNING);

//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
//        ActivityDao activityDao = new ActivityDao(factory);
//        activityDao.saveActivity(activity1);
//        System.out.println(activity1);
//        activityDao.saveActivity(activity2);
//        System.out.println(activity2);
//        activityDao.saveActivity(activity3);
//        System.out.println(activity3);
//        activityDao.saveActivity(activity4);
//        System.out.println(activity4);
//        activityDao.saveActivity(activity5);
//        System.out.println(activity5);
//        activityDao.saveActivity(activity6);
//        System.out.println(activity6);
//
//        Activity loadedActivity = activityDao.findActivityById(2L);
//        System.out.println(loadedActivity);
//
//        activityDao.deleteActivityById(4L);
//
//        List<Activity> activityList = activityDao.listActivities();
//        System.out.println(activityList);

    }






}
