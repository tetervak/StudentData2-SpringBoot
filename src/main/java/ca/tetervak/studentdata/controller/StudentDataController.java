package ca.tetervak.studentdata.controller;

import ca.tetervak.studentdata.data.StudentDataService;
import ca.tetervak.studentdata.model.StudentForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/students")
public class StudentDataController {

    private static final String[] programs = {
            "Computer Programmer", "Systems Technology",
            "Engineering Technician", "Systems Technician"};

    private final StudentDataService studentDataService;

    public StudentDataController(StudentDataService studentDataService){
        this.studentDataService = studentDataService;
    }

    @GetMapping(value={"/", "/index"})
    public String index(){
        log.trace("index() is called");
        return "students/Index";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/add-student")
    public ModelAndView addStudent(){
        log.trace("addStudent() is called");
        ModelAndView modelAndView =
                new ModelAndView("students/AddStudent",
                                    "form", new StudentForm());
        modelAndView.addObject("programs", programs);
        return modelAndView;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/insert-student")
    public String insertStudent(
            @Validated @ModelAttribute("form") StudentForm form,
            BindingResult bindingResult,
            Model model){
        log.trace("insertStudent() is called");
        // checking for the input validation errors
        if (bindingResult.hasErrors()) {
            log.trace("input validation errors");
            //model.addAttribute("form", form);
            model.addAttribute("programs", programs);
            return "students/AddStudent";
        } else {
            log.trace("the user inputs are correct");
            studentDataService.insertStudentForm(form);
            return "redirect:/students/confirm-insert/" + form.getId();
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/confirm-insert/{id}")
    public String confirmInsert(@PathVariable(name = "id") String strId, Model model){
        log.trace("confirmInsert() is called");
        try {
            int id = Integer.parseInt(strId);
            log.trace("looking for the data in the database");
            StudentForm form = studentDataService.getStudentForm(id);
            if (form == null) {
                log.trace("no data for this id=" + id);
                return "students/DataNotFound";
            } else {
                log.trace("showing the data");
                model.addAttribute("student", form);
                return "students/ConfirmInsert";
            }
        } catch (NumberFormatException e) {
            log.trace("the id in not an integer");
            return "students/DataNotFound";
        }
    }

    @GetMapping("/list-students")
    public ModelAndView listStudents() {
        log.trace("listStudents() is called");
        List<StudentForm> list = studentDataService.getAllStudentForms();
        return new ModelAndView("students/ListStudents", "students", list);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete-all")
    public String deleteAll(){
        log.trace("deleteAll() is called");
        studentDataService.deleteAllStudentForms();
        return "redirect:/students/ListStudents";
    }

    @GetMapping("student-details/{id}")
    public String studentDetails(@PathVariable String id, Model model){
        log.trace("studentDetails() is called");
        try {
            StudentForm form = studentDataService.getStudentForm(Integer.parseInt(id));
            if (form != null) {
                model.addAttribute("student", form);
                return "students/StudentDetails"; // show the student data in the form to edit
            } else {
                log.trace("no data for this id=" + id);
                return "students/DataNotFound";
            }
        } catch (NumberFormatException e) {
            log.trace("the id is missing or not an integer");
            return "students/DataNotFound";
        }
    }

    // a user clicks "Delete" link (in the table) to "DeleteStudent"
    @Secured("ROLE_ADMIN")
    @GetMapping("/delete-student")
    public String deleteStudent(@RequestParam String id, Model model) {
        log.trace("deleteStudent() is called");
        try {
            StudentForm form = studentDataService.getStudentForm(Integer.parseInt(id));
            if (form != null) {
                model.addAttribute("student", form);
                return "students/DeleteStudent"; // ask "Do you really want to remove?"
            } else {
                return "redirect:/students/list-students";
            }
        } catch (NumberFormatException e) {
            return "redirect:/students/list-students";
        }
    }

    // a user clicks "Remove Record" button in "DeleteStudent" page,
    // the form submits the data to "RemoveStudent"
    @Secured("ROLE_ADMIN")
    @PostMapping("/remove-student")
    public String removeStudent(@RequestParam String id) {
        log.trace("removeStudent() is called");
        try {
            studentDataService.deleteStudentForm(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            log.trace("the id is missing or not an integer");
        }
        return "redirect:/students/list-students";
    }

    // a user clicks "Edit" link (in the table) to "EditStudent"
    @Secured("ROLE_ADMIN")
    @GetMapping("/edit-student")
    public String editStudent(@RequestParam String id, Model model) {
        log.trace("editStudent() is called");
        try {
            StudentForm form = studentDataService.getStudentForm(Integer.parseInt(id));
            if (form != null) {
                model.addAttribute("form", form);
                model.addAttribute("programs", programs);
                return "students/EditStudent";
            } else {
                log.trace("no data for this id=" + id);
                return "redirect:/students/list-students";
            }
        } catch (NumberFormatException e) {
            log.trace("the id is missing or not an integer");
            return "redirect:/students/list-students";
        }
    }

    // the form submits the data to "UpdateStudent"
    @Secured("ROLE_ADMIN")
    @PostMapping("/update-student")
    public String updateStudent(
            @Validated @ModelAttribute("form") StudentForm form,
            BindingResult bindingResult,
            Model model) {
        log.trace("updateStudent() is called");
        // checking for the input validation errors
        if (bindingResult.hasErrors()) {
            log.trace("input validation errors");
            //model.addAttribute("form", form);
            model.addAttribute("programs", programs);
            return "students/EditStudent";
        } else {
            log.trace("the user inputs are correct");
            studentDataService.updateStudentForm(form);
            log.debug("id = " + form.getId());
            return "redirect:/students/student-details/" + form.getId();
        }
    }
}
