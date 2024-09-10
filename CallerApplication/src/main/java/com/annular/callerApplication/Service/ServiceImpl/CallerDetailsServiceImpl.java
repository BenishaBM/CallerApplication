package com.annular.callerApplication.Service.ServiceImpl;



import java.io.File;

import com.annular.callerApplication.Response;
import org.bson.types.Binary;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.annular.callerApplication.Service.CallerDetailsService;
import com.annular.callerApplication.model.CallerDetails;
import com.annular.callerApplication.repository.CallerDetailsRepository;
import com.annular.callerApplication.webModel.CallerDetailsWebModel;

@Service
public class CallerDetailsServiceImpl implements CallerDetailsService {

    @Autowired
    private CallerDetailsRepository callerDetailsRepository;

    // Method to save audio file and return the file path
    private String saveAudioFile(MultipartFile file) throws IOException {
        // Define the directory to save the file on local disk C:
        String directoryPath = "C:\\audio_files\\";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }

        // Create a unique file name
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File savedFile = new File(directory, fileName);

        // Write the file data to the file
        try (FileOutputStream fos = new FileOutputStream(savedFile)) {
            fos.write(file.getBytes());
        }

        return savedFile.getAbsolutePath(); // Return the path of the saved file
    }

    @Override
    public CallerDetails saveCallerDetails(CallerDetailsWebModel callerDetailsWebModel) {
        // Initialize a list to collect missing fields
        StringBuilder missingFields = new StringBuilder();

        if (Objects.isNull(callerDetailsWebModel)) {
            missingFields.append("CallerDetailsWebModel is null. ");
        } else {
            // Check for required fields
            if (Objects.isNull(callerDetailsWebModel.getCallerDurationSec())) {
                missingFields.append("callerDurationSec is missing. ");
            }
            if (Objects.isNull(callerDetailsWebModel.getTotalHours())) {
                missingFields.append("totalHours is missing. ");
            }
            if (Objects.isNull(callerDetailsWebModel.getCallerType())) {
                missingFields.append("callerType is missing. ");
            }
            if (Objects.isNull(callerDetailsWebModel.getSenderNumber())) {
                missingFields.append("senderNumber is missing. ");
            }
            if (Objects.isNull(callerDetailsWebModel.getReceiverNumber())) {
                missingFields.append("receiverNumber is missing. ");
            }
            if (Objects.isNull(callerDetailsWebModel.getCreatedBy())) {
                missingFields.append("createdBy is missing. ");
            }
        }

        // If there are any missing fields, throw an exception with a detailed message
        if (missingFields.length() > 0) {
            throw new IllegalArgumentException("Missing required fields: " + missingFields.toString());
        }

        // Convert CallerDetailsWebModel to CallerDetails entity
        CallerDetails callerDetails = new CallerDetails();
        // Set fields
        callerDetails.setCallerDurationSec(callerDetailsWebModel.getCallerDurationSec());
        callerDetails.setTotalHours(callerDetailsWebModel.getTotalHours());
        callerDetails.setCallerType(callerDetailsWebModel.getCallerType());
        callerDetails.setSenderNumber(callerDetailsWebModel.getSenderNumber());
        callerDetails.setReceiverNumber(callerDetailsWebModel.getReceiverNumber());
        callerDetails.setCreatedBy(callerDetailsWebModel.getCreatedBy());
        callerDetails.setUpdatedBy(callerDetailsWebModel.getUpdatedBy());
        callerDetails.setCallerStartTime(callerDetailsWebModel.getCallerStartTime());
        callerDetails.setCallerEndTime(callerDetailsWebModel.getCallerEndTime());
        callerDetails.setIsActive(true);

        // Handle audio file if present
        MultipartFile audioFile = callerDetailsWebModel.getAudioFileData();
        if (audioFile != null && !audioFile.isEmpty()) {
            try {
                String audioFilePath = saveAudioFile(audioFile);
                callerDetails.setAudioFilePath(audioFilePath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save audio file: " + e.getMessage(), e);
            }
        }

        // Set creation and update timestamps
        if (callerDetails.getCreatedOn() == null) {
            callerDetails.setCreatedOn(LocalDateTime.now());
        }
        callerDetails.setUpdatedOn(LocalDateTime.now());

        // Save the CallerDetails entity
        return callerDetailsRepository.save(callerDetails);
    }



    public CallerDetails getCallerDetailsById(String id) {
        Optional<CallerDetails> callerDetailsOptional = callerDetailsRepository.findById(id);
        if (callerDetailsOptional.isPresent()) {
            CallerDetails callerDetails = callerDetailsOptional.get();
            String audioFilePath = callerDetails.getAudioFilePath();

            // Check if the audio file exists in the specified path
            if (audioFilePath != null) {
                File audioFile = new File(audioFilePath);
                if (audioFile.exists()) {
                    // You can add any additional logic here if you want to handle the audio file
                    // For example, returning a URL to download the file, or base64 encoding, etc.
                    callerDetails.setAudioFilePath(audioFilePath); // This is already set, so it's redundant.
                } else {
                    // Handle the case where the file path is saved but the file doesn't exist
                    throw new RuntimeException("Audio file not found at the specified path.");
                }
            }

            return callerDetails;
        } else {
            throw new RuntimeException("CallerDetails not found for id: " + id);
        }

    }

    @Override
    public List<CallerDetails> getAllActiveDetails() {
        try {
            List<CallerDetails> activeCallers = callerDetailsRepository.getAllActiveStatus();
            return activeCallers;
        } catch (Exception e) {
            // You might want to handle the exception or log it here.
            throw new RuntimeException("Error fetching active caller details: " + e.getMessage());
        }
    }

	
}
