package activity.activitytrackerjpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "track_points")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrackPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @OrderBy
    private LocalDate time;

    @Column(name = "latitude")
    private double lat;

    @Column(name = "longitude")
    private double lon;

    @ManyToOne
    private Activity activity;

    public TrackPoint(LocalDate time, double lat, double lon) {
        this.time = time;
        this.lat = lat;
        this.lon = lon;
    }

    public LocalDate getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "TrackPoint{" +
                "time=" + time +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
