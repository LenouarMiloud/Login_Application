<?php
	require_once __DIR__ . '/db_connect.php';
	$response = array();
	$db = new DB_CONNECT();
 
	 $username = $_GET["username"];  
	 $password =  $_GET["password"];

	 if ($username != null && $password != null) {
   	mysql_query("SET NAMES utf8");

			$result = mysql_query("SELECT * 
 			   					   FROM `users` 
 			   					   WHERE username = '$username' and password = '$password' ");

		if(mysql_num_rows($result)>0){
		$response["users"]=array();
		while($row = mysql_fetch_array($result)){
			$user = array();
			$user["nom"]=$row["nom"];
			$user["prenom"]=$row["prenom"];
			array_push($response["users"], $user);
		}
			$response['value']="success";
				header('Content-type:application/json');
				echo json_encode($response, JSON_UNESCAPED_UNICODE);
		}else{
			$response['value']="404";
			echo json_encode($response, JSON_UNESCAPED_UNICODE);
		}
	} else{
			$response['value']="faild";
			echo json_encode($response, JSON_UNESCAPED_UNICODE);
	}	



?>	 