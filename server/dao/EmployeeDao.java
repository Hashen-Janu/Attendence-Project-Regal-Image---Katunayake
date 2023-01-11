package bit.project.server.dao;

import bit.project.server.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported=false)
public interface EmployeeDao extends JpaRepository<Employee, Integer>{
    @Query("select new Employee (e.id,e.code,e.nametitle,e.callingname,e.photo) from Employee e")
    Page<Employee> findAllBasic(PageRequest pageRequest);

    Employee findByCode(String code);
    Employee findByNic(String nic);
    Employee findByMobile(String mobile);
    Employee findByEmail(String email);
}