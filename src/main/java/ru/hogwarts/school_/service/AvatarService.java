package ru.hogwarts.school_.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school_.model.Avatar;

import java.io.IOException;
import java.util.Optional;

public interface AvatarService {


    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar readFromDb(long id);
}
