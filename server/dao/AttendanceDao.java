package bit.project.server.dao;

import bit.project.server.entity.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported=false)
public interface AttendanceDao extends JpaRepository<Attendance,Integer> {
    @Query("select new Attendance (a.id,a.date, a.breakfast,a.lunch,a.employee) from Attendance a")
    Page<Attendance> findAllBasic(PageRequest pageRequest);
}
