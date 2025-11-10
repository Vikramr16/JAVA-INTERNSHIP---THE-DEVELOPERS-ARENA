package com.example.multienv.service;
import com.example.multienv.model.Employee;
import com.example.multienv.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class EmployeeService {
    private final EmployeeRepository repo;
    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }
    public Employee create(Employee e) {
        return repo.save(e);
    }
    public List<Employee> list() {
        return repo.findAll();
    }
    public Optional<Employee> findById(Long id) {
        return repo.findById(id);
    }
    public Employee update(Long id, Employee update) {
        return repo.findById(id).map(e -> {
            e.setName(update.getName());
            e.setRole(update.getRole());
            return repo.save(e);
        }).orElseThrow();
    }
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
