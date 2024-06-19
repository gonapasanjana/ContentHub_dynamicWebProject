<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Content Hub</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Arial', sans-serif;
        }

        body {
            background: #f3f4f6;
            color: #333;
        }

        header {
            background: #fff;
            padding: 20px 40px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            position: relative;
        }

        .container {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            font-size: 1.5rem;
            font-weight: bold;
            color: #1d4ed8;
        }

        .auth-buttons {
            display: flex;
            gap: 10px;
        }

        .auth-buttons a {
            text-decoration: none;
        }

        button {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            font-size: 1rem;
            cursor: pointer;
            transition: background 0.3s ease, color 0.3s ease;
        }

        .login-btn {
            background: #1d4ed8;
            color: #fff;
        }

        .login-btn:hover {
            background: #1e40af;
        }

        .register-btn {
            background: #fbbf24;
            color: #fff;
        }

        .register-btn:hover {
            background: #f59e0b;
        }

        main {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 80vh;
            text-align: center;
        }

        .hero {
            max-width: 600px;
        }

        .hero h1 {
            font-size: 3rem;
            color: #1d4ed8;
        }

        .hero p {
            font-size: 1.2rem;
            color: #4b5563;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <header>
        <div class="container">
            <div class="logo">Content Hub</div>
            <div class="auth-buttons">
                <a href="login.jsp">
                    <button class="login-btn">Login</button>
                </a>
                <a href="register.jsp">
                    <button class="register-btn">Register</button>
                </a>
            </div>
        </div>
    </header>
    <main>
        <div class="hero">
            <h1>Welcome to Content Hub</h1>
            <p> Platform for creating and sharing engaging text-based posts.<br> Register today to start sharing your thoughts with the world.<br> Already a member? Log in to manage your posts and explore content created by others. <br>Whether you're here to write, read, or interact, MyContent Hub offers a seamless experience for content creators and consumers alike.</p>
        </div>
    </main>
</body>
</html>
