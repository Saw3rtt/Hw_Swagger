package ru.hogwarts.school_.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school_.exception.FacultyException;
import ru.hogwarts.school_.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> facultyMap = new HashMap<>();

    private long counter;

    @Override
    public Faculty create(Faculty faculty) {
        if (facultyMap.containsValue(faculty)) {
            throw new FacultyException("Такой факультет уже создан!");
        }
        faculty.setId(++counter);
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }


    @Override
    public Faculty read(long id) {
        if (!facultyMap.containsKey(id)) {
            throw new FacultyException("Факультет не найден");
        }
        return facultyMap.get(id);
    }

    @Override
    public Faculty update(Faculty faculty) {
        if (!facultyMap.containsKey(faculty.getId())) {
            throw new FacultyException("Факультет не найден");
        }
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty delete(long id) {
        return facultyMap.remove(id);
    }
    @Override
    public List<Faculty> readAll(String color){
        return facultyMap.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
}
    }
