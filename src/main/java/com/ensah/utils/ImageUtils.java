package com.ensah.utils;
import com.ensah.web.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
public class ImageUtils {


//    public static String saveImageAdmin(MultipartFile image) throws IOException {
//        // Vérifiez si le fichier est vide ou nul
//        if (image == null || image.isEmpty()) {
//            throw new IllegalArgumentException("Le fichier image est vide ou nul.");
//        }
//        // Récupérez le nom original du fichier
//        String originalFileName = image.getOriginalFilename();
//        // Générez un nom de fichier unique pour éviter les collisions de noms de fichiers
//        String uniqueFileName = generateUniqueFileName(originalFileName);
//        // Définissez le répertoire où vous souhaitez enregistrer les images (par exemple : C:/mon-dossier-images/)
//        String directoryPath = "C:/Users/Mohammed Aziz/Desktop/Gestion de terrain/gestion_terrain_front_v.1.2/src/assets/images/";
//        // Vérifiez si le dossier existe, sinon, créez-le
//        File directory = new File(directoryPath);
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
//        // Obtenez le chemin complet pour enregistrer l'image
//        String imagePath = directoryPath + uniqueFileName;
//        // Enregistrez l'image dans le dossier spécifié
//        Path imageFilePath = Paths.get(imagePath);
//        Files.write(imageFilePath, image.getBytes());
//        return uniqueFileName; // Renvoie le nom généré
//    }


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
        String directoryPath = "C:/Users/Mohammed Aziz/Desktop/Gestion de terrain/gestion_terrain_front_v.1.2/src/assets/images/";
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
        return uniqueFileName; // Renvoie le nom généré
    }
    private static String generateUniqueFileName(String originalFileName) {
        // Générez un nom de fichier unique en utilisant UUID (Universally Unique Identifier)
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID().toString() + extension;
    }

    public static void deleteImage(String imageName) {
        // Définissez le répertoire où se trouvent les images
        String directoryPath = "C:/Users/Mohammed Aziz/Desktop/Gestion de terrain/gestion_terrain_front_v.1.2/src/assets/images/";

        // Obtenez le chemin complet de l'image à supprimer
        String imagePath = directoryPath + imageName;
        File imageFile = new File(imagePath);

        // Vérifiez si le fichier existe avant de le supprimer
        if (imageFile.exists()) {
            try {
                // Supprimez le fichier
                imageFile.delete();
                System.out.println("L'image a été supprimée avec succès.");
            } catch (SecurityException e) {
                System.err.println("Impossible de supprimer l'image : " + e.getMessage());
                throw new RuntimeException("Error Impossible de supprimer l'image : "+ imageName);

            }
        } else {
            System.out.println("L'image n'existe pas dans le dossier spécifié.");
            throw new NotFoundException("Impossible de trouvée l'image n'existe " + imageName);
        }
    }

}
