SELECT 	u.numero_documento,
	r.nombre_rol
FROM usuarios u
INNER JOIN usuarios_roles ur on ur.usuario_id = u.id_user
INNER JOIN roles r on r.id_rol = ur.rol_id

