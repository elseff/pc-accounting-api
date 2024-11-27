package ru.elseff.pcaccounting.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elseff.pcaccounting.dao.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //    @Query(FIND_ALL_WITH_COMPUTERS)
//    List<Employee> findAllWithComputers(); // TODO: 11/25/24 may be get computers too

//    @NonNull
//    @Query(ALL_EMPLOYEES)
//    List<Employee> findAll();
//
//    @Query(ALL_EMPLOYEES_WITH_COMPUTERS)
//    List<Employee> findAllWithComputers();
}
