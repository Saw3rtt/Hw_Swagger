package ru.hogwarts.school_.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school_.exception.AvatarNotFoundException;
import ru.hogwarts.school_.model.Avatar;
import ru.hogwarts.school_.model.Student;
import ru.hogwarts.school_.repository.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService {
    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);
    private final String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(StudentService studentService, AvatarRepository avatarRepository, @Value("${path.to.avatars.folder}") String avatarsDir) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
        this.avatarsDir = avatarsDir;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("был вызван метод uploadAvatar с данными" + studentId + " и " + avatarFile);
        Student student = studentService.read(studentId);
        Path filePath = Path.of(avatarsDir, student.getId() + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = avatarRepository.findByStudent_id(studentId).orElse(new Avatar());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        logger.info("аватар был успешно сохранен в Базу данных и папку");
        avatarRepository.save(avatar);
    }

    private String getExtensions(String fileName) {
        logger.info("был вызван метод getExtensions с данными" + fileName);
        String substring = fileName.substring(fileName.lastIndexOf(".") + 1);
        logger.info("из метода getExtensions вернули " + substring);
        return substring;
    }

    @Override
    public Avatar readFromDb(long id) {
        return avatarRepository.findById(id)
                .orElseThrow(() -> new AvatarNotFoundException("Аватар не найден"));

    }

    @Override
    public List<Avatar> getPage(int pageNumber, int size) {
        logger.info("был вызван метод getPage с данными" + pageNumber + " и " + size);
        PageRequest request = PageRequest.of(pageNumber, size);
        List<Avatar> content = avatarRepository.findAll(request).getContent();
        logger.info("из метода getPage вернули " + content);
        return content;
    }
}