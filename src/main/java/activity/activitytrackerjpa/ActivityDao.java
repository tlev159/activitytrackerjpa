package activity.activitytrackerjpa;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@AllArgsConstructor
public class ActivityDao {

    private EntityManagerFactory factory;

    public Activity saveActivity(Activity activity) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(activity);
        em.getTransaction().commit();
        em.close();
        return activity;
    }

    public List<Activity> listActivities() {
        EntityManager em = factory.createEntityManager();
        List<Activity> loadedActivityList = em.createQuery("select a from Activity a order by a.startTime", Activity.class)
                .getResultList();
        em.close();
        return loadedActivityList;
    }

    public Activity findActivityById(long id) {
        EntityManager em = factory.createEntityManager();
        Activity activity = em.find(Activity.class, id);
        em.close();
        return activity;
    }

    public void deleteActivityById(long id) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Activity activity = em.find(Activity.class, id);
        em.remove(activity);
        em.getTransaction().commit();
        em.close();
    }

    public Activity updateActivityDescriptionById(long id, String description) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Activity activity = em.find(Activity.class, id);
        activity.setDescription(description);
        em.getTransaction().commit();
        em.close();
        return activity;
    }

    public Activity findActivityByIdWithTrackPoints(long id) {
        EntityManager em = factory.createEntityManager();
        Activity activity = em.createQuery("select a from Activity a join fetch a.trackPointList order by a.startTime", Activity.class)
                .getSingleResult();
        em.close();
        return activity;
    }

    public void addTrackPointToActivity(long id, TrackPoint trackPoint) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Activity activity = em.getReference(Activity.class, id);
        trackPoint.setActivity(activity);
        em.persist(trackPoint);
        em.getTransaction().commit();
        em.close();

    }

    public Activity findActivityWithTrackPoints(long id) {
        EntityManager em = factory.createEntityManager();
        Activity activity = em.createQuery("select a from Activity a join fetch a.trackPointList where a.id = :id", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        em.close();
        return activity;
    }

}
