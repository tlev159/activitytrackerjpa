package activity.activitytrackerjpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activities")
public class Activity {

    @TableGenerator(name = "Act_Gen",
        table = "act_id_gen",
        pkColumnName = "id_gen",
        pkColumnValue = "id_val")
    @Id
    @GeneratedValue(generator = "Act_Gen")
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalDate startTime;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(name = "activity_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ActivityType type;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "activity", orphanRemoval = true)
    private List<TrackPoint> trackPointList;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Activity(LocalDate startTime, String description, ActivityType type) {
        this.startTime = startTime;
        this.description = description;
        this.type = type;
    }

    public void addTrackPoint(TrackPoint trackPoint) {
        if (trackPointList == null) {
            trackPointList = new ArrayList<>();
        }
        trackPointList.add(trackPoint);
        trackPoint.setActivity(this);
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Activity{" +
                "startTime=" + startTime +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", trackPointList=" + trackPointList +
                '}';
    }
}
