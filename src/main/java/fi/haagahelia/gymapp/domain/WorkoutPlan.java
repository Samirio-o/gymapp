package fi.haagahelia.gymapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Represents a workout plan with a name and a target muscle group.
 * The plan will later be connected to an AppUser and its exercises.
 */
@Entity
public class WorkoutPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String muscleGroup;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    public WorkoutPlan() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * Muscle group targeted by this workout plan.
     */
    public String getMuscleGroup() {
        return muscleGroup;
    }

    public AppUser getUser() {
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}