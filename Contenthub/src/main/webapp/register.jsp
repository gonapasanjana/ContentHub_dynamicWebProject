<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <style>
        body {
            background: #f3f4f6;
            font-family: 'Arial', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .register-container {
            background: #fff;
            padding: 40px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            width: 300px;
            text-align: center;
        }

        h2 {
            margin-bottom: 20px;
            color: #1d4ed8;
        }

        input[type="text"], input[type="password"], input[type="email"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            border: none;
            border-radius: 5px;
            background: #1d4ed8;
            color: #fff;
            font-size: 1rem;
            cursor: pointer;
            transition: background 0.3s ease;
        }

        input[type="submit"]:hover {
            background: #1e40af;
        }

        .error-message {
            color: #d9534f;
            display: none;
            margin-top: 10px;
        }
        
         .login-link {
            margin-top: 10px;
        }

        .login-link a {
            color: #1d4ed8;
            text-decoration: none;
        }

        .login-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="register-container">
        <h2>Register</h2>
        <form id="registerForm" action="register" method="post">
            <input type="text" name="username" placeholder="Username" required><br>
            <input type="email" name="email" placeholder="Email" required><br>
            <input type="password" id="password" name="password" placeholder="Password" required><br>
            <input type="password" id="confirmPassword" name="confirm_password" placeholder="Confirm Password" required><br>
            <input type="submit" value="Register">
            <div class="login-link">
            Not a member? <a href="login.jsp">Login</a>
        </div>
        </form>
        <div id="registrationFailedMessage" class="error-message">Registration failed. Please try again.</div>
        <div id="passwordMismatchMessage" class="error-message">Passwords do not match. Please try again.</div>
    </div>
    <script>
        document.getElementById('registerForm').addEventListener('submit', function(event) {
            var password = document.getElementById('password').value;
            var confirmPassword = document.getElementById('confirmPassword').value;
            if (password !== confirmPassword) {
                event.preventDefault();
                document.getElementById('passwordMismatchMessage').style.display = 'block';
            }
        });

        // JavaScript code to conditionally show the registration failed message
        // Check if the "error" parameter is present in the URL query string
        const urlParams = new URLSearchParams(window.location.search);
        const errorParam = urlParams.get('error');
        if (errorParam !== null) {
            // Show the registration failed message if the "error" parameter is present
            document.getElementById('registrationFailedMessage').style.display = 'block';
        }
    </script>
</body>
</html>
