<?php
class DB_CONNECT{
	function __construct() {
        $this->connect();
    }
    function __destruct() {
        $this->close();
    }
 
    function connect() {
        require_once __DIR__ . '/db_config.php';
        $con = mysql_connect(DB_SERVER, DB_USER, DB_PASSWORD) ;
        $db = mysql_select_db(DB_DATABASE);
        return $con;
    }
    function close() {
        mysql_close();
    }
}
?>