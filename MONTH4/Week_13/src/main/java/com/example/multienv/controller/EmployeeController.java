package com.example.multienv.controller;
import com.example.multienv.model.Employee;
import com.example.multienv.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }
    @GetMapping
    public List<Employee> all() {
        return service.list();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Employee> get(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        Employee created = service.create(employee);
        return ResponseEntity.created(URI.create("/api/employees/" + created.getId())).body(created);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            Employee updated = service.update(id, employee);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
