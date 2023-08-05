package br.com.renan.service;

import java.util.List;

public interface CourseService {

    public List<String> retrieveCourses(String students);

    public List<String> doSomething(String students);
    public void deleteCourse(String course);


}
