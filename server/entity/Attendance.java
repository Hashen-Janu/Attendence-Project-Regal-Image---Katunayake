package bit.project.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Attendance {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private LocalDate date;
    private LocalTime intime;
    private LocalTime outtime;
    private String breakfast;
    private String lunch;
    private LocalDateTime tocreation;


    @ManyToOne
    private Employee employee;

    public Attendance(Integer id) {
        this.id = id;
    }

    public Attendance(Integer id, LocalDate date, String breakfast, String lunch, Employee employee) {

        this.id = id;
        this.date = date;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.employee = employee;
    }
}
