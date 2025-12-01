package com.hotel.bf.service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
@Transactional
public class FileService {
    @Value("${files.upload.baseDir}/identite/client")
    private String uploadDirectory;

    private final Logger log = LoggerFactory.getLogger(FileService.class);

    public String uploadFile(MultipartFile file, Long identifiant) throws IOException {
        if (file.isEmpty() || identifiant == null) {
            return null;
        }
        File uploadDir = new File(uploadDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return null;
        }

        // Sécuriser le nom de fichier
        String sanitizedFilename = originalFilename.replaceAll("[^a-zA-Z0-9\\.\\-_àáâãäçèéêëìíîïñòóôõöùúûüýÿ ]", "_");
        String finalFilename = identifiant + sanitizedFilename;
        Path path = Paths.get(uploadDirectory, finalFilename);

        try {
            Files.write(path, file.getBytes());
            System.out.println("Fichier sauvé: " + finalFilename);
            return finalFilename; // Retourner le nom final pour la base de données
        } catch (IOException e) {
            System.err.println("Erreur lors de l'upload: " + e.getMessage());
            e.printStackTrace();
            throw e; // Relancer l'exception pour la gestion d'erreur
        }
    }

    /**
     * Recuperer le document a partir de l'url.
     *
     */
    public String downloadFileFromLocal(String filePath) throws IOException {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("Le chemin du fichier ne peut pas être vide");
        }

        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new FileNotFoundException("Fichier introuvable : " + filePath);
        }

        byte[] fileBytes = Files.readAllBytes(path);

        // Déterminer le type MIME simple à partir de l’extension
        String contentType = "image/jpeg"; // par défaut
        if (filePath.endsWith(".png")) contentType = "image/png";
        else if (filePath.endsWith(".gif")) contentType = "image/gif";

        // Retourner en Base64 prêt pour <img>
        return "data:" + contentType + ";base64," + Base64.getEncoder().encodeToString(fileBytes);
    }

    public InputStream getPhotoInputStream(String photoUrl) throws IOException {
        if (photoUrl == null || photoUrl.isBlank()) {
            throw new IllegalArgumentException("L'URL ou chemin de la photo ne peut pas être vide");
        }

        // Si c'est une URL distante HTTP/HTTPS
        if (photoUrl.startsWith("http://") || photoUrl.startsWith("https://")) {
            return new URL(photoUrl).openStream();
        }

        // Sinon on suppose que c'est un chemin local
        Path path = Paths.get(photoUrl);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("Fichier introuvable : " + photoUrl);
        }
        return Files.newInputStream(path);
    }

}
