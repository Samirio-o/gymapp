package fi.haagahelia.gymapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class Exercise {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String muscleGroup;
    private int sets;
    private int repetitions;
    private int restTime; // in seconds

    public Exercise() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getMuscleGroup() {
        return muscleGroup;
    }

    public int getSets() {
        return sets;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

}
