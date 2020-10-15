CREATE TABLE destinations(
    destination_id SERIAL PRIMARY KEY,
    destination_uuid varchar (50),
    user_uuid varchar (50),
    location_from varchar (100),
    location_to varchar (100)
);