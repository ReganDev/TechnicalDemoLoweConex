package com.example.demo;

public class PalindromeResponse {
    private String username;
    private boolean isPalindrome;

    public PalindromeResponse(String username, boolean isPalindrome) {
        this.username = username;
        this.isPalindrome = isPalindrome;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isPalindrome() {
        return isPalindrome;
    }

    public void setPalindrome(boolean isPalindrome) {
        this.isPalindrome = isPalindrome;
    }
}
