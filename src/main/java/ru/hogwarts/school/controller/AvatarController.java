package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import javax.net.ssl.HttpsURLConnection;
import javax.print.DocFlavor;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {

        this.avatarService = avatarService;

    }

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException {
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping("/pageFromDB")
    public ResponseEntity<List<ResponseEntity<byte[]>>> getPageOfAvatar(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        List<Avatar> avatars = avatarService.getPageOfAvatar(pageNumber, pageSize);
//        String mediaType = avatars.get(0).getMediaType();
        List<ResponseEntity<byte[]>> arrayList=new ArrayList<>();
        List<String> mediaTypes = new ArrayList<>();
        for (Avatar avatar : avatars) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
            headers.setContentLength(avatar.getData().length);
//            ResponseEntity response = new ResponseEntity<>(avatar, headers, HttpStatus.OK);
            arrayList.add(ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData()));
        }
//        MediaType mediaType1 = MediaType.parseMediaTypes(mediaTypes);
//        long lenght = 0;
//        for (int i = 0; i < avatars.size(); i++) {
//            lenght = +avatars.get(i).getData().length;
//        }
//        List<byte[]> dataList = new ArrayList<>();
//        for (int i = 0; i < avatars.size(); i++) {
//           byte[] data= avatars.get(i).getData();
//           dataList.add(data); }
//        dataList.toArray();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType(mediaType));
//        headers.setContentLength(lenght);
//        ResponseEntity<List<byte[]>> body = ResponseEntity.status(HttpStatus.OK).headers(headers).body(dataList);
//        return ResponseEntity.ok(body);
        return ResponseEntity.ok(arrayList);
    }

    @GetMapping("/pageAvatarsFromFile")
    public String getPageAvatarsFromFiles(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) throws IOException {
        List<Avatar> avatars = avatarService.getPageOfAvatar(pageNumber, pageSize);
        for (Avatar avatar : avatars) {


            return avatar.getFilePath();

        }
            return HttpStatus.OK.toString();
    }
    @GetMapping("/pageAvatarsFromDB")
    public ResponseEntity<byte[]> getPageAvatarsFromDB(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) throws IOException {
        List<Avatar> avatars = avatarService.getPageOfAvatar(pageNumber, pageSize);
        for (Avatar avatar : avatars) {
            return downloadAvatar(avatar.getId());
        }

        return null;
    }

    @GetMapping(value = "/{id}/avatar-from-file")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException{
        Avatar avatar = avatarService.findAvatar(id);
        Path path = Path.of(avatar.getFilePath());
        try(InputStream is = Files.newInputStream(path);
            OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

//    @GetMapping("pageFromDB")
//    public void getPageOfAvatar(@RequestParam Integer pageNumber, @RequestParam Integer pageSize,
//                                        HttpServletResponse[] responses) throws IOException {
//        List<Avatar> avatars = avatarService.getPageOfAvatar(pageNumber, pageSize);
//        for (int i = 0; i < avatars.size(); i++) {
//            Avatar avatar = avatars.get(i);
//            HttpServletResponse response = null;
//            Path path = Path.of(avatar.getFilePath());
//            try (InputStream is = Files.newInputStream(path);
//                 OutputStream os = response.getOutputStream()) {
//                response.setStatus(200);
//                response.setContentType(avatar.getMediaType());
//                response.setContentLength((int) avatar.getFileSize());
//                is.transferTo(os);
//
//            }
//            responses[i] = response;
//
//        }
//    }
}

