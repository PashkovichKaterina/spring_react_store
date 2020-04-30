CREATE FUNCTION search_by_part_text (text VARCHAR(50)) 
   RETURNS TABLE (
	   id BIGINT,
	   name VARCHAR(50),
	   description VARCHAR(100),
	   price DECIMAL,
	   creation_date TIMESTAMP,
	   modification_date TIMESTAMP,
	   duration INTEGER
) 
AS $$
BEGIN
   RETURN QUERY SELECT certificates.id, certificates.name, certificates.description, certificates.price, certificates.creation_date, certificates.modification_date, certificates.duration
   FROM
      certificates
   WHERE
      LOWER(certificates.name) LIKE LOWER(CONCAT('%', text, '%')) OR LOWER(certificates.description) LIKE LOWER(CONCAT('%', text, '%'));
END; $$ 
LANGUAGE 'plpgsql';
