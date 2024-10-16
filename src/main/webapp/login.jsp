<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <link rel="stylesheet" href="css/login.css"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
    <header class="main-header">
        <div class="logo">
            <h1>The Art Spectrum</h1>
        </div>
        <nav class="nav-links">
            <a href="/">Home</a>
            <a href="aboutus">About</a>
            <a href="#">Contact</a>
        </nav>
    </header>

    <div class="background-image"></div> <!-- Background Image Section -->

    <div class="illustration-left">
        <img src="https://cdn-icons-png.flaticon.com/512/8761/8761422.png" alt="Cartoon illustration left">
    </div>

    <div class="illustration-right">
        <img src="https://cdn-icons-png.flaticon.com/512/8761/8761414.png" alt="Cartoon illustration right">
    </div>


    <div class="container">
        <h2>Login</h2>
        <form action="login" method="post">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="text" id="email" name="email" placeholder="Enter your email" required>
            </div>

            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" placeholder="Enter your password" required>
            </div>

            <button type="submit">Login</button>
            
            <div class="signup-link">
                <p>Don't have an account? <a href="register">Create an account</a></p>
            </div>
        </form>
    </div>
</body>
</html>
