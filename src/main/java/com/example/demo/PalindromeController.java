package com.example.demo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
public class PalindromeController {
    private final ConcurrentHashMap<String, Boolean> cache = new ConcurrentHashMap<>();


    @PostMapping("/palindrome")
    public ResponseEntity<PalindromeResponse> checkPalindrome(@RequestBody PalindromeRequest request) {
        String text = request.getText();

        // Check if the value is already in the cache
        Boolean isPalindrome = cache.get(text);

        // If the value is not in the cache, process it and store the result in the cache
        if (isPalindrome == null) {
            isPalindrome = isPalindrome(text);
            cache.put(text, isPalindrome);
        }

        return ResponseEntity.ok(new PalindromeResponse(request.getUsername(), isPalindrome));
    }



    private boolean isPalindrome(String text) {
        // Check if the input contains numbers, spaces, or punctuation
        Pattern pattern = Pattern.compile("[0-9\\s\\p{Punct}]");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            System.out.println("Input contains invalid characters, please try again");
            return false;
        }

        // remove non-alphabetic characters and convert to lowercase
        String normalizedText = text.replaceAll("[^a-zA-Z]", "").toLowerCase();

        // print the normalized text for debugging purposes
        System.out.println("Normalized text: " + normalizedText);

        int left = 0;
        int right = normalizedText.length() - 1;

        while (left < right) {
            if (normalizedText.charAt(left) != normalizedText.charAt(right)) {
                return false;
            }
            //loop continues until left is no longer less than right
            left++;
            right--;
        }

        return true;
    }
    private void persistResult(String username, boolean isPalindrome) throws IOException {
        String resultFilename = "palindrome_results.txt";
        Path resultFile = Paths.get(resultFilename);

        // create the file if it doesn't exist
        if (!Files.exists(resultFile)) {
            Files.createFile(resultFile);
        }

        // change the result to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFilename, true))) {
            writer.write(String.format("%s: %s\n", username, isPalindrome ? "Palindrome" : "Not Palindrome"));
        }
    }


}
