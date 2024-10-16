<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Art Gallery</title>
    <link rel="stylesheet" href="css/register.css"/>
    
    <style>
        .error { color: red; margin-top: 5px; }
        .error-card {
            border: 1px solid red;
            background-color: #f8d7da;
            padding: 10px;
            margin-top: 5px;
        }
    </style>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            document.getElementById('email').addEventListener('input', function() {
                var email = this.value;
                var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
                if (!emailPattern.test(email)) {
                    document.getElementById('emailError').innerHTML = "Invalid email format. Example: user@example.com";
                } else {
                    document.getElementById('emailError').innerHTML = '';
                }
            });

            document.getElementById('contactno').addEventListener('input', function() {
                var contactno = this.value;
                var contactnoPattern = /^[0-9]{10}$/;
                if (!contactnoPattern.test(contactno)) {
                    document.getElementById('contactnoError').innerHTML = "Please enter a valid 10-digit contactno number.";
                } else {
                    document.getElementById('contactnoError').innerHTML = '';
                }
            });

            document.getElementById('password').addEventListener('input', function() {
                var password = this.value;
                if (password.length < 8) {
                    document.getElementById('passwordError').innerHTML = "Password must be at least 8 characters long.";
                } else {
                    document.getElementById('passwordError').innerHTML = '';
                }
                validatePasswordMatch();

            });
            document.getElementById('confirmPassword').addEventListener('input', function() {
                validatePasswordMatch();
            });
        });
        
        function validatePasswordMatch() {
            var password = document.getElementById('password').value;
            var confirmPassword = document.getElementById('confirmPassword').value;
            if (password !== confirmPassword) {
                document.getElementById('confirmPasswordError').innerHTML = "Passwords do not match.";
            } else {
                document.getElementById('confirmPasswordError').innerHTML = '';
            }
        }
        
        function validateForm() {
            var emailError = document.getElementById('emailError').innerHTML;
            var contactnoError = document.getElementById('contactnoError').innerHTML;
            var passwordError = document.getElementById('passwordError').innerHTML;

            return !emailError && !contactnoError && !passwordError; // Only submit if no errors
        }
    </script>
</head>
<body>
<header class="main-header">
    <div class="logo">
        <h1>The Art Spectrum</h1>
    </div>
    <nav class="nav-links">
        <a href="login">Login</a>
        <a href="#">About</a>
        <a href="#">Contact</a>
    </nav>
</header>

<div class="registerWindow">
    <div class="registerHeader">Art Gallery Registration</div>
    <div class="registerContent">
        <form name="registrationForm" action="register" method="post" onsubmit="return validateForm();">
            <label for="name">Full Name:</label><br>
            <input type="text" id="name" name="name" required><br><br>
            
            <label for="email">Email:</label><br>
            <input type="email" id="email" name="email" required>
            <div id="emailError" class="error"></div>
            <br>

            <label for="password">Password:</label><br>
            <input type="password" id="password" name="password" pattern=".{8,}" required>
            <div id="passwordError" class="error"></div>
            <br>
            
            <label for="confirmPassword">Confirm Password:</label><br>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
            <div id="confirmPasswordError" class="error"></div>
            <br>
            
            <label for="gender">Gender:</label><br>
            <select id="gender" name="gender" required>
                <option value="" disabled selected>---Select---</option>
                <option value="male">Male</option>
                <option value="female">Female</option>
                <option value="prefernot_tosay">Prefer not to say</option>
            </select><br><br>

            <label for="contactno">Contact Number:</label><br>
            <input type="tel" id="contactno" name="contactno" required>
            <div id="contactnoError" class="error"></div>
            <br>
            
            <label for="address">Address:</label><br>
            <input type="text" id="address" name="address" required><br><br>

            <label for="dob">Date of Birth:</label><br>
            <input type="date" id="dob" name="dob" required><br><br>

            <label for="artPreference">Art Style Preference:</label><br>
            <select id="artPreference" name="artPreference" required>
                <option value="" disabled selected>---Select---</option>
                <option value="abstract">Abstract</option>
                <option value="realism">Realism</option>
                <option value="impressionism">Impressionism</option>
                <option value="modern">Modern</option>
                <option value="contemporary">Contemporary</option>
            </select><br><br>

            <label for="newsletter">Newsletter Subscription:</label><br>
            <select id="newsletter" name="newsletter" required>
                <option value="" disabled selected>---Select---</option>
                <option value="yes">Yes</option>
                <option value="no">No</option>
            </select><br><br>

            <input type="submit" value="Register"><br><br>

            <div style="text-align: center;">
                <span class="loginPrompt" style="font-size: 16px; color: #333;">Already have an account? </span>
                <a href="login" style="text-decoration: none; color: blue; font-size: 16px;">
                    <label style="cursor: pointer;">Login</label>
                </a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
