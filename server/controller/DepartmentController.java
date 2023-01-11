package bit.project.server.controller;

import bit.project.server.dao.DepartmentDao;
import bit.project.server.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentDao departmentDao;

    @GetMapping
    public List<Department> getAll(){
        return departmentDao.findAll();
    }
}
