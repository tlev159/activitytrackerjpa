package activity.activitytrackerjpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActivityServiceTest {

    private ActivityDao activityDao;

    @BeforeEach
    public void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        activityDao = new ActivityDao(factory);
    }

    @Test
    public void testSaveThenFind() {
        Activity activity = new Activity(LocalDate.of(2017, 02, 13), "Streetball on the place", ActivityType.BASKETBALL);

        activityDao.saveActivity(activity);

        Activity loadedActivity = activityDao.findActivityById(activity.getId());
        assertEquals(1L, loadedActivity.getId());
    }

    @Test
    public void saveThenListAll() {
        activityDao.saveActivity(new Activity(LocalDate.of(2019, 04, 21), "Running Marathon in Munich", ActivityType.RUNNING));
        activityDao.saveActivity(new Activity(LocalDate.of(2018, 03, 17), "Basketball in the hall", ActivityType.BASKETBALL));
        activityDao.saveActivity(new Activity(LocalDate.of(2017, 02, 13), "Streetball on the place", ActivityType.BASKETBALL));

        List<Activity> loadedActivityList = activityDao.listActivities();

        assertThat(loadedActivityList)
                .hasSize(3)
                .extracting(Activity::getDescription)
                .contains("Streetball on the place");
    }

    @Test
    public void changeDescription() {
        Activity activity = new Activity(LocalDate.of(2017, 02, 13), "Streetball on the place", ActivityType.BASKETBALL);

        activityDao.saveActivity(activity);

        long id = activity.getId();

        activityDao.updateActivityDescriptionById(id, "Running to anywhere");

        Activity loadedActivity = activityDao.findActivityById(id);

        assertEquals("Running to anywhere", loadedActivity.getDescription());

    }

    @Test
    public void saveThenDeleteThenList() {
        activityDao.saveActivity(new Activity(LocalDate.of(2019, 04, 21), "Running Marathon in Munich", ActivityType.RUNNING));
        activityDao.saveActivity(new Activity(LocalDate.of(2018, 03, 17), "Basketball in the hall", ActivityType.BASKETBALL));
        activityDao.saveActivity(new Activity(LocalDate.of(2017, 02, 13), "Streetball on the place", ActivityType.BASKETBALL));

        activityDao.deleteActivityById(2L);
        List<Activity> loadedActivityList = activityDao.listActivities();

        assertThat(loadedActivityList)
                .hasSize(2)
                .extracting(Activity::getDescription)
                .contains("Streetball on the place");

    }

    @Test
    public void changeDescriptionAndProofUpdateDate() {
        Activity activity = new Activity(LocalDate.of(2017, 02, 13), "Streetball on the place", ActivityType.BASKETBALL);

        activityDao.saveActivity(activity);

        long id = activity.getId();

        activityDao.updateActivityDescriptionById(id, "Running to anywhere");

        Activity loadedActivity = activityDao.findActivityById(id);

        assertTrue(loadedActivity.getCreatedAt().isBefore(loadedActivity.getUpdatedAt()));

    }

    @Test
    public void saveActivityThenTrackPointsThenLoadWithTrackPointList() {
        Activity activity = new Activity(LocalDate.of(2017, 02, 13), "Streetball on the place", ActivityType.BASKETBALL);

        TrackPoint trackPoint1 = new TrackPoint(LocalDate.of(2020, 01, 02), 41.11, 35.35);
        TrackPoint trackPoint2 = new TrackPoint(LocalDate.of(2020, 05, 11), 42.22, 36.36);
        TrackPoint trackPoint3 = new TrackPoint(LocalDate.of(2020, 11, 22), 43.33, 38.38);

        activityDao.saveActivity(activity);
        long id = activity.getId();
        activityDao.addTrackPointToActivity(id, trackPoint1);

        Activity loadedActivity = activityDao.findActivityWithTrackPoints(id);

        assertEquals(1, loadedActivity.getTrackPointList().size());

    }

    @Test
    public void testAddTrackPoint() {
        Activity activity = new Activity(LocalDate.of(2017, 02, 13), "Streetball on the place", ActivityType.BASKETBALL);

        TrackPoint trackPoint1 = new TrackPoint(LocalDate.of(2020, 01, 02), 41.11, 35.35);
        TrackPoint trackPoint2 = new TrackPoint(LocalDate.of(2020, 05, 11), 42.22, 36.36);
        TrackPoint trackPoint3 = new TrackPoint(LocalDate.of(2020, 11, 22), 43.33, 38.38);

        activityDao.saveActivity(activity);

        long id = activity.getId();
        activityDao.addTrackPointToActivity(id, trackPoint3);
        activityDao.addTrackPointToActivity(id, trackPoint2);

        Activity loadedActivity = activityDao.findActivityWithTrackPoints(id);

        assertEquals(2, loadedActivity.getTrackPointList().size());

    }
}
