-- Testing if user already exists.
SELECT username FROM login_users WHERE user = ?

-- Adding a new user to database.
INSERT INTO login_users (username, password) VALUES (?, ?);

-- Testing if correct username and password provided.
SELECT username FROM login_users WHERE username = ? AND password = ?
