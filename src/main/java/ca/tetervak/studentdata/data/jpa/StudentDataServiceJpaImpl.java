package ca.tetervak.studentdata.data.jpa;

import ca.tetervak.studentdata.data.StudentDataService;
import ca.tetervak.studentdata.model.StudentForm;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentDataServiceJpaImpl implements StudentDataService {

    private StudentDataRepositoryJpa studentDataRepository;

    StudentDataServiceJpaImpl(StudentDataRepositoryJpa studentDataRepository){
        this.studentDataRepository = studentDataRepository;
    }

    private static void copyFormToEntity(StudentForm form, StudentEntity student){
        //student.setId(form.getId());
        student.setFirstName(form.getFirstName());
        student.setLastName(form.getLastName());
        student.setProgramName(form.getProgramName());
        student.setProgramYear(form.getProgramYear());
        student.setProgramCoop(form.isProgramCoop());
        student.setProgramInternship(form.isProgramInternship());
    }

    private static void copyEntityToForm(StudentEntity student, StudentForm form){
        form.setId(student.getId());
        form.setFirstName(student.getFirstName());
        form.setLastName(student.getLastName());
        form.setProgramName(student.getProgramName());
        form.setProgramYear(student.getProgramYear());
        form.setProgramCoop(student.getProgramCoop());
        form.setProgramInternship(student.getProgramInternship());
    }

    public void insertStudentForm(StudentForm form) {
        StudentEntity student = new StudentEntity();
        copyFormToEntity(form, student);
        student = studentDataRepository.save(student);
        form.setId(student.getId());
    }

    public List<StudentForm> getAllStudentForms() {
        Order orderByLastName = new Order(Direction.ASC, "lastName");
        Order orderByFirstName = new Order(Direction.ASC, "firstName");
        Sort sortByName = Sort.by(orderByLastName, orderByFirstName);
        List<StudentEntity> studentList = studentDataRepository.findAll(sortByName);
        List<StudentForm> formList = new ArrayList<>();
        for(StudentEntity student: studentList){
            StudentForm form = new StudentForm();
            copyEntityToForm(student, form);
            formList.add(form);
        }
        return formList;
    }

    public void deleteAllStudentForms() {
        studentDataRepository.deleteAll();
    }

    public void deleteStudentForm(int id) {
        studentDataRepository.deleteById(id);
    }

    public StudentForm getStudentForm(int id) {
        Optional<StudentEntity> result = studentDataRepository.findById(id);
        if(result.isPresent()){
            StudentForm form = new StudentForm();
            StudentEntity student = result.get();
            copyEntityToForm(student, form);
            return form;
        }
        return null;
    }

    public void updateStudentForm(StudentForm form) {
        Optional<StudentEntity> result = studentDataRepository.findById(form.getId());
        if(result.isPresent()){
            StudentEntity student = result.get();
            copyFormToEntity(form, student);
            studentDataRepository.save(student);
            //studentRepository.flush();
        }
    }
}
