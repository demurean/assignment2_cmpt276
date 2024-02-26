package com.twosevensix.assi2.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.transaction.annotation.Transactional;

import com.twosevensix.assi2.models.student;
import com.twosevensix.assi2.models.studentRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class studentsController {
    @Autowired
    private studentRepository studentRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/students/view")
    public String getAllStudents(Model model)
    {
        System.out.println("Getting all students");
        // gets all students from database
        List<student> students = studentRepo.findAll();
        // end of database call

        model.addAttribute("stud", students);
        return "students/showAll";
    }

    @PostMapping("/students/add")
    public String addStudent(@RequestParam Map<String, String> newStudent, HttpServletResponse response) //String of a key, and string for the value thats coming in
    {
        System.out.println("add Student");
        String newName = newStudent.get("name");
        int newWeight = Integer.parseInt(newStudent.get("weight"));
        int newHeight = Integer.parseInt(newStudent.get("height"));
        String newHaircolour = newStudent.get("haircolour");
        double newGpa = Double.parseDouble(newStudent.get("gpa"));

        studentRepo.save(new student(newName, newWeight, newHeight, newHaircolour, newGpa));
        response.setStatus(201); //201 == just created a new object

        return "students/success";
    }

    @GetMapping("/students/changeStudent")
    public String modifyStudentInfo(@RequestParam("sid") int studentId, Model model) {
        // Retrieve the student information based on the provided ID
        Optional<student> optionalStudent = studentRepo.findById(studentId);

        if (optionalStudent.isPresent()) {
            student studentInfo = optionalStudent.get();
            model.addAttribute("student", studentInfo);
            return "students/changeStudent";
        } else {
            // Handle the case when the student with the provided ID is not found -- unlikely but who knows. countermeasure in place
            return "students/errorPage";
        }
    }

    @PostMapping("/students/change")
    @Transactional
    public String changeStudent(@RequestParam Map<String, String> modStudent, HttpServletResponse response)
    {
        try {
            System.out.println("Change Student");
            int studentId = Integer.parseInt(modStudent.get("sid"));

            Optional<student> optionalStudent = studentRepo.findById(studentId);
            if (optionalStudent.isPresent()) {
                student existingStudent = optionalStudent.get();
               
                existingStudent.setName(modStudent.get("name"));
                existingStudent.setWeight(Integer.parseInt(modStudent.get("weight")));
                existingStudent.setHeight(Integer.parseInt(modStudent.get("height")));
                existingStudent.setHaircolour(modStudent.get("haircolour"));
                existingStudent.setGpa(Double.parseDouble(modStudent.get("gpa")));

                studentRepo.save(existingStudent); // Save the modified student information
                response.setStatus(200); // 200 == OK
                return "students/success";
            }
            else
            {
                response.setStatus(404); //Can't found the student
                return "students/errorPage";
            }
        }
        catch (Exception e) {
            // Handle any errors
            e.printStackTrace();
            response.setStatus(500); // 500 == Internal Server Error
            return "students/errorPage";
        }

        }

    @PostMapping("/students/delete")
    public String deleteStudent(@RequestParam("sid") int studentId) {
        // Perform deletion operation based on student ID
        studentRepo.deleteById(studentId);
        return "redirect:/students/view"; // Redirect to the view page after deletion
        }
//conveniences
    @PostMapping("/students/return")
    public String returnToViewAll() {
        
        return "redirect:/students/view";
    }
    
    }
