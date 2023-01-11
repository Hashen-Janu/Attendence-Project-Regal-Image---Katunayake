package bit.project.server.controller;

import bit.project.server.UsecaseList;
import bit.project.server.dao.AttendanceDao;
import bit.project.server.dao.EmployeeDao;
import bit.project.server.entity.Attendance;
import bit.project.server.entity.User;
import bit.project.server.util.dto.PageQuery;
import bit.project.server.util.dto.ResourceLink;
import bit.project.server.util.exception.ConflictException;
import bit.project.server.util.exception.ObjectNotFoundException;
import bit.project.server.util.helper.PageHelper;
import bit.project.server.util.helper.PersistHelper;
import bit.project.server.util.security.AccessControlManager;
import bit.project.server.util.validation.EntityValidator;
import bit.project.server.util.validation.ValidationErrorBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@CrossOrigin

@RestController
@RequestMapping("/attendances")
public class AttendanceController {
    @Autowired
    private AccessControlManager accessControlManager;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private AttendanceDao attendanceDao;

    private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.DESC, "tocreation");


    @GetMapping
    public Page<Attendance> getAll(PageQuery pageQuery, HttpServletRequest request) {
        accessControlManager.authorize(request, "No privilege to get all attendances", UsecaseList.SHOW_ALL_ATTENDANCES);

        if(pageQuery.isEmptySearch()){
            return attendanceDao.findAll(PageRequest.of(pageQuery.getPage(), pageQuery.getSize(), DEFAULT_SORT));
        }

        String date = pageQuery.getSearchParam("date");
        Integer employeeId = pageQuery.getSearchParamAsInteger("employee");

        List<Attendance> attendances = attendanceDao.findAll(DEFAULT_SORT);
        Stream<Attendance> stream = attendances.parallelStream();

        List<Attendance> filteredAttendances = stream.filter(attendance -> {

            if(date!=null)
                if(!attendance.getDate().toString().toLowerCase().contains(date.toLowerCase())) return false;

            if(employeeId!=null)
                if(!attendance.getEmployee().getId().equals(employeeId)) return false;
            return true;
        }).collect(Collectors.toList());

        return PageHelper.getAsPage(filteredAttendances, pageQuery.getPage(), pageQuery.getSize());

    }

    @GetMapping("/basic")
    public Page<Attendance> getAllBasic(PageQuery pageQuery, HttpServletRequest request){
        accessControlManager.authorize(request, "No privilege to get all attendances' basic data", UsecaseList.SHOW_ALL_ATTENDANCES);
        return attendanceDao.findAllBasic(PageRequest.of(pageQuery.getPage(), pageQuery.getSize(), DEFAULT_SORT));
    }

    @GetMapping("/{id}")
    public Attendance get(@PathVariable Integer id, HttpServletRequest request) {
        accessControlManager.authorize(request, "No privilege to get attendance", UsecaseList.SHOW_ATTENDANCE_DETAILS, UsecaseList.UPDATE_ATTENDANCE);
        Optional<Attendance> optionalAttendance = attendanceDao.findById(id);
        if(optionalAttendance.isEmpty()) throw new ObjectNotFoundException("Attendance not found");
        return optionalAttendance.get();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id, HttpServletRequest request){
        accessControlManager.authorize(request, "No privilege to delete attendances", UsecaseList.DELETE_ATTENDANCE);

        try{
            if(attendanceDao.existsById(id)) attendanceDao.deleteById(id);
        }catch (DataIntegrityViolationException | RollbackException e){
            throw new ConflictException("Cannot delete. Because this attendance already used in another module");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceLink add(@RequestBody Attendance attendance, HttpServletRequest request) throws InterruptedException {
        User authUser = accessControlManager.authorize(request, "No privilege to add new attendance", UsecaseList.ADD_ATTENDANCE);

        attendance.setTocreation(LocalDateTime.now());
        attendance.setDate(LocalDate.now());
        attendance.setIntime(LocalTime.now());



        EntityValidator.validate(attendance);
        ValidationErrorBag errorBag = new ValidationErrorBag();

        System.out.println(attendance);

        PersistHelper.save(()->{
            return attendanceDao.save(attendance);
        });



        return new ResourceLink(attendance.getId(), "/attendance/"+attendance.getId());
    }

    @PutMapping("/{id}")
    public ResourceLink update(@PathVariable Integer id, @RequestBody Attendance attendance, HttpServletRequest request) {
        accessControlManager.authorize(request, "No privilege to update attendance details", UsecaseList.UPDATE_ATTENDANCE);

        Optional<Attendance> optionalAttendance = attendanceDao.findById(id);
        if(optionalAttendance.isEmpty()) throw new ObjectNotFoundException("Attendance not found");
        Attendance oldAttendance = optionalAttendance.get();

        attendance.setId(id);
        attendance.setOuttime(LocalTime.now());
        attendance.setDate(oldAttendance.getDate()); 
        EntityValidator.validate(attendance);

        attendance = attendanceDao.save(attendance);
        return new ResourceLink(attendance.getId(), "/attendances/"+attendance.getId());
    }

}
