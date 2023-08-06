package com.ensah.utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
public class ImageUtils {


    public static String saveImage(MultipartFile image) throws IOException {
        // Vérifiez si le fichier est vide ou nul
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Le fichier image est vide ou nul.");
        }

        // Récupérez le nom original du fichier
        String originalFileName = image.getOriginalFilename();

        // Générez un nom de fichier unique pour éviter les collisions de noms de fichiers
        String uniqueFileName = generateUniqueFileName(originalFileName);

        // Définissez le répertoire où vous souhaitez enregistrer les images (par exemple : C:/mon-dossier-images/)
        String directoryPath = "C:/Users/Mohammed Aziz/Desktop/Gestion de terrain/images";

        // Vérifiez si le dossier existe, sinon, créez-le
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Obtenez le chemin complet pour enregistrer l'image
        String imagePath = directoryPath + uniqueFileName;

        // Enregistrez l'image dans le dossier spécifié
        Path imageFilePath = Paths.get(imagePath);
        Files.write(imageFilePath, image.getBytes());

        return imagePath; // Renvoie le chemin d'accès complet où l'image a été enregistrée
    }

    private static String generateUniqueFileName(String originalFileName) {
        // Générez un nom de fichier unique en utilisant UUID (Universally Unique Identifier)
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID().toString() + extension;
    }
}
