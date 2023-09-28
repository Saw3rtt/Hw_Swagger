package ru.hogwarts.school_.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school_.exception.FacultyException;
import ru.hogwarts.school_.model.Faculty;
import ru.hogwarts.school_.model.Student;
import ru.hogwarts.school_.repository.FacultyRepository;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        logger.info("был вызван метод create с данными " + faculty);
        if (facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()).isPresent()) {
            throw new FacultyException("Такой факультет уже создан!");
        }
        Faculty savedFaculty = facultyRepository.save(faculty);
        logger.info("из метода create вернули " + savedFaculty);
        return savedFaculty;
    }


    @Override
    public Faculty read(long id) {
        logger.info("был вызван метод read с данными" + id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("Факультет не найден");
        }
        Faculty readFaculty = faculty.get();
        logger.info("из метода read вернули " + readFaculty);
        return readFaculty;
    }

    @Override
    public Faculty update(Faculty faculty) {
        logger.info("был вызван метод update с данными " + faculty);
        long id = faculty.getId();
        if (facultyRepository.findById(id).isEmpty()) {
            throw new FacultyException("Факультет не найден");
        }
        Faculty updateFaculty = facultyRepository.save(faculty);
        logger.info("из метода update вернули " + updateFaculty);
        return updateFaculty;
    }

    @Override
    public Faculty delete(long id) {
        logger.info("был вызван метод delete с данными" + id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        facultyRepository.deleteById(id);
        Faculty deleteFaculty = faculty.get();
        logger.info("из метода delete удалили факультет" + deleteFaculty);
        return deleteFaculty;
    }

    @Override
    public List<Faculty> findByColor(String color) {
        logger.info("был вызван метод findByColor для с поиска по данным " + color);
        List<Faculty> byColor = facultyRepository.findByColor(color);
        logger.info("из метода findByColor вернули факультет по данным " + byColor);
        return byColor;
    }

    @Override
    public List<Faculty> findNameOrColorIgnoreCase(String name, String color) {
        logger.info("был вызван метод findNameOrColorIgnoreCase  данным " + name + color);
        List<Faculty> nameIgnoreCaseOrColorIgnoreCase = facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
        logger.info("из метода findNameOrColorIgnoreCase вернули имя и цвет по данным " + nameIgnoreCaseOrColorIgnoreCase);
        return nameIgnoreCaseOrColorIgnoreCase;
    }

    @Override
    public List<Student> getListStudentsByFaculty(long facultyId) {
        logger.info("был вызван метод getListStudentsByFaculty с данными" + facultyId);
        List<Student> students = facultyRepository.getStudentsByFaculty(facultyId);
        logger.info("из метода getListStudentsByFaculty вернули студентов по факультету" + students);
        return students;
    }
    @Override
    public String findByLongestName(){
        return facultyRepository.findAll().stream()
                .map(faculty -> faculty.getName())
                .max(Comparator.comparingInt(name -> name.length()))
                .orElseThrow(() -> new FacultyException("У нас нет факультетов в Бд"));
    }
}

