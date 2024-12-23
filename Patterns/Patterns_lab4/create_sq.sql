CREATE TABLE student ( 
    id SERIAL PRIMARY KEY, 
    surname VARCHAR(100) NOT NULL, 
    name VARCHAR(100) NOT NULL,  
    phone VARCHAR(20), 
    telegram VARCHAR(50), 
    mail VARCHAR(100), 
    git VARCHAR(100) 
);