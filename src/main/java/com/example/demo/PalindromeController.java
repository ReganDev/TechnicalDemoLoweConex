package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.regex.Pattern;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class PalindromeController {

    private final ConcurrentHashMap<String, Boolean> cache = new ConcurrentHashMap<>();

    @PostMapping("/palindrome")
    public ResponseEntity<PalindromeResponse> checkPalindrome(@RequestBody PalindromeRequest request) {
        String text = request.getText();

        // validate input using a regular expression
        if (!Pattern.matches("^[a-zA-Z]*$", text)) {
            return ResponseEntity.badRequest().body(new PalindromeResponse(request.getUsername(), false));
        }

        // Check the cache for the result
        Boolean isPalindrome = cache.get(text);
        if (isPalindrome == null) {
            // If not cached, compute it, store it in the cache
            isPalindrome = isPalindrome(text);
            cache.put(text, isPalindrome);
        }

        return ResponseEntity.ok(new PalindromeResponse(request.getUsername(), isPalindrome));
    }

    private boolean isPalindrome(String text) {
        String normalizedText = text.toLowerCase();
        int left = 0;
        int right = normalizedText.length() - 1;

        while (left < right) {
            if (normalizedText.charAt(left) != normalizedText.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }
}
